package cn.lanqiao.deport_manage.utils;

import lombok.Data;

@Data
public class Result<T> {
    private int ok;
    private String message;
    private T data;


    public Result(){}

    public Result(int ok, String message) {
        this.ok = ok;
        this.message = message;
    }

    public Result(int ok, String message, T data) {
        this.ok = ok;
        this.message = message;
        this.data = data;
    }

    public  static <T> Result<T> success(T data) {
      Result result  = new Result(1,"查询成功",data);
      return result;
   }


  public static Result success(int ok,String message) {
        Result result = new Result(ok,message);
        return result;
  }

    public static Result fail(int ok,String message) {
        Result result = new Result(ok,message);
        return result;
    }

   public static Result fail(String message) {
        Result result = new Result(0,message);
        return result;
   }


}
