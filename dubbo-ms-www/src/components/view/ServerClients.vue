<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><i class="el-icon-menu"></i> 导航</el-breadcrumb-item>
        <el-breadcrumb-item :to="{path:'/server'}">监控容器</el-breadcrumb-item>
        <el-breadcrumb-item>{{this.$route.query.port}}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div>
      {{results.server}}-{{results.port}}<br/>
      {{results.rows}}
    </div>
  </div>
</template>
<script type="text/ecmascript-6">
  import { mapGetters } from 'vuex'
  export default {
    computed: {
      ...mapGetters({
        results: 'serverClients'
      })
    },
    created () {
      var form = this.$route.query;
      this.$store.dispatch('getClientsByServer', form)
    }
  }
</script>
<style scoped>

</style>
