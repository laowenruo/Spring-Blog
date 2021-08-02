<template>
  <v-footer app padless absolute v-if="!this.isMessage">
    <div class="footer-wrap">
      <div>©2021 - {{ new Date().getFullYear() }} By Mashiro</div>
      <div style="width:300px;margin:0 auto; padding:20px 0;">
        <img src="img/beian.png"/><a target="_blank" href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=36040302000282" style="display:inline-block;text-decoration:none;height:20px;line-height:20px;"><img src="" style="float:left;"/><p style="float:left;height:20px;line-height:20px;margin: 0px 0px 0px 5px; color:#ffffff;">赣公网安备 36040302000282号</p></a>
      </div>
    </div>
  </v-footer>
</template>

<script>
export default {
  computed: {
    isMessage() {
      return this.$route.path === "/message";
    }
  }
};
</script>

<style scoped>
.footer-wrap {
  width: 100%;
  line-height: 2;
  position: relative;
  padding: 40px 20px;
  color: #eee;
  font-size: 14px;
  text-align: center;
  background: linear-gradient(-45deg, #ee7752, #ce3e75, #23a6d5, #23d5ab);
  background-size: 400% 400%;
  animation: Gradient 10s ease infinite;
}
.footer-wrap a {
  color: #eee !important;
}
@keyframes Gradient {
  0% {
    background-position: 0 50%;
  }
  50% {
    background-position: 100% 50%;
  }
  100% {
    background-position: 0 50%;
  }
}
</style>
