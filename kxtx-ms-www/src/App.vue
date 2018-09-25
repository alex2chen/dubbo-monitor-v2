<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>
<script>
  import 'element-ui/lib/theme-default/index.css'
  export default {
    name: 'App',
    watch: {
      $route(to, from) {
        // console.log(from)
        // console.log(to)
        if (!to.matched.length) {
          // this.$router.push('/404');
        }
      }
    }
  };
</script>

<style>
  @import "../static/css/main.css";

  * {
    margin: 0;
    padding: 0;
  }

  #app {
    width: 100%;
    /*height: 800px;*/
    overflow: hidden;
    background-color: #324157;
  }

  body {
    font-family: "Helvetica Neue", Helvetica, "microsoft yahei", arial, STHeiTi, sans-serif;
  }
</style>
