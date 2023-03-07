package top.iot.gateway.core.exception;

import top.iot.gateway.core.enums.ErrorCode;
import lombok.Getter;
import org.hswebframework.web.i18n.LocaleUtils;

public class DeviceOperationException extends RuntimeException {

    @Getter
    private final ErrorCode code;

    private final String message;

    public DeviceOperationException(ErrorCode errorCode) {
        this(errorCode, errorCode.getText());
    }

    public DeviceOperationException(ErrorCode errorCode, Throwable cause) {
        super(cause);
        this.code = errorCode;
        this.message = cause.getMessage();
    }

    public DeviceOperationException(ErrorCode code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message == null ? code.getText() : message;
    }

    @Override
    public String getLocalizedMessage() {
        return LocaleUtils.resolveMessage(getMessage());
    }
}
