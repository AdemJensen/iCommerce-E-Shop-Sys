package top.chorg.icommerce.bean.dto;

public class ApiResponse<T> {

    private int res;
    private String msg;
    private T obj;

    public ApiResponse(int res, String msg, T obj) {
        this.res = res;
        this.msg = msg;
        this.obj = obj;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}
