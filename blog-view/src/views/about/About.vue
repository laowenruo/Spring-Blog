<template>
  <div>
    <!-- banner -->
    <div class="about-banner banner">
      <h1 class="banner-title">关于我</h1>
    </div>
    <!-- 关于我内容 -->
    <v-card class="blog-container">
      <div class="my-wrapper">
        <v-avatar size="110">
          <img
            class="author-avatar"
            src="http://img.mashiro.org.cn/20210622192431.jpg"
            alt="avatar"
          />
        </v-avatar>
      </div>
      <div class="about-content markdown-body" v-html="about.content" />
    </v-card>
  </div>
</template>

<script>
import { getAboutInfo } from "@/api/about";
export default {
  created() {
    this.getAboutContent();
  },
  data: function() {
    return {
      about: {
        title: "",
        musicId: "",
        content: "",
        commentEnabled: "false"
      }
    };
  },
  methods: {
    getAboutContent() {
      getAboutInfo().then(res => {
        this.about = res.data;
      });
    }
  }
};
</script>

<style scoped>
.about-banner {
  background: #49b1f5 no-repeat center center;
}
.about-content {
  word-break: break-word;
  line-height: 1.8;
  font-size: 14px;
}
.my-wrapper {
  text-align: center;
}
.author-avatar {
  transition: all 0.5s;
}
.author-avatar:hover {
  transform: rotate(360deg);
}
</style>
