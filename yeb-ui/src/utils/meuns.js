import {getRequest} from "@/utils/api";

export const initMenu = (router, store) => {
    if(store.state.routes.length > 0){
        return;
    }

    getRequest('/system/cfg/menu').then(data => {
        if(data){
            // 格式化路由
            let formatRouters = formatRouter(data)
            router.addRoutes(formatRouters)
            // 数据提交到Vuex
            store.commit('initRoutes', formatRouters)
        }
    })
}

export const formatRouter = (routers) => {
    let formatRouters = []
    routers.forEach(router => {
        let {path, component, name, iconCls,children} = router
        if(children && children instanceof Array){
            children = formatRouter(children)
        }

        let fmtRouter = {
            path: path,
            name: name,
            iconCls: iconCls,
            children: children,
            component(resolve) {
                if(component.startsWith("Home")) {
                    require(['../views/' + component + '.vue'], resolve);
                } else if (component.startsWith("Emp")) {
                    require(['../views/emp/' + component + '.vue'], resolve);
                } else if (component.startsWith("Per")) {
                    require(['../views/per/' + component + '.vue'], resolve);
                } else if (component.startsWith("Sal")) {
                    require(['../views/sal/' + component + '.vue'], resolve);
                } else if (component.startsWith("Sta")) {
                    require(['../views/sta/' + component + '.vue'], resolve);
                } else if (component.startsWith("Sys")) {
                    require(['../views/sys/' + component + '.vue'], resolve);
                }
            }
        }
        formatRouters.push(fmtRouter)
    })

    return formatRouters
}