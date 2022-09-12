<template>
  <div>
    <div class="action-container">
      <div class="search"><Input v-model="goodsName" size="large" placeholder="请输入物品名" style="width:500px" />
        <Button type="primary" @click="search" style="margin-left:5px;">查找</Button><Button type="primary"
          @click="viewAll" style="margin-left:5px;margin-right:5px;">显示所有</Button><Button type="primary"
          @click="addModal">增加</Button>
      </div>
    </div>
    <Table :columns="columns" :data="data" height="700"></Table>
    <Modal v-model="updatemodal" @on-ok="handleSubmit()" @on-cancel="cancel">
      <div>
        <Form ref="updateform" :model="updateform" :rules="ruleValidate" :label-width="80">
          <FormItem label="物品名" prop="goodsName">
            <Input v-model="updateform.goodsName" placeholder="请输入物品名"></Input>
          </FormItem>
          <FormItem label="简介">
            <Input v-model="updateform.context" placeholder="请输入简介"></Input>
          </FormItem>
          <FormItem label="数量" prop="number">
            <Input v-model="updateform.number" placeholder="不可更改" disabled></Input>
          </FormItem>
          <FormItem label="最大库存" prop="max">
            <Input v-model="updateform.max" disabled></Input>
          </FormItem>
          <FormItem label="最小库存" prop="min">
            <Input v-model="updateform.min" disabled></Input>
          </FormItem>
          <FormItem label="价格" prop="price">
            <Input v-model="updateform.price" placeholder="Enter your e-mail"></Input>
          </FormItem>
          <FormItem label="所属人">
            <Input v-model="updateform.username" placeholder="Enter your e-mail"></Input>
          </FormItem>
        </Form>
      </div>
    </Modal>
    <Modal v-model="addmodal" @on-ok="add()" @on-cancel="cancel">
      <div>
        <Form ref="addform" :model="updateform" :label-width="80">
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
            <Input v-model="addform.max" disabled ></Input>
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
    <Modal v-model="recordmodal" @on-ok="recordSubmit()" @on-cancel="cancel">
      <div>
        <Form ref="recordmodal" :model="recordform" :label-width="80">
          <FormItem label="物品名">
            <Input v-model="recordform.goodsName" disabled></Input>
          </FormItem>
          <FormItem label="所属人">
            <Input v-model="recordform.username" disabled></Input>
          </FormItem>
          <FormItem label="类型">
            <RadioGroup v-model="recordform.type">
              <Radio label="出库"></Radio>
              <Radio label="入库"></Radio>
            </RadioGroup>
          </FormItem>
          <FormItem label="数量">
            <Input v-model="recordform.number" placeholder="请输入数量"></Input>
          </FormItem>
        </Form>
      </div>
    </Modal>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        goodsName: '',
        recordmodal: false,
        addmodal: false,
        updatemodal: false,

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
          max: 1000,
          min: 1,
          price: 0,
          username: '',
        },
        recordform: {
          goodsName: '',
          username: '',
          number: '',
          type: ''
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
            width: 50
          },
          {
            title: '序号',
            type: 'index',
            width: 50
          },
          {
            title: '物品名',
            key: 'goodsName',
            width: 100
          },
          {
            title: '介绍',
            key: 'context',
            width: 150
          },
          {
            title: '数量',
            key: 'number',
            width: 100
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
            width: 150
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
                      this.remove(params)
                    }
                  }
                }, '删除')
              ]);
            }
          }
        ],
        data: [],
      }
    },
    components: {

    },
    mounted() {
      this.getGoods();
    },
    methods: {
      getGoods() {
        let postData = this.qs.stringify({
          username: this.$route.query.username,
        });
        this.axios({
            method: "post",
            url: "/api/searchGoodsByUser",
            data: postData
          })
          .then(response => {
            console.log(response.data);
            if (response.data.ok == 1) {
              this.data = response.data.data;
            } else {
              this.$Message.warning("加载失败");
            }
          })
          .catch(function(error) {
            console.log(error);
          });
      },
      viewAll() {
        this.getGoods();
      },
      addModal() {
        this.addmodal = true;
      },

      add() {
        this.axios({
            method: "post",
            url: "/api/addRec",
            params: this.addform
          })
          .then(response => {
            console.log(response.data);
            if (response.data.ok == 1) {
              this.$Message.success("添加成功");
              this.getGoods();
            } else {
              this.$Message.warning("添加失败");
            }
          })
          .catch(function(error) {
            console.log(error);
          });
      },

      search() {
        let postData = this.qs.stringify({
          goodsName: this.goodsName,
          username: this.$route.query.username
        });
        this.axios({
            method: "post",
            url: "/api/getGoodsMyself",
            data: postData
          })
          .then(response => {
            console.log(response.data);
            if (response.data.ok == 1) {
              this.$Message.success("查找成功");
              this.data = response.data.data;
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
        this.axios({
            method: "post",
            url: "/api/saveGoods",
            params: this.updateform
          })
          .then(response => {
            console.log(response.data);
            if (response.data.ok == 1) {
              this.$Message.success("更改成功");
              this.getGoods();
            } else {
              this.$Message.warning("更改失败");
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
            url: "/api/deletegoods",
            data: postData
          })
          .then(response => {
            console.log(response.data);
            if (response.data.ok == 1) {
              this.$Message.success("删除成功");
            } else {
              this.$Message.warning("删除失败");
            }
            this.getGoods();
          })
          .catch(function(error) {
            console.log(error);
          });
      },
      cancel() {}
    },
  }
</script>

<style scoped>
  .action-container {
    margin-top: 10px;
    margin-bottom: 10px;
  }
</style>
