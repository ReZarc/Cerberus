<template>
  <div class="container">
    <el-container>
      <el-header  >
        <div class="divCss">
          <el-button type="text" style="color: white;margin-top: -25px" @click="this.$router.push('/')">
            <h2 style="font-size: 30px">牙科诊所管理系统</h2>
          </el-button>
        </div>
        <div class="right">
          <el-button round @click="$router.push('/')">
            <i class="el-icon-user-solid"></i>
            <a style="padding-left: 4px">首页</a>
          </el-button>
        </div>
      </el-header>
      <el-main>
        <div class="reg-container">
          <el-steps :active="active"   class="steps">
            <el-step title="验证用户名"></el-step>
            <el-step title="设置密码"></el-step>
            <el-step title="注册成功"></el-step>
          </el-steps>
          <el-form :model="form" :rules="rules" ref="form" label-width="100px"  class="form" >
          <div v-show="active===1">
              <el-form-item label="用户名：" prop="username">
                <el-input v-model="form.username"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="warning" @click="nextPwd">下一步，设置密码</el-button>
              </el-form-item>
          </div>
          <div v-show="active===2">
            <el-form-item label="昵称" prop="nickname">
              <el-input v-model="form.nickname"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input v-model="form.password" show-password></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="confirmPassword">
              <el-input v-model="form.confirmPassword" show-password></el-input>
            </el-form-item>
            <el-form-item label="姓名：" prop="name">
              <el-input v-model="form.name"></el-input>
            </el-form-item>
            <el-form-item label="性别：" prop="gender">
              <el-select v-model="form.gender" style="width: 100%">
                <el-option   label="男" :value="1"></el-option>
                <el-option   label="女" :value="0"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="联系方式：" prop="phone">
              <el-input v-model="form.phone"></el-input>
            </el-form-item>
            <el-form-item label="年龄：" prop="age">
              <el-input-number v-model="form.age"></el-input-number>
            </el-form-item>
            <el-form-item>
              <el-button @click="register" type="warning">注册</el-button>
            </el-form-item>
          </div>
          <div v-show="active===3">
            <el-result icon="success" title="注册成功" subTitle="可点击下方按钮去登陆~">
              <template slot="extra">
                <el-button type="primary" size="medium" @click="$router.push('/login')">登录</el-button>
              </template>
            </el-result>
          </div>
          </el-form>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "index",
  data(){
    return{
      active:1,
      a: true,
      checked: false,
      form:{
        username:'',
        password:'',
        confirmPassword:'',
        nickname:'',
        gender:1,
        name:'',
        age:0,
        type:0
      },
      rules:{
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { validator: (rule, value, callback) => {
              // 如果密码长度小于6，返回错误信息
              if (value.length < 6) {
                callback(new Error('用户名至少六位'));
              } else {
                // 否则，通过验证
                callback();
              }
            }, trigger: 'blur'}
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { required: true, validator: (rule, value, callback) => {
              // 如果密码长度小于6，返回错误信息
              if (value.length < 8) {
                callback(new Error('密码至少八位'));
              } else {
                // 否则，通过验证
                callback();
              }
            }, trigger: 'blur'}
        ],
        confirmPassword: [
          { required: true, message: '请输入确认密码', trigger: 'blur' },
        ],
        nickname: [
          { required: true, message: '请输入昵称', trigger: 'blur' },
        ],
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' },
        ],
        age: [
          { required: true, message: '请输入年龄', trigger: 'blur' },
        ],
        gender: [
          { required: true, message: '请选择性别', trigger: 'blur' },
        ],

      }
    }
  },
  methods:{
    nextPwd(){
        this.$refs.form.validate((valid=>{
             if(this.form.username.trim()===""){
                this.$message.error("请填写用户名称！")
               return
             }
            this.$http.get("/user/selectByUserName?username="+this.form.username).then(res=>{
              if(res.data.code===200){
                this.$refs.form.clearValidate()
                this.active++
                return
              }
              this.$message.error(res.data.msg)
            })

        }))
    },
    register(){
      this.$refs.form.validate((valid=>{
         if(valid){
           if(this.form.password.toLowerCase()===this.form.confirmPassword.toLowerCase()){
             this.$http.post("/user/add",this.form).then(res=>{
               if(res.data.code===200){
                 this.active++
               }
             })
           }else {
             this.$message.error("两次密码不一致！")
           }
         }
      }))
    }
  }
}
</script>

<style scoped>
.steps{
  width: 100%;
}
.form{
  margin-top: 150px;
  width: 400px;
}

.reg-container{
  height: 100%;
  width: 50%;
  margin:  0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.container{
  height: 100%;
}
.el-header{
  background-color: #3d8d87;
  color: #333;
  line-height: 60px;
  display: flex;
  justify-content: space-between;
}
.center{
  max-width: 1160px;
  min-width: 1080px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-left: auto;
  margin-right: auto;
}

</style>