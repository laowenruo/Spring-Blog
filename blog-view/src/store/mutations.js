import {
  SAVE_SITE_INFO,
  SAVE_INTRODUCTION,
  SAVE_COMMENT_RESULT,
  SET_COMMENT_QUERY_PAGE_NUM,
  SET_PARENT_COMMENT_ID,
  RESET_COMMENT_FORM,
  RESTORE_COMMENT_FORM,
  SET_COMMENT_QUERY_PAGE,
  SET_COMMENT_QUERY_BLOG_ID,
  SET_IS_BLOG_RENDER_COMPLETE,
  SET_BLOG_PASSWORD_DIALOG_VISIBLE,
  SET_BLOG_PASSWORD_FORM,
  SET_FOCUS_MODE,
  SET_IS_BLOG_TO_HOME,
  SAVE_CLIENT_SIZE,
  SAVE_WEB_TITLE_SUFFIX
} from "./mutations-types";

export default {
  login(state, user) {
    state.userId = user.userInfoId;
    state.avatar = user.avatar;
    state.nickname = user.nickname;
    state.intro = user.intro;
    state.webSite = user.webSite;
    state.articleLikeSet = user.articleLikeSet ? user.articleLikeSet : [];
    state.commentLikeSet = user.commentLikeSet ? user.commentLikeSet : [];
    state.email = user.email;
    state.loginType = user.loginType;
  },
  logout(state) {
    state.userId = null;
    state.avatar = null;
    state.nickname = null;
    state.intro = null;
    state.webSite = null;
    state.articleLikeSet = [];
    state.commentLikeSet = [];
    state.email = null;
    state.loginType = null;
  },
  saveLoginUrl(state, url) {
    state.loginUrl = url;
  },
  saveEmail(state, email) {
    state.email = email;
  },
  updateUserInfo(state, user) {
    state.nickname = user.nickname;
    state.intro = user.intro;
    state.webSite = user.webSite;
  },
  updateAvatar(state, avatar) {
    state.avatar = avatar;
  },
  checkBlogInfo(state, blogInfo) {
    state.blogInfo = blogInfo;
  },
  [SAVE_SITE_INFO](state, siteInfo) {
    state.siteInfo = siteInfo;
  },
  [SAVE_INTRODUCTION](state, introduction) {
    state.introduction = introduction;
  },
  [SAVE_COMMENT_RESULT](state, data) {
    state.commentCount = data.count;
    state.commentTotalPage = data.comments.totalPage;
    state.comments = data.comments.list;
  },
  [SET_COMMENT_QUERY_PAGE](state, page) {
    state.commentQuery.page = page;
  },
  [SET_COMMENT_QUERY_BLOG_ID](state, blogId) {
    state.commentQuery.blogId = blogId;
  },
  [SET_COMMENT_QUERY_PAGE_NUM](state, pageNum) {
    state.commentQuery.pageNum = pageNum;
  },
  [SET_PARENT_COMMENT_ID](state, parentCommentId) {
    state.parentCommentId = parentCommentId;
  },
  [RESET_COMMENT_FORM](state) {
    const commentForm = {
      nickname: state.commentForm.nickname,
      email: state.commentForm.email,
      website: state.commentForm.website
    };
    //保存访客信息，下次评论时自动填充表单
    window.localStorage.setItem("commentForm", JSON.stringify(commentForm));
    state.commentForm.content = "";
    state.commentForm.notice = true;
  },
  [RESTORE_COMMENT_FORM](state) {
    const lastForm = JSON.parse(window.localStorage.getItem("commentForm"));
    if (lastForm) {
      state.commentForm.nickname = lastForm.nickname;
      state.commentForm.email = lastForm.email;
      state.commentForm.website = lastForm.website;
    }
  },
  [SET_IS_BLOG_RENDER_COMPLETE](state, ok) {
    state.isBlogRenderComplete = ok;
  },
  [SET_BLOG_PASSWORD_DIALOG_VISIBLE](state, visible) {
    state.blogPasswordDialogVisible = visible;
  },
  [SET_BLOG_PASSWORD_FORM](state, form) {
    state.blogPasswordForm = form;
  },
  [SET_FOCUS_MODE](state, focusMode) {
    state.focusMode = focusMode;
  },
  [SET_IS_BLOG_TO_HOME](state, isBlogToHome) {
    state.isBlogToHome = isBlogToHome;
  },
  [SAVE_CLIENT_SIZE](state, clientSize) {
    state.clientSize = clientSize;
  },
  [SAVE_WEB_TITLE_SUFFIX](state, webTitleSuffix) {
    state.webTitleSuffix = webTitleSuffix;
  }
};
