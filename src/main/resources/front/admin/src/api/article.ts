import { GET, POST, PUT, DELETE } from "@/utils/request.ts"

export function list(params: any) {
    return GET("/sys/article/list", params)
}

export function info(id: number) {
    return GET("/sys/article/info", {id})
}

export function insert(data: any) {
    return POST("/sys/article/insert", data)
}

export function update(data: any) {
    return PUT("/sys/article/update", data)
}
export function del(ids: number | Array<number>) {
    return DELETE("/sys/article/del/" + ids.toString(), {})
}

export function updateArticleState(data: any) {
    return PUT(`/sys/article/updateArticleState`,data)
}



