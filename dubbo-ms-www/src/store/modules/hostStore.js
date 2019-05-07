/* eslint-disable new-cap */
import * as TYPES from '../mutations_types'
import { hostApi } from '../../api'
import baseStore from './baseStore'
import type from '../../util/istype'
const state = {
  hosts: [],
  hostConsumers: {},
  hostProviders: {}
}
const getters = {
  hosts: state => state.hosts,
  hostConsumers: state => state.hostConsumers,
  hostProviders: state => state.hostProviders
}
const hostEvent = {
  [TYPES.HOST_LIST_SUC](state, payload) {
    state.hosts = payload
  },
  [TYPES.HOST_CONSUMERS_SUC](state, payload) {
    state.hostConsumers = payload
  },
  [TYPES.HOST_PROVIDERS_SUC](state, payload) {
    state.hostProviders = payload
  }
}
class hostStore extends baseStore {
  constructor () {
    super()
    this.state = state
    this.mutations = hostEvent
    this.getters = getters
  }

  get actions () {
    let getHosts = ({commit, state}, form) => {
      super.requestHandle(hostApi.listHosts, commit, TYPES.HOST_LIST_SUC, TYPES.ALERT_MSG, form, Array.isArray)
    }
    let getConsumersByHost = ({commit, state}, form) => {
      super.requestHandle(hostApi.getHostConsumers, commit, TYPES.HOST_CONSUMERS_SUC, TYPES.ALERT_MSG, form, type.isObject)
    }
    let getProvidersByHost = ({commit, state}, form) => {
      super.requestHandle(hostApi.getHostProviders, commit, TYPES.HOST_PROVIDERS_SUC, TYPES.ALERT_MSG, form, type.isObject)
    }
    return {
      [TYPES.getHosts]: getHosts,
      [TYPES.getConsumersByHost]: getConsumersByHost,
      [TYPES.getProvidersByHost]: getProvidersByHost
    }
  }
}
export default new hostStore()
