<template>
    <div class="zinde10">
        <div style="border: 1px solid #ccc; margin-top: 10px;">
            <Toolbar :editor="editorRef" :defaultConfig="toolbarConfig" :mode="props.mode"
                style="border-bottom: 1px solid #ccc" />
            <Editor :defaultConfig="editorConfig" :mode="props.mode" v-model="valueHtml"
                style="height: 500px; overflow-y: hidden;" @onCreated="handleCreated" @onChange="handleChange"
                @onDestroyed="handleDestroyed" />
        </div>
    </div>
    <hr>
</template>

<script setup lang="ts">
import '@wangeditor/editor/dist/css/style.css'
import { onBeforeUnmount, ref, shallowRef, onMounted, watch } from 'vue'
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
import { Boot, IDomEditor } from '@wangeditor/editor';
import ctrlEnterModule from '@wangeditor/plugin-ctrl-enter'
import markdownModule from '@wangeditor/plugin-md'

//使用markdown 语法
Boot.registerModule(markdownModule)
//ctrl+enter
Boot.registerModule(ctrlEnterModule)
interface Props {
    modelValue: string | undefined
    mode?: string
    placeholder: string
}

const props = defineProps<Props>();
const emit = defineEmits(['update:modelValue']);

// 编辑器实例，必须用 shallowRef，重要！
const editorRef = shallowRef()

// 内容 HTML
const valueHtml = ref<string  | undefined>(props.modelValue)

// 模拟 ajax 异步获取内容
onMounted(() => {

})


watch(props, (newVal: Props) => {
    valueHtml.value = newVal.modelValue
})


const toolbarConfig = {}
const editorConfig = { placeholder: props.placeholder }
// 组件销毁时，也及时销毁编辑器，重要！
onBeforeUnmount(() => {
    const editor = editorRef.value
    if (editor == null) return
    editor.destroy()
})

const handleCreated = (editor: IDomEditor) => {
    console.log('created', editor);
    editorRef.value = editor
}
const handleChange = (editor: IDomEditor) => {
    console.log('change:', editor.getHtml());
    emit('update:modelValue', editor.getHtml());

}
const handleDestroyed = (editor: IDomEditor) => {
    console.log('destroyed', editor)
}
</script>
<style scoped lang="scss">
.zinde10 {
    position: relative;
    z-index: 10;
}
</style>