import * as url from '../api/urls'
const appLists = [{
  name: 'kxtx-dubbo-monitor2',
  owner: 'kxtx',
  organization: '',
  providerCount: 2,
  consumerCount: 1,
  efferentCount: 0,
  afferentCount: 0
}, {
  name: 'kxtx-dubbo-monitor2',
  owner: 'kxtx',
  organization: '',
  providerCount: 2,
  consumerCount: 1,
  efferentCount: 0,
  afferentCount: 0
}]

export const appList = [{
  path: url.webBaseUrl + url.appUrl,
  data: appLists
}
]
