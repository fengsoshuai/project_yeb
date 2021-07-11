let proxyObj = {}
proxyObj['/'] = {
    // websocket
    ws: false,
    // 目标地址
    target: 'http://localhost:80',
    // 发送请求头中host会设置成target
    changeOrigin: true,
    // 不重写请求地址
    pathRewrite: {
        '^/': '/'
    }
}

module.exports = {
    devServer: {
        host: 'localhost',
        port: 8080,
        proxy: proxyObj
    }
}