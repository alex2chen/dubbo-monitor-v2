/**
 * 类型校验
 * type.isObject({}) // true
 * type.isNumber(NaN) // true
 * type.isRegExp(/abc/) // true
 */
let type = function (o) {
  var s = Object.prototype.toString.call(o)
  return s.match(/\[object (.*?)\]/)[1].toLowerCase()
}
const types = ['Null', 'Undefined', 'Object', 'Array', 'String', 'Number', 'Boolean', 'Function', 'RegExp', 'NaN', 'Infinite']
types.forEach(function (t) {
  type['is' + t] = function (o) {
    return type(o) === t.toLowerCase()
  }
})
export default type
