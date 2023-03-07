package top.iot.gateway.core.metadata;

import top.iot.gateway.core.message.UpdateTagMessage;
import top.iot.gateway.core.message.event.EventMessage;
import top.iot.gateway.core.message.function.FunctionInvokeMessage;
import top.iot.gateway.core.message.function.FunctionInvokeMessageReply;
import top.iot.gateway.core.message.property.*;
import top.iot.gateway.core.things.ThingMetadata;

import java.util.List;

/**
 * 设备物模型定义
 *
 * @author zhouhao
 * @since 1.0.0
 */
public interface DeviceMetadata extends ThingMetadata {

    /**
     * @return 所有属性定义
     * @see ReadPropertyMessage
     * @see WritePropertyMessage
     * @see ReportPropertyMessage
     * @see ReadPropertyMessageReply
     * @see WritePropertyMessageReply
     */
    List<PropertyMetadata> getProperties();

    /**
     * @return 所有功能定义
     * @see FunctionInvokeMessage
     * @see FunctionInvokeMessageReply
     */
    List<FunctionMetadata> getFunctions();

    /**
     * @return 事件定义
     * @see EventMessage
     */
    List<EventMetadata> getEvents();

    /**
     * @return 标签定义
     * @see UpdateTagMessage
     */
    List<PropertyMetadata> getTags();

    /**
     * 合并物模型，合并后返回新的物模型对象
     *
     * @param metadata 要合并的物模型
     * @since 1.1.6
     */
    @Override
    default <T extends ThingMetadata> DeviceMetadata merge(T metadata) {
        return this.merge(metadata,MergeOption.DEFAULT_OPTIONS);
    }

    default  <T extends ThingMetadata> DeviceMetadata merge(T metadata, MergeOption... options) {
        throw new UnsupportedOperationException("unsupported merge metadata");
    }
}
