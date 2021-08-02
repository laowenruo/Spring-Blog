import axios from "@/plugins/axios";

export function getArticleListByCategoryName(categoryName, pageNum) {
  return axios({
    url: "categoriesBlog",
    method: "GET",
    params: {
      categoryName,
      pageNum
    }
  });
}

export function getArticleListByTagName(tagName, pageNum) {
  return axios({
    url: "tagBlog",
    method: "GET",
    params: {
      tagName,
      pageNum
    }
  });
}

export function getBlogDetail(id){
  return axios({
    url: "blogDetail",
    method: "GET",
    params: {
      id
    }
  });
}

export function listNewestArticles(){
  return axios({
    url: "articlesnewest",
    method: "GET",
    params: {}
  });
}
