package com.focus.service.house;

import com.focus.service.ServiceMultiResult;
import com.focus.web.dto.HouseDTO;
import com.focus.web.form.RentSearch;

/**
 * @Description：
 * @Author: shadow
 * @Date: create in 17:01 2019/1/13
 */
public interface ISearchService {
    /**
     * 查询房源列表
     * @param rentSearch
     * @return
     */
    ServiceMultiResult<HouseDTO> query(RentSearch rentSearch);
}
