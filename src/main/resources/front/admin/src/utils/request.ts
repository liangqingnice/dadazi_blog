import axios from "axios";
import { ElMessage, ElLoading } from 'element-plus';
import userStore from "@/stores/user";


const instance = axios.create({
    baseURL: "/api", // 代理api，直接读取变量
    timeout: 10000,
});


function showLoading(promise: Promise<any>, isLodding: boolean = true) {
    if (!isLodding) {
        // 如果不需要显示加载提示，则直接返回promise  
        return promise;
    }
    let loadingInstance: ReturnType<typeof ElLoading.service> | null = null;
    // 包装原始 promise 以处理加载提示  
    return new Promise((resolveWrapper, rejectWrapper) => {
        loadingInstance = ElLoading.service({
            fullscreen: true,
            lock: true,
            text: '加载中...',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
        });
        promise.then(result => {
            resolveWrapper(result.data);
        }).catch(rejectWrapper).finally(() => {
            if (loadingInstance) {
                loadingInstance.close();
                loadingInstance = null;
            }
        });
    }).then(data => {
        // 返回一个带有取消方法的对象  
        if (loadingInstance) {
            loadingInstance.close();
            loadingInstance = null;
        }
        return data;
    });
}


function request(method: string, url: string, data?: any, params?: any, isLodding: boolean = false) {
    return showLoading(instance({
        method,
        url,
        data, // POST, PUT 请求使用 data  
        params, // GET, DELETE 请求使用 params  
    }), isLodding);
}

// 封装HTTP方法  
function GET(url: string, params?: any, showLoading = true) {
    return request('get', url, undefined, params, showLoading);
}

function POST(url: string, data?: any, showLoading = true) {
    return request('post', url, data, undefined, showLoading);
}
function PUT(url: string, data?: any, showLoading = true) {
    return request('put', url, data, undefined, showLoading);
}
function DELETE(url: string, params?: any, showLoading = true) {
    return request('delete', url, undefined, params, showLoading);
}



//添加请求拦截器
instance.interceptors.request.use(
    (config) => {
        //请求前的回调
        //添加token
        const tokenStore = userStore();
        //判断有没有token
        if (tokenStore.token) {
            config.headers.Authorization = tokenStore.token
        }
        return config;
    },
    (err) => {
        //请求错误的回调
        Promise.reject(err)
    }
)

instance.interceptors.response.use((response) => {
    if (response.data.code == 500) {
        ElMessage.error(response.data.message)
        return Promise.reject(new Error(response.data.message))
    }
    return response;
}, (error) => {
    console.log(error);
    ElMessage.error(error.data.message)
    return Promise.reject(error);
});

export {
    GET, DELETE, POST, PUT
}


