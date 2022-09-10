<template>
  <div class="container">
    <div class="menu-container">
      <Menu theme="dark" active-name="2">
        <MenuGroup title="仓库">
          <MenuItem name="1" to='/admin/goods'>
          <Icon type="md-document" />
          仓库管理
          </MenuItem>
          <MenuItem name="2" to='/admin/echarts'>
          <Icon type="md-document" />
          仓库视图
          </MenuItem>
        </MenuGroup>
        <MenuGroup title="用户">
          <MenuItem name="3" to='/admin/user'>
          <Icon type="md-person" />
          用户管理
          </MenuItem>
        </MenuGroup>
        <MenuGroup title="出入库">
          <MenuItem name="4" to='/admin/record'>
          <Icon type="md-reorder" />
          记录
          </MenuItem>
        </MenuGroup>
        <MenuGroup title="操作">
          <MenuItem name="5" to='/login'>
          <Icon type="ios-power" />
          退出
          </MenuItem>
        </MenuGroup>
      </Menu>
    </div>
    <div class="view-container" >
      <router-view></router-view>

    </div>
  </div>
</template>

<script>
  export default {
    data() {
      return {

      }
    },
    components: {

    },
    mounted() {

    },
    methods: {

    },
  }
</script>

<style scoped>
  .container {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    min-width: 200px;
    z-index: -10;
    zoom: 1;
    background-repeat: no-repeat;
    background-size: cover;
    -webkit-background-size: cover;
    -o-background-size: cover;
    background-position: center 0;
    display: flex;
  }

  .menu-container {
    float: left;
    height: 100%;
    width: 240px;
    position: relative;
    background-color: #515a6e;
  }

  .view_container {
    height: 100%;
    width: 80%;
    float: left;
  }
</style>
