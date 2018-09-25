/* eslint-disable new-cap */
import * as TYPES from '../mutations_types'
import { systemApi } from '../../api'
import errormsg from '../../util/errormsg'
const state = {
  systems: []
}
const getters = {
  systems: state => state.systems
}
// TODO:需改造(重用性)
const systemAction = {
  [TYPES.getSystem]({commit, state}, form) {
    commit(TYPES.SHOW_LOADING)
    systemApi.listSystem(form).then((payload) => {
      commit(TYPES.HIDE_LOADING)
      // rootState.ajaxLoading = false
      if (Array.isArray(payload)) {
        commit(TYPES.SYSTEM_QUERY_LIST_SUC, payload)
      } else {
        errormsg.setMessage('请求失败: 服务器响应格式有误！')
        commit(TYPES.ALERT_MSG, errormsg)
      }
    }, (error) => {
      commit(TYPES.HIDE_LOADING)
      errormsg.setMessage('请求失败: ' + error)
      commit(TYPES.ALERT_MSG, errormsg)
    })
  }
}
const systemEvent = {
  [TYPES.SYSTEM_QUERY_LIST_SUC](state, payload) {
    state.systems = payload
  }
}
class systemStore {
  constructor () {
    this.state = state
    this.mutations = systemEvent
    this.getters = getters
    this.actions = systemAction
  }
}
export default new systemStore()

