<template>
  <div>
    <!-- banner -->
    <div class="archive-banner banner">
      <h1 class="banner-title">归档</h1>
    </div>
    <!-- 归档列表 -->
    <v-card class="blog-container">
      <timeline>
        <timeline-title> 目前共计{{ count }}篇文章，继续加油 </timeline-title>
        <timeline-item v-for="item of archiveList" :key="item.id">
          <!-- 日期 -->
          <span class="time">{{ item.createTime | date }}</span>
          <!-- 文章标题 -->
          <router-link
            :to="'/articles/' + item.id"
            style="color:#666;text-decoration: none"
          >
            {{ item.title }}
          </router-link>
        </timeline-item>
      </timeline>
      <!-- 分页按钮 -->
      <v-pagination
        v-model="pageNum"
        :length="Math.ceil(count / 10)"
        total-visible="7"
      />
    </v-card>
  </div>
</template>

<script>
import { Timeline, TimelineItem, TimelineTitle } from "vue-cute-timeline";
import { getArchives } from "@/api/archive";
export default {
  created() {
    this.listArchives();
  },
  components: {
    Timeline,
    TimelineItem,
    TimelineTitle
  },
  data: function() {
    return {
      pageNum: 1,
      count: 0,
      archiveList: []
    };
  },
  methods: {
    listArchives() {
      getArchives(this.pageNum).then(res => {
        this.count = res.data.count;
        this.archiveList = res.data.archiveList.list;
      });
    }
  },
  watch: {
    pageNum(value) {
      getArchives(value).then(res => {
        this.count = res.data.count;
        this.archiveList = res.data.archiveList.list;
      });
    }
  }
};
</script>
<style scoped>
.archive-banner {
  background: #49b1f5 no-repeat center center;
}
.time {
  font-size: 0.75rem;
  color: #555;
  margin-right: 1rem;
}
</style>
