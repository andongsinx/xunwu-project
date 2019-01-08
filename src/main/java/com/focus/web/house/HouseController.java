package com.focus.web.house;

import com.focus.base.ApiResponse;
import com.focus.service.ServiceMultiResult;
import com.focus.service.house.IAddressService;
import com.focus.web.dto.SubwayDTO;
import com.focus.web.dto.SubwayStationDTO;
import com.focus.web.dto.SupportAddressDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Description：
 * @Author: shadow
 * @Date: create in 12:50 2019/1/6
 */
@Controller
public class HouseController {

    @Autowired
    IAddressService addressService;

    /**
     * 获取城市支持接口
     *
     * @return
     */
    @GetMapping("address/support/cities")
    @ResponseBody
    public ApiResponse getSupportCities() {
        ServiceMultiResult<SupportAddressDTO> result = addressService.findAllCities();

        if (result.getResultSize() == 0) {
            return ApiResponse.ofStatus(ApiResponse.Status.NOT_FOUND);
        }
        return ApiResponse.ofSuccess(result);
    }


    /**
     * 获取城市支持区域列表
     *
     * @return
     */
    @GetMapping("address/support/regions")
    @ResponseBody
    public ApiResponse getSupportRegions(@RequestParam("city_name") String cityName) {
        ServiceMultiResult<SupportAddressDTO> result = addressService.findAllRegionsByCityName(cityName);
        if (result.getResultSize() == 0) {
            return ApiResponse.ofStatus(ApiResponse.Status.NOT_FOUND);
        }
        return ApiResponse.ofSuccess(result);
    }


    /**
     * 获取具体城市所支持的地铁线路
     * @param cityEnName
     * @return
     */
    @GetMapping("address/support/subway/line")
    @ResponseBody
    public ApiResponse getSupportSubwayLine(@RequestParam(name = "city_name") String cityEnName) {
        List<SubwayDTO> subways = addressService.findAllSubwayByCity(cityEnName);
        if (subways.isEmpty()) {
            return ApiResponse.ofStatus(ApiResponse.Status.NOT_FOUND);
        }

        return ApiResponse.ofSuccess(subways);
    }

    /**
     * 获取对应地铁线路所支持的地铁站点
     * @param subwayId
     * @return
     */
    @GetMapping("address/support/subway/station")
    @ResponseBody
    public ApiResponse getSupportSubwayStation(@RequestParam(name = "subway_id") Long subwayId) {
        List<SubwayStationDTO> stationDTOS = addressService.findAllStationBySubway(subwayId);
        if (stationDTOS.isEmpty()) {
            return ApiResponse.ofStatus(ApiResponse.Status.NOT_FOUND);
        }

        return ApiResponse.ofSuccess(stationDTOS);
    }

}
