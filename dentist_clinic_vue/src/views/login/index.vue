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
            <el-button round @click="$router.push('/register')">
              <i class="el-icon-user-solid"></i>
              <a style="padding-left: 4px">注册</a>
            </el-button>
          </div>
        </el-header>
        <el-main>
              <div class="login_container">
                    <el-card>
                         <div class="login-title">账号密码登录</div>
                         <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
                           <el-form-item prop="username">
                                <el-input v-model="loginForm.username" placeholder="请输入登录账号"></el-input>
                           </el-form-item>
                           <el-form-item prop="password">
                             <el-input v-model="loginForm.password"  show-password placeholder="请输入登录密码"></el-input>
                           </el-form-item>
                           <el-form-item >
                             <el-button type="warning" style="width: 100%" @click="login">登   录</el-button>

                             <div style="display: flex;justify-content: space-between">
                                 <a href="#">忘记密码</a>
                                 <a href="/register">免费注册</a>
                             </div>
                           </el-form-item>
                         </el-form>
                    </el-card>
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
      a: true,
      checked: false,
      loginForm:{
          username: '',
          password: '',
          type: 1
      },
      loginRules:{
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入用户密码', trigger: 'blur' }
        ],
        type: [
          { required: true, message: '请输入用户类型', trigger: 'blur' }
        ],
      }
    }
  },
  methods:{
    login(){

      this.$refs['loginForm'].validate((valid)=>{
         if(valid){
            this.$http.get('/user/login',{params:this.loginForm}).then(res=>{
                if(res.data.code===200){
                    this.$message.success("登录成功~")
                    localStorage.setItem("user",JSON.stringify(res.data.data))
                    this.$router.push("/")
                } else {
                  this.$message.error(res.data.msg)
                }
            })
         }
      })

    }
  },
}
</script>

<style scoped>

.login-form a{
  color: #0086F6;
  text-decoration: underline;
  padding: 0 3px;
  border-bottom: none;
}

.p{
  margin-left: 4px;
  color: #999;
  line-height: 12px;
  font: 12px/1.5 sans-serif;
}

.login-form /deep/.el-checkbox__inner{
  border-radius: 10px;
}

.login-form{
  margin-top: 20px;
}

.login-title{
    font-size: 17px;
    font-weight: 550;
}
.login_container{
    display: flex;
    flex-direction: row-reverse;
    background: url("../../assets/background.jpg")  repeat-y;
    background-size: cover;
    height: 100%;

}
.login_container .el-card{
  width: 350px;
  height: 370px;
  padding: 20px;
  margin-right: 25vw;
  margin-top: 90px;
}
.el-main{
  padding: 0;
}


.container{
   height: 100%;
}

.el-main{
  width: 100%;
  background-color: #f5f7fa;
}
.main{
  height: 100%;
  max-width: 1160px;
  min-width: 1080px;
  margin: 0 auto;
  display: flex;
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
.el-header{
  background-color: #3d8d87;
  color: #333;
  line-height: 60px;
  display: flex;
  justify-content: space-between;
}
.el-container{
  height: 100%;
}

</style>