<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><i class="el-icon-menu"></i> 导航</el-breadcrumb-item>
        <el-breadcrumb-item :to="{path:'/applications'}">Applications</el-breadcrumb-item>
        <el-breadcrumb-item>{{this.$route.query.application}}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-table :data="results" stripe border class="table">
      <el-table-column type="index"/>
      <el-table-column label="提供方">
        <template scope="scope">
          {{ scope.row }}
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script type="text/ecmascript-6">
  import { mapGetters } from 'vuex'
  export default {
    computed: {
      ...mapGetters({
        results: 'appProviderList'
      })
    },
    created () {
      var form = this.$route.query;
      this.$store.dispatch('getProvidersByApp', form)
    }
  }
</script>
<style scoped>

</style>
