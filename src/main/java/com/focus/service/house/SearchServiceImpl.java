package com.focus.service.house;

import com.focus.repository.HouseDetailRepository;
import com.focus.repository.HouseRepository;
import com.focus.repository.HouseTagRepository;
import com.focus.repository.SupportAddressRepository;
import com.focus.service.ServiceMultiResult;
import com.focus.web.dto.HouseDTO;
import com.focus.web.form.RentSearch;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description：
 * @Author: shadow
 * @Date: create in 17:02 2019/1/13
 */
@Service
public class SearchServiceImpl implements ISearchService {
    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private HouseDetailRepository houseDetailRepository;

    @Autowired
    private HouseTagRepository tagRepository;

    @Autowired
    private SupportAddressRepository supportAddressRepository;

    @Autowired
    private IAddressService addressService;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ServiceMultiResult<HouseDTO> query(RentSearch rentSearch) {







        return null;
    }
}
