<template>
  <div>
    <div class="comment-title"><i class="iconfont iconpinglunzu" />评论</div>
    <!-- 评论框 -->
    <div class="comment-input-wrapper">
      <div style="display:flex">
        <!--头像，通过vuex中获取-->
        <v-avatar size="40">
          <img
            v-if="this.$store.state.avatar"
            :src="this.$store.state.avatar"
          />
          <img v-else src="https://www.hualigs.cn/image/60a11871c8904.jpg" />
        </v-avatar>
        <div style="width:100%" class="ml-3">
          <div class="comment-input">
            <textarea
              class="comment-textarea"
              v-model="commentForm.content"
              placeholder="留下点什么吧..."
              auto-grow
              dense
            />
          </div>
          <!-- 操作按钮 -->
          <div class="emoji-container">
            <!--表单提交-->
            <v-form>
              <v-container>
                <v-row>
                  <v-tooltip bottom>
                    <template v-slot:activator="{ on, attrs }">
                      <v-col cols="12" md="4">
                        <v-text-field
                          v-model="commentForm.nickname"
                          label="昵称"
                          required
                          v-bind="attrs"
                          v-on="on"
                        ></v-text-field>
                      </v-col>
                    </template>
                    <span>输入QQ号将自动拉取昵称和头像</span>
                  </v-tooltip>
                  <v-tooltip bottom>
                    <template v-slot:activator="{ on, attrs }">
                      <v-col cols="12" md="4">
                        <v-text-field
                          v-model="commentForm.email"
                          label="电子邮箱"
                          required
                          v-bind="attrs"
                          v-on="on"
                        ></v-text-field>
                      </v-col>
                    </template>
                    <span>用于接收回复邮件</span>
                  </v-tooltip>
                  <v-switch
                    style="padding-left: 30px !important;"
                    v-model="commentForm.notice"
                    :label="'订阅回复'"
                  ></v-switch>
                </v-row>
              </v-container>
            </v-form>
            <button
              @click="insertComment"
              class="upload-btn v-comment-btn"
              style="margin-left:auto"
            >
              提交
            </button>
          </div>
        </div>
      </div>
    </div>
    <!-- 评论详情 -->
    <div>
      <!-- 评论数量 -->
      <div class="count">{{ count }} 评论</div>
      <!-- 评论列表 -->
      <div
        style="display:flex"
        class="pt-5"
        v-for="(item, index) of commentList"
        :key="index"
      >
        <!-- 头像 -->
        <v-avatar size="40" class="comment-avatar">
          <img :src="item.avatar" />
        </v-avatar>
        <div class="comment-meta">
          <!-- 用户名 -->
          <div class="comment-user">
            <span>{{ item.nickname }}</span>
            <span class="blogger-tag" v-if="item.adminComment === true"
              >博主</span
            >
          </div>
          <!-- 信息 -->
          <div class="comment-info">
            <!-- 发表时间 -->
            <span style="margin-right:10px">{{ item.createTime | date }}</span>
            <!-- 回复 -->
            <span class="reply-btn" @click="replyComment(index, item)">
              回复
            </span>
          </div>
          <!-- 评论内容 -->
          <p v-html="item.content" class="comment-content"></p>
          <!-- 回复人 -->
          <div
            style="display:flex"
            v-for="reply of item.replyComments"
            :key="reply.id"
          >
            <!-- 头像 -->
            <v-avatar size="36" class="comment-avatar">
              <img :src="reply.avatar" alt="头像" />
            </v-avatar>
            <div class="reply-meta">
              <!-- 用户名 -->
              <div class="comment-user">
                <span>{{ reply.nickname }}</span>
                <span class="blogger-tag" v-if="reply.adminComment === true"
                  >博主</span
                >
              </div>
              <!-- 信息 -->
              <div class="comment-info">
                <!-- 发表时间 -->
                <span style="margin-right:10px">
                  {{ reply.createTime | date }}
                </span>
                <!-- 回复 -->
                <span class="reply-btn" @click="replyComment(index, reply)">
                  回复
                </span>
              </div>
              <!-- 回复内容 -->
              <p class="comment-content">
                <!-- 回复用户名 -->
                <template v-if="reply.parentCommentId !== item.userId">
                  <span class="ml-1"> @{{ reply.parentCommentNickname }} </span>
                </template>
                <span v-html="reply.content" />
              </p>
            </div>
          </div>
          <!-- 回复框 -->
          <div id="replydiv0">
            <Reply ref="reply" @reloadReply="reloadReply" />
          </div>
        </div>
      </div>
      <!-- 没有评论提示 -->
      <div class="text-center" v-if="count > 10">
        <v-pagination
          v-model="pageNum"
          :length="pageSize"
          @input="handleCurrentChange"
        ></v-pagination>
      </div>
      <div v-else style="padding:1.25rem;text-align:center">
        来发评论吧~
      </div>
    </div>
  </div>
</template>

<script>
import Reply from "./Reply";
import { getCommentsList, submitComment } from "@/api/comment";
import { getCommentsListByParentCommentId } from "@/api/comment";
export default {
  components: {
    Reply
  },
  data: function() {
    return {
      pageNum: 1,
      commentForm: {
        content: "",
        nickname: "",
        email: "",
        notice: false
      },
      reFresh: true
    };
  },
  props: {
    commentList: {
      type: Array
    },
    count: {
      type: Number
    },
    pageSize: {
      type: Number
    }
  },
  methods: {
    replyComment(index, item) {
      this.$refs.reply.forEach(item => {
        item.$el.style.display = "none";
      });
      //传值给回复框
      this.$refs.reply[index].nickname = item.nickname;
      this.$refs.reply[index].parentId = item.id;
      this.$refs.reply[index].index = index;
      this.$refs.reply[index].$el.style.display = "block";
    },
    checkReplies(index, item) {
      this.axios
        .get("/api/comments/replies/" + item.id, {
          params: { current: 1 }
        })
        .then(({ data }) => {
          this.$refs.check[index].style.display = "none";
          item.replyDTOList = data.data;
          //超过1页才显示分页
          if (Math.ceil(item.replyCount / 5) > 1) {
            this.$refs.paging[index].style.display = "flex";
          }
        });
    },
    changeReplyCurrent(current, index, commentId) {
      //查看下一页回复
      this.axios
        .get("/comments/replies/" + commentId, {
          params: { current: current }
        })
        .then(({ data }) => {
          this.commentList[index].replyDTOList = data.data;
        });
    },
    listComments() {
      //查看下一页评论
      const path = this.$route.path;
      const arr = path.split("/");
      getCommentsList(0, arr[2], this.pageNum, 10).then(res => {
        this.commentList = res.data.comments.list;
      });
    },
    insertComment() {
      //判空
      if (this.commentForm.content.trim() === "") {
        this.$toast({ type: "error", message: "评论不能为空" });
        return false;
      }
      if (this.commentForm.nickname.trim() === "") {
        this.$toast({ type: "error", message: "用户名不能为空" });
        return false;
      }
      if (this.commentForm.email.trim() === "") {
        this.$toast({ type: "error", message: "电子邮箱不能为空" });
        return false;
      }
      //验证邮箱格式
      let reg = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
      if (
        this.commentForm.email.trim() !== "" &&
        !reg.test(this.commentForm.email)
      ) {
        this.$toast({ type: "error", message: "邮箱格式不正确" });
        return false;
      }
      const adminToken = window.localStorage.getItem("adminToken");
      const path = this.$route.path;
      const arr = path.split("/");
      let form = {
        nickname: this.commentForm.nickname,
        email: this.commentForm.email,
        content: this.commentForm.content,
        notice: this.commentForm.notice,
        blogId: arr[2],
        parentCommentId: -1,
        page: 0
      };
      submitComment(adminToken, form).then(res => {
        if (res.code === 200) {
          //查询最新评论
          this.$emit("reloadComment");

          this.$toast({ type: "success", message: res.message });
        } else {
          this.$toast({ type: "error", message: res.message });
        }
      });
    },
    reloadReply(index) {
      console.log(index);
      /*this.axios
        .get("/comments/reply/" + this.commentList[index].id, {
          params: {
            current: this.$refs.page[index].current
          }
        })
        .then(({ data }) => {
          this.commentList[index].replyCount++;
          //回复大于5条展示分页
          if (this.commentList[index].replyCount > 5) {
            this.$refs.paging[index].style.display = "flex";
          }
          this.$refs.check[index].style.display = "none";
          this.$refs.reply[index].$el.style.display = "none";
          this.commentList[index].replyDTOList = data.data;
        });*/
      getCommentsListByParentCommentId(this.commentList[index].id).then(res => {
        this.$emit("reloadComment");
        this.$refs.check[index].style.display = "none";
        this.$refs.reply[index].$el.style.display = "none";
        this.commentList[index].replayComments = res.data.comments;
      });
    },
    //监听页码改变的事件
    handleCurrentChange(newPage) {
      this.pageNum = newPage;
      this.listComments();
    }
  },
  computed: {},
  watch: {
    commentList() {
      this.reFresh = false;
      this.$nextTick(() => {
        this.reFresh = true;
      });
    }
  }
};
</script>

<style scoped>
p {
  margin-bottom: 1.25rem !important;
}
.blogger-tag {
  background: #ffa51e;
  font-size: 12px;
  display: inline-block;
  border-radius: 2px;
  color: #fff;
  padding: 0 5px;
  margin-left: 6px;
}
.comment-title {
  display: flex;
  align-items: center;
  font-size: 1.25rem;
  font-weight: bold;
  line-height: 40px;
  margin-bottom: 10px;
}
.comment-title i {
  font-size: 1.5rem;
  margin-right: 5px;
}
.comment-input-wrapper {
  border: 1px solid #90939950;
  border-radius: 4px;
  padding: 10px;
  margin: 0 0 10px;
}
.count {
  padding: 5px;
  line-height: 1.75;
  font-size: 1.25rem;
  font-weight: bold;
}
.comment-meta {
  margin-left: 0.8rem;
  width: 100%;
  border-bottom: 1px dashed #f5f5f5;
}
.reply-meta {
  margin-left: 0.8rem;
  width: 100%;
}
.comment-user {
  font-size: 14px;
  line-height: 1.75;
}
.comment-user a {
  color: #1abc9c !important;
  font-weight: 500;
  transition: 0.3s all;
}
.comment-nickname {
  text-decoration: none;
  color: #1abc9c !important;
  font-weight: 500;
}
.comment-info {
  line-height: 1.75;
  font-size: 0.75rem;
  color: #b3b3b3;
}
.reply-btn {
  cursor: pointer;
  float: right;
  color: #ef2f11;
}
.comment-content {
  font-size: 0.875rem;
  line-height: 1.75;
  padding-top: 0.625rem;
}
.comment-avatar {
  transition: all 0.5s;
}
.comment-avatar:hover {
  transform: rotate(360deg);
}
.load-wrapper {
  margin-top: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.load-wrapper button {
  background-color: #49b1f5;
  color: #fff;
}
</style>
