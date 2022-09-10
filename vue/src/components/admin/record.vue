<template>

  <div>
    <Page :page-size="page.pageSize" :total="page.total" :current="page.pageNo" @on-change="changePage" ></Page>
    <Table :columns="columns" :data="tableData" height="700"></Table>
  </div>

</template>

<script>
  export default {
    data() {
      return {
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
          pageNo:1,
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
                      this.commit(params)
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
                      this.refuse(params)
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
          .catch(function (error) {
            console.log(error);
          });
      },
      commit(params) {
          if (params.row.state == "暂未处理") {
          let postData = this.qs.stringify({
            id: params.row.id,
            goodsName: params.row.goodsName,
            username: params.row.username
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
                  if (params.row.type == "出库")
                    this.updateform.number = this.updateform.number - params.row.number;
                  else
                    this.updateform.number = this.updateform.number + params.row.number;
                  this.update();
                  let postData = this.qs.stringify({
                    id: params.row.id,
                    goodsName: params.row.goodsName,
                    username: params.row.username,
                    number: params.row.number,
                    type: params.row.type,
                    state: "已批准",
                  });
                  this.axios({
                      method: "post",
                      url: "/api/updaterecord",
                      data: postData
                    })
                    .then(response => {
                      console.log(response.data);
                      if (response.data.ok == 1) {
                        this.$Message.success("同意成功");
                        this.initTableData();
                        this.getGoods();
                      } else {
                        console.log(response.data);
                        this.$Message.warning("同意失败");
                      }
                    })
                    .catch(function (error) {
                      console.log(error);
                    });
                }
              } else {
                this.$Message.warning("查找失败");
              }
            })
            .catch(function (error) {
              console.log(error);
            });
          }
        this.getRecord();
      },
      refuse(params) {
        if (params.row.state == "暂未处理") {
          let postData = this.qs.stringify({
            id: params.row.id,
            goodsName: params.row.goodsName,
            username: params.row.username,
            number: params.row.number,
            type: params.row.type,
            state: '不予批准',
          });
          this.axios({
              method: "post",
              url: "/api/updaterecord",
              data: postData
            })
            .then(response => {
              console.log(response.data);
              if (response.data.ok == 1) {
                this.$Message.success("拒绝成功");
                this.initTableData();
                this.getGoods();
              } else {
                console.log(response.data);
                this.$Message.warning("拒绝失败");
              }
            })
            .catch(function (error) {
              console.log(error);
            });
          this.getRecord();
        }

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
          .catch(function (error) {
            console.log(error);
          });
      },
      //初始化table数据
      initTableData() {
        console.log("pageNo :  " + this.page.pageNo)
        this.axios({
          method:"post",
          url:"/api/getRecordPage",
          params:this.page
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
</style>
