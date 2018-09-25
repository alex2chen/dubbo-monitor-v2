<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><i class="el-icon-menu"></i> 导航</el-breadcrumb-item>
        <el-breadcrumb-item>服务</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-table :data="results" stripe border class="table">
      <el-table-column prop="name" label="服务名" width="380"></el-table-column>
      <el-table-column prop="application" label="应用名"></el-table-column>
      <el-table-column prop="owner" label="所属"></el-table-column>
      <el-table-column label="提供方">
        <template scope="scope">
          [{{ scope.row.providerCount }}]
          <router-link v-if="scope.row.providerCount>0"
                       :to="{ path: '/serviceprovider', query: { service: scope.row.name }}" replace>
            <kx-icon name="link_12_12"/>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column label="消费方">
        <template scope="scope">
          [{{ scope.row.consumerCount }}]
          <router-link v-if="scope.row.consumerCount>0"
                       :to="{ path: '/serviceconsumer', query: { service: scope.row.name }}" append>
            <kx-icon name="link_12_12"/>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column label="统计">
        <template scope="scope">
          <router-link :to="{ path: '/statiscs', query: { service: scope.row.name }}" append>
            <kx-icon name="link_12_12"/>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column label="报表">
        <template scope="scope">
          <router-link :to="{ path: '/chartview', query: { service: scope.row.name }}" append>
            <kx-icon name="report"/>
          </router-link>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page.sync="pageNo"
      :page-sizes="[10, 50, 100, 200]"
      :page-size="pageSize"
      layout="sizes, prev, pager, next"
      :total="results.length" class="page-bar">
    </el-pagination>
  </div>
</template>
<script type="text/ecmascript-6">
  import { mapGetters } from 'vuex'
  import KxIcon from '../icon/icon'
  export default {
    data() {
      return {
        pageSize: 10,
        pageNo: 1
      }
    },
    components: {KxIcon},
    computed: {
      ...mapGetters({
        results: 'services'
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
    mounted () {
      this.$store.dispatch('getService')
    },
    methods: {
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
