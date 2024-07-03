<template>
  <div class="flex">
    <div v-if="fileList.length" class="flex">
      <div class="imgbox" style="margin-left: 10px;" v-for="(url, index) in fileList" :key="index">
        <el-image class="avatar" :src="url" :preview-src-list="[url]" />
        <div class="flex operate">
          <el-icon class="icon pointer block" @click="preFile(url)">
            <ZoomIn />
          </el-icon>
          <el-icon class="icon pointer block" @click="delFile(index)">
            <Delete />
          </el-icon>
        </div>
        <div class="mask"></div>
      </div>
    </div>
    <div class="uploader" v-if="fileList.length < prop.limit">
      <el-upload action="#" ref="upload" multiple :limit="prop.limit" :http-request="handleFileUpload"
        :show-file-list="false">
        <el-icon class="uploader-icon">
          <Plus />
        </el-icon>
      </el-upload>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, ref, watch } from 'vue';
import { Plus, ZoomIn, Delete } from '@element-plus/icons-vue';
import { uploadApi } from "@/api/common";
import { ElImage, ElMessage, type UploadRequestOptions } from 'element-plus';
import { preview } from 'vue3-image-preview';
const fileList = ref<string[]>([]);

interface Props {
  limit: number;
  path: string;
  modelValue: string[]|null|undefined
}
const prop = defineProps<Props>();
//删除 
const delFile = (index: number) => {
  fileList.value.splice(index, 1);
};

watch(prop, (newVal: Props) => {
  if (newVal.modelValue) fileList.value = newVal.modelValue
})
onMounted(() => {
  console.log("组件", prop);
})

const preFile = (url: string) => {
  preview({
    images: url
  });
}
const handleFileUpload = async (options: UploadRequestOptions) => {
  try {
    const formData = new FormData();
    formData.append("file", options.file);
    formData.append("path", prop.path);
    const result = await uploadApi(formData);
    fileList.value.push(result.data.fullPath);
    ElMessage.success("上传成功！");
  } catch (error) {
    console.error("上传失败！", error);
    ElMessage.error("上传失败！");
  }
};
</script>

<style scoped>
.mask {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  opacity: 0;
  transition: opacity 0.3s ease-in-out;
}

.imgbox:hover .mask,
.imgbox:hover .operate {
  opacity: 1;
}

.imgbox {
  position: relative;
  width: 178px;
  height: 178px;
}

.avatar {
  width: 178px;
  height: 178px;
  border: 1px dashed #ddd;
}

.operate {
  display: flex;
  justify-content: space-around;
  align-items: center;
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  opacity: 0;
  transition: opacity 0.3s ease-in-out;
  z-index: 1;
}

.icon {
  font-size: 20px;
  color: #fff;
}

.uploader {
  margin-left: 10px;
}

.el-icon.uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
  line-height: 178px;
}
</style>
<style>
.uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}
</style>