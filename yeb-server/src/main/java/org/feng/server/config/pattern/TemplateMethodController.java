package org.feng.server.config.pattern;

import com.baomidou.mybatisplus.extension.service.IService;
import lombok.extern.slf4j.Slf4j;
import org.feng.consts.Consts;
import org.feng.server.entity.ResponseBean;
import org.feng.server.exception.EntityArgumentsException;

import java.util.Arrays;
import java.util.List;

/**
 * 模板方法设计：对通用的单个实体的增删改查进行统一封装
 *
 * @author FengJinSong
 */
@Slf4j
public abstract class TemplateMethodController<T> {

    private IService<T> service;

    /**
     * 初始化service
     * <p>
     *     if(super.getService() == null){
     *             super.setService(positionService);
     *      }
     * </p>
     */
    protected abstract void initService();
    /**
     * 增加实体
     * @param bean 实体
     * @return 响应结果
     */
    protected ResponseBean add(T bean){
        initService();
        try {
            beforeAdd(bean);
            if(service.save(bean)){
                afterAdd(bean);
                return ResponseBean.success(Consts.ADD_SUCCESS);
            }
        } catch (EntityArgumentsException e){
            log.error(e.getMessage());
            return ResponseBean.error(e.getMessage());
        }
        return ResponseBean.error(Consts.ADD_FAILED);
    }

    /**
     * 更新实体
     * @param bean 实体
     * @return 响应结果
     */
    protected ResponseBean update(T bean){
        initService();
        try {
            beforeUpdate(bean);
            if(service.updateById(bean)){
                afterUpdate(bean);
                return ResponseBean.success(Consts.UPDATE_SUCCESS);
            }
        } catch (EntityArgumentsException e) {
            log.error(e.getMessage());
            return ResponseBean.error(e.getMessage());
        }
        return ResponseBean.error(Consts.UPDATE_FAILED);
    }

    /**
     * 删除实体
     * @param id 实体id
     * @return 响应结果
     */
    protected ResponseBean delete(Integer id){
        initService();
        try {
            beforeDelete(id);
            if(service.removeById(id)){
                afterDelete(id);
                return ResponseBean.success(Consts.DELETE_SUCCESS);
            }
        } catch (EntityArgumentsException e) {
            log.error(e.getMessage());
            return ResponseBean.error(e.getMessage());
        }
        return ResponseBean.error(Consts.DELETE_FAILED);
    }

    /**
     * 删除多个实体
     * @param ids 实体id数组
     * @return 响应结果
     */
    protected ResponseBean delete(Integer[] ids){
        initService();
        try {
            beforeDeletes(ids);
            if(service.removeByIds(Arrays.asList(ids))){
                afterDeletes(ids);
                return ResponseBean.success(Consts.DELETE_SUCCESS);
            }
        } catch (EntityArgumentsException e) {
            log.error(e.getMessage());
            return ResponseBean.error(e.getMessage());
        }
        return ResponseBean.error(Consts.DELETE_FAILED);
    }


    /**
     * 查询所有数据
     * @return 实体列表
     */
    protected List<T> getAll(){
        initService();
        return service.list();
    }

    /**
     * 查询所有数据，并封装在 ResponseBean 的数据中
     * @return ResponseBean
     */
    protected ResponseBean getAllWithResponse(){
        initService();
        return ResponseBean.response(200, "success", service.list());
    }

    /**
     * 新增数据之前处理
     * @throws EntityArgumentsException 参数异常
     */
    protected void beforeAdd(T bean) throws EntityArgumentsException {}

    /**
     * 新增数据之后处理
     * @throws EntityArgumentsException 参数异常
     */
    protected void afterAdd(T bean) throws EntityArgumentsException {}

    /**
     * 更新数据之前处理
     * @throws EntityArgumentsException 参数异常
     */
    protected void beforeUpdate(T bean) throws EntityArgumentsException {}

    /**
     * 更新数据之后处理
     * @throws EntityArgumentsException 参数异常
     */
    protected void afterUpdate(T bean) throws EntityArgumentsException {}

    /**
     * 删除数据之前处理
     * @throws EntityArgumentsException 参数异常
     */
    protected void beforeDelete(Integer id) throws EntityArgumentsException {}

    /**
     * 删除数据之后处理
     * @throws EntityArgumentsException 参数异常
     */
    protected void afterDelete(Integer id) throws EntityArgumentsException {}

    /**
     * 删除多条数据之前处理
     * @throws EntityArgumentsException 参数异常
     */
    protected void beforeDeletes(Integer[] ids) throws EntityArgumentsException {}

    /**
     * 删除多条数据之后处理
     * @throws EntityArgumentsException 参数异常
     */
    protected void afterDeletes(Integer[] ids) throws EntityArgumentsException {}

    public void setService(IService<T> service) {
        this.service = service;
    }

    public IService<T> getService() {
        return service;
    }
}
