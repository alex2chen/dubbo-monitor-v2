/* eslint-disable new-cap */
import * as TYPES from '../mutations_types'
import { serviceApi, statisticApi } from '../../api'
import baseStore from './baseStore'
import type from '../../util/istype'
const state = {
  services: [],
  serviceConsumers: [],
  serviceProviders: [],
  statistics: {}
}
const getters = {
  services: state => state.services,
  serviceConsumers: state => state.serviceConsumers,
  serviceProviders: state => state.serviceProviders,
  statistics: state => state.statistics
}
const serviceEvent = {
  [TYPES.SERVICE_QUERY_LIST_SUC](state, payload) {
    state.services = payload
  },
  [TYPES.SERVICE_QUERY_CONSUMERS_SUC](state, payload) {
    state.serviceConsumers = payload
  },
  [TYPES.SERVICE_QUERY_PROVIDERS_SUC](state, payload) {
    state.serviceProviders = payload
  },
  [TYPES.STATISTICS_QUERY_SUC](state, payload) {
    state.statistics = payload
  }
}
class serviceStore extends baseStore {
  constructor () {
    super()
    this.state = state
    this.mutations = serviceEvent
    this.getters = getters
  }

  get actions () {
    let getService = ({commit, state}, form) => {
      super.requestHandle(serviceApi.listService, commit, TYPES.SERVICE_QUERY_LIST_SUC, TYPES.ALERT_MSG, form, Array.isArray)
    }
    let getConsumersByService = ({commit, state}, form) => {
      super.requestHandle(serviceApi.listConsumersByService, commit, TYPES.SERVICE_QUERY_CONSUMERS_SUC, TYPES.ALERT_MSG, form, Array.isArray)
    }
    let getProvidersByService = ({commit, state}, form) => {
      super.requestHandle(serviceApi.listProvidersByService, commit, TYPES.SERVICE_QUERY_PROVIDERS_SUC, TYPES.ALERT_MSG, form, Array.isArray)
    }
    let getStatistics = ({commit, state}, form) => {
      super.requestHandle(statisticApi.getStatistic, commit, TYPES.STATISTICS_QUERY_SUC, TYPES.ALERT_MSG, form, type.isObject)
    }
    return {
      [TYPES.getService]: getService,
      [TYPES.getConsumersByService]: getConsumersByService,
      [TYPES.getProvidersByService]: getProvidersByService,
      [TYPES.getStatistics]: getStatistics
    }
  }
}
export default new serviceStore()

