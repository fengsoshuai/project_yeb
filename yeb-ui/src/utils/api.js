/**
 * 封装 axios 调用，统一拦截
 */
import axios from "axios";
import {Message} from "element-ui";
import router from "@/router";

/**
 * 拦截请求，设置请求头
 */
axios.interceptors.request.use(config=>{
    if (window.sessionStorage.getItem('tokenStr')) {
        // 请求携带自定义token
        config.headers['Authorization'] = window.sessionStorage.getItem('tokenStr');
    }
    return config
},error => {
    console.log(error);
})



/**
 * 拦截响应
 */
axios.interceptors.response.use(success => {
    if(success.status && success.status === 200) {
        if(success.data.code === 500 || success.data.code === 401 || success.data.code === 403){
            Message.error({message: success.data.message})
            return
        }
        if(success.data.message){
            Message.success({message: success.data.message})
        }
    }
    return success.data
},
    error => {
        if(error.response.code === 504 || error.response.code === 404){
            Message.error('服务器错误！')
        } else if(error.response.code === 403){
            Message.error('权限不足！请联系管理员')
        } else if(error.response.code === 401){
            Message.error('尚未登陆，请先登陆')
            router.replace('/');
        } else {
            if(error.response.message){
                Message.error(error.response.message)
            } else {
                Message.error('未知错误')
            }
        }
    });

let base = 'http://localhost:80'

/**
 * axios 发送 post 请求
 * @param url 请求地址
 * @param params json 请求参数
 * @returns 响应结果
 */
export const postRequest = (url, params) => {
    return axios({
        method: 'post',
        url: `${base}${url}`,
        data: params
    })
}

/**
 * axios 发送 put 请求
 * @param url 请求地址
 * @param params json 请求参数
 * @returns 响应结果
 */
export const putRequest = (url, params) => {
    return axios({
        method: 'put',
        url: `${base}${url}`,
        data: params
    })
}
/**
 * axios 发送 get 请求
 * @param url 请求地址
 * @param params json 请求参数
 * @returns 响应结果
 */
export const getRequest = (url, params) => {
    return axios({
        method: 'get',
        url: `${base}${url}`,
        data: params
    })
}

/**
 * axios 发送 delete 请求
 * @param url 请求地址
 * @param params json 请求参数
 * @returns 响应结果
 */
export const deleteRequest = (url, params) => {
    return axios({
        method: 'delete',
        url: `${base}${url}`,
        data: params
    })
}