<template>
  <div class="grid-content bg-purple-dark">
    <div class="header">
      <div class="logo">
        <!--<img src="/static/images/logo.jpg" alt="logo"/>-->
        微服务应用治理系统
      </div>
    </div>
  </div>
</template>
<script>
  export default {
    data () {
      return {
        name: 'header'
      }
    }
  }
</script>
<style scoped>
  .header {
    top: 0;
    left: 0;
    width: 100%;
    height: 80px;
    background-color: #242f42;
    font-size: 22px;
    line-height: 80px;
    color: #fff;
  }
  .logo {
    float: left;
    margin-left: 20px;
    text-align: center;
  }
</style>
