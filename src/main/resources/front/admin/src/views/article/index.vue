<script setup lang="ts">
import { paginationFunction } from "@/utils/composables/paginationfunction";
import { list, del, info, insert, update, updateArticleState } from "@/api/article";
import { articleCategoryList } from "@/api/articlecategory";
import Editor from "@/components/editor/index.vue";
import { onMounted, ref, reactive } from "vue";
const { loading, tableList, totalNum, isOpen, tips } = paginationFunction()
import ImgUpload from "@/components/ImgUpload/index.vue"
import { ElMessage, ElMessageBox } from 'element-plus'
import type { FormInstance, FormRules } from 'element-plus'
const queryForm = ref<FormInstance>()
const formRef = ref<FormInstance>()
//文章类型列表
const articleTypeList = ref<ArticleType[]>([])
interface ArticleType {
    createTime: Date
    id?: any
    categoryName?: string
    categoryAlias?: string,
    createUser?: number | null
}


//参数
const params = reactive<Article>({
    page: 1,
    size: 10,
    categoryId: null,
    state: "",
    title: "",

})
const form = reactive<Article>({
    id: "",
    title: "",
    content: "",
    coverImg: "",
    categoryId: null
})


interface Article {
    page?: number
    size?: number
    id?: any
    title: string
    content?: string
    coverImg?: string
    state?: string
    categoryId: number | null
}

//获取表格数据
async function getList() {
    loading.value = true
    const result = await list(params);
    const { records, total } = result.data
    totalNum.value = total
    tableList.value = records
    loading.value = false
}

//获取文章类型列表
async function getArticleTypeList() {
    const result = await articleCategoryList();
    articleTypeList.value = result.data
}
//初始化表格信息
onMounted(() => {
    getList()
    getArticleTypeList()
})
//分页变更
const changeDbPage = () => {
    getList()
}
//编辑
const edit = (row: Article) => {
    const id = row.id
    tips.value = "修改"
    getInfo(id)
    isOpen.value = true;

}
async function getInfo(id: number) {
    const result = await info(id)
    Object.assign(form, result.data)
    form.id = id
}


//删除数据
async function delDb(ids: number | Array<number>) {
    try {
        await del(ids);
        getList()
        ElMessage.success("删除成功！")
    } catch (error) {
        console.log(error);
    }
}
//删除操作
const remove = (row: Article) => {
    ElMessageBox.confirm('确认要删除吗?', 'Warning',
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
        }).then(() => {
            delDb(row.id)
        }).catch(error => {
            console.log(error);
        })
}
//查询
const query = () => {
    getList()
}
//重置表单
const rest = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.resetFields()
    getList()
}
//提交表达那
const sumbitForm = async (formEl: FormInstance | undefined) => {
    if (!formEl) return
    await formEl.validate(async (valid) => {
        if (valid) {
            let result;
            if (form && form.id) {
                result = await update(form)
            } else {
                result = await insert(form)
            }
            ElMessage.success(result.message)
            if (result.code == 200) {
                isOpen.value = false
                getList()
            }
        }
    })
}
//验证规则
const rules = reactive<FormRules<Article>>({
    "title": [{ required: true, message: '请输入标题', trigger: ['blur', 'change'] }],
    "content": [{ required: true, message: '请输入文章内容', trigger: ['blur', 'change'] }]

})
//添加
const add = () => {
    tips.value = "新增"
    Object.assign(form, {
        id: "",
        categoryName: "",
        categoryAlias: ""
    })
    isOpen.value = true
}
//变更文章状态
const stateChange = (row: Article) => {
    let text = row.state === "0" ? "未发布" : "已发布";
    ElMessageBox.confirm(`确认要修改为${text}吗?`, 'Warning',
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
        }).then(async () => {
            const result = await updateArticleState({ id: row.id, state: row.state })
            if (result.code == 200) {
                ElMessage.success("修改成功!")
            }
        }).catch(error => {
            row.state = row.state === "0" ? "1" : "0";
            console.log(error);
        })
}
//关闭
const handleClose = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.resetFields()
}
</script>
<template>
    <div>
        <el-form :inline="true" :model="params" ref="queryForm">
            <el-form-item label="标题" prop="title">
                <el-input v-model="params.title" placeholder="请输入标题" clearable />
            </el-form-item>

            <el-form-item label="分类" prop="categoryId" style="width: 240px">
                <el-select v-model="params.categoryId" placeholder="请选择文章分类">
                    <el-option v-for="item in articleTypeList" :key="item.id" :label="item.categoryName"
                        :value="item.id" clearable />
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="query">查询</el-button>
                <el-button type="primary" @click="rest(queryForm)">重置</el-button>
            </el-form-item>
        </el-form>
        <el-button type="primary" @click="add">新增</el-button>
        <el-table :data="tableList" v-loading="loading">
            <el-table-column align="center" prop="title" label="标题" />
            <el-table-column align="center" prop="title" label="状态">
                <template #default="scope">
                    <el-switch v-model="scope.row.state" active-value="1" inactive-value="0"
                        @change="stateChange(scope.row)">
                        <template #active-action>
                            <span>T</span>
                        </template>
                        <template #inactive-action>
                            <span>F</span>
                        </template>
                    </el-switch>
                </template>
            </el-table-column>

            <el-table-column align="center" prop="createTime" label="创建时间" />
            <el-table-column align="center" prop="updateTime" label="更新时间" />
            <el-table-column align="center" fixed="right" label="操作" width="120">
                <template #default="scope">
                    <el-button link type="primary" size="small" @click="edit(scope.row)">
                        编辑
                    </el-button>
                    <el-button link type="primary" size="small" @click="remove(scope.row)">
                        删除
                    </el-button>
                </template>
            </el-table-column>

        </el-table>
        <el-pagination v-model:current-page="params.page" v-model:page-size="params.size" :page-sizes="[10, 20, 30, 50]"
            layout="total, sizes, prev, pager, next, jumper" :total="totalNum" @change="changeDbPage" />
        <el-dialog v-model="isOpen" :title="tips" draggable :append-to-body="true" @close="handleClose(formRef)">
            <el-form :model="form" ref="formRef" :rules="rules">
                <el-form-item label="标题" prop="title">
                    <el-input v-model="form.title" placeholder="请输入标题" clearable />
                </el-form-item>
                <el-form-item label="内容" prop="content">
                    <Editor v-if="isOpen" placeholder="请输入内容" :textVal="form.content" />
                </el-form-item>
                <el-form-item label="封面" prop="coverImg">
                    <ImgUpload v-model="form.coverImg" />
                </el-form-item>
            </el-form>
            <template #footer>
                <div class="dialog-footer">
                    <el-button type="primary" @click="sumbitForm(formRef)">
                        确认
                    </el-button>
                </div>
            </template>
        </el-dialog>

    </div>
</template>
<style scoped></style>
