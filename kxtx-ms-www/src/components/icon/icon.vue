<template>
  <!--<img :class="'kx-icon-' + name"/>-->
  <img :src="typeImg" alt="详情"/>
</template>

<script>
  export default {
    name: 'KxIcon',
    computed: {
      typeImg() {
        return require(`./assets/${this.name}.png`)
      }
    },
    props: {
      name: String
    }
  };
</script>
