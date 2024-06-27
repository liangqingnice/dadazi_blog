<script setup lang="ts">
import { ref } from 'vue'
import { Menu as IconMenu, Message, Setting } from '@element-plus/icons-vue'

</script>
<template>
    <div class="toolbar">
        <el-dropdown>
            <el-icon>
                <setting />
            </el-icon>
            <template #dropdown>
                <el-dropdown-menu>
                    <el-dropdown-item>View</el-dropdown-item>
                    <el-dropdown-item>Add</el-dropdown-item>
                    <el-dropdown-item>Delete</el-dropdown-item>
                </el-dropdown-menu>
            </template>
        </el-dropdown>
        <span>Tom</span>
    </div>
</template>
<style scoped lang="scss">

</style>