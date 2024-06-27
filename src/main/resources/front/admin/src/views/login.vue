<script setup lang="ts">
import { User, Lock, ScaleToOriginal } from '@element-plus/icons-vue'
import { ref, onMounted, reactive } from 'vue'
import { getCaptcha, login, userInfo } from "@/api/user";
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessage, ElNotification, } from 'element-plus'
import userStore from "@/stores/user";
interface RuleForm {
    username: string,
    password: string,
    code: string,
    codeKey: string
}

const rules = reactive<FormRules<RuleForm>>(
    {
        username: [
            { required: true, message: '请输入账号', trigger: ['blur', 'change'] },
            { pattern: /^[a-zA-Z0-9_]{4,16}$/, message: '用户名必须是4到16位的字母、数字或下划线', trigger: ['blur', 'change'] },
        ],
        password: [
            { required: true, message: '请输入密码', trigger: ['blur', 'change'] },
            { pattern: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,16}$/, message: '密码至少包含大写字母、小写字母、数字、特殊符号、特殊符号@$!%*?& 8-16个字符', trigger: ['blur', 'change'] },
        ],
        code: [
            { required: true, message: '请输入验证码', trigger: ['blur', 'change'] },
        ]
    }
)
const userLogin = reactive<RuleForm>({
    username: "testa",
    password: "521!@Lqk",
    code: "",
    codeKey: "",
})
const ruleFormRef = ref<FormInstance>()

const ver = reactive({
    img: "",
})
//获取验证码
async function queryCaptcha() {
    const result = await getCaptcha();
    const { code, codeKey, img } = result.data
    userLogin.code = code
    userLogin.codeKey = codeKey
    ver.img = img
}

//获取用户信息
async function getUserInfo() {
    const result = await userInfo()
    userStore().userInfo = result.data
}

//用户登录
function loginForm(formEl: FormInstance | undefined) {
    if (!formEl) return
    formEl.validate(async (valid) => {
        if (valid) {
            const result = await login(userLogin)
            if (result == 200) ElMessage(result.message)
            const { data } = result
            userStore().token = data
            getUserInfo();

        }
    })

}

//初始化
onMounted(() => {
    queryCaptcha()
})
//切换验证码
const switchCaptcha = () => {
    queryCaptcha()
}
//忘记密码
const forgotPassword = () => {
    ElNotification({
        title: '提示',
        message: "请联系管理员修改密码",
    })

}

</script>
<template>
    <el-row class="login-page">
        <el-col :span="12" class="bg"></el-col>
        <el-col :span="6" :offset="3" class="form">
            <!-- 登录表单 -->
            <el-form ref="ruleFormRef" size="large" :model="userLogin" autocomplete="off" :rules="rules">
                <el-form-item>
                    <h1>登录</h1>
                </el-form-item>
                <el-form-item prop="username">
                    <el-input :prefix-icon="User" placeholder="请输入用户名" v-model="userLogin.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input name="password" :prefix-icon="Lock" type="password" show-password placeholder="请输入密码"
                        v-model="userLogin.password"></el-input>
                </el-form-item>
                <el-form-item prop="code">
                    <el-row :gutter="20" justify="space-between">
                        <el-col :span="16">
                            <el-input :prefix-icon="ScaleToOriginal" placeholder="请输入验证码"
                                v-model="userLogin.code"></el-input>
                        </el-col>
                        <el-col :span="8">
                            <el-image fit="fill" :src="ver.img" @click="switchCaptcha" />
                        </el-col>
                    </el-row>
                </el-form-item>
                <el-form-item class="flex">
                    <div class="flex">
                        <!-- <el-checkbox>记住我</el-checkbox> -->
                        <el-link type="primary" :underline="false" @click="forgotPassword">忘记密码？</el-link>
                    </div>
                </el-form-item>
                <!-- 登录按钮 -->
                <el-form-item>
                    <el-button class="button" type="primary" auto-insert-space
                        @click="loginForm(ruleFormRef)">登录</el-button>
                </el-form-item>
            </el-form>
            <div>


            </div>
        </el-col>
    </el-row>

</template>
<style scoped lang="scss">
.login-page {
    height: 100vh;
    background-color: #fff;

    .bg {
        background: url('@/assets/logo2.png') no-repeat 60% center / 240px auto,
            url('@/assets/login_bg.jpg') no-repeat center / cover;
    }

    .form {
        display: flex;
        flex-direction: column;
        justify-content: center;
        user-select: none;

        .title {
            margin: 0 auto;
        }

        .button {
            width: 100%;
        }

        .flex {
            width: 100%;
            display: flex;
            justify-content: space-between;
        }

        .verimg {
            text-align: right;
            align-items: end;
            width: 120px;
            height: 38px;
            cursor: pointer;
        }
    }
}
</style>
