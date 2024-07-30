package podpivasniki.shortify.site.axontest.axon.module.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPasswordUpdatedEvent {
    String login;
    String password;
}
