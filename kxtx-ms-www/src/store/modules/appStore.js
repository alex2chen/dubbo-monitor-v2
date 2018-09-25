/* eslint-disable new-cap */
import * as TYPES from '../mutations_types'
import { appApi } from '../../api'
import baseStore from './baseStore'
let baseStoreIntance = new baseStore()
const state = {
  appList: [],
  appConsumerList: [],
  appProviderList: [],
  appDependList: []
}
const getters = {
  appList: state => state.appList,
  appConsumerList: state => state.appConsumerList,
  appProviderList: state => state.appProviderList,
  appDependList: state => state.appDependList
}
const actions = {
  async [TYPES.getAppList]({commit, state, rootState}, application) {
    return await baseStoreIntance.requestHandle(appApi.listApps, commit, TYPES.APP_LIST_SUC, TYPES.APP_LIST_FAIL, application, Array.isArray)
  },
  [TYPES.getConsumersByApp]({commit, state, rootState}, application) {
    baseStoreIntance.requestHandle(appApi.listAppConsumers, commit, TYPES.APP_LIST_CONSUMERS_SUC, TYPES.ALERT_MSG, application, Array.isArray)
  },
  [TYPES.getProvidersByApp]({commit, state, rootState}, application) {
    baseStoreIntance.requestHandle(appApi.listAppProviders, commit, TYPES.APP_LIST_PROVIDERS_SUC, TYPES.ALERT_MSG, application, Array.isArray)
  },
  [TYPES.getDependsByApp]({commit, state, rootState}, application) {
    baseStoreIntance.requestHandle(appApi.listAppDepends, commit, TYPES.APP_LIST_DEPENDS_SUC, TYPES.ALERT_MSG, application, Array.isArray)
  }
}
const mutations = {
  [TYPES.APP_LIST_SUC] (state, payload) {
    state.appList = payload
  },
  [TYPES.APP_LIST_FAIL] (state, error) {
  },
  [TYPES.APP_LIST_CONSUMERS_SUC] (state, payload) {
    state.appConsumerList = payload
  },
  [TYPES.APP_LIST_PROVIDERS_SUC] (state, payload) {
    state.appProviderList = payload
  },
  [TYPES.APP_LIST_DEPENDS_SUC] (state, payload) {
    state.appDependList = payload
  }
}
export default {
  state,
  getters,
  actions,
  mutations
}
