package com.focus.service.house;

import com.focus.entity.House;
import com.focus.entity.Subway;
import com.focus.entity.SubwayStation;
import com.focus.entity.SupportAddress;
import com.focus.repository.SubwayRepository;
import com.focus.repository.SubwayStationRepository;
import com.focus.repository.SupportAddressRepository;
import com.focus.service.ServiceMultiResult;
import com.focus.service.ServiceResult;
import com.focus.web.dto.SubwayDTO;
import com.focus.web.dto.SubwayStationDTO;
import com.focus.web.dto.SupportAddressDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @Description：
 * @Author: shadow
 * @Date: create in 13:03 2019/1/6
 */
@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private SupportAddressRepository supportAddressRepository;

    @Autowired
    private SubwayRepository subwayRepository;

    @Autowired
    private SubwayStationRepository subwayStationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ServiceMultiResult<SupportAddressDTO> findAllCities() {
        List<SupportAddress> addresses = supportAddressRepository.findAllByLevel(SupportAddress.Level.CITY.getValue());
        List<SupportAddressDTO> addressDTOS = new ArrayList<>();

        if (addresses != null && addresses.size() > 0) {
            addresses.forEach(address -> addressDTOS.add(modelMapper.map(address, SupportAddressDTO.class)));
        }

        return new ServiceMultiResult<>(addressDTOS.size(), addressDTOS);
    }

    @Override
    public ServiceMultiResult<SupportAddressDTO> findAllRegionsByCityName(String cityName) {
        if (StringUtils.isEmpty(cityName)) {
            return new ServiceMultiResult<>(0, null);
        }
        List<SupportAddress> supportAddresses = supportAddressRepository.findAllByLevelAndBelongTo(SupportAddress.Level.REGION.getValue(), cityName);
        List<SupportAddressDTO> regions = new ArrayList<>();
        if (supportAddresses != null && supportAddresses.size() > 0) {
            supportAddresses.forEach(address -> regions.add(modelMapper.map(address, SupportAddressDTO.class)));
        }
        return new ServiceMultiResult<>(regions.size(), regions);
    }

    @Override
    public List<SubwayDTO> findAllSubwayByCity(String cityEnName) {
        List<SubwayDTO> result = new ArrayList<>();
        List<Subway> subways = subwayRepository.findAllByCityEnName(cityEnName);
        if (subways == null || subways.size() == 0) {
            return result;
        }
        subways.forEach(subway -> result.add(modelMapper.map(subway, SubwayDTO.class)));
        return result;
    }

    @Override
    public List<SubwayStationDTO> findAllStationBySubway(Long subwayId) {
        List<SubwayStationDTO> result = new ArrayList<>();
        List<SubwayStation> subwayStations = subwayStationRepository.findAllBySubwayId(subwayId);
        if (subwayStations == null || subwayStations.size() == 0) {
            return result;
        }
        subwayStations.forEach(subwayStation -> result.add(modelMapper.map(subwayStation, SubwayStationDTO.class)));
        return result;
    }

    @Override
    public Map<SupportAddress.Level, SupportAddressDTO> findCityAndRegion(String cityEnName, String regionEnName) {
        Map<SupportAddress.Level, SupportAddressDTO> map = new HashMap<>();
        SupportAddress city = supportAddressRepository.findByEnNameAndLevel(cityEnName, SupportAddress.Level.CITY.getValue());
        SupportAddress region = supportAddressRepository.findByEnNameAndBelongTo(regionEnName, cityEnName);
        map.put(SupportAddress.Level.CITY, modelMapper.map(city, SupportAddressDTO.class));
        map.put(SupportAddress.Level.REGION, modelMapper.map(region, SupportAddressDTO.class));
        return map;
    }

    @Override
    public ServiceResult<SubwayDTO> findSubway(Long subwayLineId) {

        Optional<Subway> optional = subwayRepository.findById(subwayLineId);
        Subway subway = optional.get();
        if (subway == null) {
            return new ServiceResult<>(false);
        }
        SubwayDTO subwayDTO = modelMapper.map(subway, SubwayDTO.class);
        return new ServiceResult<>(true, null, subwayDTO);
    }

    @Override
    public ServiceResult<SubwayStationDTO> findSubwayStation(Long subwayStationId) {
        Optional<SubwayStation> optional = subwayStationRepository.findById(subwayStationId);
        SubwayStation subwayStation = optional.get();
        if (subwayStation == null) {
            return new ServiceResult<>(false);
        }
        SubwayStationDTO subwayStationDTO = modelMapper.map(subwayStation, SubwayStationDTO.class);
        return new ServiceResult<>(true, null, subwayStationDTO);
    }
}
