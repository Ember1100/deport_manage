<template>
 <div id="myChart" class="container">
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
 mounted(){
this.drawLine();
},
 methods: {
drawLine(){
        // 基于准备好的dom，初始化echarts实例
        let myChart = this.echarts.init(document.getElementById('myChart'))
        // 绘制图表
        myChart.setOption({
          color: ['#3398DB'],
            title: { text: '物品详情表' },
            tooltip: {},
            xAxis: {
                data: ["笔记本","手机","单车","火车","牙膏","茶杯","椅子","钱包"]
            },
            yAxis: {},
            series: [{
                name: '销量',
                type: 'bar',
                data: [3276, 1200, 500,1836,900,1500,400,2500]
            }]
        });
    }
},
}
</script>

<style scoped>
.container{
width: 1200px;
height: 750px;
}
</style>
