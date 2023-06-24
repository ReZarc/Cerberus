<template>
  <div>
    <el-card>
      <div slot="header">
        <h2>管理用户</h2>
      </div>
      <div>
        <el-form :inline="true" :model="params" class="demo-form-inline">
          <el-form-item label="查询用户">
            <el-input v-model="params.name" placeholder="请输入关键字"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="query">查询</el-button>
            <el-button type="primary" @click="reset" >重置</el-button>
          </el-form-item>
          <el-button type="primary" @click="showPost" style="float: right">添加</el-button>
        </el-form>
        <el-table
            :data="user"
            stripe
            style="width: 100%">
          <el-table-column
              prop="username"
              label="用户帐号"
              width="150">
          </el-table-column>
          <el-table-column
              prop="nickname"
              label="用户昵称">
          </el-table-column>
          <el-table-column
              prop="name"
              label="用户姓名">
          </el-table-column>

            <el-table-column
                prop="gender"
                label="性别"
                :formatter="genderFormatter"
            >
          </el-table-column>
          <el-table-column
              prop="age"
              label="年龄">
          </el-table-column>
          <el-table-column
              prop="phone"
              label="联系方式">
          </el-table-column>
          <el-table-column
              prop="type"
              label="角色"
          :formatter="typeFormatter"
          >
          </el-table-column>
          <el-table-column
              label="操作"
              width="100">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="showEdit(scope.row)">编辑</el-button>
              <el-button type="text" size="small" @click="del(scope.row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
            style="margin-top:20px"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="this.params.pageNum"
            :page-sizes="[5, 8, 10, 15]"
            :page-size="100"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
        </el-pagination>
      </div>
    </el-card>

    <el-dialog
        title="编辑"
        :visible.sync="dialogEditVisible"
        width="700px"
        :before-close="dialogEditClose"
    >
      <el-form :model="form" :rules="rules" ref="form" label-width="100px" style="width: 600px">
        <el-form-item label="用户账号：" prop="username">
          <el-input v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item label="用户昵称：" prop="nickname">
          <div style="display: flex">
            <el-cascader :props="props"  v-model="district" style="width: 300px" v-if="district.length" ></el-cascader>
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
          <el-form-item label="角色：" prop="type">
            <el-select v-model="form.type" placeholder="请选择用户类型" style="width: 100%">
              <el-option   label="病人" :value="0"></el-option>
              <el-option   label="医生" :value="1"></el-option>
              <el-option   label="药师" :value="2"></el-option>
              <el-option   label="管理员" :value="3"></el-option>
            </el-select>
          </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
          <el-button @click="dialogEditClose">取 消</el-button>
          <el-button type="primary" @click="submitForm('form')">确 定</el-button>
        </span>
    </el-dialog>
    <el-dialog
        title="添加"
        :visible.sync="dialogPostVisible"
        width="700px"
        :before-close="dialogPostClose"
    >
      <el-form :model="form" :rules="rules" ref="form" label-width="100px" style="width: 600px">
        <el-form-item label="用户账号：" prop="username">
          <el-input v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item label="用户昵称：" prop="nickname">
          <div style="display: flex">
            <el-cascader :props="props"  v-model="district" style="width: 300px" v-if="district.length" ></el-cascader>
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
        <el-form-item label="角色：" prop="type">
          <el-select v-model="form.type" placeholder="请选择用户类型" style="width: 100%">
            <el-option   label="病人" :value="0"></el-option>
            <el-option   label="医生" :value="1"></el-option>
            <el-option   label="药师" :value="2"></el-option>
            <el-option   label="管理员" :value="3"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
          <el-button @click="dialogPostClose">取 消</el-button>
          <el-button type="primary" @click="postForm('form')">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "list",
  data(){
    let _this = this
    return{
      dialogPostVisible:false,
      dialogEditVisible:false,
      params:{
        name:'',
        pageNum:1,
        pageSize:5,
        uid:0
      },
      total:0,
      user:[],
      district:[
      ],
      form: {
        id:0,
        username:'',
        nickname:'',
        name:'',
        age:0,
        phone:'',
        gender:0,
        type:0,
      },
      fileList:[],
      inputVisible: false,
      inputValue: '',
      props: {
        lazy: true,
        lazyLoad (node, resolve) {
          let { level } = node;
          let name = node.value||''
          let params = {
            name,
            level
          }
          _this.$http.get("/homestayClient/selectDictByName",{params}).then(res=>{
            let data =  res.data.data
            const nodes = data.map(item => ({
              value: `${item.name}`,
              label: `${item.name}`,
              leaf: level >=2
            }));
            resolve(nodes);
          })
        }
      },
      rules: {
        username: [
          { required: true, message: '请输入帐号', trigger: 'blur' }
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
        type: [
          { required: true, message: '请选择用户类型', trigger: 'blur' }
        ]
      }
    }
  },
  methods:{

    del(id){

      this.$confirm('此操作将永久删除该帐号, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
          let _user = JSON.parse(localStorage.user);
          if(_user.id === id) {
            this.$message({
              type: 'info',
              message: '不能删除自己!'
            });
          } else {
            this.$http.delete("/user?id=" + id).then(resp=>{
              if(resp.data.code === 200){
                this.$message.success("删除成功！")
                this.queryList()
              } else {
                this.$message.error("删除失败！")
              }
            })
          }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    genderFormatter(row, column, val) {
        if(val === 1) return '男'
        else return '女'
    },
    typeFormatter(row, column, val) {
      if(val === 0) return '病人'
      else if(val === 1) return '医生'
      else if(val === 2) return '药师'
      return '管理员'
    },
    dialogPostClose(){
      this.district =[]
      this.dialogPostVisible = false
    },
    dialogEditClose(){
      this.district =[]
      this.dialogEditVisible = false
    },
    showEdit(row){
      this.form = {...row}
      this.dialogEditVisible = true
    },
    showPost(){
      this.dialogPostVisible = true
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$http.put("/user",this.form).then(resp=>{
            if(resp.data.code === 200){
              this.$message.success("修改成功！")
              this.queryList()
              this.dialogEditVisible = false
            }else{
              this.$message.error("修改失败！")
            }
          })
        }
      });
    },
    postForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$http.post("/user/add", this.form).then(resp => {
            if (resp.data.code === 200) {
              this.$message.success("添加成功！")
              this.queryList()
              this.dialogPostVisible = false
            } else {
              this.$message.error("添加失败！")
            }
          })
        }
      });
    },

    reset(){
      this.params ={
        name:'',
        pageNum:1,
        pageSize:5,
        uid:this.form.uid
      }
      this.queryList()
    },
    query() {
      this.params.pageNum = 1
      this.queryList()
    },
    queryList(){
      console.log(this.params)
      this.$http.get('/user/selectAll',{params:this.params}).then(res=>{
        if(res.data.code===200){
          this.user = res.data.data.rows
          this.total = res.data.data.total
        }
      })
    },
    handleSizeChange(val) {
      this.params.pageSize = val
      this.queryList()
    },
    handleCurrentChange(val) {
      this.params.pageNum = val
      this.queryList()
    }
  },
  created() {
    let user = JSON.parse(localStorage.getItem("user")||'{}')
    if(user.id){
      this.form.uid= user.id
      this.params.uid= user.id
    }
    this.queryList()
  }
}
</script>

<style scoped>

</style>