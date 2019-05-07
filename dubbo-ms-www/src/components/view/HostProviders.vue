<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><i class="el-icon-menu"></i> 导航</el-breadcrumb-item>
        <el-breadcrumb-item :to="{path:'/host'}">容器</el-breadcrumb-item>
        <el-breadcrumb-item>{{this.$route.query.host}}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div>
      服务地址：{{results.address}}<br/>
      <el-table :data="results.rows" stripe border style="width: 100%">
        <el-table-column type="index"/>
        <el-table-column label="URL">
          <template scope="scope">
            {{ scope.row }}
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>
<script type="text/ecmascript-6">
  import { mapGetters } from 'vuex'
  export default {
    computed: {
      ...mapGetters({
        results: 'hostProviders'
      })
    },
    created () {
      var form = this.$route.query;
      this.$store.dispatch('getProvidersByHost', form)
    }
  }
</script>
<style scoped>

</style>
