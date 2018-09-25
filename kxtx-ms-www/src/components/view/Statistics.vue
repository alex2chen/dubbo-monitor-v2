<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><i class="el-icon-menu"></i> 导航</el-breadcrumb-item>
        <el-breadcrumb-item :to="{path:'/service'}">服务</el-breadcrumb-item>
        <el-breadcrumb-item>{{this.$route.query.service}}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-form :inline="true" :model="formInline" class="query-form">
      <el-form-item label="调用日期:">
        <div class="block">
          <el-date-picker v-model="formInline.startEndDate" type="daterange" placeholder="选择日期范围"></el-date-picker>
        </div>
      </el-form-item>
      <el-form-item label="方法:">
        <template>
          <el-select v-model="formInline.methodName" filterable placeholder="请选择">
            <el-option v-for="item in chartMethods" :key="item.value" :label="item.value"
                       :value="item.value"></el-option>
          </el-select>
        </template>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="searchData">查询</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="results.rows" stripe border class="table">
      <el-table-column prop="method" label="方法"></el-table-column>
      <el-table-column label="成功">
        <template scope="scope">
          <span class="consumer">{{scope.row.consumerSuccess }}</span><i class="el-icon-caret-right "/><span
          class="provider">{{scope.row.providerSuccess }}</span>
        </template>
      </el-table-column>
      <el-table-column label="失败">
        <template scope="scope">
          <span class="consumer">{{scope.row.consumerFailure }}</span><i class="el-icon-caret-right "/><span
          class="provider">{{scope.row.providerFailure }}</span>
        </template>
      </el-table-column>
      <el-table-column label="平均消耗(ms)">
        <template scope="scope">
          <span class="consumer"> {{scope.row.consumerAvgElapsed }}</span><i class="el-icon-caret-right "/><span
          class="provider">{{scope.row.providerAvgElapsed }}</span>
        </template>
      </el-table-column>
      <el-table-column label="最大消耗(ms)">
        <template scope="scope">
          <span class="consumer">{{scope.row.consumerMaxElapsed }}</span><i class="el-icon-caret-right "/><span
          class="provider"> {{scope.row.providerMaxElapsed }}</span>
        </template>
      </el-table-column>
      <el-table-column label="最大并发">
        <template scope="scope">
          <span class="consumer"> {{scope.row.consumerMaxConcurrent }}</span>
          <i class="el-icon-caret-right "/><span
          class="provider">{{scope.row.providerMaxConcurrent }}</span>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin-top: 30px;">
      注：<span class="provider">provider</span>,<span class="consumer">consumer</span>
    </div>
  </div>
</template>
<script type="text/ecmascript-6">
  import { formatDate } from '@/util/date'
  import { mapGetters } from 'vuex'
  export default {
    name: 'Statistics',
    data () {
      return {
        formInline: {
          startEndDate: '',
          methodName: ''
        }
      }
    },
    methods: {
      searchData: function (event) {
        let form = {type: 'provider'}
        form.service = this.$route.query.service
        form.method = this.formInline.methodName
        if (this.formInline.startEndDate) {
          form.invokeDateFrom = formatDate(this.formInline.startEndDate[0], 'yyyy-MM-dd hh:mm:ss')
          form.invokeDateTo = formatDate(this.formInline.startEndDate[1], 'yyyy-MM-dd hh:mm:ss')
        }
        this.$store.dispatch('getStatistics', form)
      }
    },
    computed: {
      ...mapGetters({
        results: 'statistics',
        chartMethods: 'chartMethods'
      })
    },
    created () {
      this.searchData()
      this.$store.dispatch('getChartMethods', {service: this.$route.query.service});
    }
  }
</script>

<style scoped>
  .provider {
    color: #1c8de0;
    font-weight: bold;
  }

  .consumer {
    color: #dea726;
    font-weight: bold;
  }
</style>
