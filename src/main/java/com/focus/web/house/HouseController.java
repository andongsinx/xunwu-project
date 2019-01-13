package com.focus.web.house;

import com.focus.base.ApiResponse;
import com.focus.base.RentValueBlock;
import com.focus.service.ServiceMultiResult;
import com.focus.service.ServiceResult;
import com.focus.service.house.IAddressService;
import com.focus.service.house.IHouseService;
import com.focus.service.house.ISearchService;
import com.focus.web.dto.HouseDTO;
import com.focus.web.dto.SubwayDTO;
import com.focus.web.dto.SubwayStationDTO;
import com.focus.web.dto.SupportAddressDTO;
import com.focus.web.form.RentSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Description：
 * @Author: shadow
 * @Date: create in 12:50 2019/1/6
 */
@Controller
public class HouseController {

    @Autowired
    private IAddressService addressService;

    @Autowired
    private ISearchService searchService;

    @Autowired
    private IHouseService houseService;


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
     *
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
     *
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


    /**
     * 租房首页
     *
     * @param rentSearch
     * @param model
     * @param session
     * @param redirectAttributes
     * @return
     */
    @GetMapping("rent/house")
    public String rentHomePage(@ModelAttribute RentSearch rentSearch, Model model, HttpSession session,
                               RedirectAttributes redirectAttributes) {

        if (StringUtils.isEmpty(rentSearch.getCityEnName())) {
            String cityEnName = (String) session.getAttribute("cityEnName");
            if (StringUtils.isEmpty(cityEnName)) {
                redirectAttributes.addAttribute("msg", "must choose city!");
                return "redirect:/index";
            } else {
                rentSearch.setCityEnName(cityEnName);
            }

        } else {
            session.setAttribute("cityEnName", rentSearch.getCityEnName());
        }

        ServiceResult<SupportAddressDTO> city = addressService.findCity(rentSearch.getCityEnName());
        if (!city.isSuccess()) {
            redirectAttributes.addAttribute("msg", "must choose city");
            return "redirect:/index";
        }

        model.addAttribute("currentCity",city.getResult());
        ServiceMultiResult<SupportAddressDTO> addressResult = addressService.findAllRegionsByCityName(rentSearch.getCityEnName());
        if (addressResult.getResult() == null || addressResult.getTotal() < 1) {
            redirectAttributes.addAttribute("msg", "must choose city");
            return "redirect:/index";
        }
        ServiceMultiResult<HouseDTO> serviceMultiResult = houseService.query(rentSearch);

        model.addAttribute("total", serviceMultiResult.getTotal());
        model.addAttribute("houses", serviceMultiResult.getResult());
        if (StringUtils.isEmpty(rentSearch.getRegionEnName())) {
            rentSearch.setRegionEnName("*");
        }

        model.addAttribute("regions", addressResult.getResult());
        model.addAttribute("searchBody", rentSearch);
        model.addAttribute("priceBlock", RentValueBlock.PRICE_BLOCK);
        model.addAttribute("areaBlock", RentValueBlock.AREA_BLOCK);



        return "rent-list";
    }


}
