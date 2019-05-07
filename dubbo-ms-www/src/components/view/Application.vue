<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><i class="el-icon-menu"></i> 导航</el-breadcrumb-item>
        <el-breadcrumb-item>应用</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <el-form :inline="true" :model="formInline" class="query-form">
      <el-form-item label="应用名:">
        <el-input v-model="formInline.application" placeholder="please input"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="searchData">查询</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="results" stripe border class="table">
      <el-table-column prop="name" label="应用名"></el-table-column>
      <el-table-column prop="owner" label="所属"></el-table-column>
      <el-table-column label="提供方">
        <template scope="scope">
          [{{ scope.row.providerCount }}]
          <router-link v-if="scope.row.providerCount>0"
                       :to="{ path: '/appproviders', query: { application: scope.row.name }}" replace>
            <kx-icon name="link_12_12"/>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column label="消费方">
        <template scope="scope">
          [{{ scope.row.consumerCount }}]
          <router-link v-if="scope.row.consumerCount>0"
                       :to="{ path: '/appconsumers', query: { application: scope.row.name }}" replace>
            <kx-icon name="link_12_12"/>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column label="被引用">
        <template scope="scope">
          [{{ scope.row.efferentCount }}]
          <router-link v-if="scope.row.efferentCount>0"
                       :to="{ path: '/appdepends', query: { application: scope.row.name,reverse:true }}" replace>
            <kx-icon name="link_12_12"/>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column label="依赖">
        <template scope="scope">
          [{{ scope.row.afferentCount }}]
          <router-link v-if="scope.row.afferentCount>0"
                       :to="{ path: '/appdepends', query: { application: scope.row.name,reverse:false }}" replace>
            <kx-icon name="link_12_12"/>
          </router-link>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script type="text/ecmascript-6">
  /* eslint-disable no-trailing-spaces,spaced-comment,spaced-comment */
  //  import { appApi } from '../../api'
  import { mapState, mapMutations, mapGetters } from 'vuex'
  export default {
    data () {
      return {
        formInline: {
          application: ''
        }
      };
    },
    computed: {
      ...mapGetters({
        results: 'appList'// 映射 this.results 为 store.getters.appList
      }),
      ...mapState(['ajaxLoading' // 只能访问rootstate
      ])
    },
    mounted () {
      this.searchData();
    },
    methods: {
      ...mapMutations(['APP_LIST_SUCCESS', 'APP_LIST_FAILURE']),
      searchData (event) {
//      searchData ({state}, event) {
//        this.$store.dispatch('show_loading')
//        appApi.query(this.$store, this.formInline.application).then((payload) => {
//          // loading.close()
//          if (Array.isArray(payload)) {
//            this.APP_QUERY_LIST_SUC(state, payload)
//          } else {
//            let error = {message: '格式有误'}
//            this.APP_QUERY_LIST_FAIL(state, error)
//          }
//        }, (error) => {
//          this.$store.dispatch('hide_loading')
//          this.$message({
//            showClose: true,
//            message: '请求服务器发送错误:' + error,
//            type: 'error'
//          })
//          this.APP_QUERY_LIST_FAIL(state, error)
//        })
        this.$store.dispatch('getAppList', {
          application: this.formInline.application
        }).then((errormsg) => {
          if (errormsg.message !== '') {
            this.$message.error(errormsg.message);
          }
        })
      }

    }
  }
</script>
<style scoped>

</style>
