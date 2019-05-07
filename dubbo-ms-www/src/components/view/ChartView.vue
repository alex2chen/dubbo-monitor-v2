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
          <el-date-picker v-model="formInline.startEndDate" type="daterange" :picker-options="pickerOptions"
                          placeholder="选择日期范围"></el-date-picker>
        </div>
      </el-form-item>
      <el-form-item label="方法:">
        <template>
          <el-select v-model="formInline.serviceName" filterable placeholder="请选择">
            <el-option v-for="item in chartMethods" :key="item.value" :label="item.value"
                       :value="item.value"></el-option>
          </el-select>
        </template>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="searchData">查询</el-button>
      </el-form-item>
    </el-form>
    <el-row>
      <template v-for="item in this.chartMethods">
        <el-col :span="12">
          <div class="chart" :id="'chartQps'+item.value"/>
        </el-col>
        <el-col :span="12">
          <div class="chart" :id="'chartArt'+item.value"/>
        </el-col>
      </template>
    </el-row>
    <!--<el-col :span="12">-->
    <!--<div class="chart" id="chartQps1"/>-->
    <!--</el-col>-->
    <!--<el-col :span="12">-->
    <!--<div class="chart" id="chartArt2"/>-->
    <!--</el-col>-->
    <!--<el-col :span="12">-->
    <!--<div class="chart" id="chartQps3"/>-->
    <!--</el-col>-->
    <!--<el-col :span="12">-->
    <!--<div class="chart" id="chartArt4"/>-->
    <!--</el-col>-->
    <!--<el-col :span="12">-->
    <!--<div class="chart" id="chartQps5"/>-->
    <!--</el-col>-->
    <!--<el-col :span="12">-->
    <!--<div class="chart" id="chartArt6"/>-->
    <!--</el-col>-->
    <!--</el-row>-->

  </div>
</template>
<script type="text/ecmascript-6">
  /* eslint-disable no-trailing-spaces */
  import Vue from 'vue'
  import { mapGetters } from 'vuex'
  import echarts from 'echarts'
  import { formatDate } from '@/util/date'
  export default {
    data () {
      return {
        formInline: {
          startEndDate: '',
          serviceName: ''
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
        chartMethods: 'chartMethods',
        chartDatas: 'chartData'
      })
    },
    created() {
      let endDate = new Date()
      let startDate = new Date()
      startDate.setDate(endDate.getDate() - 1)
      this.formInline.startEndDate = [startDate, endDate]
      this.loadMethods()
    },
    mounted () {
    },
    methods: {
      searchData: function (event) {
        this.clearChart()
        let form = {service: this.$route.query.service}
        form.method = this.formInline.serviceName
        if (this.formInline.startEndDate) {
          if (this.formInline.startEndDate[0] && this.formInline.startEndDate[1]) {
            form.invokeDateFrom = formatDate(this.formInline.startEndDate[0], 'yyyy-MM-dd hh:mm:ss')
            form.invokeDateTo = formatDate(this.formInline.startEndDate[1], 'yyyy-MM-dd hh:mm:ss')
          }
        } else {
          let date = new Date()
          form.invokeDateFrom = formatDate(date, 'yyyy-MM-dd') + ' 00:00:00'
          form.invokeDateTo = formatDate(date, 'yyyy-MM-dd hh:mm:ss')
        }
        this.$store.dispatch('loadChartData', form).then((value) => {
          if (value.message !== '') {
            this.$message.error(value.message);
          } else {
            Vue.nextTick(() => {
              // this.drawChart(this.chartDatas)
              this.drawChart(value.privData)
            })
          }
        })
      },
      loadMethods() {
        this.$store.dispatch('getChartMethods', {service: this.$route.query.service});
      },
      drawChart(data) {
        let chart = data
        if (chart) {
          // let index = 0;
          chart.map(item => {
            // index++
            // let elId = 'chart' + item.chartType + index
            let elId = 'chart' + item.chartType + item.method
            let el = document.getElementById(elId)
            if (el) {
              let chartObj = echarts.init(el)
              console.log(item.seriesData)
              chartObj.setOption({
                title: {
                  text: item.title + ':' + item.method,
                  subtext: item.subtext
                },
                tooltip: {
                  trigger: 'item',
                  formatter: function (params) {
                    let date = new Date(params.data[0]);
                    return formatDate(date, 'yyyy-MM-dd hh:mm:ss') + '<br/>' + params.seriesName + ': ' + params.data[1]
                  }
                },
                toolbox: {
                  show: true,
                  feature: {
                    mark: {show: true},
                    dataView: {show: true, readOnly: false},
                    restore: {show: true},
                    saveAsImage: {show: true}
                  }
                },
                legend: {
                  data: ['provider', 'consumer']
                },
                xAxis: {
                  type: 'time'
                },
                yAxis: {
                  type: 'value'
                },
                series: item.seriesData
              })
            }
          });
        }
      },
      clearChart() {
        if (this.chartMethods) {
          this.chartMethods.map(item => {
            if (item !== this.formInline.serviceName) {
              let el = document.getElementById('chartQps' + item.value)
              if (el) el.innerHTML = ''
              let el2 = document.getElementById('chartArt' + item.value)
              if (el2) el2.innerHTML = ''
            }
          })
        }
      }
    }
  }
</script>
<style>
  .chart {
    width: 100%;
    height: 400px;
  }
</style>
