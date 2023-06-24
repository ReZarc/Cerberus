<template xmlns:el-col="http://www.w3.org/1999/html">
  <div>
    <el-card>
      <div slot="header">
        <h2>管理预约</h2>
      </div>
      <div v-if="orders.length">
        <el-card class="order-item" v-for="item in orders" :key="item.id" >
          <div slot="header" class="clearfix">
            <span>预约编号：{{item.onum }} &nbsp; 预约日期：{{ item.createTime }}&nbsp;  <el-button type="text" @click="delOrder(item.onum)">删除预约</el-button></span>
          </div>
          <div class="order-details">
            <div class="left">
              <p>预约时间: {{ item.checkInTime }} - {{ item.checkOutTime }}</p>
              <p>预约病人: {{ item.patientName }}</p>
              <p>预约医生：{{ item.doctorName }}</p>
              <p>挂号费：{{ item.price }}元</p>
              <p>就诊科室：{{ item.dentistryName }} {{item.roomName }} 室</p>
            </div>
            <div class="right">
              <h4 style="color:rgb(170, 170, 170)"  v-if="item.status===4">被驳回</h4>
              <h4 style="color:rgb(170, 170, 170)"  v-if="item.status===3">已取消</h4>
              <h4 style="color:#67C23A" v-else-if="item.status===2">已完成</h4>
              <h4 style="color:rgb(255, 0, 0)" v-else-if="item.status===5">就诊中</h4>
              <h4  style="color:#409EFF" v-else-if="item.status===1">已预约</h4>
              <h4  style="color:#409EFF" v-else-if="item.status===0">
                审核中
                <el-button type="primary" @click="Approve(item.onum)">通过</el-button>
                <el-button type="primary" @click="Reject(item.onum)">拒绝</el-button>
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
      this.$http.get('/order/selectAll',{params:this.params}).then(res=>{
        this.orders = res.data.data.rows
        this.total = res.data.data.total
        console.log(this.orders[0].roomName)
      })
    },
    Approve(onum) { // 你可以在这里发送一个axios请求，告诉后端你要通过这个预约，并传递item.onum作为参数 //
      this.$http.put("/order/approve?onum="+onum).then(resp=>{
        if(resp.data.code === 200){
          this.$message.success("审核已通过")
          this.queryAll()
        }else{
          this.$message.error("操作已取消")
        }
      })
    },
    Reject(onum) {
      this.$http.put("/order/reject?onum="+onum).then(resp=>{
        if(resp.data.code === 200){
          this.$message.success("审核已拒绝")
          this.queryAll()
        }else{
          this.$message.error("操作已取消")
        }
      })
    },
    cancelOrder(onum){
      this.$confirm('是否确定取消该预约?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '放弃',
        type: 'warning'
      }).then(() => {
        this.$http.put('/order/cancelOrder?onum='+onum ).then(res=>{
          if(res.data.code===200){
            this.$message.success("取消成功~")
            this.queryAll()
          }else{
            this.$message.success("取消失败，请稍后重试！")
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已放弃取消订单'
        });
      });

    },
    delOrder(onum){
      this.$confirm('此操作将取消预约并删除记录，该操作不可恢复，是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '放弃',
        type: 'warning'
      }).then(() => {
        this.$http.delete('/order?onum='+onum ).then(res=>{
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
.el-card{
  width: 100%;
}
.el-main{
  width: 100%;

}
.main{
  width: 100%;
  height: 100%;
  max-width: 1160px;
  min-width: 768px;
  margin: 0 auto;
  display: flex;
}

.home{
  width: 100%;
  height: 100%;
}

.el-container{
  height: 100%;
}
.el-main{
  background-color: #f5f7fa;
}
</style>