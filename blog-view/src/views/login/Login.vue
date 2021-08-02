<template>
  <div class="login-container">
    <div class="login-card">
      <div class="login-title">管理员登录</div>
      <!-- 登录表单 -->
      <form>
        <v-text-field
          v-model="loginForm.username"
          :counter="10"
          label="用户名"
          placeholder="请输入您的用户名"
        ></v-text-field>
        <v-text-field
          v-model="loginForm.password"
          label="密码"
          placeholder="请输入您的密码"
          :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
          :type="show ? 'text' : 'password'"
          @click:append="show = !show"
        ></v-text-field>
        <v-btn
          class="mt-4"
          block
          color="blue"
          style="color:#fff"
          @click="login"
        >
          登录
        </v-btn>
      </form>
    </div>
  </div>
</template>

<script>
import { login } from "@/api/login";

export default {
  data: function() {
    return {
      loginForm: {
        username: "",
        password: ""
      }
    };
  },
  methods: {
    login() {
      if (this.loginForm.username.trim().length === 0) {
        this.$toast({ type: "error", message: "用户名不能为空" });
        return false;
      }
      if (this.loginForm.password.trim().length === 0) {
        this.$toast({ type: "error", message: "密码不能为空" });
        return false;
      }
      //发送登录请求
      login(this.loginForm).then(res => {
        if (res.code === 200) {
          window.localStorage.setItem("adminToken", res.data.token);
          console.log(res.data.token);
          this.$store.commit("login", res.data.user);
          this.$router.push("/");
          this.$toast({ type: "success", message: res.data.message });
        } else {
          this.$toast({ type: "error", message: res.data.message });
        }
      });
    }
  }
};
</script>

<style scoped>
.login-container {
  position: absolute;
  top: 0;
  bottom: 0;
  right: 0;
  left: 0;
  background: url("http://img.mashiro.org.cn/20210622193056.jpg") center center /
    cover no-repeat;
}
.login-card {
  position: absolute;
  top: 0;
  bottom: 0;
  right: 0;
  background: #fff;
  padding: 170px 60px 180px;
  width: 350px;
}
.login-title {
  color: #303133;
  font-weight: bold;
  font-size: 1rem;
}
.login-form {
  margin-top: 1.2rem;
}
.login-card button {
  margin-top: 1rem;
  width: 100%;
}
</style>
