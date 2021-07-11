import {getRequest} from "@/utils/api";

export const initMenu = (router, store) => {
    if(store.state.routes.length > 0){
        return;
    }

    getRequest('/system/cfg/menus').then(data => {
        if(data){
            // 格式化路由
            let formatRouters = formatRouter(data)
            router.addRoutes(formatRouters)
            // 数据提交到Vuex
            store.commit('initRoutes', formatRouters)
        }
    })
}

const routeList = ["Emp", "Per", "Sal", "Sta", "Sys"]

export const formatRouter = (routers) => {

    let formatRouters = []
    routers.forEach(router => {
        let {path, component, name, iconCls, children} = router
        if(children && children instanceof Array){
            children = formatRouter(children)
        }

        let fmtRouter = {
            path: path,
            name: name,
            iconCls: iconCls,
            children: children,
            component(resolve) {
                let route = routeList.filter(item => component.startsWith(item))[0]
                if(route){
                    require(['../views/'+ route.toLowerCase() + '/' + component + '.vue'], resolve)
                } else {
                    require(['../views/' + component + '.vue'], resolve);
                }
            }
        }
        formatRouters.push(fmtRouter)
    })

    return formatRouters
}