<template xmlns:el-col="http://www.w3.org/1999/html">
  <div class="home">
    <el-container class="is-vertical">
      <el-main>
        <div class="main">
          <el-card >
            <div slot="header" >
              <span v-if="user.type === 0 || user.type === 1">
                我的预约
                <el-button v-if="user.type === 0" type=“primary” @click="showAppoint">预约就诊</el-button>
              </span>
              <span v-else-if="user.type === 2">配药单</span>
            </div>
            <div v-if="orders.length">
              <el-card class="order-item" v-for="item in orders" :key="item.id" >
                <div slot="header" class="clearfix">
                  <span v-if="user.type === 0 || user.type === 1">预约编号：{{item.onum }} &nbsp; 预约日期：{{ item.createTime }}
                    <el-button type="text" @click="delOrder(item.onum)">删除记录</el-button>
                    <el-button type="text" @click="cancelOrder(item.onum)" v-if="item.status === 0 || item.status === 1">取消预约</el-button>
                  </span>
                  <span v-if="user.type === 2">编号：{{item.onum }} &nbsp; 创建时间：{{ item.createTime }}
                  </span>
                </div>
                <div class="order-details" v-if="user.type === 0">
                  <div class="left">
                    <p>预约时间: {{ item.checkInTime }} - {{ item.checkOutTime }}</p>
                    <p>医生姓名：{{ item.doctorName }}</p>
                    <p>联系方式：{{ item.phone }}</p>
                    <p>挂号费：{{ item.price }}元</p>
                    <p>就诊科室：{{ item.dentistryName }} {{ item.roomName }} 室</p>
                  </div>
                  <div class="right">
                    <h4 style="color:rgb(0, 0, 0)"  v-if="item.status===4">被驳回</h4>
                    <h4 style="color:rgb(255, 0, 0)" v-else-if="item.status===5">就诊中</h4>
                    <h4 style="color:rgb(170, 170, 170)"  v-else-if="item.status===3">已取消</h4>
                    <h4 style="color:rgb(0, 255, 0)" v-else-if="item.status===2">已完成</h4>
                    <h4  style="color:#409EFF" v-else-if="item.status===1" >
                      已预约
                      <p v-if="item.count !== 1">您前方还有 {{ item.count - 1 }} 人</p>
                      <p v-else>您是下一位</p>
                    </h4>
                    <h4  style="color:#409EFF" v-else-if="item.status===0">审核中</h4>
                  </div>
                </div>
                <!--                  医生-->
                <div class="order-details" v-else-if="user.type === 1">
                  <div class="left">
                    <p>预约时间: {{ item.checkInTime }} - {{ item.checkOutTime }}</p>
                    <p>病人姓名：{{ item.patientName }}</p>
                    <p>联系方式：{{ item.phone }}</p>
                    <p>挂号费：{{ item.price }}元</p>
                    <p>就诊科室：{{ item.dentistryName }} {{ item.roomName }} 室</p>
                  </div>
                  <div class="right">
                    <h4 style="color:rgb(170, 170, 170)"  v-if="item.status===3">已取消</h4>
                    <h4 style="color:rgb(255, 0, 0)" v-else-if="item.status===5">
                      <el-button type="primary" @click="Complish(item.onum)">完成就诊</el-button>
                      <el-button type="primary" @click="showSurgery(item.onum)">安排手术</el-button>
                      <el-button type="primary" @click="showDispense(item.onum)">配发药物</el-button>
                      就诊中
                    </h4>
                    <h4 style="color:rgb(0, 255, 0)" v-else-if="item.status===2">已完成</h4>
                    <h4  style="color:#409EFF" v-else-if="item.status===1" >
                      已预约
                      <el-button type="primary" @click="Consult(item.onum)">叫号就诊</el-button>
                    </h4>
                  </div>
                </div>
                <!--                  药师-->
                <div class="order-details" v-else-if="user.type === 2">
                  <div class="left">
                    <p>创建时间: {{ item.createTime }}</p>
                    <p>医生姓名：{{ item.doctorName }}</p>
                    <p>病人姓名：{{ item.patientName }}</p>
                    <p>病情描述：{{ item.describe }}</p>
                    <p>配药：{{ item.drugName }} * {{item.times}}</p>
                    <p>费用：{{ item.price }}元</p>
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
      </el-main>
    </el-container>

    <el-dialog
        title="分配药物"
        :visible.sync="dialogDispenseVisible"
        width="700px"
        :before-close="dialogDispenseClose"
    >
      <el-form :model="form1" :rules="rules1" ref="form1" label-width="100px" style="width: 600px">
        <el-form-item label="药物名称：" prop="drugId">
          <el-select v-model="form1.drugId" style="width: 100%">
            <el-option   label="布洛芬胶囊" :value="1"></el-option>
            <el-option   label="氧化锌丁香油糊剂" :value="2"></el-option>
            <el-option   label="氢氧化钙" :value="3"></el-option>
            <el-option   label="多聚甲醛失活剂" :value="4"></el-option>
            <el-option   label="阿莫西林胶囊" :value="5"></el-option>
            <el-option   label="樟脑水合氯醛酊" :value="6"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="药物数量：" prop="times">
          <el-input-number v-model="form1.times"></el-input-number>
        </el-form-item>
        <el-form-item label="病情描述：" prop="describe">
          <div style="display: flex">
            <el-input v-model="form1.describe"></el-input>
          </div>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogDispenseClose">取 消</el-button>
        <el-button type="primary" @click="addDispense( 'form1')">完成添加</el-button>
      </span>
    </el-dialog>
    <el-dialog
        title="安排手术"
        :visible.sync="dialogSurgeryVisible"
        width="700px"
        :before-close="dialogSurgeryClose"
    >
      <el-form :model="form2" :rules="rules2" ref="form2" label-width="100px" style="width: 600px">
        <el-form-item label="手术类型：" prop="type">
          <el-select v-model="form2.type" style="width: 100%">
            <el-option   label="拔牙" :value="0"></el-option>
            <el-option   label="补牙" :value="1"></el-option>
            <el-option   label="种牙" :value="2"></el-option>
            <el-option   label="其他" :value="3"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="手术室" prop="roomId">
          <el-select v-model="form2.roomId" style="width: 30%">
            <el-option   label="201" :value="8"></el-option>
            <el-option   label="202" :value="9"></el-option>
            <el-option   label="203" :value="10"></el-option>
            <el-option   label="204" :value="11"></el-option>
            <el-option   label="205" :value="12"></el-option>
            <el-option   label="206" :value="13"></el-option>
            <el-option   label="207" :value="14"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="时间：">
          <el-time-select
              placeholder="起始时间"
              v-model="form2.surgeryInTime"
              :picker-options="{
                start: '08:30',
                step: '00:15',
                end: '18:30'
              }" prop="surgeryInTime">
          </el-time-select>
          <el-time-select
              placeholder="结束时间"
              v-model="form2.surgeryOutTime"
              :picker-options="{
              start: '08:30',
              step: '00:15',
              end: '18:30',
              minTime: form2.surgeryInTime
              }" prop="surgeryInTime">
          </el-time-select>
        </el-form-item>
        <el-form-item label="病情描述：" prop="describe">
          <div style="display: flex">
            <el-input v-model="form2.describe"></el-input>
          </div>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogDispenseClose">取 消</el-button>
        <el-button type="primary" @click="addSurgery( 'form2')">预定</el-button>
      </span>
    </el-dialog>

    <el-dialog
        title="预约挂号"
        :visible.sync="dialogAppointVisible"
        width="700px"
        :before-close="dialogAppointClose"
    >
      <el-form :model="form3" :rules="rules3" ref="form3" label-width="100px" style="width: 600px">
        <el-form-item label="就诊科室：" prop="dentistryId">
          <el-select v-model="form3.dentistryId" style="width: 60%">
            <el-option v-for="dentistry in dentistrys" :label="dentistry.name" :value="dentistry.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="roomId">
          <el-select v-model="form3.roomId" style="width: 30%">
            <el-option v-for="room in rooms" :label="room.name" :value="room.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="看诊医生：" prop="doctorId">
          <el-select v-model="form3.doctorId" style="width: 60%">
            <el-option v-for="doctor in doctors" :label="doctor.name" :value="doctor.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="时间：">
          <el-time-select
              placeholder="起始时间"
              v-model="form3.checkInTime"
              :picker-options="{
                start: '08:30',
                step: '00:15',
                end: '18:30'
              }" prop="surgeryInTime">
          </el-time-select>
          <el-time-select
              placeholder="结束时间"
              v-model="form3.checkOutTime"
              :picker-options="{
              start: '08:30',
              step: '00:15',
              end: '18:30',
              minTime: form3.checkInTime
              }" prop="surgeryOutTime">
          </el-time-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogAppointClose">取 消</el-button>
        <el-button type="primary" @click="addAppoint( 'form3')">预定</el-button>
      </span>
    </el-dialog>
  </div>

</template>


<script>
let _type = JSON.parse(localStorage.getItem("user")).type
export default {
  name: "index",
  computed: {

  },

  data(){
    return{
      currentDate: new Date().toLocaleDateString('zh-CN', { year: 'numeric', month: '2-digit', day: '2-digit' }).replace(/\//g, '-'),
      user:{},
      dialogSurgeryVisible:false,
      dialogAppointVisible:false,
      dialogDispenseVisible:false,
      total:0,
      params:{
        pageNum:1,
        pageSize:2,
        uid:''
      },
      orders:[],
      doctors:[],
      dentistrys:[],
      rooms:[],
      form1: {
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
      form2:{
        onum: '',
        describe:'',
        price: 0,
        type: 0,
        status: 0,
        roomId: 1,
        doctorId: '',
        surgeryInTime: '',
        surgeryOutTime: '',
      },
      form3: {
        onum: '',
        createTime: '',
        dentistryId: 1,
        doctorId: '',
        checkInTime: '',
        checkOutTime: '',
        price: 0,
        roomId: 1,
        status: 0,
      },
      rules1: {
        name: [
          { required: true, message: '请输入药物名称', trigger: 'blur' }
        ],
        times: [
          { required: true, message: '请输入帐号', trigger: 'blur' }
        ],
        describe: [
          { required: true, message: '病情描述不能为空', trigger: 'blur' }
        ],
      },
      rules2: {
        roomId: [
          { required: true, message: '请选择手术室', trigger: 'blur' }
        ],
        doctorId: [
          { required: true, message: '请选择执刀医生', trigger: 'blur' }
        ],
        type: [
          { required: true, message: '请选择手术类型', trigger: 'blur' }
        ],
        surgeryInTime: [
          { required: true, message: '请选择时间', trigger: 'blur' }
        ],
        surgeryOutTime: [
          { required: true, message: '请选择时间', trigger: 'blur' }
        ],
      },
      rules3: {
        doctorId: [
          { required: true, message: '请选择主刀医生', trigger: 'blur' }
        ],
        roomId: [
          { required: true, message: '请选择科室', trigger: 'blur' }
        ],
        dentistryId: [
          { required: true, message: '请选择时间', trigger: 'blur' }
        ],
        surgeryOutTime: [
          { required: true, message: '请选择时间', trigger: 'blur' }
        ],

      }
    }
  },
  methods:{
    handleCurrentChange(val){
      this.params.pageNum = val
      this.queryAll()
    },
    addAppoint(formName) {
      this.$http.get("user/checkIsOrder?id="+this.user.id).then(res=> {
        console.log(this.user.id)
        if(res.data.code === 200){
          this.$refs[formName].validate((valid) => {
            if (valid) {
              this.form3.userId = this.user.id;
              this.form3.checkInTime = this.currentDate + " " + this.form3.checkInTime + ":00";
              this.form3.checkOutTime = this.currentDate + " " + this.form3.checkOutTime + ":00";
              this.$http.post("/order/addAppoint",this.form3).then(resp=>{
                if(resp.data.code === 200){
                  this.$message.success("添加成功！")
                  this.queryAll()
                  this.dialogAppointVisible = false
                }else{
                  this.$message.error("添加失败！")
                }
              })
            }
          });
        }else{
          this.$message.error("当前有其他预约未就诊！")
        }
      })

    },
    addSurgery(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.form2.surgeryInTime = this.currentDate + " " + this.form2.surgeryInTime + ":00";
          this.form2.surgeryOutTime = this.currentDate + " " + this.form2.surgeryOutTime + ":00";
          this.$http.post("/order/addSurgery",this.form2).then(resp=>{
            if(resp.data.code === 200){
              this.$message.success("添加成功！")
              this.queryAll()
              this.dialogSurgeryVisible = false
            }else{
              this.$message.error("添加失败！")
            }
          })
        }
      });
    },
    addDispense(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$http.post("/order/addDrugOrder",this.form1).then(resp=>{
            if(resp.data.code === 200){

              this.dialogDispenseVisible = false
              this.$message.success("添加成功")
              this.queryAll()
            }else{
              this.$message.error("添加失败！")
            }
          })
        }
      });
    },
    dialogDispenseClose(){
      this.dialogDispenseVisible = false
    },
    dialogSurgeryClose(){
      this.dialogSurgeryVisible = false
    },
    dialogAppointClose(){
      this.dialogAppointVisible = false
    },

    showDispense(onum) {
      this.form1.onum = onum
      this.dialogDispenseVisible = true
    },
    showSurgery(onum) {
      this.form2.onum = onum
      this.dialogSurgeryVisible = true
    },
    showAppoint() {
      this.dialogAppointVisible = true
    },
    queryAll(onum) {
      if(_type === 1) {
        this.params.pageSize = 100
        this.$http.get('/user/selectSurRoom',{params:this.params}).then(res=>{
          this.rooms = res.data.data.rows
        })
        this.params.pageSize = 3
        this.$http.get('/order/selectByDocid',{params:this.params}).then(res=>{
          this.orders = res.data.data.rows
          this.total = res.data.data.total
        })
      } else if(_type === 0) {
        this.params.pageSize = 100
        this.$http.get('/user/selectRoom',{params:this.params}).then(res=>{
          this.rooms = res.data.data.rows
        })
        this.$http.get('/user/selectDentistry',{params:this.params}).then(res=>{
          this.dentistrys = res.data.data.rows
        })
        this.$http.get('/user/selectDoc',{params:this.params}).then(res=>{
          this.doctors = res.data.data.rows
        })
        this.params.pageSize = 3
        this.$http.get('/order/selectByUid',{params:this.params}).then(res=>{
          this.orders = res.data.data.rows
          this.total = res.data.data.total
        })
      } else {
        this.$http.get('/order/selectDrugOrderAll',{params:this.params}).then(res=>{
          this.orders = res.data.data.rows
          this.total = res.data.data.total
        })
      }
      // console.log(this.orders)
    },
    Complish(onum) {
      this.$http.put("/order/complish?onum="+onum).then(resp=>{
        if(resp.data.code === 200){
          this.$message.success("操作成功")
          this.queryAll()
        }else{
          this.$message.error("操作已取消")
        }
      })
    },
    Prescript(onum) {
      this.$http.put("/order/prescript?onum="+onum).then(resp=>{
        console.log(onum)
        if(resp.data.code === 200){
          this.$message.success("操作成功")
          this.queryAll()
        }else{
          this.$message.error("操作已取消")
        }
      })
    },
    Consult(onum) {
      this.$http.get("/user/checkDiagnose?id="+this.user.id).then(res=>{
        if(res.data.code === 200) {
          this.$http.get("user/checkRoomStatus?onum="+onum).then(ress=> {
            if(ress.data.code === 200) {
              this.$http.put("/order/consult?onum="+onum).then(resp=>{
                if(resp.data.code === 200){
                  this.$message.success("操作成功")
                  this.queryAll()
                }else{
                  this.$message.error("操作失败，请稍后重试")
                }
              })
            } else {
              this.$message.error("房间正在使用中！")
            }
          })
        } else {
          this.$message.error("当前有其他患者预约未结束！")
        }
      })

    },
    cancelOrder(onum){
      this.$confirm('是否确定取消该订单?', '提示', {
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
      this.$confirm('此操作将删除预约，该操作不可恢复，是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '放弃',
        type: 'warning'
      }).then(() => {
        this.$http.delete('/order?onum='+onum ).then(res=>{
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
      console.log(user.id)
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