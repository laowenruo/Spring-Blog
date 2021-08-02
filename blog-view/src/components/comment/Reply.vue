<template>
  <div class="reply-input-wrapper" style="display: none" ref="reply">
    <textarea
      class="comment-textarea"
      :placeholder="'回复 @' + nickname + '：'"
      auto-grow
      dense
      v-model="commentForm.content"
    />
    <div class="emoji-container">
      <div style="margin-left:auto">
        <button @click="cancleReply" class="cancle-btn v-comment-btn">
          取消
        </button>
        <button @click="insertReply" class="upload-btn v-comment-btn">
          提交
        </button>
      </div>
    </div>
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
  </div>
</template>

<script>
import { submitComment } from "@/api/comment";
export default {
  data: function() {
    return {
      index: 0,
      nickname: "",
      parentId: null,
      commentForm: {
        content: "",
        nickname: "",
        email: "",
        notice: false
      }
    };
  },
  methods: {
    cancleReply() {
      this.$refs.reply.style.display = "none";
    },
    insertReply() {
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
      var reg = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
      if (
        this.commentForm.email.trim() !== "" &&
        !reg.test(this.commentForm.email)
      ) {
        this.$toast({ type: "error", message: "邮箱格式不正确" });
        return false;
      }
      const path = this.$route.path;
      const arr = path.split("/");
      let token = window.localStorage.getItem("adminToken");
      let form = {
        nickname: this.commentForm.nickname,
        content: this.commentForm.content,
        email: this.commentForm.email,
        notice: this.commentForm.notice,
        blogId: arr[2],
        parentCommentId: this.parentId,
        page: 0
      };
      submitComment(token, form).then(res => {
        if (res.code === 200) {
          this.$emit("reloadReply", this.index);
          this.commentForm.content = "";
          this.$refs.reply.style.display = "none";
          this.$toast({ type: "success", message: "回复成功" });
        } else {
          this.$toast({ type: "success", message: res.data.message });
        }
      });
    }
  }
};
</script>

<style scoped>
.reply-input-wrapper {
  border: 1px solid #90939950;
  border-radius: 4px;
  padding: 10px;
  margin: 0 0 10px;
}
</style>
