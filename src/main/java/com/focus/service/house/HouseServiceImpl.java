package com.focus.service.house;

import com.focus.base.HouseStatus;
import com.focus.base.UserLoginUtil;
import com.focus.entity.*;
import com.focus.repository.*;
import com.focus.service.ServiceMultiResult;
import com.focus.service.ServiceResult;
import com.focus.web.dto.HouseDTO;
import com.focus.web.dto.HouseDetailDTO;
import com.focus.web.dto.HousePictureDTO;
import com.focus.web.form.DataTableSearch;
import com.focus.web.form.HouseForm;
import com.focus.web.form.PhotoForm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Description：
 * @Author: shadow
 * @Date: create in 19:30 2019/1/7
 */
@Service
public class HouseServiceImpl implements IHouseService {

    @Autowired
    private SubwayRepository subwayRepository;

    @Autowired
    private SubwayStationRepository subwayStationRepository;

    @Autowired
    private HouseTagRepository houseTagRepository;

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private HouseDetailRepository houseDetailRepository;


    @Autowired
    private HousePictureRepository housePictureRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${qiniu.cdn.prefix}")
    private String cdnPrefix;


    @Override
    @Transactional
    public ServiceResult<HouseDTO> save(HouseForm houseForm) {
        HouseDetail houseDetail = new HouseDetail();
        ServiceResult<HouseDTO> subwayValidation = wrapperHouseDetail(houseDetail, houseForm);
        if (subwayValidation != null) {
            return subwayValidation;
        }
        House house = new House();
        modelMapper.map(houseForm, house);
        Date now = new Date();
        house.setCreateTime(now);
        house.setLastUpdateTime(now);
        house.setAdminId(UserLoginUtil.getLoginUserId());
        house = houseRepository.save(house);

        houseDetail.setHouseId(house.getId());
        houseDetail = houseDetailRepository.save(houseDetail);
        List<HousePicture> pictureList = generatePictures(houseForm, house.getId());
        pictureList = housePictureRepository.saveAll(pictureList);

        HouseDTO houseDTO = modelMapper.map(house, HouseDTO.class);
        HouseDetailDTO houseDetailDTO = modelMapper.map(houseDetail, HouseDetailDTO.class);
        List<HousePictureDTO> pictureDTOS = new ArrayList<>();
        pictureList.forEach(housePicture -> pictureDTOS.add(modelMapper.map(housePicture, HousePictureDTO.class)));

        houseDTO.setPictures(pictureDTOS);
        houseDTO.setHouseDetail(houseDetailDTO);

        houseDTO.setCover(this.cdnPrefix + houseDTO.getCover());
        int a = 1/0;

        List<String> tags = houseForm.getTags();
        List<HouseTag> houseTags = new ArrayList<>();
        if (tags != null && tags.size() > 0) {
            for (String tag : tags) {
                houseTags.add(new HouseTag(house.getId(), tag));
            }
            houseTagRepository.saveAll(houseTags);
            houseDTO.setTags(tags);
        }
        return new ServiceResult<HouseDTO>(true, null, houseDTO);
    }

    @Override
    public ServiceMultiResult<HouseDTO> queryAdmin(DataTableSearch searchBody) {
        List<HouseDTO> houseDTOS = new ArrayList<>();
        Sort sort = new Sort(Sort.Direction.fromString(searchBody.getDirection()),searchBody.getOrderBy());
        int page= searchBody.getStart() / searchBody.getLength();

        Pageable pageable = PageRequest.of(page,searchBody.getLength(),sort);
        Specification<House> specification = (root, query, cb) -> {
            Predicate predicate = cb.equal(root.get("adminId"), UserLoginUtil.getLoginUserId());
            predicate = cb.and(predicate, cb.notEqual(root.get("status"), HouseStatus.DELETED.getValue()));

            if (searchBody.getCity() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("cityEnName"), searchBody.getCity()));
            }

            if (searchBody.getStatus() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("status"), searchBody.getStatus()));
            }

            if (searchBody.getCreateTimeMin() != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("createTime"), searchBody.getCreateTimeMin()));
            }

            if (searchBody.getCreateTimeMax() != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("createTime"), searchBody.getCreateTimeMax()));
            }

            if (searchBody.getTitle() != null) {
                predicate = cb.and(predicate, cb.like(root.get("title"), "%" + searchBody.getTitle() + "%"));
            }

            return predicate;
        };

        Page<House> houses = houseRepository.findAll(specification, pageable);
        houses.forEach(house -> {
            HouseDTO houseDTO = modelMapper.map(house, HouseDTO.class);
            houseDTO.setCover(this.cdnPrefix + house.getCover());
            houseDTOS.add(houseDTO);
        });

        return new ServiceMultiResult<>(houses.getTotalElements(), houseDTOS);

    }

    /**
     * 图片对象列表生成信息填充
     *
     * @param houseForm
     * @param houseId
     * @return
     */
    private List<HousePicture> generatePictures(HouseForm houseForm, Long houseId) {
        List<HousePicture> housePictures = new ArrayList<>();
        List<PhotoForm> photos = houseForm.getPhotos();
        if (photos == null || photos.size() == 0) {
            return housePictures;
        }

        photos.forEach(photo -> {
            HousePicture picture = modelMapper.map(photo, HousePicture.class);
            picture.setHouseId(houseId);
            picture.setCdnPrefix(cdnPrefix);
            housePictures.add(picture);
        });
        return housePictures;
    }


    /**
     * 包装house详细信息
     *
     * @return
     */
    private ServiceResult<HouseDTO> wrapperHouseDetail(HouseDetail houseDetail, HouseForm houseForm) {
        Optional<Subway> optionalSubway = subwayRepository.findById(houseForm.getSubwayLineId());
        Subway subway = optionalSubway.get();
        if (subway == null) {
            return new ServiceResult<>(false, "Not Valid Subway line!");
        }

        Optional<SubwayStation> optionalSubwayStation = subwayStationRepository.findById(houseForm.getSubwayStationId());
        SubwayStation subwayStation = optionalSubwayStation.get();
        if (subwayStation == null || subway.getId() != subwayStation.getSubwayId()) {
            return new ServiceResult<>(false, "Not valid Subway Station");
        }

        houseDetail.setSubwayLineId(subway.getId());
        houseDetail.setSubwayLineName(subway.getName());

        houseDetail.setSubwayStationId(subwayStation.getId());
        houseDetail.setSubwayStationName(subwayStation.getName());

        houseDetail.setDescription(houseForm.getDescription());
        houseDetail.setDetailAddress(houseForm.getDetailAddress());
        houseDetail.setLayoutDesc(houseForm.getLayoutDesc());
        houseDetail.setRentWay(houseForm.getRentWay());
        houseDetail.setRoundService(houseForm.getRoundService());
        houseDetail.setTraffic(houseForm.getTraffic());
        return null;
    }

}