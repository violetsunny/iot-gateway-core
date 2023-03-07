package top.iot.gateway.core.message.property;

import com.alibaba.fastjson.JSONObject;
import top.iot.gateway.core.message.CommonThingMessage;
import top.iot.gateway.core.message.MessageType;
import top.iot.gateway.core.things.ThingProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 上报物属性
 *
 * @author zhouhao
 * @since 1.1.9
 */
@Getter
@Setter
public class DefaultReportPropertyMessage extends CommonThingMessage<DefaultReportPropertyMessage> implements ThingReportPropertyMessage {

    /**
     * 属性值信息,key为物模型中的属性ID,value为物模型对应的类型值.
     * <p>
     * 注意: value如果是结构体(对象类型),请勿传入在协议包中自定义的对象,应该转为{@link Map}传入.
     */
    private Map<String, Object> properties;

    /**
     * 属性源的时间戳,表示不同属性值产生的时间戳,单位毫秒
     */
    private Map<String, Long> propertySourceTimes;

    /**
     * 属性状态信息
     */
    private Map<String, String> propertyStates;

    public static DefaultReportPropertyMessage create() {
        return new DefaultReportPropertyMessage();
    }

    public DefaultReportPropertyMessage success(Map<String, Object> properties) {
        this.properties = properties;
        return this;
    }

    @Override
    public DefaultReportPropertyMessage success(List<ThingProperty> properties) {
        this.properties = new LinkedHashMap<>();
        this.propertySourceTimes = new LinkedHashMap<>();
        this.propertyStates = new LinkedHashMap<>();
        for (ThingProperty property : properties) {
            this.properties.put(property.getProperty(), property.getValue());
            this.propertySourceTimes.put(property.getProperty(), property.getTimestamp());
            this.propertyStates.put(property.getProperty(), property.getState());
        }
        return this;
    }

    @Override
    public DefaultReportPropertyMessage propertySourceTimes(Map<String, Long> times) {
        this.propertySourceTimes = times;
        return this;
    }

    @Override
    public DefaultReportPropertyMessage propertyStates(Map<String, String> states) {
        this.propertyStates = states;
        return this;
    }

    @Override
    public DefaultReportPropertyMessage properties(Map<String, Object> properties) {
        return success(properties);
    }

    @Override
    @SuppressWarnings("all")
    public void fromJson(JSONObject jsonObject) {
        super.fromJson(jsonObject);
        this.properties = jsonObject.getJSONObject("properties");
        this.propertySourceTimes = (Map) jsonObject.getJSONObject("propertySourceTimes");
        this.propertyStates = (Map) jsonObject.getJSONObject("propertyStates");
    }

    public MessageType getMessageType() {
        return MessageType.REPORT_PROPERTY;
    }

}
