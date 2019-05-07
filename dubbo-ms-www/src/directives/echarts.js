/**
 * 1.fixed page resize
 * 2.fixed page one instances
 */

import Vue from 'vue'
import echarts from 'echarts'
export default {
  deep: true,
  params: ['loading'],
  // paramWatchers: {
  //   loading: (val, oldVal) => {
  //     var self = this
  //     if (val === true) {
  //       self.instance.showLoading()
  //     } else {
  //       self.instance.hideLoading()
  //     }
  //   }
  // },
  bind: (el, binding, vnode) => {
    let page = this
    // var self = this
    let self = el
    Vue.nextTick(function () {
      // init echarts instance
      self.instance = echarts.init(el)
      // show loading animation
      if (page.a.params.loading === true) {
        // self.instance.showLoading()
      }
      // auto resize
      // var resizeEvent = new Event('resize');  ie9,10 no work
      page.resizeEventHandler = function () {
        self.instance.resize()
      }
      // _this.el.addEventListener('resize', _this.resizeEventHandler, false)
      if (window.attachEvent) {
        window.attachEvent('onresize', page.resizeEventHandler)
      } else {
        window.addEventListener('resize', page.resizeEventHandler, false)
      }
    })
  },
  update: (el, binding) => {
    // var self = this
    let self = el
    let options = binding.value
    // console.log(self.instance)
    // console.log(options)
    Vue.nextTick(function () {
      self.instance.clear() // echarts Redraw
      self.instance.setOption(options)
    })
  },
  unbind: (el) => {
    let self = el
    // console.warn(self)
    self.instance.dispose()
    if (window.attachEvent) {
      window.detachEvent('onresize', self.resizeEventHandler)
    } else {
      window.removeEventListener('resize', self.resizeEventHandler, false)
    }
  }
}
