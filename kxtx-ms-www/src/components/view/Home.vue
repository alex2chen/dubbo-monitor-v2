<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><i class="el-icon-menu"></i> 导航</el-breadcrumb-item>
        <el-breadcrumb-item>Home</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-form :inline="true" :model="formInline" class="query-form">
      <el-form-item label="调用日期:">
        <div class="block">
          <el-date-picker v-model="formInline.startEndDate" :picker-options="pickerOptions" type="datetimerange"
                          placeholder="选择日期范围"></el-date-picker>
        </div>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="searchData">查询</el-button>
      </el-form-item>
    </el-form>
    <div>
      <div class="chart" v-echarts="chartSuc"/>
      <div class="chart" v-echarts="chartFail"/>
    </div>
  </div>
</template>
<script type="text/ecmascript-6">
  /* eslint-disable no-trailing-spaces,no-undef */
  import { mapGetters } from 'vuex'
  import { formatDate } from '@/util/date'
  export default {
    data () {
      return {
        formInline: {
          startEndDate: ''
        },
        pickerOptions: {
          shortcuts: [{
            text: '最近一周',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近三个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit('pick', [start, end]);
            }
          }]
        }
      }
    },
    computed: {
      ...mapGetters({
        chartSuc: 'chartTopSuc',
        chartFail: 'chartTopFail'
      })
    },
    mounted () {
      let endDate = new Date()
      let startDate = new Date()
      startDate.setDate(endDate.getDate() - 1)
      this.formInline.startEndDate = [startDate, endDate]
      this.searchData()
    },
    methods: {
      searchData: function (event) {
        let form = {type: 'provider'}
        console.log(this.formInline.startEndDate)
        if (this.formInline.startEndDate) {
          if (this.formInline.startEndDate[0] && this.formInline.startEndDate[1]) {
            form.invokeDateFrom = formatDate(this.formInline.startEndDate[0], 'yyyy-MM-dd hh:mm:ss')
            form.invokeDateTo = formatDate(this.formInline.startEndDate[1], 'yyyy-MM-dd hh:mm:ss')
          }
        }
        this.$store.dispatch('loadTopChartData', form);
      },
      drawChartSuc() {
//        this.chartSuc = echarts.init(document.getElementById('chartSuc'));
//        this.chartSuc.setOption({
//          title: {text: this.results.data[0].title},
//          tooltip: {},
//          xAxis: {
//            data: this.results.data[0].xAxisCategories
//          },
//          yAxis: this.results.data[0].yAxisTitle,
//          series: this.results.data[0].seriesData
//        })
      }
    }
  }
</script>
<style scoped>
  .chart {
    width: 100%;
    height: 400px;
  }
</style>
