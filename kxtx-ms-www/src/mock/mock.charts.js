import * as url from '../api/urls'
const topData = [
  {
    'title': 'The Top 20 of Invoke Success',
    'xAxisCategories': [
      'test',
      'sayHello',
      'save'
    ],
    'yAxisTitle': '次数',
    'seriesData': [
      {
        'name': 'provider',
        'data': [
          [2],
          [31],
          [3]
        ]
      }
    ],
    'method': null
  },
  {
    'title': 'The Top 20 of Invoke Failure',
    'xAxisCategories': [
      'test',
      'sayHello',
      'save'
    ],
    'yAxisTitle': '次数',
    'seriesData': [
      {
        'name': 'provider',
        'data': [
          [1],
          [2],
          [0]
        ]
      }
    ],
    'method': null
  }
]
const chartData = [{
  'title': 'Requests per second (QPS)',
  'chartType': 'Qps',
  'xAxisCategories': null,
  'yAxisTitle': 't/s',
  'seriesData': [{'name': 'provider', 'data': [[1.497860853028E12, 0.2667]]}, {
    'name': 'consumer',
    'data': [[1.497860852709E12, 0.25]]
  }],
  'method': 'sayHello'
}, {
  'title': 'Average response time (ms)',
  'chartType': 'Art',
  'xAxisCategories': null,
  'yAxisTitle': 'ms/t',
  'seriesData': [{'name': 'provider', 'data': [[1.497860853028E12, 123.0]]}, {
    'name': 'consumer',
    'data': [[1.497860852709E12, 1565.0]]
  }],
  'method': 'sayHello'
}, {
  'title': 'Requests per second (QPS)',
  'chartType': 'Qps',
  'xAxisCategories': null,
  'yAxisTitle': 't/s',
  'seriesData': [{'name': 'provider', 'data': [[1.497860853028E12, 0.25]]}, {
    'name': 'consumer',
    'data': [[1.497860852709E12, 0.25]]
  }],
  'method': 'sayBye'
}, {
  'title': 'Average response time (ms)',
  'chartType': 'Art',
  'xAxisCategories': null,
  'yAxisTitle': 'ms/t',
  'seriesData': [{'name': 'provider', 'data': [[1.497860853028E12, 2.0]]}, {
    'name': 'consumer',
    'data': [[1.497860852709E12, 63.0]]
  }],
  'method': 'sayBye'
}]
export const chartList = [{
  path: url.webBaseUrl + url.chartLoadTopData,
  data: topData
}, {
  path: url.webBaseUrl + url.chartLoadDataUrl,
  data: chartData
}
]
