/* eslint-disable new-cap */
import * as TYPES from '../mutations_types'
import { serverApi } from '../../api'
import baseStore from './baseStore'
import type from '../../util/istype'
const state = {
  servers: [],
  serverClients: {}
}
const getters = {
  servers: state => state.servers,
  serverClients: state => state.serverClients
}
const serverEvent = {
  [TYPES.SERVER_QUERY_LIST_SUC](state, payload) {
    state.servers = payload
  },
  [TYPES.SERVER_QUERY_CLIENTS_SUC](state, payload) {
    state.serverClients = payload
  }
}
class serviceStore extends baseStore {
  constructor () {
    super()
    this.state = state
    this.mutations = serverEvent
    this.getters = getters
  }

  get actions () {
    let getServer = ({commit, state}, form) => {
      super.requestHandle(serverApi.listServer, commit, TYPES.SERVER_QUERY_LIST_SUC, TYPES.ALERT_MSG, form, Array.isArray)
    }
    let getClientsByServer = ({commit, state}, form) => {
      super.requestHandle(serverApi.getClientsByServer, commit, TYPES.SERVER_QUERY_CLIENTS_SUC, TYPES.ALERT_MSG, form, type.isObject)
    }
    return {
      [TYPES.getServer]: getServer,
      [TYPES.getClientsByServer]: getClientsByServer
    }
  }
}
export default new serviceStore()

