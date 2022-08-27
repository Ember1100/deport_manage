package cn.lanqiao.deport_manage.web;

import cn.lanqiao.deport_manage.entity.Record;
import cn.lanqiao.deport_manage.service.RecordService;
import cn.lanqiao.deport_manage.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class RecordController {
    @Autowired
    private RecordService recordService;
    //获取所有记录接口
    @RequestMapping("record")
    @ResponseBody
    public Result getAllRecord(){
       try{
           List<Record> list =recordService.getAllRecord();
           return Result.success(list);
       }catch (Exception e){
           e.printStackTrace();
       }
       return Result.fail("服务器出错");
    }

}