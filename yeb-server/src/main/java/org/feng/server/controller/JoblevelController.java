package org.feng.server.controller;


import io.swagger.annotations.ApiOperation;
import org.feng.consts.Consts;
import org.feng.server.entity.Joblevel;
import org.feng.server.entity.ResponseBean;
import org.feng.server.service.IJoblevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  职称管理控制器
 * </p>
 *
 * @author FengJinSong
 * @since 2021-06-16
 */
@RestController
@RequestMapping("/system/basic/joblevel")
public class JoblevelController {

    @Autowired
    private IJoblevelService joblevelService;

    @GetMapping("/")
    @ApiOperation("获取所有职称信息")
    public List<Joblevel> getAllJoblevels(){
        return joblevelService.list();
    }

    @PostMapping("/")
    @ApiOperation("添加职称信息")
    public ResponseBean addJoblevel(@RequestBody Joblevel joblevel){
        if(joblevelService.save(joblevel)){
            return ResponseBean.success(Consts.ADD_SUCCESS);
        }
        return ResponseBean.error(Consts.ADD_FAILED);
    }

    @PutMapping("/")
    @ApiOperation("更新职称信息")
    public ResponseBean updateJoblevel(@RequestBody Joblevel joblevel){
        if(joblevelService.updateById(joblevel)){
            return ResponseBean.success("更新成功");
        }
        return ResponseBean.error("更新失败");
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除职称信息")
    public ResponseBean deleteJoblevel(@PathVariable Integer id){
        if(joblevelService.removeById(id)){
            return ResponseBean.success("删除成功");
        }
        return ResponseBean.error("删除失败");
    }

    @ApiOperation("批量删除职称信息")
    @DeleteMapping
    public ResponseBean deleteJoblevelsByIds(Integer[] ids){
        if(joblevelService.removeByIds(Arrays.asList(ids))){
            return ResponseBean.success("删除成功");
        }
        return ResponseBean.error("删除失败");
    }
}
