<template xmlns:el-col="http://www.w3.org/1999/html">
  <div>
    <el-card>
      <div slot="header">
        <h2>管理药物订单</h2>
      </div>
      <div v-if="orders.length">
        <el-card class="order-item" v-for="item in orders" :key="item.id" >
          <div slot="header" class="clearfix">
            <span>预约编号：{{item.onum }} &nbsp; 创建时间：{{ item.createTime }}&nbsp;  <el-button type="text" @click="delDrugOrder(item.onum)">删除记录</el-button></span>
          </div>
          <div class="order-details">
            <div class="left">
              <p>配药病人: {{ item.patientName }}</p>
              <p>病情描述：{{ item.describe }}</p>
              <p>价格：{{ item.price }}元</p>
              <p>配药：{{ item.drugName }} * {{item.times}}</p>
              <p>配药医生：{{ item.doctorName }}</p>
            </div>
            <div class="right">
              <h4 style="color:rgb(0, 255, 0)" v-if="item.status===2">已缴费</h4>
              <h4  style="color:#409EFF" v-else-if="item.status===1" >已配药</h4>
              <h4  style="color:#409EFF" v-else-if="item.status===0">
                未配药
                <el-button type="primary" @click="Prescript(item.onum)">配药</el-button>
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
    delDrugOrder(onum) {
      this.$confirm('此操作将删除该配药单，该操作不可恢复，是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '放弃',
        type: 'warning'
      }).then(() => {
        this.$http.delete('/order/deleteDrugOrder?onum='+onum ).then(res=>{
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

    },
    handleCurrentChange(val){
      this.params.pageNum = val
      this.queryAll()
    },
    queryAll(){
      this.$http.get('/order/selectDrugOrderAll',{params:this.params}).then(res=>{
        this.orders = res.data.data.rows
        this.total = res.data.data.total
      })
    },
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