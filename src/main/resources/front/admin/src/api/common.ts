import { POST } from "@/utils/request.ts"
export function uploadApi(data: any) {
    return POST("/upload", data)
}
