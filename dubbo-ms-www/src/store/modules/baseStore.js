import * as TYPES from '../mutations_types'
import errorMsg from '../../util/errormsg'
import { deprecate } from 'core-decorators'
export default class baseStore {
  constructor () {
    this.sucMutation = ''
    this.failMutation = ''
    this.errormsg = errorMsg
  }

  get getSucMutation () {
    return this.sucMutation
  }

  set setSucMutation (value) {
    this.sucMutation = value
  }

  get getFailMutation () {
    return this.failMutation
  }

  set setFailMutation (value) {
    this.failMutation = value
  }

  set setErrormsg (error) {
    this.errormsg = error
  }

  get getErrorMsg () {
    return this.errormsg
  }

  requestHandle (apiFunc, commit, sucMutation, failMutation, form, validatorFunc) {
    this.errormsg.setMessage('')
    this.sucMutation = sucMutation
    this.failMutation = failMutation
    commit(TYPES.SHOW_LOADING)
    apiFunc(form).then((payload) => {
      commit(TYPES.HIDE_LOADING)
      this.execSucHandle(commit, payload, validatorFunc)
    }, (error) => {
      commit(TYPES.HIDE_LOADING)
      this.errormsg.setMessage(`请求失败: ${error}`)
      this.execFailHandle(commit)
    })
    return this.errormsg
  }

  execSucHandle (commit, payload, validatorFunc) {
    if (validatorFunc(payload)) {
      if (this.sucMutation !== '' && this.sucMutation != null) {
        commit(this.sucMutation, payload)
      }
    } else {
      this.errormsg.setMessage('请求失败: 服务器响应格式有误！')
      this.execFailHandle(commit)
    }
  }

  @deprecate(this)
  execFailHandle (commit) {
    if (this.failMutation !== '' && this.failMutation != null) {
      commit(this.failMutation, this.errormsg)
    }
  }
}

