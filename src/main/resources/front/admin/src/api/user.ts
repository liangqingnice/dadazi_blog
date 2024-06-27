import { GET, POST } from "@/utils/request.ts"

export function getCaptcha() {
   return GET("/sys/user/captcha", {})
}

export function login(data: any) {
   return POST("/sys/user/login", data)
}

export function userInfo(){
   return GET("/sys/user/userInfo", {})
}



