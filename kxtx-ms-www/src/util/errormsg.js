const TYPES = ['error', 'info', 'warning', 'success']

class Errormsg {
  constructor (type) {
    this.type = type
    this.showClose = true
    this.message = ''
    this.duration = 2000
    this.privData = null
  }

  setType (type) {
    this.type = type
  }

  setShowClose (showClose) {
    this.showClose = showClose
  }

  setMessage (message) {
    this.message = message
  }

  setPriData (value) {
    this.privData = value
  }
}
export default new Errormsg(TYPES[0])
