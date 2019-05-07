<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><i class="el-icon-menu"></i> 导航</el-breadcrumb-item>
        <el-breadcrumb-item>监控容器</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-table :data="results" stripe border class="table">
      <el-table-column prop="hostname" label="主机名"></el-table-column>
      <el-table-column prop="port" label="端口"></el-table-column>
      <el-table-column prop="address" label="地址"></el-table-column>
      <el-table-column label="消费端">
        <template scope="scope">
          [{{ scope.row.clientCount }}]
          <router-link v-if="scope.row.clientCount>0"
                       :to="{ path: '/serverclients', query: { port: scope.row.port }}" replace>
            <kx-icon name="link_12_12"/>
          </router-link>
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
        results: 'servers'
      })
    },
    created () {
      this.$store.dispatch('getServer')
    }
  };
</script>
<style>
</style>
