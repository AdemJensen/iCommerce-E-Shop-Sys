package top.chorg.icommerce.bean.dto;

public class ApiResponse<T> {

    private final int res;
    private final String msg;
    private final T obj;

    public ApiResponse(int res, String msg, T obj) {
        this.res = res;
        this.msg = msg;
        this.obj = obj;
    }

    public int getRes() {
        return res;
    }

    public String getMsg() {
        return msg;
    }

    public T getObj() {
        return obj;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "res=" + res +
                ", msg='" + msg + '\'' +
                ", obj=" + (obj == null ? "NULL" : obj.toString()) +
                '}';
    }
}
