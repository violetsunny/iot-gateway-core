package top.iot.gateway.core.metadata.unit;

import top.iot.gateway.core.metadata.FormatSupport;
import top.iot.gateway.core.metadata.Metadata;

import java.io.Serializable;
import java.util.Map;

/**
 * 值单位
 *
 * @author bsetfeng
 * @author zhouhao
 * @version 1.0
 **/
public interface ValueUnit extends Metadata, FormatSupport, Serializable {

    /**
     * 单位符号
     * @return 符号
     */
    String getSymbol();

    @Override
    default Map<String, Object> getExpands() {
        return null;
    }
}
