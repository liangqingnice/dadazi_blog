import { defineStore } from 'pinia'
const userStore = defineStore('token', {
    state: () => {
        return {
            token: "",
            userInfo:null
        }
    },
    getters: {
        getToken(state): string {
            return state.token;
        }
    },
    actions: {
        setToken(newToken: string) {
            this.token = newToken;
        },
        delToken() {
            this.token = ""
        }
    },persist: true,
})


export default userStore;