<template xmlns:el-col="http://www.w3.org/1999/html">
  <div>
    <el-card >
      <div slot="header" >
        <span v-if="user.type === 0 || user.type === 1">手术预约</span>
      </div>
      <div v-if="orders.length">
        <el-card class="order-item" v-for="item in orders" :key="item.id" >
          <div slot="header" class="clearfix">
                  <span>预约编号：{{ item.id }} &nbsp; 创建时间：{{ item.createTime }}
                    <el-button  type="text" @click="delOrder(item.id)">删除记录</el-button>
                  </span>
          </div>
          <div class="order-details" v-if="user.type === 0">
            <div class="left">
              <p>预约时间: {{ item.surgeryInTime }} - {{ item.surgeryOutTime }}</p>
              <p>医生姓名：{{ item.doctorName }}</p>
              <p>就诊科室：{{ item.roomName }} 室</p>
              <p v-if="item.type === 0">手术类型： 拔牙  </p>
              <p v-else-if="item.type === 1">手术类型： 补牙  </p>
              <p v-else-if="item.type === 2">手术类型： 种牙  </p>
              <p v-else-if="item.type === 3">手术类型： 其他  </p>
            </div>
            <div class="right">
              <h4 style="color:rgb(0, 255, 0)" v-if="item.status===2">已完成</h4>
              <h4 style="color:rgb(255, 0, 0)" v-else-if="item.status===1">手术中</h4>
              <h4 style="color:#409EFF" v-else-if="item.status===0" >已预约</h4>
            </div>
          </div>
          <div class="order-details" v-else-if="user.type === 1">
            <div class="left">
              <p>预约时间: {{ item.surgeryInTime }} - {{ item.surgeryOutTime }}</p>
              <p>病人姓名：{{ item.patientName }}</p>
              <p>手术室：{{ item.roomName }} 室</p>
              <p v-if="item.type === 0">手术类型： 拔牙  </p>
              <p v-else-if="item.type === 1">手术类型： 补牙  </p>
              <p v-else-if="item.type === 2">手术类型： 种牙  </p>
              <p v-else-if="item.type === 3">手术类型： 其他  </p>
            </div>
            <div class="right">
              <h4 style="color:rgb(0, 255, 0)" v-if="item.status===2">已完成</h4>
              <h4 style="color:rgb(255, 0, 0)" v-else-if="item.status===1">
                手术中
                <el-button type="primary" @click="endSurgery(item.id)">完成手术</el-button>
              </h4>
              <h4 style="color:#409EFF" v-else-if="item.status===0" >
                已预约
                <el-button type="primary" @click="beginSurgery(item.id)">开始手术</el-button>
              </h4>
            </div>
          </div>
        </el-card>
      </div>
      <el-empty style="margin-top: 15vh" description="尚无预约" v-else></el-empty>
      <el-pagination
          style="float: right"
          v-show="orders.length"
          @current-change="handleCurrentChange"
          :current-page="params.pageNum"
          :page-size="params.pageSize"
          layout="prev, pager, next"
          :total="total">
      </el-pagination>
    </el-card>
  </div>
</template>

<script>
let _type = JSON.parse(localStorage.getItem("user")).type
export default {
  name: "index",
  components:{
  },
  data(){
    return{
      user:{},
      dialogSurgeryVisible:false,
      dialogDispenseVisible:false,
      total:0,
      params:{
        pageNum:1,
        pageSize:2,
        uid:''
      },
      orders:[],
      form: {
        onum: '',
        drugId: '',
        times: 1,
        describe:'',
        doctorName: '',
        checkInTime: '',
        checkOutTime: '',
        price: 0,
        patientName:'',
        dentistryName: '',
        status: 0,
      },
      rules: {
        name: [
          { required: true, message: '请输入药物名称', trigger: 'blur' }
        ],
        times: [
          { required: true, message: '请输入帐号', trigger: 'blur' }
        ],
        describe: [
          { required: true, message: '病情描述不能为空', trigger: 'blur' }
        ],
      }
    }
  },
  methods:{
    endSurgery(id) {
      this.$http.put("/order/endSurgery?id="+id).then(resp=>{
        if(resp.data.code === 200){
          this.$message.success("操作成功")
          this.queryAll()
        }else{
          this.$message.error("操作已取消")
        }
      })
    },
    beginSurgery(id) {
      this.$http.put("/order/beginSurgery?id="+id).then(resp=>{
        if(resp.data.code === 200){
          this.$message.success("操作成功")
          this.queryAll()
        }else{
          this.$message.error("操作已取消")
        }
      })
    },
    handleCurrentChange(val){
      this.params.pageNum = val
      this.queryAll()
    },

    queryAll() {
      if(_type === 1) {
        this.$http.get('/order/selectSurByDocid',{params:this.params}).then(res=>{
          this.orders = res.data.data.rows
          this.total = res.data.data.total
        })
      } else if(_type === 0) {
        this.$http.get('/order/selectSurByUid',{params:this.params}).then(res=>{
          this.orders = res.data.data.rows
          this.total = res.data.data.total
        })
      }
      console.log(this.total)
    },
    delOrder(id){
      console.log(id);
      this.$confirm('此操作将删除预约，该操作不可恢复，是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '放弃',
        type: 'warning'
      }).then(() => {
        this.$http.delete('/order/deleteSurgeryOrder?id='+id ).then(res=>{
          if(res.data.code===200){
            this.$message.success("取消预约成功")
            this.queryAll()
          }else{
            this.$message.success("操作失败，请稍后重试！")
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消操作'
        });
      });
    }
  },
  created() {
    let user = JSON.parse(localStorage.getItem("user")||'{}')
    if(user.id){
      this.user = user
      this.params.uid= user.id
      this.queryAll()
    }
  }
}
</script>
<style scoped>
.order-details{
  display: flex;
  justify-content: space-between;
  padding-left: 20px;
}
.order-details .right h4{
  margin-bottom: 0;
}
.order-details .left p{
  font-size: 12px;
  margin: 0.5em 0;
}
.order-item :deep(.el-card__header){
  background-color: #f6f6f6;
}
.order-item{
  margin-bottom: 20px ;
}

</style>