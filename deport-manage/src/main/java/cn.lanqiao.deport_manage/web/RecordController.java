package cn.lanqiao.deport_manage.web;

import cn.lanqiao.deport_manage.entity.Record;
import cn.lanqiao.deport_manage.service.RecordService;
import cn.lanqiao.deport_manage.utils.Result;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin

public class RecordController {
    @Autowired
    private RecordService recordService;

    //获取所有记录接口
    @RequestMapping("/record")
    @ResponseBody
    public Result getAllRecord() {
        try {
            List<Record> list = recordService.getAllRecord();
            return Result.success(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("服务器出错");
    }

    //分页接口
    @PostMapping("/getRecordPage")
    public Result getRecordPage(@RequestParam(defaultValue = "1") int pageNo,
                                @RequestParam(defaultValue = "5") int pageSize) {
        try {
            PageInfo<Record> page = recordService.getRecordPage(pageNo, pageSize);
            return Result.success(page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("服务器内部错误");
    }

    @RequestMapping("/addRecord")
    public Result addRecord(Record record) {
        try {
            int i = recordService.addRecord(record);
            if (i==1) {
                return Result.success(1,"记录添加成功");
            } else {
                return Result.fail("记录添加失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("服务器内部错误");
    }
    @PostMapping("/updaterecord")
    public Result updaterecord(Integer id,String state){
        try {
            int r = recordService.updateState(id,state);
            System.out.println(id);
            System.out.println(state);
            return Result.success(1,"记录添加成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return  Result.fail("系统异常") ;
    }
    @PostMapping("/searchgoodsbyusergoods")
    public Result searchgoodsbyusergoods(Integer id){
        try {
            Record re= recordService.select(id);
            if(re != null){
                return Result.success(re);
            }else{
                return Result.fail(0,"失败") ;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  Result.fail("系统异常") ;
    }


}
