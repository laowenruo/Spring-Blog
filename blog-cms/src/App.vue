<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
import {getWebTitleSuffix} from "@/api/siteSetting";
import {SAVE_WEB_TITLE_SUFFIX} from "@/store/mutations-types";

export default {
  name: 'app',
  created() {
    this.getWebTitleSuffix()
  },
  methods: {
    getWebTitleSuffix() {
      getWebTitleSuffix().then(res => {
        if (res.code === 200) {
          console.log(res.data.webTitleSuffix);
          this.$store.commit(SAVE_WEB_TITLE_SUFFIX, res.data.webTitleSuffix)
          document.title = this.$route.meta.title + res.data.webTitleSuffix
        }
      })
    },
  }
}
</script>
<style>
</style>
