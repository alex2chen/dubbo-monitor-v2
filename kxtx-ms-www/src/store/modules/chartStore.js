/* eslint-disable new-cap */
import * as TYPES from '../mutations_types'
import { chartApi } from '../../api'
import baseStore from './baseStore'
import type from '../../util/istype'
import { override } from 'core-decorators'
const state = {
  chartMethods: [],
  chartData: [],
  chartTopData: []
}
const getters = {
  chartMethods: state => state.chartMethods,
  chartData: state => state.chartData,
  chartTopData: state => state.chartTopData,
  chartTopSuc: state => {
    let data = state.chartTopData[0]
    if (data) {
      let seriesData = []
      data.seriesData[0].data.map(e => {
        seriesData.push(e[0])
      })
      return {
        title: {text: data.title, subtext: data.subtext},
        tooltip: {
          trigger: 'item',
          formatter: function (params) {
            return `${params.name}<br/>调用次数：${params.value}`
          }
        },
        legend: {
          data: data.xAxisCategories
        },
        toolbox: {
          show: true,
          feature: {
            mark: {show: true},
            dataView: {show: true, readOnly: false},
            magicType: {show: true, type: ['line', 'bar']},
            restore: {show: true},
            saveAsImage: {show: true}
          }
        },
        calculable: true,
        xAxis: {
          data: data.xAxisCategories
        },
        yAxis: {
          title: {
            text: data.yAxisTitle
          }
        },
        series: [{
          type: 'bar',
          name: data.seriesData[0].name,
          data: seriesData,
          markLine: {
            data: [
              {type: 'average', name: '平均值'}
            ]
          }
        }]
      }
    }
    return {}
  },
  chartTopFail: state => {
    let data = state.chartTopData[1]
    if (data) {
      let seriesData = []
      data.seriesData[0].data.map(e => {
        seriesData.push(e[0])
      })
      return {
        title: {text: data.title, subtext: data.subtext},
        tooltip: {
          trigger: 'item',
          formatter: function (params) {
            return `${params.name}<br/>调用次数：${params.value}`
          }
        },
        legend: {
          data: data.xAxisCategories
        },
        toolbox: {
          show: true,
          feature: {
            mark: {show: true},
            dataView: {show: true, readOnly: false},
            magicType: {show: true, type: ['line', 'bar']},
            restore: {show: true},
            saveAsImage: {show: true}
          }
        },
        calculable: true,
        xAxis: {
          data: data.xAxisCategories
        },
        yAxis: {
          title: {
            text: data.yAxisTitle
          }
        },
        series: [{
          type: 'bar',
          name: data.seriesData[0].name,
          data: seriesData,
          markLine: {
            data: [
              {type: 'average', name: '平均值'}
            ]
          }
        }]
      }
    }
    return {}
  }
}
const chartEvent = {
  [TYPES.CHART_QUERY_LIST_SUC](state, payload) {
    let methods = []
    if (payload) {
      payload.map(n => {
        methods.push({value: n})
      })
    }
    state.chartMethods = methods
  },
  [TYPES.CHART_LOADDATA_SUC](state, payload) {
    let data = []
    if (payload) {
      payload.map(item => {
        let seriesData = []
        if (item.seriesData) {
          item.seriesData.map(s => {
            s.itemStyle = '{normal:{opacity:0}}'
            s.lineStyle = '{normal:{opacity:0}}'
            s.type = 'line'
            seriesData.push(s)
          })
          item.seriesData = seriesData
        }
        data.push(item)
      })
    }
    state.chartData = data
  },
  [TYPES.CHART_LOADTOPDATA_SUC](state, payload) {
    state.chartTopData = payload
  }
}
class statusStore extends baseStore {
  constructor () {
    super()
    this.state = state
    this.mutations = chartEvent
    this.getters = getters
  }

  @override
  async requestHandle (apiFunc, commit, sucMutation, failMutation, form, validatorFunc) {
    this.getErrorMsg.setMessage('')
    this.setSucMutation = sucMutation
    this.setFailMutation = failMutation
    commit(TYPES.SHOW_LOADING)
    await apiFunc(form).then((payload) => {
      commit(TYPES.HIDE_LOADING)
      this.getErrorMsg.setPriData(payload)
      this.execSucHandle(commit, payload, validatorFunc)
    }, (error) => {
      commit(TYPES.HIDE_LOADING)
      this.getErrorMsg.setMessage(`请求失败: ${error}`)
      this.execFailHandle(commit)
    })
    return this.getErrorMsg
  }

  get actions () {
    let getChartMethods = ({commit, state}, form) => {
      super.requestHandle(chartApi.listChart, commit, TYPES.CHART_QUERY_LIST_SUC, TYPES.ALERT_MSG, form, Array.isArray)
    }
    let loadChartData = ({commit, dispatch, state}, form) => {
      // await super.requestHandle(chartApi.getLoadData, commit, TYPES.CHART_LOADDATA_SUC, null, form, type.isArray)
      return new Promise((resolve, reject) => {
        let msg = this.requestHandle(chartApi.getLoadData, commit, TYPES.CHART_LOADDATA_SUC, null, form, type.isArray)
        resolve(msg)
      })
    }
    let loadTopChartData = ({commit, state}, form) => {
      super.requestHandle(chartApi.getTopData, commit, TYPES.CHART_LOADTOPDATA_SUC, TYPES.ALERT_MSG, form, type.isArray)
    }
    return {
      getChartMethods,
      loadChartData,
      loadTopChartData
    }
  }
}
export default new statusStore()
