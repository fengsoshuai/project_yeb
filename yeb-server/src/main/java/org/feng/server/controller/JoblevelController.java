package org.feng.server.controller;


import io.swagger.annotations.ApiOperation;
import org.feng.server.config.pattern.TemplateMethodController;
import org.feng.server.entity.Joblevel;
import org.feng.server.entity.ResponseBean;
import org.feng.server.service.IJoblevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
public class JoblevelController extends TemplateMethodController<Joblevel> {

    @Autowired
    private IJoblevelService joblevelService;

    @GetMapping("/")
    @ApiOperation("获取所有职称信息")
    public ResponseBean getAllJoblevels(){
        return super.getAllWithResponse();
    }

    @PostMapping("/")
    @ApiOperation("添加职称信息")
    public ResponseBean addJoblevel(@RequestBody Joblevel joblevel){
        return super.add(joblevel);
    }

    @PutMapping("/")
    @ApiOperation("更新职称信息")
    public ResponseBean updateJoblevel(@RequestBody Joblevel joblevel){
        return super.update(joblevel);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除职称信息")
    public ResponseBean deleteJoblevel(@PathVariable Integer id){
        return super.delete(id);
    }

    @ApiOperation("批量删除职称信息")
    @DeleteMapping
    public ResponseBean deleteJoblevelsByIds(Integer[] ids){
        return super.delete(ids);
    }

    @Override
    protected void initService() {
        if(super.getService() == null){
            super.setService(joblevelService);
        }
    }
}
