package com.focus.service.house;

import com.focus.entity.SupportAddress;
import com.focus.service.ServiceMultiResult;
import com.focus.service.ServiceResult;
import com.focus.web.dto.SubwayDTO;
import com.focus.web.dto.SubwayStationDTO;
import com.focus.web.dto.SupportAddressDTO;

import java.util.List;
import java.util.Map;

/**
 * @Description：
 * @Author: shadow
 * @Date: create in 13:03 2019/1/6
 */
public interface IAddressService {
    /**
     * 查询所有城市
     * @return
     */
    ServiceMultiResult<SupportAddressDTO> findAllCities();

    /**
     * 更具城市名查询区域
     * @param cityName
     * @return
     */
    ServiceMultiResult<SupportAddressDTO> findAllRegionsByCityName(String cityName);

    /**
     * 更具城市获取地铁线路
     * @param cityEnName
     * @return
     */
    List<SubwayDTO> findAllSubwayByCity(String cityEnName);

    /**
     * 根据地铁线路查询地铁站
     * @param subwayId
     * @return
     */
    List<SubwayStationDTO> findAllStationBySubway(Long subwayId);

    /**
     * 更具城市和区域查询
     * @param cityEnName
     * @param regionEnName
     * @return
     */
    Map<SupportAddress.Level, SupportAddressDTO> findCityAndRegion(String cityEnName, String regionEnName);

    /**
     * 更具subway id查询
     * @param subwayLineId
     * @return
     */
    ServiceResult<SubwayDTO> findSubway(Long subwayLineId);

    /**
     * 根据subwaystation id查询
     * @param subwayStationId
     * @return
     */
    ServiceResult<SubwayStationDTO> findSubwayStation(Long subwayStationId);
}
