/* eslint-disable new-cap */
import * as TYPES from '../mutations_types'
import { registryApi } from '../../api'
import baseStore from './baseStore'
import type from '../../util/istype'
const state = {
  registrys: [],
  registryRegs: [],
  registrySubs: []
}
const getters = {
  registrys: state => state.registrys,
  registryRegs: state => state.registryRegs,
  registrySubs: state => state.registrySubs
}
const registryEvent = {
  [TYPES.REGISTY_GET_LIST_SUC](state, payload) {
    state.registrys = payload
  },
  [TYPES.REGISTY_GET_REG_SUC](state, payload) {
    state.registryRegs = payload
  },
  [TYPES.REGISTY_GET_SUBS_SUC](state, payload) {
    state.registrySubs = payload
  }
}
class serviceStore extends baseStore {
  constructor () {
    super()
    this.state = state
    this.mutations = registryEvent
    this.getters = getters
    // this.actions = registryAction
  }

  get actions () {
    let getRegistry = ({commit, state}, form) => {
      super.requestHandle(registryApi.listRegistry, commit, TYPES.REGISTY_GET_LIST_SUC, TYPES.ALERT_MSG, form, Array.isArray)
    }
    let getRegByRegistry = ({commit, state}, form) => {
      super.requestHandle(registryApi.listRegByRegistry, commit, TYPES.REGISTY_GET_REG_SUC, TYPES.ALERT_MSG, form, type.isArray)
    }
    let getSubsByRegistry = ({commit, state}, form) => {
      super.requestHandle(registryApi.listSubsRegiseter, commit, TYPES.REGISTY_GET_SUBS_SUC, TYPES.ALERT_MSG, form, type.isArray)
    }
    return {
      [TYPES.getRegistry]: getRegistry,
      [TYPES.getRegByRegistry]: getRegByRegistry,
      [TYPES.getSubsByRegistry]: getSubsByRegistry
    }
  }
}
export default new serviceStore()

