package com.anji.mirror.auth.controller;

import com.anji.mirror.auth.domain.vo.DictVO;
import com.anji.mirror.auth.service.DictService;
import com.anji.mirror.common.annotation.Log;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.anji.mirror.auth.domain.po.DictPO;
import com.anji.mirror.common.enums.DictNameEnum;
import com.anji.mirror.common.model.RequestModel;
import com.anji.mirror.common.model.ResponseModel;
import com.anji.mirror.common.security.HasPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 数据字典表 前端控制器
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-08-20
 */
@RestController
@RequestMapping("/dict")
public class DictController {

    @Autowired
    private DictService dictService;

    @PostMapping("/create")
    @HasPermission("dictManage:add")
    @Log(pageTitle = "创建字典")
    public ResponseModel create(@RequestBody RequestModel<DictVO> requestModel) {
        return dictService.create(requestModel);
    }

    @PostMapping("/updateById")
    @HasPermission("dictManage:edit")
    @Log(pageTitle = "根据id修改字典")
    public ResponseModel updateById(@RequestBody RequestModel<DictVO> requestModel) {
        return dictService.updateById(requestModel);
    }

    @PostMapping("/deleteById")
    @HasPermission("dictManage:delete")
    @Log(pageTitle = "根据id删除字典")
    public ResponseModel deleteById(@RequestBody RequestModel<DictVO> requestModel) {
        return dictService.deleteById(requestModel);
    }

    @PostMapping("/queryById")
    @HasPermission("dictManage:find")
    @Log(pageTitle = "根据id查询一条字典记录")
    public ResponseModel queryById(@RequestBody RequestModel<DictVO> requestModel) {
        return dictService.queryById(requestModel);
    }

    @PostMapping("/queryByPage")
    @HasPermission("dictManage:find")
    @Log(pageTitle = "根据参数分页查询字典列表")
    public ResponseModel queryByPage(@RequestBody RequestModel<DictVO> requestModel) {
        return dictService.queryByPage(requestModel);
    }

    @PostMapping("/queryForCodeSelect")
    @Log(pageTitle = "前端select组件接口")
    public ResponseModel queryForCodeSelect(@RequestBody RequestModel<DictVO> requestModel){
        return dictService.queryForCodeSelect(requestModel);
    }

    @PostMapping("/listDictByDictName")
    @Log(pageTitle = "根据字典名称查询列表")
    public ResponseModel listDictByDictName(@RequestParam("dictName") DictNameEnum dictName) {
        if (dictName == null) {
            return ResponseModel.success();
        }
        List<DictPO> dicts = dictService.list(Wrappers.<DictPO>lambdaQuery().eq(DictPO::getDictName, dictName.name()));
        Map<String, String> result = dicts.stream().collect(Collectors.toMap(key -> key.getItemValue(), value -> value.getItemDesc()));
        return ResponseModel.success(result);
    }

}

