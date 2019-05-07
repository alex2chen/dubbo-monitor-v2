import Vue from 'vue'
import Router from 'vue-router'
import Home from 'pathComponent/layout/Main.vue'
// import Content from 'pathComponent/layout/Content.vue'
import NotFound from 'pathComponent/view/404.vue'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      redirect: '/home',
      name: '',
      hidden: true
    },
    {
      path: '/404',
      component: NotFound,
      name: '',
      hidden: true
    },
    {
      path: '*',
      redirect: {path: '/404'},
      name: '',
      hidden: true
    },
    {
      path: '/menu',
      component: Home,
      name: '导航',
      hidden: false,
      iconcls: 'el-icon-menu',
      children: [{
        path: '/home',
        component: resolve => require(['pathComponent/view/Home.vue'], resolve),
        name: '首页',
        hidden: false
      }, {
        path: '/host',
        component: resolve => require(['pathComponent/view/Host.vue'], resolve),
        name: '容器',
        hidden: false
      }, {
        path: '/hostconsumers',
        component: resolve => require(['pathComponent/view/HostConsumers.vue'], resolve),
        name: '',
        hidden: true
      }, {
        path: '/hostproviders',
        component: resolve => require(['pathComponent/view/HostProviders.vue'], resolve),
        name: '',
        hidden: true
      }, {
        path: '/applications',
        component: resolve => require(['pathComponent/view/Application.vue'], resolve),
        name: '应用',
        hidden: false
      }, {
        path: '/appconsumers',
        component: resolve => require(['pathComponent/view/AppConsumers.vue'], resolve),
        name: '',
        hidden: true
      }, {
        path: '/appproviders',
        component: resolve => require(['pathComponent/view/AppProviders.vue'], resolve),
        name: '',
        hidden: true
      }, {
        path: '/appdepends',
        component: resolve => require(['pathComponent/view/AppDepends.vue'], resolve),
        name: '',
        hidden: true
      }, {
        path: '/service',
        component: resolve => require(['pathComponent/view/Service.vue'], resolve),
        name: '服务',
        hidden: false
      }, {
        path: '/serviceconsumer',
        component: resolve => require(['pathComponent/view/ServiceConsumers.vue'], resolve),
        name: '',
        hidden: true
      }, {
        path: '/serviceprovider',
        component: resolve => require(['pathComponent/view/ServiceProviders.vue'], resolve),
        name: '',
        hidden: true
      }, {
        path: '/registy',
        component: resolve => require(['pathComponent/view/Registy.vue'], resolve),
        name: '注册中心',
        hidden: false
      }, {
        path: '/registeredCount',
        component: resolve => require(['pathComponent/view/RegisteredCount.vue'], resolve),
        name: '',
        hidden: true
      }, {
        path: '/subscribedCount',
        component: resolve => require(['pathComponent/view/SubscribedCount.vue'], resolve),
        name: '',
        hidden: true
      }, {
        path: '/server',
        component: resolve => require(['pathComponent/view/Server.vue'], resolve),
        name: '监控容器',
        hidden: false
      }, {
        path: '/serverclients',
        component: resolve => require(['pathComponent/view/ServerClients.vue'], resolve),
        name: '',
        hidden: true
      }, {
        path: '/status',
        component: resolve => require(['pathComponent/view/Status.vue'], resolve),
        name: '依赖组件',
        hidden: false
      }, {
        path: '/system',
        component: resolve => require(['pathComponent/view/System.vue'], resolve),
        name: '系统信息',
        hidden: false
      }, {
        path: '/statiscs',
        component: resolve => require(['pathComponent/view/Statistics.vue'], resolve),
        name: '',
        hidden: true
      }, {
        path: '/report',
        component: resolve => require(['pathComponent/view/Report.vue'], resolve),
        name: 'report',
        hidden: true
      }, {
        path: '/chartview',
        component: resolve => require(['pathComponent/view/ChartView.vue'], resolve),
        name: '',
        hidden: true
      }
      ]
    }
  ]
})
