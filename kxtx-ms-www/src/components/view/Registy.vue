<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><i class="el-icon-menu"></i> 导航</el-breadcrumb-item>
        <el-breadcrumb-item>注册中心</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-table :data="results" stripe border class="table">
      <el-table-column prop="server" label="服务地址"></el-table-column>
      <el-table-column prop="hostname" label="主机名"></el-table-column>
      <el-table-column label="状态">
        <template scope="scope">
          <span v-if="scope.row.available" class="gren">
            running
          </span>
          <span v-else="scope.row.available" class="red">
            stop
          </span>
        </template>
      </el-table-column>
      <el-table-column label="注册服务">
        <template scope="scope">
          [{{ scope.row.registeredCount }}]
          <router-link v-if="scope.row.registeredCount>0"
                       :to="{ path: '/registeredCount', query: { registry: scope.row.server }}" replace>
            <kx-icon name="link_12_12"/>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column label="消费服务">
        <template scope="scope">
          [{{ scope.row.subscribedCount }}]
          <router-link v-if="scope.row.subscribedCount>0"
                       :to="{ path: '/subscribedCount', query: { registry: scope.row.server }}" replace>
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
        results: 'registrys'
      })
    },
    created () {
      this.$store.dispatch('getRegistry')
    }
  };
</script>
<style scoped>
  .gren {
    color: #00ff00
  }

  .red {
    color: #e64242
  }
</style>
