import axios from "axios";
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import {Message} from "element-ui"

const request = axios.create({
	baseURL: 'http://localhost:8090/admin/',
	timeout: 10000,
})

let CancelToken = axios.CancelToken

// 请求拦截
request.interceptors.request.use(
	config => {
		//对于访客模式，除GET请求外，都拦截并提示
		const userJson = window.localStorage.getItem('user') || '{}'
		const user = JSON.parse(userJson)
		if (userJson !== '{}' && user.role !== 'ROLE_admin' && config.method !== 'get') {
			config.cancelToken = new CancelToken(function executor(cancel) {
				cancel('演示模式，不允许操作')
			})
			return config
		}
		NProgress.start()
		const token = window.localStorage.getItem('token')
		if (token) {
			config.headers.Authorization = token
		}
		return config
	}
)

// 响应拦截
request.interceptors.response.use(
	config => {
		NProgress.done()
		return config.data
	}, error => {
		if (error.message === '演示模式，不允许操作') {
			Message.error(error)
		}
	}
)

export default request