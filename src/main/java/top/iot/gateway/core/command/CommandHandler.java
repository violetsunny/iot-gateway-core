package top.iot.gateway.core.command;

public interface CommandHandler<C extends Command<Response>,Response> {

    Response handle(C command, CommandSupport support);

}
