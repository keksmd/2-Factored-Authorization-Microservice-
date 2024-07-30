package podpivasniki.shortify.site.axontest.query.api.event;


import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import podpivasniki.shortify.site.axontest.axon.module.events.UserCreatedEvent;
import podpivasniki.shortify.site.axontest.axon.module.events.UserPasswordUpdatedEvent;
import podpivasniki.shortify.site.axontest.axon.module.events.UserRegistartionCancelEvent;
import podpivasniki.shortify.site.axontest.query.api.service.UserQueryDataBaseService;

@Component
public class UserEventHandler {
    final UserQueryDataBaseService userQueryDataBaseService;

    public UserEventHandler(UserQueryDataBaseService userQueryDataBaseService) {
        this.userQueryDataBaseService = userQueryDataBaseService;
    }

    @EventHandler
    public void handle(UserCreatedEvent event) {
        userQueryDataBaseService.saveUser(event);
    }
    @EventHandler
    public void handle(UserPasswordUpdatedEvent event) {
        userQueryDataBaseService.updateUser(event);
    }
    @EventHandler
    public void handle(UserRegistartionCancelEvent event){
        userQueryDataBaseService.deleteUser(event.getLogin());
    }

}
