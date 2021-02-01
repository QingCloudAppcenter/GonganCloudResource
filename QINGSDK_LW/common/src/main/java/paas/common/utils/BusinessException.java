package paas.common.utils;
 /**自定义异常类*/
public class BusinessException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public BusinessException(Object Obj) {
        super(Obj.toString());
    }
}
