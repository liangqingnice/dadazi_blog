/// <reference types="vite/client" />
declare module "*.vue" {
    import type { DefineComponent, defineComponent } from "vue"
    const component: DefineComponent<{}, {}, any>;
    export default component
}