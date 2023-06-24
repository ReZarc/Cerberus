<template xmlns:el-col="http://www.w3.org/1999/html">
  <div class="home">
    <el-container class="is-vertical">
      <el-main>
        <div class="main">
          <el-card>
            <div slot="header">
              <span>配药单</span>
            </div>
            <div v-if="orders.length">
              <el-card v-for="item in orders" :key="item.id" class="order-item">
                <div slot="header" class="clearfix">
                  <span>编号：{{ item.onum }} &nbsp; 创建时间：{{ item.createTime }}</span>
                </div>
                <div class="order-details">
                  <div class="left">
                    <p>创建时间: {{ item.createTime }}</p>
                    <p>医生姓名：{{ item.doctorName }}</p>
                    <p>病情描述：{{ item.describe }}</p>
                    <p>配药：{{ item.drugName }} * {{ item.times }}</p>
                    <p>费用：{{ item.price }}元</p>
                  </div>
                  <div class="right">
                    <h4 v-if="item.status===2" style="color:rgb(0, 255, 0)">已缴费</h4>
                    <h4 v-else-if="item.status===1" style="color:#409EFF">
                      已配药
                    </h4>
                    <h4 v-else-if="item.status===0" style="color:#409EFF">未配药</h4>
                  </div>
                </div>
              </el-card>

            </div>
            <el-empty v-else description="尚无预约" style="margin-top: 15vh"></el-empty>
            <el-pagination
                v-show="orders.length"
                :current-page="params.pageNum"
                :page-size="params.pageSize"
                :total="total"
                layout="prev, pager, next"
                style="float: right"
                @current-change="handleCurrentChange">
            </el-pagination>
          </el-card>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "index",
  components: {
  },
  data() {
    return {
      user: {},
      total: 0,
      params: {
        pageNum: 1,
        pageSize: 3,
        uid: ''
      },
      orders: [],
      form: {
        onum: '',
        drugId: '',
        times: 1,
        describe: '',
        doctorName: '',
        checkInTime: '',
        checkOutTime: '',
        price: 0,
        patientName: '',
        dentistryName: '',
        status: 0,
      },
      rules: {
        name: [
          {required: true, message: '请输入药物名称', trigger: 'blur'}
        ],
        times: [
          {required: true, message: '请输入帐号', trigger: 'blur'}
        ],
        describe: [
          {required: true, message: '病情描述不能为空', trigger: 'blur'}
        ],
      }
    }
  },
  methods: {
    handleCurrentChange(val) {
      this.params.pageNum = val
      this.queryAll()
    },
    queryAll() {
      this.$http.get('/order/selectDrugOrderByUid', {params: this.params}).then(res => {
        this.orders = res.data.data.rows
        this.total = res.data.data.total
      })
    },
    delOrder(onum) {
      this.$confirm('此操作将删除该配药单，该操作不可恢复，是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '放弃',
        type: 'warning'
      }).then(() => {
        this.$http.delete('/order/deleteDrugOrder?onum=' + onum).then(res => {
          if (res.data.code === 200) {
            this.$message.success("删除成功")
            this.queryAll()
          } else {
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
    let user = JSON.parse(localStorage.getItem("user") || '{}')
    if (user.id) {
      this.user = user
      this.params.uid = user.id
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