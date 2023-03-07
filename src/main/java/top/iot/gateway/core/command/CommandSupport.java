package top.iot.gateway.core.command;

import top.iot.gateway.core.Wrapper;

import javax.annotation.Nonnull;

public interface CommandSupport extends Wrapper {

    @Nonnull
    <R> R execute(@Nonnull Command<R> command);

}
