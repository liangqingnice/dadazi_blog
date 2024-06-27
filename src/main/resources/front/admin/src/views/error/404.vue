<script setup lang="ts">
import { useRouter } from "vue-router";
const router= useRouter();
const  backHome=()=>{
  router.push("/")
}
</script>

<template>
  <el-container>
    <el-main>
      <div class="glitch">
        <h1 class="glitch-text" data-text="404">404</h1>
        <p class="glitch-tips">您访问的页面不存在</p>
        <el-button type="primary" @click="backHome">返回首页</el-button>
      </div>
    </el-main>
  </el-container>
</template>

<style scoped>
.glitch {
  text-align: center;
  margin-top: 12% ;
  margin-bottom: 10% ;

}

.glitch-tips {
  font-size: 40px;
  font-weight: 800;
  color: #6486e8;
  margin-bottom: 20px;
}


@keyframes glitch {
  0% {
    transform: skew(0deg);
    opacity: 1;
  }

  5% {
    transform: skew(5deg);
    opacity: 0.5;
  }

  10% {
    transform: skew(-5deg);
    opacity: 1;
  }

  15% {
    transform: skew(5deg);
    opacity: 0.5;
  }

  20% {
    transform: skew(0deg);
    opacity: 1;
  }

  100% {
    transform: skew(0deg);
    opacity: 1;
  }
}

.glitch-text {
  font-size: 100px;
  user-select: none;
  color: #6486e8;
  position: relative;
  animation: glitch 2s infinite;
}

.glitch-text::before,
.glitch-text::after {
  content: attr(data-text);
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  overflow: hidden;
  color: rgb(45, 219, 219);
  clip-path: polygon(0 0, 100% 0, 100% 50%, 0 50%);
}

.glitch-text::before {
  animation: glitch-before 2s infinite;
}

.glitch-text::after {
  animation: glitch-after 2s infinite;
}

@keyframes glitch-before {
  0% {
    clip-path: polygon(0 0, 100% 0, 100% 5%, 0 5%);
  }

  20% {
    clip-path: polygon(0 0, 100% 0, 100% 50%, 0 50%);
  }

  40% {
    clip-path: polygon(0 0, 100% 0, 100% 5%, 0 5%);
  }

  60% {
    clip-path: polygon(0 0, 100% 0, 100% 50%, 0 50%);
  }

  80% {
    clip-path: polygon(0 0, 100% 0, 100% 5%, 0 5%);
  }

  100% {
    clip-path: polygon(0 0, 100% 0, 100% 50%, 0 50%);
  }
}

@keyframes glitch-after {
  0% {
    clip-path: polygon(0 50%, 100% 50%, 100% 100%, 0 100%);
  }

  20% {
    clip-path: polygon(0 5%, 100% 5%, 100% 95%, 0 95%);
  }

  40% {
    clip-path: polygon(0 50%, 100% 50%, 100% 100%, 0 100%);
  }

  60% {
    clip-path: polygon(0 5%, 100% 5%, 100% 95%, 0 95%);
  }

  80% {
    clip-path: polygon(0 50%, 100% 50%, 100% 100%, 0 100%);
  }

  100% {
    clip-path: polygon(0 50%, 100% 50%, 100% 100%, 0 100%);
  }
}
</style>
