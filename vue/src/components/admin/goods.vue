<template>
  <div>
    <div class="action-container">
      <div class="search">
        <Input v-model="goodsName" size="large" placeholder="请输入物品名" style="width:500px" />
        <Button type="primary" @click="search" style="margin-left:5px;">查找</Button>
        <Button type="primary" @click="viewAll" style="margin-left:5px;margin-right:5px;">显示所有</Button>
        <Button type="primary" @click="addModal">入库</Button>
      </div>
    </div>
    <Page :page-size="page.pageSize" :total="page.total" :current="page.pageNo" @on-change="changePage"></Page>
    <Table :columns="columns" :data="tableData" height="700" ref="table"> </Table>

    <Modal v-model="updatemodal" @on-ok="handleSubmit()" @on-cancel="cancel">
      <div class="modal">
        <Form ref="updateform" :model="updateform" :rules="ruleValidate" style="margin-top:20px;margin-bottom:20px;"
          :label-width="80">
          <FormItem label="物品名" prop="goodsName">
            <Input v-model="updateform.goodsName" placeholder="Enter your name"></Input>
          </FormItem>
          <FormItem label="简介">
            <Input v-model="updateform.context" placeholder="Enter your context"></Input>
          </FormItem>
          <FormItem label="数量" prop="number">
            <Input v-model="updateform.number" disabled></Input>
          </FormItem>
          <FormItem label="最大库存" prop="max">
            <Input v-model="updateform.max" disabled> </Input>
          </FormItem>
          <FormItem label="最小库存" prop="min">
            <Input v-model="updateform.min" disabled></Input>
          </FormItem>
          <FormItem label="价格" prop="price">
            <Input v-model="updateform.price" placeholder="Enter your e-mail"></Input>
          </FormItem>
          <FormItem label="所属人">
            <Input v-model="updateform.username" placeholder="Enter your username"></Input>
          </FormItem>
        </Form>
      </div>
    </Modal>

    <Modal v-model="addmodal" @on-ok="add()" @on-cancel="cancel">
      <div class="modal">
        <Form ref="addform" :model="updateform" :label-width="80" style="margin-top:20px;margin-bottom:20px;">
          <FormItem label="物品名">
            <Input v-model="addform.goodsName" placeholder="Enter your name"></Input>
          </FormItem>
          <FormItem label="简介">
            <Input v-model="addform.context" placeholder="Enter your e-mail"></Input>
          </FormItem>
          <FormItem label="数量">
            <Input v-model="addform.number" placeholder="Enter your name"></Input>
          </FormItem>
          <FormItem label="最大库存">
            <Input v-model="addform.max" disabled></Input>
          </FormItem>
          <FormItem label="最小库存">
            <Input v-model="addform.min" disabled></Input>
          </FormItem>
          <FormItem label="价格">
            <Input v-model="addform.price" placeholder="Enter your e-mail"></Input>
          </FormItem>
          <FormItem label="所属人">
            <Input v-model="addform.username" placeholder="Enter your e-mail"></Input>
          </FormItem>
        </Form>
      </div>
    </Modal>

    <Modal v-model="deletemodal" @on-ok="remove()" @on-cancel="cancel" >
      <div class="modal">
        <form ref="updateform" :model="updateform">
          <p>请输入出库数量：</p>
          <Input v-model="updateform.number" ></Input>
          <!-- <p style="font-size: 22px;">确定提交
          <span style="color: blue;">{{updateform.goodsName}}</span>
          出库请求吗?</p> -->
        </form>
      </div>
    </Modal>

  </div>

</template>
<script>
  export default {
    data() {
      return {
        goodsName: '',
        addmodal: false,
        updatemodal: false,
        deletemodal:false,
        page: { //分页参数
          pageNo: 1,
          pageSize: 5,
          total: 0
        },
        addform: {
          goodsName: '',
          context: '',
          number: 0,
          max: 1000,
          min: 1,
          price: 0,
          username: '',
        },
        updateform: {
          goodsName: '',
          context: '',
          number: 0,
          max: 0,
          min: 0,
          price: 0,
          username: '',
        },
        ruleValidate: {
          goodsName: [{
            required: true,
            message: '物品名不能为空',
            trigger: 'blur'
          }],
          number: [{
            required: true,
            message: '物品不能为空',
            trigger: 'blur'
          }],
          max: [{
            required: true,
            message: '库存量不能为空',
            trigger: 'blur'
          }],
          min: [{
            required: true,
            message: '库存量不能为空',
            trigger: 'blur'
          }],
          price: [{
            required: true,
            message: '价格不能为空',
            trigger: 'blur'
          }]
        },
        columns: [{
            type: 'selection',
            width: 80
          },
          {
            title: '序号',
            type: 'index',
            width: 80
          },
          {
            title: '物品名',
            key: 'goodsName',
            width: 120
          },
          {
            title: '介绍',
            key: 'context',
            width: 150
          },
          {
            title: '数量',
            key: 'number',
            width: 150
          },
          {
            title: '最大库存',
            key: 'max',
            width: 150
          },
          {
            title: '最小库存',
            key: 'min',
            width: 150
          },
          {
            title: '价格',
            key: 'price',
            width: 100
          },
          {
            title: '所属人',
            key: 'username',
            width: 100
          },
          {
            title: '操作',
            key: 'action',
            width: 150,
            align: 'center',
            render: (h, params) => {
              return h('div', [
                h('Button', {
                  props: {
                    type: 'primary',
                    size: 'small'
                  },
                  style: {
                    marginRight: '5px'
                  },
                  on: {
                    click: () => {
                      this.update(params)
                    }
                  }
                }, '修改'),
                h('Button', {
                  props: {
                    type: 'error',
                    size: 'small'
                  },
                  on: {
                    click: () => {
                       this.delete(params)
                    },

                  }
                }, '出库')
              ]);
            }
          }
        ],
        tableData: [],
      }
    },
    components: {

    },
    mounted() {
      this.initTableData()
    },
    methods: {
      getGoods() {
        this.axios({
            method: "post",
            url: "/api/goods",
          })
          .then(response => {
            this.tableData = response.data.data;
          })
          .catch(function(error) {
            console.log(error);
          });
      },
      viewAll() {
        this.initTableData()

      },
      addModal() {
        this.addmodal = true;
      },
      add() {
        let postData = this.qs.stringify({
          goodsName: this.addform.goodsName,
          context: this.addform.context,
          number: this.addform.number,
          min: this.addform.min,
          max: this.addform.max,
          price: this.addform.price,
          username: this.addform.username
        });
        this.axios({
            method: "post",
            url: "/api/addRec",
            data: postData
          })
          .then(response => {
            console.log(response.data);
            console.log(this.addform)
            if (response.data.ok == 1) {
              this.$Message.success("申请提交入库成功");
              this.initTableData()
            } else {
              this.$Message.warning("申请提交入库失败");
            }
          })
          .catch(function(error) {
            console.log(error);
          });
      },
      search() {
        let postData = this.qs.stringify({
          goodsName: this.goodsName,
        });
        this.axios({
            method: "post",
            url: "/api/searchGoods",
            data: postData
          })
          .then(response => {
            console.log(response.data);
            if (response.data.ok == 1) {
              this.$Message.success("查找成功");
              this.tableData = response.data.data;
            } else {
              this.$Message.warning("查找失败");
            }
          })
          .catch(function(error) {
            console.log(error);
          });
      },

      update(params) {
        this.updateform = params.row;
        this.updatemodal = true;
      },
      handleSubmit() {
        console.log(this.updateform.context)
        let postData = this.qs.stringify({
          goodsName: this.updateform.goodsName,
          context: this.updateform.context,
          number: this.updateform.number,
          min: this.updateform.min,
          max: this.updateform.max,
          price: this.updateform.price,
          username: this.updateform.username,
          id: this.updateform.id
        });
        this.axios({
            method: "post",
            url: "/api/saveGoods",
            data: postData
          })
          .then(response => {
            console.log(response.data);
            if (response.data.ok == 1) {
              this.$Message.success("更改成功");
              this.initTableData()
            } else {
              this.$Message.warning("更改失败");
            }
          })
          .catch(function(error) {
            console.log(error);
          });
      },

      delete(params) {
        this.deletemodal = true;
        this.updateform = params.row;
      },

      remove() {
        let postData = this.qs.stringify({
           id: this.updateform.id,
           number:this.updateform.number,
        });
        this.axios({
            method: "post",
            url: "/api/deleteGoods",
            data: postData
          })
          .then(response => {
            console.log(response.data);
            if (response.data.ok == 1) {
              this.$Message.success("申请提交出库成功");
              this.initTableData();
            } else {
              this.$Message.warning("申请提交出库失败");
            }
            this.initTableData();
          })
          .catch(function(error) {
            console.log(error);
          });
      },

      cancel() {},

      //初始化table数据
      initTableData() {
        console.log("pageNo :  " + this.page.pageNo)
        this.axios({
            method: "get",
            url: "/api/goodsGetPage",
            params: this.page
          })
          .then(response => {
            console.log(response.data);
            if (response.data.ok == 1) {
              this.page.total = response.data.data.total,
                this.tableData = response.data.data.list;
            }
          })
      },

      // 改变当前页
      changePage(index) {
        this.page.pageNo = index;
        this.initTableData();
      },
    },
  }
</script>

<style scoped>
  .action-container {
    margin-top: 10px;
    margin-bottom: 10px;
  }

  .modal {
    padding: 20px;
    font-size: 12px;
    line-height: 2;
    /* background-image: url(../../img/modal.png) */
  }
  p span {
    color: black;
  }
</style>
