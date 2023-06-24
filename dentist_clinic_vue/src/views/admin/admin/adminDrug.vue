<template>
  <div>
    <el-card>
      <div slot="header">
        <h2>管理药物</h2>
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
            :data="drug"
            stripe
            style="width: 100%">
          <el-table-column
              prop="name"
              label="药物名称"
              width="150">
          </el-table-column>
          <el-table-column
              prop="describe"
              label="描述"
              width="750">
          </el-table-column>
          <el-table-column
              prop="tag"
              label="标签">
            <template slot-scope="drug">
              <el-tag type="success" style="margin: 5px" v-for="t in drug.row.tag.split(',')" :key="t">{{ t }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column
              prop="price"
              label="价格（元）"
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
        <el-form-item label="药物名称：" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="描述：" prop="describe">
            <el-input  type="textarea" v-model="form.describe"></el-input>
        </el-form-item>
        <el-form-item label="标签：" prop="tagArr">
          <el-tag
              style="margin: 0 5px"
              :key="tag"
              v-for="tag in form.tagArr"
              closable
              :disable-transitions="false"
              @close="handleClose(tag)">
            {{tag}}
          </el-tag>
          <el-input
              v-model="inputValue"
              ref="saveTagInput"
              size="small"
              @keyup.enter.native="handleInputConfirm"

          ></el-input>
        </el-form-item>
        <el-form-item label="价格：" prop="price">
          <el-input-number v-model="form.price"></el-input-number>
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
        <el-form-item label="药物名称：" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="描述：" prop="describe">
          <el-input  type="textarea" v-model="form.describe"></el-input>
        </el-form-item>
        <el-form-item label="标签：" prop="tagArr">
          <el-tag
              style="margin: 0 5px"
              :key="tag"
              v-for="tag in form.tagArr"
              closable
              :disable-transitions="false"
              @close="handleClose(tag)">
            {{tag}}
          </el-tag>
          <el-input
              v-model="inputValue"
              ref="saveTagInput"
              size="small"
              @keyup.enter.native="handleInputConfirm"

          ></el-input>
        </el-form-item>
        <el-form-item label="价格：" prop="price">
          <el-input-number v-model="form.price"></el-input-number>
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
      drug:[],
      district:[
      ],
      form: {
        name:'',
        describe: '',
        tag: '',
        tagArr:[],
        price:'',
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

      this.$confirm('此操作将永久删除该药品, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
          this.$http.delete("order/deleteDrug?id=" + id).then(resp=>{
            if(resp.data.code === 200){
              this.$message.success("删除成功！")
              this.queryList()
            } else {
              this.$message.error("删除失败！")
            }
          })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
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
      this.$set(this.form,'tagArr',this.form.tag.split(','))
      this.dialogEditVisible = true
    },
    showPost(){
      this.dialogPostVisible = true
    },

    handleClose(tag) {
      this.form.tagArr.splice(this.form.tagArr.indexOf(tag), 1);
    },
    handleInputConfirm() {
      let inputValue = this.inputValue;
      if (inputValue) {
        if(this.form.tagArr.indexOf(inputValue)>=0) {
          this.$message.error("该标签已添加！")
          return
        }
        this.form.tagArr.push(inputValue);
        this.inputValue = '';
      }
    },
    submitForm(formName) {
      if(this.form.tagArr.length>0){
        this.form.tag=this.form.tagArr.join(",")
      }
      this.$refs[formName].validate((valid) => {
        if (valid) {

          this.$http.put("/order/updateDrug",this.form).then(resp=>{
            if(resp.data.code===200){
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
      if(this.form.tagArr.length>0) {
        this.form.tag=this.form.tagArr.join(",")
      }
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$http.post("/order/addDrug",this.form).then(resp=>{
            if(resp.data.code===200){
              this.$message.success("添加成功！")
              this.$refs.form.resetFields()
              this.district  = []
              this.dialogPostVisible = false
            }else{
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
      this.$http.get('/order/selectDrugAll',{params:this.params}).then(res=>{
        if(res.data.code===200){
          this.drug = res.data.data.rows
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