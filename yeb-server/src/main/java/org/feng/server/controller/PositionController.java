package org.feng.server.controller;


import io.swagger.annotations.ApiOperation;
import org.feng.server.entity.Position;
import org.feng.server.entity.ResponseBean;
import org.feng.server.service.IPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  职位管理
 * </p>
 *
 * @author FengJinSong
 * @since 2021-06-16
 */
@RestController
@RequestMapping("/system/cfg/pos")
public class PositionController {

    @Autowired
    private IPositionService positionService;

    @GetMapping("/")
    @ApiOperation(value = "获取所有职位信息")
    public List<Position> getAllPositions(){
        return positionService.list();
    }


    @PostMapping("/")
    @ApiOperation(value = "添加职位信息")
    public ResponseBean addPosition(@RequestBody Position position){
        position.setCreateDate(LocalDateTime.now());
        if(positionService.save(position)){
            return ResponseBean.success("添加成功");
        }
        return ResponseBean.error("添加失败");
    }

    @PutMapping("/")
    @ApiOperation(value = "更新职位信息")
    public ResponseBean updatePosition(@RequestBody Position position){
        if(positionService.updateById(position)){
            return ResponseBean.success("更新成功");
        }
        return ResponseBean.error("更新失败");
    }

    @ApiOperation(value = "删除职位信息")
    @DeleteMapping("/{id}")
    public ResponseBean deletePosition(@PathVariable Integer id){
        if(positionService.removeById(id)){
            return ResponseBean.success("删除成功");
        }
        return ResponseBean.error("删除失败");
    }


    @ApiOperation(value = "批量删除职位信息")
    @DeleteMapping("/")
    public ResponseBean deletePositionsByIds(Integer[] ids){
        if(positionService.removeByIds(Arrays.asList(ids))){
            return ResponseBean.success("删除成功");
        }
        return ResponseBean.error("删除失败");
    }



}
