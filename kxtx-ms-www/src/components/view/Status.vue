<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><i class="el-icon-menu"></i> 导航</el-breadcrumb-item>
        <el-breadcrumb-item>依赖组件</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-table :data="results" stripe border style="width: 100%">
      <el-table-column prop="name" label="组件名称" width="120"></el-table-column>
      <el-table-column label="运行状态"  width="200">
        <template scope="scope">
          <el-icon name="time"></el-icon>
          <span style="margin-left: 10px" class="gren">{{ scope.row.status.level }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="description" label="描述"></el-table-column>
    </el-table>
  </div>
</template>
<script type="text/ecmascript-6">
  import { mapGetters } from 'vuex'
  export default {
    computed: {
      ...mapGetters({
        results: 'statuses'
      })
    },
    created () {
      this.searchData();
    },
    methods: {
      searchData: function (event) {
        this.$store.dispatch('getStatus')
      }
    }
  };
</script>
<style>
  h1, h2 {
    font-weight: normal;
  }

  .gren {
    color: #00ff00
  }

  .red {
    color: #e64242
  }
</style>
