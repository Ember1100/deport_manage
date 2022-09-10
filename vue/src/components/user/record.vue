<template>
  <div style="width: 1200px;">

    <Table :columns="columns" :data="data" height="700"></Table>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        modal1: false,
        p: false,
        columns: [{
            type: 'selection',
          },
          {
            title: '序号',
            type: 'index',
          },
          {
            title: '商品名',
            key: 'goodsName',
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
                    type: 'error',
                    size: 'small',
                  },
                  on: {
                    click: () => {
                          this.remove(params)
                    },

                  }
                }, '删除')
              ]);
            }
          }
        ],
        data: [],
        record: {
          username: this.$route.query.username,
        }
      }
    },
    components: {

    },
    mounted() {
      this.getRecord();
    },
    methods: {
      getRecord() {
        let postData = this.qs.stringify({
          username: this.$route.query.username,
        });
        this.axios({
            method: "post",
            url: "/api/getUserRecord",
            data: postData
          })
          .then(response => {
            if (response.data.ok == 1) {
              console.log(this.record.username);

              this.data = response.data.data;
              console.log(this.data);
            }

          })
          .catch(function(error) {
            console.log(error);
          });
      },

      remove(params) {
        let postData = this.qs.stringify({
          id: params.row.id,
        });
        this.axios({
            method: "post",
            url: "/api/delRecord",
            data: postData
          })
          .then(response => {
            console.log(response.data);
            if (response.data.ok == 1) {
              this.$Message.success("删除成功");
            } else {
              this.$Message.warning("删除失败");
            }
            this.getRecord();
          })
          .catch(function(error) {
            console.log(error);
          });
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
