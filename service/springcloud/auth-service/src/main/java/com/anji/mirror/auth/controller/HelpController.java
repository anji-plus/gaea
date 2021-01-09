package com.anji.mirror.auth.controller;

import com.anji.mirror.auth.domain.vo.HelpVO;
import com.anji.mirror.auth.service.HelpService;
import com.anji.mirror.common.annotation.Log;
import com.anji.mirror.common.model.RequestModel;
import com.anji.mirror.common.model.ResponseModel;
import com.anji.mirror.common.security.HasPermission;
import com.haitong.nla.auth.domain.vo.SerarchConditionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 帮助中心表 前端控制器
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-11-23
 */
@RestController
@RequestMapping("/help")
public class HelpController {

    @Autowired
    private HelpService helpService;

    @PostMapping("/create")
    @HasPermission("helpManage:add")
    @Log(pageTitle = "创建帮助中心记录")
    public ResponseModel create(@RequestBody RequestModel<HelpVO> requestModel) {
        return helpService.create(requestModel);
    }

    @PostMapping("/updateById")
    @HasPermission("helpManage:edit")
    @Log(pageTitle = "根据id修改帮助中心记录")
    public ResponseModel updateById(@RequestBody RequestModel<HelpVO> requestModel) {
        return helpService.updateById(requestModel);
    }

    @PostMapping("/deleteById")
    @HasPermission("helpManage:delete")
    @Log(pageTitle = "根据id删除帮助中心记录")
    public ResponseModel deleteById(@RequestBody RequestModel<HelpVO> requestModel) {
        return helpService.deleteById(requestModel);
    }

    @PostMapping("/queryById")
    @Log(pageTitle = "根据id查询一条帮助中心记录")
    public ResponseModel queryById(@RequestBody RequestModel<HelpVO> requestModel) {
        return helpService.queryById(requestModel);
    }

    @PostMapping("/queryByPage")
    @Log(pageTitle = "根据参数分页查询帮助中心记录列表")
    public ResponseModel queryByPage(@RequestBody RequestModel<HelpVO> requestModel) {
        return helpService.queryByPage(requestModel);
    }

    @PostMapping("/titleCheck")
    @Log(pageTitle = "校验标题")
    public ResponseModel titleCheck(@RequestBody RequestModel<HelpVO> requestModel) {
        return helpService.titleCheck(requestModel);
    }

    @PostMapping("/querytitleByCategory")
    @Log(pageTitle = "根据分类查询标题列表")
    public ResponseModel querytitleByCategory(@RequestBody RequestModel<HelpVO> requestModel) {
        return helpService.querytitleByCategory(requestModel);
    }

    @PostMapping("/searchKeyWord")
    @Log(pageTitle = "关键词搜索")
    public ResponseModel searchKeyWord(@RequestBody RequestModel<SerarchConditionVO> requestModel) {
        return helpService.searchKeyWord(requestModel);
    }


}

