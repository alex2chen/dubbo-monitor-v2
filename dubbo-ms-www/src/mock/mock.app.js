import * as url from '../api/urls'
const appLists = [{
  name: 'dubbo-monitor2',
  owner: 'xxx',
  organization: '',
  providerCount: 2,
  consumerCount: 1,
  efferentCount: 0,
  afferentCount: 0
}, {
  name: 'dubbo-monitor2',
  owner: 'xxx',
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
