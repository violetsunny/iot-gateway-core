package top.iot.gateway.core.message.exception;

public class FunctionIllegalParameterException extends IllegalParameterException {


    public FunctionIllegalParameterException(String parameter, String message) {

        super(parameter, message);
    }
}
