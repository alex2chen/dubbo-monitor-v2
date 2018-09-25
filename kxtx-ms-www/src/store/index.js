/* eslint-disable eol-last,spaced-comment */
import vue from 'vue'
import vuex from 'vuex'
vue.use(vuex)
import state from './states.js'
// import getters from './getters'
import { mutations } from './mutations'
import { actions } from './actions'
import plugins from './plugins'
import appStore from './modules/appStore'
import hostStore from './modules/hostStore'
import systemStore from './modules/systemStore'
import statusStore from './modules/statusStore'
import serviceStore from './modules/serviceStore'
import serverStore from './modules/serverStore'
import registryStore from './modules/registryStore'
import chartStore from './modules/chartStore'
export default new vuex.Store({
  state,
  // getters,
  mutations,
  actions,
  modules: {
    appStore,
    hostStore,
    serviceStore,
    registryStore,
    systemStore,
    serverStore,
    statusStore,
    chartStore
  },
  strict: process.env.NODE_ENV !== 'production', // 使用严格模式(只要状态在mutation 方法外被修改就会抛出错误)
  plugins
})
