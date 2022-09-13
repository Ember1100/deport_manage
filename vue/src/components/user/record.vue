<template>
  <div style="width: 1200px;">

    <Table :columns="columns" :data="data" height="700"></Table>

    <Modal v-model="deletemodal" @on-ok="remove()" @on-cancel="cancel">
      <form ref="deleteform" :model="deleteform">
        <p style="font-size: 22px;">确定删除这条记录吗?</p>
      </form>
    </Modal>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        deletemodal: false,
        p: false,
        deleteform: {
          goodsName: '',
          context: '',
          number: 0,
          max: 0,
          min: 0,
          price: 0,
          username: '',
        },
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
                      this.delete(params)
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

      delete(params) {
        this.deleteform = params.row;
        this.deletemodal = true;
        console.log(params)
      },
      cancel() {},
      remove() {
        let postData = this.qs.stringify({
          id: this.deleteform.id,
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
