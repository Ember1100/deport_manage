<template>
  <div>
    <div class="action-container">
      <div>
        <Input v-model="username" size="large" placeholder="请输入用户名" style="width:500px" />
        <Button type="primary" @click="search" style="margin-left:5px;">查找</Button>
        <Button type="primary" @click="viewAll" style="margin-left:5px;margin-right:5px;">显示所有</Button>
        <Button type="primary" @click="addModal">增加</Button>
      </div>
    </div>
    <Page :page-size="page.pageSize" :total="page.total" :current="page.pageNo" @on-change="changePage"></Page>
    <Table :columns="columns" :data="tableData" height="700"></Table>
    <Modal v-model="updatemodal" @on-ok="handleSubmit()" @on-cancel="cancel">
      <div>
        <Form ref="updateform" :model="updateform" :rules="ruleValidate" :label-width="80">
          <FormItem label="用户名" prop="username">
            <Input v-model="updateform.username" placeholder="Enter your name" disabled></Input>
          </FormItem>
          <FormItem label="昵称" prop="nickname">
            <Input v-model="updateform.nickname" placeholder="Enter your nickname"></Input>
          </FormItem>
          <FormItem label="密码" prop="password">
            <Input v-model="updateform.password" type="password" placeholder="Enter your password"></Input>
          </FormItem>
          <FormItem label="性别" prop="sex">
            <RadioGroup v-model="updateform.sex">
              <Radio label="男"></Radio>
              <Radio label="女"></Radio>
            </RadioGroup>
          </FormItem>
          <FormItem label="号码" prop="phone">
            <Input v-model="updateform.phone" placeholder="Enter your phone"></Input>
          </FormItem>
          <FormItem label="邮箱" prop="email">
            <Input v-model="updateform.email" placeholder="Enter your e-mail"></Input>
          </FormItem>
          <FormItem label="地址">
            <Input v-model="updateform.address" placeholder="Enter your address"></Input>
          </FormItem>
        </Form>
      </div>
    </Modal>
    <Modal v-model="addmodal" @on-ok="add()" @on-cancel="cancel">
      <div>
        <Form :model="addform" :rules="ruleValidate" :label-width="80">
          <FormItem label="用户名" prop="username">
            <Input v-model="addform.username" placeholder="Enter your name"></Input>
          </FormItem>
          <FormItem label="昵称" prop="nickname">
            <Input v-model="addform.nickname" placeholder="Enter your e-mail"></Input>
          </FormItem>
          <FormItem label="密码" prop="password">
            <Input v-model="addform.password" type="password" placeholder="Enter your name"></Input>
          </FormItem>
          <FormItem label="性别" prop="sex">
            <RadioGroup v-model="addform.sex">
              <Radio label="男"></Radio>
              <Radio label="女"></Radio>
            </RadioGroup>
          </FormItem>
          <FormItem label="号码" prop="phone">
            <Input v-model="addform.phone" placeholder="Enter your name"></Input>
          </FormItem>
          <FormItem label="邮箱" prop="email">
            <Input v-model="addform.email" placeholder="Enter your e-mail"></Input>
          </FormItem>
          <FormItem label="地址">
            <Input v-model="addform.address" placeholder="Enter your e-mail"></Input>
          </FormItem>
        </Form>
      </div>
    </Modal>

    <Modal v-model="deletemodal" @on-ok="remove()" @on-cancel="cancel" >
      <form ref="updateform" :model="updateform">
        <p style="font-size: 22px;">确定删除用户<span>{{updateform.username}}</span>吗</p>
      </form>
    </Modal>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        username: '',
        addmodal: false,
        updatemodal: false,
        deletemodal:false,
        page: { //分页参数
          pageNo: 1,
          pageSize: 5,
          total: 0
        },
        addform: {
          username: '',
          nickname: '',
          password: '',
          sex: '',
          phone: '',
          email: '',
          address: '',
        },
        updateform: {
          username: '',
          nickname: '',
          password: '',
          sex: '',
          phone: '',
          email: '',
          address: '',
        },
        ruleValidate: {
          username: [{
            required: true,
            message: '用户名不能为空',
            trigger: 'blur'
          }],
          nickname: [{
            required: true,
            message: '昵称不能为空',
            trigger: 'blur'
          }],
          password: [{
              required: true,
              message: '密码不能为空',
              trigger: 'blur'
            },
            {
              type: "string",
              min: 6,
              message: "密码长度不小于6位",
              trigger: "blur"
            },
            {
              type: "string",
              max: 12,
              message: "密码长度不大于12位",
              trigger: "blur"
            }
          ],
          sex: [{
            required: true,
            message: '请选择性别',
            trigger: 'blur'
          }],
          phone: [{
              type: "string",
              min: 11,
              message: "请输入正确手机号",
              trigger: "blur"
            },
            {
              type: "string",
              max: 11,
              message: "请输入正确手机号",
              trigger: "blur"
            }
          ],
          email: [{
            type: 'email',
            message: '请输入正确邮箱',
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
            title: '用户名',
            key: 'username',
            width: 100
          },
          {
            title: '密码',
            key: 'password',
            width: 120
          },
          {
            title: '昵称',
            key: 'nickname',
            width: 150
          },
          {
            title: '性别',
            key: 'sex',
            width: 100
          },
          {
            title: '手机号码',
            key: 'phone',
            width: 150
          },
          {
            title: '邮箱',
            key: 'email',
            width: 150
          },
          {
            title: '地址',
            key: 'address',
            width: 150
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
                    }
                  }
                }, '删除')
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
      // this.getUser();
      this.initTableData();
    },
    methods: {
      getUser() {
        this.axios({
            method: "post",
            url: "/api/user",
          })
          .then(response => {
            this.data = response.data.data;
          })
          .catch(function(error) {
            console.log(error);
          });
      },
      viewAll() {
        this.initTableData();
      },
      addModal() {
        this.addmodal = true;
      },
      add() {
        let postData = this.qs.stringify({
          username: this.addform.username,
          nickname: this.addform.nickname,
          password: this.addform.password,
          sex: this.addform.sex,
          phone: this.addform.phone,
          email: this.addform.email,
          address: this.addform.address
        });
        this.axios({
            method: "post",
            url: "/api/adduser",
            data: postData
          })
          .then(response => {
            console.log(response.data);
            if (response.data.ok == 1) {
              this.$Message.success("添加成功");
              this.initTableData();
            } else {
              this.$Message.warning("用户名已存在");
            }
          })
          .catch(function(error) {
            console.log(error);
          });
      },
      search() {
        let postData = this.qs.stringify({
          username: this.username,
        });
        this.axios({
            method: "post",
            url: "/api/searchUser",
            data: postData
          })
          .then(response => {
            console.log(response.data);
            if (response.data.ok == 1) {
              this.$Message.success("查找成功");
              this.tableData = [];
              this.tableData.push(response.data.data);
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
        let postData = this.qs.stringify({
          username: this.updateform.username,
          nickname: this.updateform.nickname,
          password: this.updateform.password,
          sex: this.updateform.sex,
          phone: this.updateform.phone,
          email: this.updateform.email,
          address: this.updateform.address
        });
        this.axios({
            method: "post",
            url: "/api/updateuser",
            data: postData
          })
          .then(response => {
            console.log(response.data);
            if (response.data.ok == 1) {
              this.$Message.success("更改成功");
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
        });
        this.axios({
            method: "post",
            url: "/api/deleteUser",
            data: postData
          })
          .then(response => {
            console.log(response.data);
            if (response.data.ok == 1) {
              this.$Message.success("删除成功");
            } else {
              this.$Message.warning("删除失败");
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
            method: "post",
            url: "/api/getUserPage",
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

 p span {
   color: orange;
 }
</style>
