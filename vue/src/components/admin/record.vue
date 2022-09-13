<template>

  <div>
    <Page :page-size="page.pageSize" :total="page.total" :current="page.pageNo" @on-change="changePage"></Page>
    <Table :columns="columns" :data="tableData" height="700"></Table>

    <Modal v-model="agreemodal" @on-ok="commit()" @on-cancel="cancel">
      <div class="modal">
        <form ref="updateform" :model="updateform">
          <p>确定同意用户<span style="color: red;">{{updateform.username}}</span>请求吗</p>
          <!-- <p style="font-size: 22px;">确定提交
          <span style="color: blue;">{{updateform.goodsName}}</span>
          出库请求吗?</p> -->
        </form>
      </div>
    </Modal>


    <Modal v-model="denymodal" @on-ok="refuse()" @on-cancel="cancel">
      <div class="modal">
        <form ref="updateform" :model="updateform">
          <p>确定拒绝用户<span style="color: red;">{{updateform.username}}</span>请求吗</p>
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
        agreemodal: false,
        denymodal: false,
        updateform: {
          goodsName: '',
          context: '',
          number: 0,
          max: 0,
          min: 0,
          price: 0,
          username: '',
        },

        page: { //分页参数
          pageNo: 1,
          pageSize: 5,
          total: 0
        },
        columns: [{
            type: 'selection',
          },
          {
            title: '序号',
            type: 'index',
          },
          {
            title: '物品名',
            key: 'goodsName',
          },
          {
            title: '所属人',
            key: 'username',
          },
          {
            title: '数量',
            key: 'number',
          },
          {
            title: '类型',
            key: 'type',
          },
          {
            title: '状态',
            key: 'state',
          },
          {
            title: '操作',
            key: 'action',
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
                      this.agree(params)
                    }
                  }
                }, '同意'),
                h('Button', {
                  props: {
                    type: 'error',
                    size: 'small'
                  },
                  on: {
                    click: () => {
                      this.deny(params)
                    }
                  }
                }, '拒绝')
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
      // this.getRecord();
      this.initTableData();
    },
    methods: {
      getRecord() {
        this.axios({
            method: "post",
            url: "/api/record",
          })
          .then(response => {
            this.data = response.data.data;
          })
          .catch(function(error) {
            console.log(error);
          });
      },
      agree(params) {
        console.log(params.row.state)
        if (params.row.state == "暂未处理") {
          this.agreemodal = true;
          this.updateform = params.row;
        }
      },
      commit() {
        let postData = this.qs.stringify({
          id: this.updateform.id,
          goodsName: this.updateform.goodsName,
          username: this.updateform.username,
          number: this.updateform.number,
          type: this.updateform.type,
        });
        this.axios({
            method: "post",
            url: "/api/searchgoodsbyusergoods",
            data: postData
          })
          .then(response => {
            console.log(response.data);
            if (response.data.ok == 1) {
              if (response.data.data.length != 0) {
                this.updateform = response.data.data
                console.log(this.updateform);
                this.update();
                let postData = this.qs.stringify({
                  id: this.updateform.id,
                  goodsName: this.updateform.goodsName,
                  username: this.updateform.username,
                  number: this.updateform.number,
                  type: this.updateform.type,
                  state: "已批准",
                });
                this.axios({
                    method: "post",
                    url: "/api/updaterecord",
                    data: postData
                  })
                  .then(response => {
                    console.log(response.data);
                    console.log(1111);
                    if (response.data.ok == 1) {
                      this.$Message.success("同意成功");
                      this.initTableData();
                    } else {
                      console.log(response.data);
                      this.$Message.warning("同意失败");
                    }
                  })
                  .catch(function(error) {
                    console.log(error);
                  });
              }
            } else {
              this.$Message.warning("查找失败");
            }
          })
          .catch(function(error) {
            console.log(error);
          });
        this.initTableData();
      },


      deny(params) {
        if (params.row.state == "暂未处理") {
          this.denymodal = true;
          this.updateform = params.row;
        }
      },


      refuse() {
          let postData = this.qs.stringify({
            id:this.updateform.id,
            goodsName: this.updateform.goodsName,
            username: this.updateform.username,
            number: this.updateform.number,
            type: this.updateform.type,
            state: '不予批准',
          });
          this.axios({
              method: "post",
              url: "/api/refuse",
              data: postData
            })
            .then(response => {
              console.log(response.data);
              if (response.data.ok == 1) {
                this.$Message.success("拒绝成功");
                this.initTableData();
              } else {
                console.log(response.data);
                this.$Message.warning("拒绝失败");
              }
            })
            .catch(function(error) {
              console.log(error);
            });
          this.initTableData();
      },

      update() {
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
            url: "/api/updategoods",
            data: postData
          })
          .then(response => {})
          .catch(function(error) {
            console.log(error);
          });
      },
      //初始化table数据
      initTableData() {
        console.log("pageNo :  " + this.page.pageNo)
        this.axios({
            method: "post",
            url: "/api/getRecordPage",
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
      cancel() {},

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
</style>
