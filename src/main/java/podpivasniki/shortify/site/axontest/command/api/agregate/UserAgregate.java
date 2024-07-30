package podpivasniki.shortify.site.axontest.command.api.agregate;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import podpivasniki.shortify.site.axontest.axon.module.commands.*;
import podpivasniki.shortify.site.axontest.axon.module.events.*;

@Aggregate
@Data
public class UserAgregate {
    @AggregateIdentifier
    String login;
    String password;
    String state;

    public UserAgregate() {
    }



    @CommandHandler
    public UserAgregate(UserCreateCommad commad) {
        var event = UserCreatedEvent.builder().login(commad.getLogin()).password(commad.getPassword()).build();
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(UserCreatedEvent event) {
        this.login = event.getLogin();
        this.password = event.getPassword();
        this.state = "filled";
    }

    @EventSourcingHandler
    public void on(UserPasswordUpdatedEvent event) {
        this.password = event.getPassword();
    }

    @CommandHandler
    public void handle(UserPasswordUpdateCommad commad) {
        var event = UserPasswordUpdatedEvent.builder().password(commad.getPassword()).login(commad.getLogin()).build();
        AggregateLifecycle.apply(event);
    }
    @CommandHandler
    public void handle(UserConfirmedCommand commad) {
        var event = UserConfirmedEvent.builder().login(commad.getLogin().replace("email","")).build();
        AggregateLifecycle.apply(event);
    }
    @EventSourcingHandler
    public void on(UserConfirmedEvent event) {
    }
    @CommandHandler
    public void emailConfirm(UserNeedsConfirmCommad commad) {
        var event = UserNeedsConfirmEvent.builder().loginPlusEmail(commad.getLogin() + "email").build();
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(UserNeedsConfirmEvent event) {
        this.state = "confirmed";
    }
    @CommandHandler
    public void cancelRegistartion (UserCanceledRegistrationCommand commad) {
        var event = UserRegistartionCancelEvent.builder().login(commad.getLogin()).build();
        AggregateLifecycle.apply(event);
    }
    @EventSourcingHandler
    public void on(UserRegistartionCancelEvent event) {
        this.state = "canceled";
    }


}
