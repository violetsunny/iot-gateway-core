package top.iot.gateway.core.metadata.types;

import top.iot.gateway.core.metadata.Converter;
import top.iot.gateway.core.metadata.ValidateResult;

public class GeoShapeType extends AbstractType<GeoShapeType> implements Converter<GeoShape> {
    public static final String ID = "geoShape";
    public static final GeoShapeType GLOBAL = new GeoShapeType();

    @Override
    public ValidateResult validate(Object value) {
        GeoShape shape;
        if (null == (shape = convert(value))) {
            return ValidateResult.builder()
                    .success(false)
                    .errorMsg("不支持的GepShape格式:" + value)
                    .build();
        }
        return ValidateResult.success(shape);
    }

    @Override
    public GeoShape convert(Object value) {

        return GeoShape.of(value);
    }

    @Override
    public String getId() {
        return "geoShape";
    }

    @Override
    public String getName() {
        return "地理地形";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public Object format(Object value) {
        return value;
    }
}
