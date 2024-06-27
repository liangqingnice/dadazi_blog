<script setup lang="ts">
import '@wangeditor/editor/dist/css/style.css'
import { onBeforeUnmount, ref, shallowRef, onMounted } from 'vue'
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
import { Boot, IEditorConfig, IDomEditor } from '@wangeditor/editor'
import linkCardModule from '@wangeditor/plugin-link-card'
import ctrlEnterModule from '@wangeditor/plugin-ctrl-enter'
import markdownModule from '@wangeditor/plugin-md'
import { ElMessage } from 'element-plus'

//使用markdown 语法
Boot.registerModule(markdownModule)
//ctrl+enter
Boot.registerModule(ctrlEnterModule)
//链接卡片
Boot.registerModule(linkCardModule)

const mode: string = 'default';
const textValue: string = "";
const editorRef = shallowRef()
//异步获取内容
onMounted(() => {

})
//编辑器工具栏配置
const toolbarConfig = {}
//编辑器配置
const editorConfig: Partial<IEditorConfig> = {
    placeholder: '请输入内容...', hoverbarKeys: {
        link: {
            menuKeys: [
                'editLink', 'unLink', 'viewLink', 'convertToLinkCard'
            ],
        },
    },
    MENU_CONF: {
        convertToLinkCard: {
            async getLinkCardInfo(linkText: string, linkUrl: string) {
                return new Promise(resolve => {
                    setTimeout(() => {
                        const info = { title: linkText, iconImgSrc: '' }
                        resolve(info)
                    }, 100)
                })
            }
        },

    },
}


// 组件销毁时，也及时销毁编辑器
onBeforeUnmount(() => {
    const editor = editorRef.value
    if (editor == null) return
    editor.destroy()
})

// 记录 editor 实例，重要！
const handleCreated = (editor: IDomEditor) => {
    editorRef.value = editor
}

const handleChange = (editor: IDomEditor) => { console.log('change:', editor.children) }
const handleDestroyed = (editor: IDomEditor) => { console.log('destroyed', editor) }
const handleFocus = (editor: IDomEditor) => { console.log('focus', editor) }
const handleBlur = (editor: IDomEditor) => { console.log('blur', editor) }
const customAlert = (info: any, type: string) => { ElMessage.error(`${type} - ${info}`) }
const customPaste = (editor: IDomEditor, event: ClipboardEvent, callback: Function) => {
    console.log('ClipboardEvent 粘贴事件对象', event)
    // const html = event.clipboardData.getData('text/html') // 获取粘贴的 html
    // const text = event.clipboardData.getData('text/plain') // 获取粘贴的纯文本
    // const rtf = event.clipboardData.getData('text/rtf') // 获取 rtf 数据（如从 word wsp 复制粘贴）

    // 自定义插入内容
    editor.insertText('xxx')

    // 返回 false ，阻止默认粘贴行为
    event.preventDefault()
    callback(false) // 返回值（注意，vue 事件的返回值，不能用 return）

    // 返回 true ，继续默认的粘贴行为
    // callback(true)
}



</script>
<template>
    <div>
        <div style="border: 1px solid #ccc; margin-top: 10px;">
            <Toolbar :editor="editorRef" :defaultConfig="toolbarConfig" :mode="mode"
                style="border-bottom: 1px solid #ccc" />
            <Editor :defaultConfig="editorConfig" :mode="mode" v-model="textValue"
                style="height: 400px; overflow-y: hidden;" @onCreated="handleCreated" @onChange="handleChange"
                @onDestroyed="handleDestroyed" @onFocus="handleFocus" @onBlur="handleBlur" @customAlert="customAlert"
                @customPaste="customPaste" />
        </div>
    </div>
</template>

<style scoped></style>