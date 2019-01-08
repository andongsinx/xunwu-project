package com.focus.service.house;

import com.focus.service.ServiceResult;
import com.focus.web.dto.HouseDTO;
import com.focus.web.form.HouseForm;

/**
 * @Description：
 * @Author: shadow
 * @Date: create in 12:52 2019/1/6
 */
public interface IHouseService {

    ServiceResult<HouseDTO> save(HouseForm houseForm);

}
