/* eslint-disable no-unused-vars */
import * as url from './urls'
import axios from 'axios'
const AUTH_BASE = {
  username: 'admin',
  password: 'admin'
}
const AUTH_TOKEN = {
  host: '127.0.0.1',
  port: 9000,
  auth: {
    username: 'admin',
    password: 'admin'
  }
}
axios.defaults.baseURL = url.webBaseUrl
// axios.defaults.headers.common['Authorization'] = AUTH_BASE
// axios.defaults.headers.common['Proxy-Authorization'] = AUTH_TOKEN
export const appApi = {
  listApps(form) {
    return axios.get(url.appUrl, {params: form}).then((response) => {
      return response.data
    })
  },
  listAppConsumers(form) {
    return axios.get(url.appConsumersUrl, {params: form}).then((response) => {
      return response.data
    })
  },
  listAppProviders(form) {
    return axios.get(url.appProvidersUrl, {params: form}).then((response) => {
      return response.data
    })
  },
  listAppDepends(form) {
    return axios.get(url.appDependenciesUrl, {params: form}).then((response) => {
      return response.data
    })
  }
}
export const chartApi = {
  listChart(form) {
    return axios.get(url.chartLoadMethods, {params: form}).then((response) => {
      return response.data
    })
  },
  getLoadData(form) {
    return axios.post(url.chartLoadDataUrl, form).then((response) => {
      return response.data
    })
  },
  getTopData(form) {
    return axios.post(url.chartLoadTopData, form).then((response) => {
      return response.data
    })
  }
}
export const hostApi = {
  listHosts(form) {
    return axios.get(url.hostUrl).then((response) => {
      return response.data
    })
  },
  getHostConsumers(form) {
    return axios.get(url.hostConsumersUrl, {params: form}).then((response) => {
      return response.data
    })
  },
  getHostProviders(form) {
    return axios.get(url.hostProvidersUrl, {params: form}).then((response) => {
      return response.data
    })
  }
}
export const registryApi = {
  listRegistry(form) {
    return axios.get(url.registryUrl).then((response) => {
      return response.data
    })
  },
  listRegByRegistry(form) {
    return axios.get(url.registryGetRegUrl, {params: form}).then((response) => {
      return response.data
    })
  },
  listSubsRegiseter(form) {
    return axios.get(url.registryGetSubsUrl, {params: form}).then((response) => {
      return response.data
    })
  },
  cancelRegiseter(form) {
    return axios.get(url.registryCancelRegUrl, {params: form}).then((response) => {
      return response.data
    })
  },
  cancelSubscribe(form) {
    return axios.get(url.registryCancelSubsUrl, {params: form}).then((response) => {
      return response.data
    })
  }
}
export const serverApi = {
  listServer(form) {
    return axios.get(url.serverUrl).then((response) => {
      return response.data
    })
  },
  getClientsByServer(form) {
    return axios.get(url.serverClientsUrl, {params: form}).then((response) => {
      return response.data
    })
  }
}
export const serviceApi = {
  listService(form) {
    return axios.get(url.serviceUrl).then((response) => {
      return response.data
    })
  },
  listConsumersByService(form) {
    return axios.get(url.serviceConsumersUrl, {params: form}).then((response) => {
      return response.data
    })
  },
  listProvidersByService(form) {
    return axios.get(url.serviceProvidersUrl, {params: form}).then((response) => {
      return response.data
    })
  }
}
export const statisticApi = {
  getStatistic(form) {
    return axios.post(url.statisticUrl, form).then((response) => {
      return response.data
    })
  }
}
export const statusApi = {
  listStatus(form) {
    return axios.get(url.statusUrl).then((response) => {
      return response.data
    })
  }
}
export const systemApi = {
  listSystem(form) {
    return axios.get(url.systemUrl).then((response) => {
      return response.data
    })
  }
}
