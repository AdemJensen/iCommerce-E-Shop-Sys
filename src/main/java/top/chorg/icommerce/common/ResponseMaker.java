package top.chorg.icommerce.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.chorg.icommerce.bean.dto.ApiResponse;

public class ResponseMaker {

    private static final Logger LOG = LoggerFactory.getLogger(ResponseMaker.class);

    public static <E> ApiResponse<E> success(E obj) {
        ApiResponse<E> result = make(0, "OK", obj);
        LOG.debug("A success response has been made: " + result);
        return result;
    }

    public static <E> ApiResponse<E> error(int res, String msg, E obj) {
        ApiResponse<E> result = make(res, msg, obj);
        LOG.debug("An error response has been made: " + result);
        return result;
    }

    public static <E> ApiResponse<E> make(int res, String msg, E obj) {
        return new ApiResponse<>(res, msg, obj);
    }

}
