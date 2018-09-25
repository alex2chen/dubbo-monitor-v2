// root acctions,
// use case:  this.$store.dispatch('show_loading')
import * as TYPES from './mutations_types'
export const actions = {
  [TYPES.showLoading]({commit}) {
    return new Promise((resolve, reject) => {
      commit(TYPES.SHOW_LOADING)
      resolve()
    })
  },
  [TYPES.hideLoading]({commit}) {
    return new Promise((resolve, reject) => {
      commit(TYPES.HIDE_LOADING)
      resolve()
    })
  }
}
