<script setup lang="ts">

import { list, del, info, insert, update } from "@/api/articlecategory";
import { onMounted, reactive, ref } from "vue";
import { ElMessage, ElMessageBox } from 'element-plus'
import type { FormInstance, FormRules } from 'element-plus'
import { paginationFunction } from "@/utils/composables/paginationfunction";
const { loading, tableList, totalNum, isOpen, tips } = paginationFunction()
const queryForm = ref<FormInstance>()
const formRef = ref<FormInstance>()
const changeDbPage = () => {
  getList()
}
//初始化表格信息
onMounted(() => {
  getList()
})

//获取表格数据
async function getList() {
  loading.value = true
  const result = await list(params);
  const { records, total } = result.data
  totalNum.value = total
  tableList.value = records
  loading.value = false
}

//参数
const params = reactive<ArticleCategory>({
  page: 1,
  size: 10,
  categoryName: "",
  categoryAlias: ""
})
const form = reactive<ArticleCategory>({
  id: "",
  categoryName: "",
  categoryAlias: ""
})
interface ArticleCategory {
  page?: number
  size?: number
  id?: any
  categoryName: string
  categoryAlias: string
  createTime?: Date
}


//编辑
const edit = (row: ArticleCategory) => {
  const id = row.id
  tips.value = "修改"
  getInfo(id)
  isOpen.value = true;

}
async function getInfo(id: number) {
  const result = await info(id)
  const { categoryAlias, categoryName } = result.data
  form.categoryAlias = categoryAlias
  form.categoryName = categoryName
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
const remove = (row: ArticleCategory) => {
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
const query = () => {
  getList()
}

const rest = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
  getList()
}

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

const rules = reactive<FormRules<ArticleCategory>>({
  "categoryName": [{ required: true, message: '请输入分类名称', trigger: ['blur', 'change'] }],
  "categoryAlias": [{ required: true, message: '请输入分类别名', trigger: ['blur', 'change'] }]

})

const add = () => {
  tips.value = "新增"
  Object.assign(form, {
    id: "",
    categoryName: "",
    categoryAlias: ""
  })
  isOpen.value = true
}
</script>
<template>
  <div>
    <el-form :inline="true" :model="params" ref="queryForm">
      <el-form-item label="分类名称" prop="categoryName">
        <el-input v-model="params.categoryName" placeholder="请输入分类名称" clearable />
      </el-form-item>
      <el-form-item label="分类别称" prop="categoryAlias">
        <el-input v-model="params.categoryAlias" placeholder="请输入分类别称" clearable />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="query">查询</el-button>
        <el-button type="primary" @click="rest(queryForm)">重置</el-button>
      </el-form-item>
    </el-form>
    <el-button type="primary" @click="add">新增</el-button>
    <el-table :data="tableList" v-loading="loading">
      <el-table-column align="center" prop="categoryName" label="分类名称" />
      <el-table-column align="center" prop="categoryAlias" label="分类别称" />
      <el-table-column align="center" prop="updateTime" label="更新时间" />
      <el-table-column align="center" prop="createTime" label="创建时间" />
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
    <el-pagination :hide-on-single-page="true" v-model:current-page="params.page" v-model:page-size="params.size"
      :page-sizes="[10, 20, 30, 50]" layout="total, sizes, prev, pager, next, jumper" :total="totalNum"
      @change="changeDbPage" />


    <el-dialog v-model="isOpen" :title="tips" draggable>
      <el-form :model="form" ref="formRef" :rules="rules">
        <el-form-item label="分类名称" prop="categoryName">
          <el-input v-model="form.categoryName" placeholder="请输入分类名称" clearable />
        </el-form-item>
        <el-form-item label="分类别称" prop="categoryAlias">
          <el-input v-model="form.categoryAlias" placeholder="请输入分类别称" clearable />
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