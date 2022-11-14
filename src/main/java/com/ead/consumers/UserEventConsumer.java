package com.ead.consumers;

import com.ead.assembler.users.UserEventResponseAssembler;
import com.ead.enums.ActionTypeE;
import com.ead.model.UserModel;
import com.ead.model.response.users.UserEventResponse;
import com.ead.services.users.DeleteUserByIdService;
import com.ead.services.users.SaveUserPaymentNotStartedService;
import com.ead.services.users.SaveUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventConsumer {

    private final UserEventResponseAssembler assembler;

    private final SaveUserService saveUserService;

    private final DeleteUserByIdService deleteUserByIdService;

    private final SaveUserPaymentNotStartedService saveUserPaymentNotStartedService;

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "${ead.broker.queue.userEventQueue.name}", durable = "true"),
                    exchange = @Exchange(
                            value = "${ead.broker.exchange.userEvent}",
                            type = ExchangeTypes.FANOUT,
                            ignoreDeclarationExceptions = "true"
                    )
            )
    )
    public void listenerUserEvent(@Payload UserEventResponse response) {
        final UserModel userModel = this.assembler.toModel(response);

        final var actionTypeE = ActionTypeE.valueOf(response.getActionTypeE());

        switch (actionTypeE) {
            case CREATE -> this.saveUserPaymentNotStartedService.call(userModel);
            case UPDATE -> this.saveUserService.call(userModel);
            case DELETE -> this.deleteUserByIdService.call(userModel.getId());
            default -> throw new UnsupportedOperationException();
        }
    }
}
