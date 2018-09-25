<!--已废弃-->
<template>
  <div>
    <transition name="move" mode="out-in">
      <router-view></router-view>
    </transition>
  </div>
</template>
<script>
  export default {
    name: 'Content'
  }
</script>
<style scoped>
</style>
