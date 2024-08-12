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
import podpivasniki.shortify.site.axontest.axon.module.MyPayLoad;
import podpivasniki.shortify.site.axontest.axon.module.commands.UserCanceledRegistrationCommand;
import podpivasniki.shortify.site.axontest.axon.module.commands.UserNeedsConfirmCommad;
import podpivasniki.shortify.site.axontest.axon.module.events.UserConfirmedEvent;
import podpivasniki.shortify.site.axontest.axon.module.events.UserCreatedEvent;
import podpivasniki.shortify.site.axontest.axon.module.events.UserNeedsConfirmEvent;
import podpivasniki.shortify.site.axontest.axon.module.events.UserRegistartionCancelEvent;

import java.time.Duration;

@Saga
@Slf4j

public class ConfirmingSage {
    final static String CONFIRM_DEADLINE = "NOT_CONFIRMED";

    transient  DeadlineManager deadlineManager;

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
            log.info("SAGA - UserCreatedEvent -  user needs confirm");
            deadlineManager.schedule(Duration.ofMinutes(1), CONFIRM_DEADLINE,new MyPayLoad(event.getLogin()));
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
        log.info("SAGA - UserConfirmedEvent -  user confirmed");
        deadlineManager.cancelSchedule(CONFIRM_DEADLINE,event.getLogin());

    }
    @EndSaga
    @SagaEventHandler(associationProperty = "login")
    public void handle(UserRegistartionCancelEvent event) {
        log.info("SAGA - UserRegistartionCancelEvent -  user canceled");
        deadlineManager.cancelSchedule(CONFIRM_DEADLINE,event.getLogin());
    }

    @DeadlineHandler(deadlineName = CONFIRM_DEADLINE)
    public void deadlinePassed(MyPayLoad loginPayload) {
        cancelRegistration(loginPayload.getValue());

    }


}
