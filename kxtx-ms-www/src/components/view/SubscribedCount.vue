<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><i class="el-icon-menu"></i> 导航</el-breadcrumb-item>
        <el-breadcrumb-item :to="{path:'/registy'}">注册中心</el-breadcrumb-item>
        <el-breadcrumb-item>{{this.$route.query.registry}}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-table :data="pageResult" stripe border class="table">
      <el-table-column type="index" width="60"/>
      <el-table-column label="消费项">
        <template scope="scope">
          <span>{{ scope.row }}</span>
        </template>
      </el-table-column>
    </el-table>
    <div>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="pageNo"
        :page-sizes="[10, 50, 100, 200]"
        :page-size="pageSize"
        layout="sizes, prev, pager, next"
        :total="results.length" class="page-bar">
      </el-pagination>
      <br/>
    </div>

  </div>
</template>
<script type="text/ecmascript-6">
  import { mapGetters } from 'vuex'
  export default {
    data() {
      return {
        pageSize: 10,
        pageNo: 1
      }
    },
    computed: {
      ...mapGetters({
        results: 'registrySubs'
      }),
      pageResult() {
        let offset = (this.pageNo - 1) * this.pageSize;
        if (this.results) {
          return (offset + this.pageSize >= this.results.length) ? this.results.slice(offset, this.results.length) : this.results.slice(offset, offset + this.pageSize);
        } else {
          return []
        }
      }
    },
    created () {
      var form = this.$route.query;
      this.$store.dispatch('getSubsByRegistry', form)
    },
    methods: {
      pagination(pageNo, pageSize, array) {
        var offset = (pageNo - 1) * pageSize;
        return (offset + pageSize >= array.length) ? array.slice(offset, array.length) : array.slice(offset, offset + pageSize);
      },
      handleSizeChange(val) {
        this.pageSize = val;
      },
      handleCurrentChange(val) {
        this.pageNo = val
      }
    }
  }
</script>
<style scoped>
  .page-bar {
    float: right;
    padding: 7px 0px;
  }
</style>
