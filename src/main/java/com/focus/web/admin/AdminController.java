package com.focus.web.admin;

import com.focus.base.ApiDataTableResponse;
import com.focus.base.ApiResponse;
import com.focus.service.ServiceMultiResult;
import com.focus.web.form.DataTableSearch;
import com.focus.entity.SupportAddress;
import com.focus.service.ServiceResult;
import com.focus.service.file.IFileService;
import com.focus.service.house.IAddressService;
import com.focus.service.house.IHouseService;
import com.focus.web.dto.*;
import com.focus.web.form.HouseForm;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @Description：admin 控制器
 * @Author：shadow
 * @Date：ceate in 16:29 2018/12/31
 */
@Controller
public class AdminController {


    @Autowired
    private IFileService fileService;

    @Autowired
    private IAddressService addressService;

    @Autowired
    private IHouseService houseService;


    @Autowired
    private Gson gson;

    /**
     * 后台管理中心
     *
     * @return
     */
    @GetMapping("/admin/center")
    public String adminCenterPage() {
        return "admin/center";
    }

    /**
     * 欢迎页
     *
     * @return
     */
    @GetMapping("/admin/welcome")
    public String welcomePage() {
        return "admin/welcome";
    }

    /**
     * 管理员登录页
     *
     * @return
     */
    @GetMapping("/admin/login")
    public String adminLoginPage() {
        return "admin/login";
    }


    /**
     * 新增房源页面
     *
     * @return
     */
    @GetMapping("/admin/add/house")
    public String addHousePage() {
        return "admin/house-add";
    }


    /**
     * 图片上传
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/admin/upload/photo", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ApiResponse uploadPic(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ApiResponse.ofStatus(ApiResponse.Status.NOT_VALID_PARAM);
        }
        String filename = file.getOriginalFilename();
        InputStream is = null;
        Response response = null;
        try {
            is = file.getInputStream();
            response = fileService.uploadFile(is);
            if (response.isOK()) {
                QiNiuPutRet ret = gson.fromJson(response.bodyString(), QiNiuPutRet.class);
                return ApiResponse.ofSuccess(ret);
            }
            return ApiResponse.ofMessage(response.statusCode, response.getInfo());

        } catch (QiniuException e) {
            response = e.response;
            try {
                return ApiResponse.ofMessage(response.statusCode, response.bodyString());
            } catch (QiniuException e1) {
                e1.printStackTrace();
                return ApiResponse.ofStatus(ApiResponse.Status.INTERNAL_SERVER_ERROR);
            }
        } catch (IOException e) {
            return ApiResponse.ofStatus(ApiResponse.Status.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 新增房源接口
     *
     * @return
     */
    @PostMapping("/admin/add/house")
    @ResponseBody
    public ApiResponse addHouse(@Valid @ModelAttribute("form-house-add") HouseForm houseForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ApiResponse(HttpStatus.BAD_REQUEST.value(), bindingResult.getAllErrors().get(0).getDefaultMessage(), null);
        }
        if (houseForm.getPhotos() == null || houseForm.getCover() == null) {
            return ApiResponse.ofMessage(HttpStatus.BAD_REQUEST.value(), "必须上传图片！");
        }

        Map<SupportAddress.Level, SupportAddressDTO> addressMap = addressService.findCityAndRegion(houseForm.getCityEnName(), houseForm.getRegionEnName());
        if (addressMap.keySet().size() != 2) {
            return ApiResponse.ofStatus(ApiResponse.Status.NOT_VALID_PARAM);
        }

        ServiceResult<HouseDTO> result = houseService.save(houseForm);
        if (result.isSuccess()) {
            return ApiResponse.ofSuccess(result.getResult());
        }

        return ApiResponse.ofStatus(ApiResponse.Status.NOT_VALID_PARAM);

    }


    /**
     * 房源列表页
     *
     * @return
     */
    @GetMapping("admin/house/list")
    public String houseListPage() {
        return "admin/house-list";
    }


    /**
     * 获取房源列表接口
     *
     * @return
     */
    @PostMapping("admin/houses")
    @ResponseBody
    public ApiDataTableResponse houses(@ModelAttribute("searchBody") DataTableSearch searchBody) {
        ServiceMultiResult<HouseDTO> result = houseService.queryAdmin(searchBody);
        ApiDataTableResponse response = new ApiDataTableResponse(ApiResponse.Status.SUCCESS);
        response.setData(result.getResult());
        response.setDraw(searchBody.getDraw());
        response.setRecordsTotal(result.getTotal());
        response.setRecordsFiltered(result.getTotal());
        return response;
    }


}
