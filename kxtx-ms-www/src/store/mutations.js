// root mutation
import * as types from './mutations_types'
import Vue from 'vue'
export const mutations = {
  [types.SHOW_LOADING](state) {
    state.ajaxLoading = true
  },

  [types.HIDE_LOADING](state) {
    state.ajaxLoading = false
  },
  [types.ALERT_MSG](state, msgOptions) {
    Vue.prototype.$message({
      message: msgOptions.message,
      type: msgOptions.type,
      duration: msgOptions.duration,
      showClose: msgOptions.showClose
    })
  }
}
