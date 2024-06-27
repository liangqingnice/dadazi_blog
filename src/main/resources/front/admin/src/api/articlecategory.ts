import { GET, POST, PUT, DELETE } from "@/utils/request.ts"

export function list(params: any) {
    return GET("/sys/articleCategory/list", params)
}

export function info(id: number) {
    return GET("/sys/articleCategory/info", {id})
}

export function insert(data: any) {
    return POST("/sys/articleCategory/insert", data)
}

export function update(data: any) {
    return PUT("/sys/articleCategory/update", data)
}
export function del(ids: number | Array<number>) {
    return DELETE("/sys/articleCategory/del/" + ids.toString(), {})
}

export function articleCategoryList() {
    return GET("/sys/articleCategory/articleCategoryList", {})
}



