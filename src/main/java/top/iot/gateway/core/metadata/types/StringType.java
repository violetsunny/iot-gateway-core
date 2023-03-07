package top.iot.gateway.core.metadata.types;

import top.iot.gateway.core.metadata.DataType;
import top.iot.gateway.core.metadata.ValidateResult;
import top.iot.gateway.core.metadata.Converter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StringType extends AbstractType<StringType> implements DataType, Converter<String> {
    public static final String ID = "string";
    public static final StringType GLOBAL = new StringType();

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getName() {
        return "字符串";
    }

    @Override
    public ValidateResult validate(Object value) {
        return ValidateResult.success(String.valueOf(value));
    }

    @Override
    public String format(Object value) {
        return String.valueOf(value);
    }

    @Override
    public String convert(Object value) {
        return value == null ? null : String.valueOf(value);
    }
}
