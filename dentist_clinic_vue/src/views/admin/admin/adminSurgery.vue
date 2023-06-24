<template xmlns:el-col="http://www.w3.org/1999/html">
  <div>
    <el-card>
      <div slot="header">
        <h2>管理手术预约</h2>
      </div>
      <div v-if="orders.length">
        <el-card class="order-item" v-for="item in orders" :key="item.id" >
          <div slot="header" class="clearfix">
            <span>预约编号：{{item.id }} &nbsp; 预约日期：{{ item.createTime }}&nbsp;  <el-button type="text" @click="delOrder(item.id)">删除记录</el-button></span>
          </div>
          <div class="order-details">
            <div class="left">
              <p>预约时间: {{ item.surgeryInTime }} - {{ item.surgeryOutTime }}</p>
              <p>预约病人: {{ item.patientName }}</p>
              <p>预约医生：{{ item.doctorName }}</p>
              <p>手术费：{{ item.price }}元</p>
              <p>手术室：{{ item.roomName }} 室</p>
            </div>
            <div class="right">
              <h4 style="color:#67C23A" v-if="item.status===2">已完成</h4>
              <h4  style="color:red" v-else-if="item.status===1">手术中</h4>
              <h4  style="color:#409EFF" v-else-if="item.status===0">已预约 </h4>
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
export default {
  name: "index",
  components:{
  },
  data(){
    return{
      user:{},
      total:0,
      params:{
        pageNum:1,
        pageSize:5,
        uid:''
      },
      orders:[],
      form: {
        onum: '',
        createTime: '',
        doctorName: '',
        checkInTime: '',
        checkOutTime: '',
        price: 0,
        dentistryName: '',
        status: 0,
      },
    }
  },
  methods:{
    handleCurrentChange(val){
      this.params.pageNum = val
      this.queryAll()
    },
    queryAll(){
      // console.log(this.params.uid)
      this.$http.get('/order/selectSurgeryAll',{params:this.params}).then(res=>{
        this.orders = res.data.data.rows
        this.total = res.data.data.total
        // console.log(this.orders[0].createTime)
        //
        // console.log(Object.keys(res.data.data.rows[0]))
        // console.log(this.total)
      })
    },
    delOrder(id){
      this.$confirm('此操作将删除记录，该操作不可恢复，是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '放弃',
        type: 'warning'
      }).then(() => {
        this.$http.delete('/order/deleteSurgeryOrder?id='+id ).then(res=>{
          if(res.data.code===200){
            this.$message.success("删除成功")
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