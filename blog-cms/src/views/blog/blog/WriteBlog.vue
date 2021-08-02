<template>
	<div>
		<!--面包屑导航-->
		<Breadcrumb parentTitle="博客管理"/>

		<el-form :model="form" :rules="formRules" ref="formRef" label-position="top">
			<el-row :gutter="20">
				<el-col :span="12">
					<el-form-item label="文章标题" prop="title">
						<el-input v-model="form.title" placeholder="请输入标题"></el-input>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item label="文章首图URL" prop="firstPicture">
            <el-upload
                :headers="headers"
                action="http://localhost:8082/api/admin/upload"
                :on-success="handleSuccess"
                :limit="1">
              <el-button size="small" type="primary">点击上传</el-button>
            </el-upload>
          </el-form-item>
				</el-col>
			</el-row>

			<el-form-item label="文章描述" prop="description">
				<mavon-editor v-model="form.description"/>
			</el-form-item>

			<el-form-item label="文章正文" prop="content">
				<mavon-editor v-model="form.content" ref=md @imgAdd="imgAdd"/>
			</el-form-item>

			<el-row :gutter="20">
				<el-col :span="12">
					<el-form-item label="分类" prop="cate">
						<el-select v-model="form.cate" placeholder="请选择分类（输入可添加新分类）" :allow-create="true" :filterable="true" style="width: 100%;">
							<el-option :label="item.name" :value="item.id" v-for="item in categoryList" :key="item.id"></el-option>
						</el-select>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item label="标签" prop="tagList">
						<el-select v-model="form.tagList" placeholder="请选择标签（输入可添加新标签）" :allow-create="true" :filterable="true" :multiple="true" style="width: 100%;">
							<el-option :label="item.name" :value="item.id" v-for="item in tagList" :key="item.id"></el-option>
						</el-select>
					</el-form-item>
				</el-col>
			</el-row>

			<el-row :gutter="20">
				<el-col :span="8">
					<el-form-item label="浏览次数" prop="views">
						<el-input v-model="form.views" placeholder="请输入文章字数（可选）默认为 0" type="number"></el-input>
					</el-form-item>
				</el-col>
			</el-row>

			<el-form-item style="text-align: right;">
				<el-button type="primary" @click="dialogVisible=true">保存</el-button>
			</el-form-item>
		</el-form>

		<!--编辑可见性状态对话框-->
		<el-dialog title="博客可见性" width="30%" :visible.sync="dialogVisible">
			<!--内容主体-->
			<el-form label-width="50px" @submit.native.prevent>
				<el-form-item>
					<el-radio-group v-model="radio">
						<el-radio :label="1">公开</el-radio>
						<el-radio :label="2">私密</el-radio>
					</el-radio-group>
				</el-form-item>
				<el-form-item v-if="radio!==2">
					<el-row>
						<el-col :span="6">
							<el-switch v-model="form.appreciation" active-text="赞赏"></el-switch>
						</el-col>
						<el-col :span="6">
							<el-switch v-model="form.recommend" active-text="推荐"></el-switch>
						</el-col>
						<el-col :span="6">
							<el-switch v-model="form.commentEnabled" active-text="评论"></el-switch>
						</el-col>
						<el-col :span="6">
							<el-switch v-model="form.top" active-text="置顶"></el-switch>
						</el-col>
					</el-row>
				</el-form-item>
			</el-form>
			<!--底部-->
			<span slot="footer">
				<el-button @click="dialogVisible=false">取 消</el-button>
				<el-button type="primary" @click="submit">保存</el-button>
			</span>
		</el-dialog>
	</div>
</template>

<script>
	import Breadcrumb from "@/components/Breadcrumb";
	import {getCategoryAndTag, saveBlog, getBlogById, updateBlog/*, upload*/} from '@/api/blog';
  import axios from 'axios'

	export default {
		name: "WriteBlog",
		components: {Breadcrumb,},
		data() {
			return {
				categoryList: [],
				tagList: [],
				dialogVisible: false,
				radio: 1,
				form: {
					title: '',
					firstPicture: '',
					description: '',
					content: '',
					cate: null,
					tagList: [],
					views: 0,
					appreciation: false,
					recommend: false,
					commentEnabled: false,
					top: false,
					published: false,
				},
				formRules: {
					title: [{required: true, message: '请输入标题', trigger: 'change'}],
					firstPicture: [{required: true, message: '请输入首图链接', trigger: 'change'}],
					cate: [{required: true, message: '请选择分类', trigger: 'change'}],
					tagList: [{required: true, message: '请选择标签', trigger: 'change'}],
				},
			}
		},
		created() {
			this.getData()
			if (this.$route.params.id) {
				this.getBlog(this.$route.params.id)
			}
		},
    computed: {
      headers() {
        return{
          "Authorization" :"Bearer " + localStorage.getItem("token") // 直接从本地获取token就行
        }
      }
    },
		methods: {

			getData() {
				getCategoryAndTag().then(res => {
					if (res.code === 200) {
						this.categoryList = res.data.categories
						this.tagList = res.data.tags
					} else {
						this.msgError(res.message)
					}
				}).catch(() => {
					this.msgError('请求失败')
				})
			},
			getBlog(id) {
				getBlogById(id).then(res => {
					if (res.code === 200) {
						this.computeCategoryAndTag(res.data)
						this.form = res.data
						this.radio = this.form.published ? 1 : 2
					} else {
						this.msgError(res.message)
					}
				}).catch(() => {
					this.msgError('请求失败')
				})
			},
			computeCategoryAndTag(blog) {
				blog.cate = blog.category.id
				blog.tagList = []
				blog.tags.forEach(item => {
					blog.tagList.push(item.id)
				})
			},
      handleSuccess (response) {
        console.log(response.message)
        this.form.firstPicture = response.message
      },
			submit() {
				this.$refs.formRef.validate(valid => {
					if (valid) {
						if (this.radio === 2) {
							this.form.appreciation = false
							this.form.recommend = false
							this.form.commentEnabled = false
							this.form.top = false
							this.form.published = false
						} else {
							this.form.published = true
						}
						if (this.$route.params.id) {
							this.form.category = null
							this.form.tags = null
							updateBlog(this.form).then(res => {
								if (res.code === 200) {
									this.msgSuccess(res.message)
									this.$router.push('/blogs')
								} else {
									this.msgError(res.message)
								}
							}).catch(() => {
								this.msgError('请求失败')
							})
						} else {
							saveBlog(this.form).then(res => {
								if (res.code === 200) {
									this.msgSuccess(res.message)
									this.$router.push('/blogs')
								} else {
									this.msgError(res.message)
								}
							}).catch(() => {
								this.msgError('请求失败')
							})
						}
					} else {
						this.dialogVisible = false
						return this.msgError('请填写必要的表单项')
					}
				})
			},
      imgAdd(pos, file){
        const formDate = new FormData();
        formDate.append("file", file);
        const newRequest = axios.create({
          baseURL: '/api/admin',
          timeout: 10000,
        });
        newRequest({
          method: "POST",
          url: "upload",
          data: formDate,
          headers: {
            "Authorization" :"Bearer " + localStorage.getItem("token")
          }
        }).then(({ data }) => {
          this.$refs.md.$img2Url(pos, data.message);
        });
      },
		}
	}
</script>

<style scoped>

</style>
