package org.feng.server.service.impl;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.feng.consts.Consts;
import org.feng.server.entity.MenuRole;
import org.feng.server.entity.ResponseBean;
import org.feng.server.mapper.MenuRoleMapper;
import org.feng.server.service.IMenuRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  权限菜单业务
 * </p>
 *
 * @author FengJinSong
 * @since 2021-06-16
 */
@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements IMenuRoleService {

    @Autowired
    private MenuRoleMapper menuRoleMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseBean updateMenuRole(Integer rid, Integer[] mids) {
        // 先删除所有对应的权限菜单
        menuRoleMapper.delete(new QueryWrapper<MenuRole>().eq("rid", rid));
        if(ArrayUtil.isEmpty(mids)){
            return ResponseBean.success(Consts.UPDATE_SUCCESS);
        }
        // 插入数据
        Integer rows = menuRoleMapper.insertRecord(rid, mids);
        if(rows == mids.length){
            return ResponseBean.success(Consts.UPDATE_SUCCESS);
        }
        return ResponseBean.error(Consts.UPDATE_FAILED);
    }
}
