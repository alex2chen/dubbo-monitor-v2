import Mock from 'mockjs'

import { appList } from './mock.app'
import { chartList } from './mock.charts'
function addToMock (list) {
  list.forEach(n => {
    Mock.mock(n.path, n.data)
  })
}
if (process.env.USE_MOCK) {
  addToMock(appList)
  addToMock(chartList)
}
export default Mock
