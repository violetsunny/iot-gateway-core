package top.iot.gateway.core.metadata.types;

import top.iot.gateway.core.metadata.DataType;
import top.iot.gateway.core.metadata.ValidateResult;

/**
 * @author zhouhao
 * @since 1.0.0
 */
public class UnknownType implements DataType {

    @Override
    public ValidateResult validate(Object value) {
        return ValidateResult.success();
    }

    @Override
    public String getId() {
        return "unknown";
    }

    @Override
    public String getName() {
        return "未知类型";
    }

    @Override
    public String getDescription() {
        return "未知类型";
    }

    @Override
    public String format(Object value) {
        return String.valueOf(value);
    }
}
