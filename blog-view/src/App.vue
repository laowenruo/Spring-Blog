<template>
  <v-app id="app">
    <!-- 导航栏 -->
    <TopNavBar></TopNavBar>
    <!-- 侧边导航栏 -->
    <SideNavBar></SideNavBar>
    <!-- 内容 -->
    <v-content>
      <router-view :key="$route.fullPath" />
    </v-content>
    <!-- 页脚 -->
    <Footer></Footer>
    <!-- 返回顶部 -->
    <BackTop></BackTop>
    <!-- 搜索模态框 -->
    <searchModel></searchModel>
  </v-app>
</template>

<script>
import TopNavBar from "./components/layout/TopNavBar";
import SideNavBar from "./components/layout/SideNavBar";
import Footer from "./components/layout/Footer";
import BackTop from "./components/BackTop";
import searchModel from "./components/model/SearchModel";
import { SAVE_WEB_TITLE_SUFFIX } from "@/store/mutations-types";
import { getWebTitleSuffix } from "@/api/login";
export default {
  components: {
    TopNavBar,
    SideNavBar,
    Footer,
    BackTop,
    searchModel
  },
  created() {
    this.getWebTitleSuffix();
  },
  methods: {
    getWebTitleSuffix() {
      getWebTitleSuffix().then(res => {
        if (res.code === 200) {
          this.$store.commit(SAVE_WEB_TITLE_SUFFIX, res.data.webTitleSuffix);
          document.title = this.$route.meta.title + res.data.webTitleSuffix;
        }
      });
    }
  }
};
</script>
