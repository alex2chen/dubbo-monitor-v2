<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><i class="el-icon-menu"></i> 导航</el-breadcrumb-item>
        <el-breadcrumb-item>容器</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-table :data="results" stripe border class="table">
      <el-table-column prop="host" label="主机"></el-table-column>
      <el-table-column prop="hostname" label="主机名"></el-table-column>
      <el-table-column prop="application" label="应用名"></el-table-column>
      <el-table-column prop="owner" label="所属"></el-table-column>
      <el-table-column label="提供方">
        <template scope="scope">
          [{{ scope.row.providerCount }}]
          <router-link v-if="scope.row.providerCount>0"
                       :to="{ path: '/hostproviders', query: { host: scope.row.host }}" replace>
            <kx-icon name="link_12_12"/>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column label="消费方">
        <template scope="scope">
          [{{ scope.row.consumerCount }}]
          <router-link v-if="scope.row.consumerCount>0"
                       :to="{ path: '/hostconsumers', query: { host: scope.row.host }}" replace>
            <kx-icon name="link_12_12"/>
          </router-link>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script type="text/ecmascript-6">
  /* eslint-disable no-trailing-spaces */
  import { mapGetters } from 'vuex'
  export default {
    computed: {
      ...mapGetters({
        results: 'hosts'
      })
    },
    created () {
      this.$store.dispatch('getHosts');
    }
  }
</script>
<style>

</style>
