/* eslint-disable new-cap */
import * as TYPES from '../mutations_types'
import { statusApi } from '../../api'
import baseStore from './baseStore'
const state = {
  statuses: []
}
const getters = {
  statuses: state => state.statuses
}
const statusEvent = {
  [TYPES.STATUS_QUERY_LIST_SUC](state, payload) {
    state.statuses = payload
  }
}
class statusStore extends baseStore {
  constructor () {
    super()
    this.state = state
    this.mutations = statusEvent
    this.getters = getters
  }
  get actions () {
    let getStatus = ({commit, state}, form) => {
      super.requestHandle(statusApi.listStatus, commit, TYPES.STATUS_QUERY_LIST_SUC, TYPES.ALERT_MSG, form, Array.isArray)
    }
    return {
      [TYPES.getStatus]: getStatus
    }
  }
}
export default new statusStore()
