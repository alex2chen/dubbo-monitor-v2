<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><i class="el-icon-setting"></i> 导航</el-breadcrumb-item>
        <el-breadcrumb-item>系统信息</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-table :data="results" stripe border style="width: 100%">
      <el-table-column label="监控项" width="120">
        <template scope="scope">
          {{ scope.row[0] }}
        </template>
      </el-table-column>
      <el-table-column label="监控值">
        <template scope="scope">
          <span v-html="scope.row[1]"/>
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
        results: 'systems'
      })
    },
    created () {
      this.$store.dispatch('getSystem')
    }
  }
</script>
<style>

</style>
