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
     *
     * @param houseForm
     * @return
     */
    ServiceResult<HouseDTO> save(HouseForm houseForm);

    /**
     * 查询房源列表
     *
     * @param searchBody
     * @return
     */
    ServiceMultiResult<HouseDTO> queryAdmin(DataTableSearch searchBody);

    /**
     * 通过id查询详细信息
     *
     * @param id
     * @return
     */
    ServiceResult<HouseDTO> findCompleteOne(Long id);

    /**
     * 更新房源
     *
     * @param houseForm
     * @return
     */
    ServiceResult<HouseDTO> update(HouseForm houseForm);

    /**
     * 删除图片
     *
     * @param id
     * @return
     */
    ServiceResult removeHousePhoto(Long id);

    /**
     * 更新封面
     *
     * @param coverId
     * @param targetId
     * @return
     */
    ServiceResult updateCover(Long coverId, long targetId);

    /**
     * 添加tag
     * @param houseId
     * @param tag
     * @return
     */
    ServiceResult addHouseTag(Long houseId, String tag);

    /**
     * 移除标签
     * @param houseId
     * @param tag
     * @return
     */
    ServiceResult removeHouseTag(Long houseId, String  tag);
}
