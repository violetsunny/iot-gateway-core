package top.iot.gateway.core.message.interceptor;

import top.iot.gateway.core.message.Message;
import top.iot.gateway.core.message.codec.MessageDecodeContext;
import reactor.core.publisher.Mono;

/**
 * 设备消息解码拦截器
 *
 * @see MessageDecodeContext
 */
public interface DeviceMessageDecodeInterceptor extends DeviceMessageCodecInterceptor {

    /**
     * 解码前执行
     *
     * @param context 上下文
     */
   default Mono<Void> preDecode(MessageDecodeContext context){
       return Mono.empty();
   }

    /**
     * 解码后执行
     *
     * @param context       消息上下文
     * @param deviceMessage 解码后的设备消息
     * @return 新的设备消息
     */
    default <T extends Message,R extends T> Mono<T> postDecode(MessageDecodeContext context, R deviceMessage){
        return Mono.empty();
    }

}
