<script setup>
import Aside from "@/views/layouts/modules/aside.vue";
import Header from "@/views/layouts/modules/header.vue";

</script>
<template>
    <el-container>
        <el-aside>
            <Aside />
        </el-aside>
        <el-container>
            <el-header>
                <Header />
            </el-header>
            <el-main>
                <el-scrollbar>
                    <RouterView></RouterView>
                </el-scrollbar>
            </el-main>
        </el-container>
    </el-container>
</template>
<style scoped lang="scss">
.el-header {
    background-color: var(--el-color-primary-light-7);
    color: var(--el-text-color-primary);
}

.el-aside {
    color: var(--el-text-color-primary);
    background: var(--el-color-primary-light-8);
}

.el-main {
    padding: 0;
}
</style>
