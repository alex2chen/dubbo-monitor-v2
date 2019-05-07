<template>

  <div class="grid-content bg-purple sidebar">
    <div>
      <el-menu @open="handleOpen" @close="handleClose" class="el-menu-vertical-demo"
               theme="dark" default-active="/home" unique-opened router>
        <template v-for="(item,index) in $router.options.routes" v-if="!item.hidden">
          <el-submenu :index="index+''">
            <template slot="title"><i :class="item.iconcls"></i>{{item.name}}</template>
            <template v-for="child in item.children" v-if="item.children">
              <el-menu-item :index="child.path" :key="child.path" v-if="!child.hidden">
                {{child.name}}
              </el-menu-item>
            </template>
          </el-submenu>
        </template>
        <!--<el-submenu index="1">-->
        <!--<template slot="title"><i class="el-icon-menu"></i>Host</template>-->
        <!--<el-menu-item index="applications">Applications</el-menu-item>-->
        <!--<el-menu-item index="service">Services</el-menu-item>-->
        <!--</el-submenu>-->
      </el-menu>
    </div>
  </div>
</template>

<script>
  export default {
    computed: {
      onRoutes () {
        return this.$route.path.replace('/', '');
      }
    },
    methods: {
      handleOpen(key, keyPath) {
        // console.log(key, keyPath);
      },
      handleClose(key, keyPath) {
        // console.log(key, keyPath);
      }
    }
  }
</script>

<style scoped>
  .sidebar {
    background: #324157;
    height: 100%;
    width: 100%;
  }
</style>
