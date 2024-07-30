package podpivasniki.shortify.site.axontest.saga;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.deadline.DeadlineManager;
import org.axonframework.deadline.annotation.DeadlineHandler;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import podpivasniki.shortify.site.axontest.axon.module.commands.UserCanceledRegistrationCommand;
import podpivasniki.shortify.site.axontest.axon.module.commands.UserNeedsConfirmCommad;
import podpivasniki.shortify.site.axontest.axon.module.events.UserConfirmedEvent;
import podpivasniki.shortify.site.axontest.axon.module.events.UserCreatedEvent;
import podpivasniki.shortify.site.axontest.axon.module.events.UserNeedsConfirmEvent;

import java.time.Duration;

@Saga
@Slf4j

public class ConfirmingSage {
    final static String CONFIRM_DEADLINE = "NOT_CONFIRMED";
    @Autowired
    transient  DeadlineManager deadlineManager;
    @Autowired
    transient  CommandGateway commandGateway;
    public ConfirmingSage() {

    }

    public ConfirmingSage(DeadlineManager deadlineManager, CommandGateway commandGateway) {
        this.deadlineManager = deadlineManager;
        this.commandGateway = commandGateway;
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "login")
    public void handle(UserCreatedEvent event) {
        log.info("SAGA - UserCreatedEvent -  started");
        try {
            var confirm = UserNeedsConfirmCommad.builder().login(event.getLogin()).build();
            commandGateway.send(confirm).join();
            log.info("SAGA - UserCreatedEvent -  1st step passed");
            deadlineManager.schedule(Duration.ofMinutes(1), CONFIRM_DEADLINE, event);
        } catch (Exception e) {
            cancelRegistration(event.getLogin());
        }
    }

    private void cancelRegistration(String login) {
        var deleteCommand = UserCanceledRegistrationCommand.builder().login(login).build();
        commandGateway.send(deleteCommand).join();
        log.info("SAGA - UserCreatedEvent -  failed");
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "login")
    public void handle(UserConfirmedEvent event) {
        log.info("SAGA - UserNeedsConfirmEvent -  ended nice");

    }

    @DeadlineHandler(deadlineName = CONFIRM_DEADLINE)
    public void deadlinePassed(UserNeedsConfirmEvent event) {
        cancelRegistration(event.getLoginPlusEmail().replace("email", ""));


    }


}
