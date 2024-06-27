import {ref } from "vue";  
// 封装成组合式函数  
export function paginationFunction() {  
  // 是否开启loading  
  const loading = ref(false);  
  // 表格数据  
  const tableList = ref([]);  
  // 总数  
  const totalNum = ref(0);  
  const isOpen = ref(false);  
  const tips = ref(""); 
  return {  
    loading,  
    tableList,  
    totalNum,  
    isOpen,  
    tips 
  };  
}