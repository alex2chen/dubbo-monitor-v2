/* eslint-disable no-new,no-unused-vars */

import Vue from 'vue'
/**
 * TODO:后续需优化-按需引入
 */
import ElementUI from 'element-ui'
Vue.use(ElementUI)
import App from './App'
import router from './router'
import store from './store'
Vue.config.productionTip = false
// register directives
import echarts from './directives/echarts'
Vue.directive('echarts', echarts)

import KxIcon from 'pathComponent/icon'
KxIcon.install(Vue)
import mock from './mock'
new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: {App}
})

