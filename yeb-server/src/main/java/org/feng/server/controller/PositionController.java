package org.feng.server.controller;


import io.swagger.annotations.ApiOperation;
import org.feng.server.config.pattern.TemplateMethodController;
import org.feng.server.entity.Position;
import org.feng.server.entity.ResponseBean;
import org.feng.server.service.IPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  职位管理
 * </p>
 *
 * @author FengJinSong
 * @since 2021-06-16
 */
@RestController
@RequestMapping("/system/basic/pos")
public class PositionController extends TemplateMethodController<Position> {

    @Autowired
    private IPositionService positionService;

    @GetMapping("/")
    @ApiOperation(value = "获取所有职位信息")
    public ResponseBean getAllPositions(){
        return super.getAllWithResponse();
    }


    @PostMapping("/")
    @ApiOperation(value = "添加职位信息")
    public ResponseBean addPosition(@RequestBody Position position){
        return super.add(position);
    }

    @PutMapping("/")
    @ApiOperation(value = "更新职位信息")
    public ResponseBean updatePosition(@RequestBody Position position){
        return super.update(position);
    }

    @ApiOperation(value = "删除职位信息")
    @DeleteMapping("/{id}")
    public ResponseBean deletePosition(@PathVariable Integer id){
        return super.delete(id);
    }


    @ApiOperation(value = "批量删除职位信息")
    @DeleteMapping("/")
    public ResponseBean deletePositionsByIds(Integer[] ids){
        return super.delete(ids);
    }

    @Override
    protected void initService() {
        if(super.getService() == null){
            super.setService(positionService);
        }
    }
}
