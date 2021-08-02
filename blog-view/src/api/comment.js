import axios from "@/plugins/axios";

export function getCommentsList(page, blogId, pageNum, pageSize) {
  return axios({
    url: "comments",
    method: "GET",
    params: {
      page,
      blogId,
      pageNum,
      pageSize
    }
  });
}

export function getCommentsListByParentCommentId(parentCommentId) {
  return axios({
    url: "/comments/reply",
    method: "GET",
    params: {
      parentCommentId
    }
  });
}

export function submitComment(token, form) {
  return axios({
    url: "comment",
    method: "POST",
    headers: {
      Authorization: token
    },
    data: {
      ...form
    }
  });
}
