<template>
  <div class="home" style="height: 100%">
    <el-container style="height: 100%">
      <el-header style="position: fixed;top: 0px;left: 0px;width:100%;z-index: 1">
        <div class="divCss">
          <el-button type="text" style="color: white;margin-top: -25px" @click="this.$router.push('/')">
            <h2 style="font-size: 30px">牙科诊所管理系统</h2>
          </el-button>
        </div>
        <div class="right">
          <el-button  v-if="user.id" size="mini" round @click.native="showEdit">
              <span class="el-dropdown-link">
                <a style="padding-left: 4px">尊敬的 {{user.nickname}}</a>
              </span>
          </el-button>
          <el-button  v-else size="mini" round @click="$router.push('/login')">
            <i class="el-icon-user-solid"></i>
            <a style="padding-left: 4px">请登录</a>
          </el-button>
          <el-button  v-if="user.id" size="mini" round @click.native="exit">
            <i class="el-icon-switch-button"></i>
            <a style="padding-left: 4px">退出登录</a>
          </el-button>
          <el-button  v-else size="mini" round @click="$router.push('/register')">
            <i class="el-icon-user-solid"></i>
            <a style="padding-left: 4px">注册</a>
          </el-button>
        </div>
      </el-header>
      <el-container  style="margin-top: 60px">
        <el-aside :width="width" style="position: fixed" v-if="user.type === 3">
          <el-menu unique-opened default-active="/admin/adminUser" class="el-menu-vertical-demo"  style="height: 100%;position: fixed;width: 200px"  router>
            <el-submenu index="1">
              <template slot="title">
                <i class="iconfont icon-icon-test2"></i>
                <span  class="spanClass">我的</span>
              </template>
              <el-menu-item index="/admin/adminUser" >
                <i class="el-icon-user"></i>
                <span class="secondFontCss" slot="title">管理用户</span>
              </el-menu-item>
              <el-menu-item index="/admin/adminDrug" >
                <i class="el-icon-suitcase"></i>
                <span class="secondFontCss" slot="title">管理药物</span>
              </el-menu-item>
              <el-menu-item index="/admin/adminAppoint">
                <i class="el-icon-date"></i>
                <span class="secondFontCss" slot="title">管理预约</span>
              </el-menu-item>
              <el-menu-item index="/admin/adminDoc" >
                <i class="el-icon-user-solid"></i>
                <span class="secondFontCss" slot="title">管理医生</span>
              </el-menu-item>
              <el-menu-item index="/admin/adminOrder">
                <i class="el-icon-tickets"></i>
                <span class="secondFontCss" slot="title">管理订单</span>
              </el-menu-item>
              <el-menu-item index="/admin/adminSurgery" >
                <i class="el-icon-first-aid-kit"></i>
                <span class="secondFontCss" slot="title">管理手术</span>
              </el-menu-item>
            </el-submenu>
          </el-menu>
        </el-aside>
        <el-aside :width="width" style="position: fixed" v-else>
          <el-menu unique-opened default-active="/admin/adminPatientOrder" class="el-menu-vertical-demo"  style="height: 100%;position: fixed;width: 200px"  router>
            <el-submenu index="1">
              <template slot="title">
                <i class="iconfont icon-icon-test2"></i>
                <span  class="spanClass">管理权限</span>
              </template>
              <el-menu-item index="/admin/adminPatientOrder" v-if="user.type === 1 || user.type === 0">
                <i class="el-icon-user"></i>
                <span class="secondFontCss" slot="title">我的预约</span>
              </el-menu-item>
              <el-menu-item index="/admin/adminPatientOrder" v-if="user.type === 2">
                <i class="el-icon-user"></i>
                <span class="secondFontCss" slot="title">处方配药</span>
              </el-menu-item>
              <el-menu-item index="/admin/adminDrugOrder" v-if="user.type === 0" >
                <i class="el-icon-suitcase"></i>
                <span class="secondFontCss" slot="title">配药订单</span>
              </el-menu-item>
              <el-menu-item index="/admin/adminSurgeryOrder" v-if="user.type === 1 || user.type === 0">
                <i class="el-icon-date"></i>
                <span class="secondFontCss" slot="title" >手术安排</span>
              </el-menu-item>
            </el-submenu>
          </el-menu>
        </el-aside>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
    <el-dialog
        title="编辑"
        :visible.sync="dialogVisible"
        width="700px"
        :before-close="dialogClose"
    >
      <el-form :model="form" :rules="rules" ref="form" label-width="100px" style="width: 600px">
        <el-form-item label="用户密码：" prop="password">
          <el-input v-model="form.password" show-password placeholder="请输入原密码"></el-input>
        </el-form-item>
        <el-form-item label="修改密码：" prop="password1">
          <el-input v-model="form.password1" show-password placeholder="若要修改密码请输入"></el-input>
        </el-form-item>
        <el-form-item label="用户昵称：" prop="nickname">
          <div style="display: flex">
            <el-input v-model="form.nickname"></el-input>
          </div>
        </el-form-item>
        <el-form-item label="用户姓名：" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="性别：" prop="gender">
          <el-select v-model="form.gender" style="width: 100%">
            <el-option   label="男" :value="1"></el-option>
            <el-option   label="女" :value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="年龄：" prop="age">
          <el-input-number v-model="form.age"></el-input-number>
        </el-form-item>
        <el-form-item label="联系方式：" prop="phone">
          <el-input v-model="form.phone"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
          <el-button @click="dialogClose">取 消</el-button>
          <el-button type="primary" @click="submitForm('form')">确 定</el-button>
        </span>
    </el-dialog>
  </div>
</template>

<script>


export default {
  data(){
    return{
      rules: {
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        nickname: [
          { required: true, message: '请输入昵称', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' }
        ],
        age: [
          { required: true, message: '请输入年龄', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入联系方式', trigger: 'blur' },
        ],
        gender: [
          { required: true, message: '请选择性别', trigger: 'blur' }
        ],
      },
      dialogVisible:false,
      isCollapse:false,
      width:'200px',
      user:{
      },
      form: {
        id:0,
        username:'',
        nickname:'',
        password1:'',
        password:'',
        name:'',
        age:0,
        phone:'',
        gender:0,
        type:0,
      },
    }
  },
  methods:{
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$http.put("/user/changeProfile",this.form).then(resp=>{
            if(resp.data.code === 200){
              this.$message.success("修改成功，下次登录生效！")
              this.dialogVisible = false
            }else{
              this.$message.error("密码错误！")
            }
          })
        }
      });
    },
    dialogClose(){
      this.dialogVisible = false
    },
    showEdit(){
      console.log(this.user)
      this.form = {...this.user}
      this.dialogVisible = true
    },
    exit() {
      this.user = {}
      localStorage.removeItem('user')
      this.$router.push('/')
    }
  },
  created() {
    let user = JSON.parse(localStorage.getItem("user")||'{}')
    if(user.id){
      this.user = user
    }
  }
}
</script>
<style scoped>
.home{
  width: 100%;
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
  color: #67C23A;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-left: auto;
  margin-right: auto;
}

.el-aside {
  background-color: #67C23A;
  color: #333;
  line-height: 200px;
}

.el-main {

  background-color: #FFFFFF;
  color: #333;
  margin-left: 200px;
}

.spanClass{
  font-size: 17px;
  padding-left: 15px;
}
.secondFontCss{
  margin-left: 5px;
}
</style>
