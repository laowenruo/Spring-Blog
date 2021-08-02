import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/home/Home.vue";
import Login from "@/views/login/Login";
import Article from "../views/article/Article.vue";
import Archive from "../views/archive/Archive.vue";
import Tag from "../views/tag/Tag.vue";
import Category from "../views/category/Category.vue";
import About from "../views/about/About.vue";
import ArticleList from "../components/ArticleList.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    component: Home,
    meta: {
      title: "个人博客"
    }
  },
  {
    path: "/login",
    component: Login,
    meta: {
      title: "登录"
    }
  },
  {
    path: "/articles/:id",
    component: Article
  },
  {
    path: "/archives",
    component: Archive,
    meta: {
      title: "归档"
    }
  },
  {
    path: "/tags",
    component: Tag,
    meta: {
      title: "标签"
    }
  },
  {
    path: "/categories",
    component: Category,
    meta: {
      title: "分类"
    }
  },
  {
    path: "/categories/:name",
    component: ArticleList
  },
  {
    path: "/categories/:id",
    component: ArticleList
  },
  {
    path: "/about",
    component: About,
    meta: {
      title: "关于我"
    }
  },
  {
    path: "/tags/:name",
    component: ArticleList
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;
