<template>
  <div>
    <!-- banner -->
    <div class="category-banner banner">
      <h1 class="banner-title">分类</h1>
    </div>
    <!-- 分类列表 -->
    <v-card class="blog-container">
      <div class="category-title">分类 - {{ count }}</div>
      <ul class="category-list">
        <li
          class="category-list-item"
          v-for="item of categoryList"
          :key="item.id"
        >
          <router-link :to="'/categories/' + item.name">
            {{ item.name }}
<!--            <span class="category-count">({{ count }})</span>-->
          </router-link>
        </li>
      </ul>
    </v-card>
  </div>
</template>

<script>
import { getCategoryList } from "@/api/category";
export default {
  created() {
    this.listCategories();
  },
  data: function() {
    return {
      categoryList: [],
      count: 0
    };
  },
  methods: {
    listCategories() {
      getCategoryList()
        .then(res => {
          if (res.code === 200) {
            this.categoryList = res.data.categoryList;
            this.count = res.data.count;
          } else {
            this.msgError(res.message);
          }
        })
        .catch(() => {
          this.msgError("请求失败");
        });
    }
  }
};
</script>

<style scoped>
.category-banner {
  background: #65a386 no-repeat center center;
}
.category-title {
  text-align: center;
  font-size: 36px;
  line-height: 2;
}
@media (max-width: 759px) {
  .category-title {
    font-size: 28px;
  }
}
.category-list {
  margin: 0 1.8rem;
  list-style: none;
}
.category-list-item {
  padding: 8px 1.8rem 8px 0;
}
.category-list-item:before {
  display: inline-block;
  position: relative;
  left: -0.75rem;
  width: 12px;
  height: 12px;
  border: 0.2rem solid #49b1f5;
  border-radius: 50%;
  background: #fff;
  content: "";
  transition-duration: 0.3s;
}
.category-list-item:hover:before {
  border: 0.2rem solid #ff7242;
}
.category-list-item a:hover {
  transition: all 0.3s;
  color: #8e8cd8;
}
.category-list-item a:not(:hover) {
  transition: all 0.3s;
}
.category-count {
  margin-left: 0.5rem;
  font-size: 0.75rem;
  color: #858585;
}
</style>
