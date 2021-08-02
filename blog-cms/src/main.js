import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './assets/css/base.css'
import './assets/css/icon/iconfont.css'

//element-ui
import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
//moment
import './util/dateTimeFormatUtils.js'
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

Vue.use(mavonEditor)
Vue.use(Element)


Vue.prototype.msgSuccess = function (message) {
  this.$message.success(message)
}

Vue.prototype.msgError = function (message) {
  this.$message.error(message)
}

Vue.prototype.msgInfo = function (message) {
  this.$message.info(message);
}


Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
