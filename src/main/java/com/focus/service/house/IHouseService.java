package com.focus.service.house;

import com.focus.service.ServiceMultiResult;
import com.focus.service.ServiceResult;
import com.focus.web.dto.HouseDTO;
import com.focus.web.form.DataTableSearch;
import com.focus.web.form.HouseForm;

/**
 * @Description：
 * @Author: shadow
 * @Date: create in 12:52 2019/1/6
 */
public interface IHouseService {

    /**
     * 新增房源
     * @param houseForm
     * @return
     */
    ServiceResult<HouseDTO> save(HouseForm houseForm);

    /**
     * 查询房源列表
     * @param searchBody
     * @return
     */
    ServiceMultiResult<HouseDTO> queryAdmin(DataTableSearch searchBody);

    /**
     * 通过id查询详细信息
     * @param id
     * @return
     */
    ServiceResult<HouseDTO> findCompleteOne(Long id);
}
