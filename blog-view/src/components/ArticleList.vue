<template>
  <div>
    <!-- 标签或分类名 -->
    <div :class="categoryOrTag + ' banner'">
      <h1 class="banner-title animated fadeInDown">{{ title }} - {{ name }}</h1>
    </div>
    <div class="article-list-wrapper">
      <v-row>
        <v-col md="4" cols="12" v-for="item of articleList" :key="item.id">
          <!-- 文章 -->
          <v-card class="animated zoomIn article-item-card">
            <div class="article-item-cover">
              <router-link :to="'/articles/' + item.id">
                <!-- 缩略图 -->
                <v-img
                  class="on-hover"
                  width="100%"
                  height="100%"
                  :src="item.firstPicture"
                />
              </router-link>
            </div>
            <div class="article-item-info">
              <!-- 文章标题 -->
              <div>
                <router-link :to="'/articles/' + item.id">
                  {{ item.title }}
                </router-link>
              </div>
              <div style="margin-top:0.375rem">
                <!-- 发表时间 -->
                <v-icon size="20">mdi-clock-outline</v-icon>
                {{ item.createTime | date }}
                <!-- 文章分类 -->
                <router-link
                  :to="'/categories/' + item.category.name"
                  class="float-right"
                >
                  <v-icon>mdi-bookmark</v-icon>{{ item.category.name }}
                </router-link>
              </div>
            </div>
            <!-- 分割线 -->
            <v-divider></v-divider>
            <!-- 文章标签 -->
            <div class="tag-wrapper">
              <router-link
                :to="'/tags/' + tag.name"
                class="tag-btn"
                v-for="tag of item.tags"
                :key="tag.id"
              >
                {{ tag.name }}
              </router-link>
            </div>
          </v-card>
        </v-col>
      </v-row>
      <div class="text-center" v-if="pageSize > 1">
        <v-pagination
          @input="handleCurrentChange"
          v-model="pageNum"
          :length="pageSize"
        ></v-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import { getArticleListByCategoryName } from "@/api/article";
import { getArticleListByTagName } from "@/api/article";
export default {
  created() {
    const path = this.$route.path;
    const categoryName = this.$route.params.name;
    if (path.indexOf("/categories") !== -1) {
      this.title = "分类";
      this.categoryOrTag = "category-banner";
      this.name = categoryName;
    } else {
      this.title = "标签";
      this.categoryOrTag = "tag-banner";
      this.name = categoryName;
    }
    this.getList();
  },
  data: function() {
    return {
      pageNum: 1,
      pageSize: 5,
      articleList: [],
      name: "",
      title: "",
      categoryOrTag: "",
      tags: []
    };
  },
  methods: {
    getList: function() {
      if (this.title === "分类") {
        getArticleListByCategoryName(
          this.$route.params.name,
          this.pageNum
        ).then(res => {
          if (res.code === 200) {
            this.articleList = res.data.blogList.list;
            this.pageSize = res.data.blogList.totalPage;
          } else {
            this.msgError(res.message);
          }
        });
      } else {
        getArticleListByTagName(this.$route.params.name, this.pageNum).then(
          res => {
            if (res.code === 200) {
              this.articleList = res.data.blogList.list;
              this.pageSize = res.data.blogList.totalPage;
            } else {
              this.msgError(res.message);
            }
          }
        );
      }
    },
    //监听页码改变的事件
    handleCurrentChange(newPage) {
      this.pageNum = newPage;
      this.getList();
    }
  }
};
</script>
<style scoped>
.tag-banner {
  background: url(https://cdn.jsdelivr.net/gh/axh2018/axh2018.github.io/medias/banner/6.jpg)
    center center / cover no-repeat;
  background-color: #49b1f5;
}
.category-banner {
  background: url(https://cdn.jsdelivr.net/gh/axh2018/axh2018.github.io/medias/banner/6.jpg)
    center center / cover no-repeat;
  background-color: #49b1f5;
}
@media (min-width: 760px) {
  .article-list-wrapper {
    max-width: 1106px;
    margin: 370px auto 1rem auto;
  }
  .article-item-card:hover {
    transition: all 0.3s;
    box-shadow: 0 4px 12px 12px rgba(7, 17, 27, 0.15);
  }
  .article-item-card:not(:hover) {
    transition: all 0.3s;
  }
  .article-item-card:hover .on-hover {
    transition: all 0.6s;
    transform: scale(1.1);
  }
  .article-item-card:not(:hover) .on-hover {
    transition: all 0.6s;
  }
  .article-item-info {
    line-height: 1.7;
    padding: 15px 15px 12px 18px;
    font-size: 15px;
  }
}
@media (max-width: 759px) {
  .article-list-wrapper {
    margin-top: 230px;
    padding: 0 12px;
  }
  .article-item-info {
    line-height: 1.7;
    padding: 15px 15px 12px 18px;
  }
}
.article-item-card {
  border-radius: 8px !important;
  box-shadow: 0 4px 8px 6px rgba(7, 17, 27, 0.06);
}
.article-item-card a {
  transition: all 0.3s;
}
.article-item-cover {
  height: 220px;
  overflow: hidden;
}
.article-item-card a:hover {
  color: #8e8cd8;
}
.tag-wrapper {
  padding: 10px 15px 10px 18px;
}
.tag-wrapper a {
  color: #fff !important;
}
.tag-btn {
  display: inline-block;
  font-size: 0.725rem;
  line-height: 22px;
  height: 22px;
  border-radius: 10px;
  padding: 0 12px !important;
  background: linear-gradient(to right, #bf4643 0%, #6c9d8f 100%);
  opacity: 0.6;
  margin-right: 0.5rem;
}
</style>
