<template>
  <div>
    <el-row>
      <el-col :span="24">
        <v-head></v-head>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="4">
        <v-sidebar></v-sidebar>
      </el-col>
      <el-col :span="20">
        <div class="grid-content bg-purple-light">
          <div class="content" v-loading.lock="this.$store.state.ajaxLoading" element-loading-text="玩命加载中...">
            <transition name="move" mode="out-in">
              <router-view></router-view>
            </transition>
          </div>
        </div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <v-footer></v-footer>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import vHead from '../common/Header.vue';
  import vSidebar from '../common/Sidebar.vue';
  import vFooter from '../common/Footer.vue';
  export default {
    components: {
      vHead, vSidebar, vFooter
    }
  }
</script>
<style>
  .el-row {
    margin-top: 0px;

  }

  .el-col {
    border-radius: 4px;
  }

  .bg-purple-dark {
    background: #99a9bf;
  }

  .bg-purple {
    background: #d3dce6;
  }

  .bg-purple-light {
    background: #e5e9f2;
  }

  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }

  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }

  .content {
    background-color: #ffffff;
    min-height: 500px;
    padding: 30px;
  }
</style>
