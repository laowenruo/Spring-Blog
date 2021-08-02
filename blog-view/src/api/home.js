import axios from "@/plugins/axios";

export function getBlogInfo() {
  return axios({
    url: "/",
    method: "GET",
    params: {}
  });
}

export function getBlogList(pageNum) {
  return axios({
    url: "indexBlogList",
    method: "GET",
    params: {
      pageNum
    }
  });
}
