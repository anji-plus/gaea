package com.anji.mirror.auth.controller;

import com.anji.mirror.auth.domain.vo.SettingVO;
import com.anji.mirror.auth.service.SettingService;
import com.anji.mirror.common.annotation.Log;
import com.anji.mirror.common.model.RequestModel;
import com.anji.mirror.common.model.ResponseModel;
import com.anji.mirror.common.security.HasPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-10-10
 */
@RestController
@RequestMapping("/setting")
public class SettingController {

    @Autowired
    private SettingService settingService;

    @PostMapping("/create")
    @HasPermission("settingManage:add")
    @Log(pageTitle = "创建参数")
    public ResponseModel create(@RequestBody RequestModel<SettingVO> requestModel) {
        return settingService.create(requestModel);
    }

    @PostMapping("/updateById")
    @HasPermission("settingManage:edit")
    @Log(pageTitle = "根据id修改参数")
    public ResponseModel updateById(@RequestBody RequestModel<SettingVO> requestModel) {
        return settingService.updateById(requestModel);
    }

    @PostMapping("/deleteById")
    @HasPermission("settingManage:delete")
    @Log(pageTitle = "根据id删除参数")
    public ResponseModel deleteById(@RequestBody RequestModel<SettingVO> requestModel) {
        return settingService.deleteById(requestModel);
    }

    @PostMapping("/queryById")
    @HasPermission("settingManage:find")
    @Log(pageTitle = "根据id查询一条参数记录")
    public ResponseModel queryById(@RequestBody RequestModel<SettingVO> requestModel) {
        return settingService.queryById(requestModel);
    }

    @PostMapping("/queryByPage")
    @HasPermission("settingManage:find|bigScreenManage:find")
    @Log(pageTitle = "根据参数分页查询参数列表")
    public ResponseModel queryByPage(@RequestBody RequestModel<SettingVO> requestModel) {
        return settingService.queryByPage(requestModel);
    }

    @PostMapping("/switchEnableById")
    @HasPermission("settingManage:edit")
    @Log(pageTitle = "根据id切换状态")
    public ResponseModel switchEnableById(@RequestBody RequestModel<SettingVO> requestModel) {
        return settingService.switchEnableById(requestModel);
    }

    @PostMapping("/queryByName")
    @HasPermission("settingManage:edit")
    @Log(pageTitle = "根据名称查询一条参数记录")
    public ResponseModel queryByName(@RequestBody RequestModel<SettingVO> requestModel) {
        return settingService.queryByName(requestModel);
    }

    /*****************************下方跨服务调用*******************************************/
    @PostMapping("/queryBySettingName")
    @HasPermission("settingManage:find")
    @Log(pageTitle = "根据settingName查询一条记录")
    public SettingVO queryBySettingName(@RequestParam("settingName") String settingName) {
        return settingService.queryBySettingName(settingName);
    }

//    @PostMapping("/getIntegerBySettingName")
//    @HasPermission("settingManage:find")
//    public Integer getIntegerBySettingName(@RequestParam("settingName") String settingName) {
//        return settingService.getIntegerBySettingName(settingName);
//    }


}

