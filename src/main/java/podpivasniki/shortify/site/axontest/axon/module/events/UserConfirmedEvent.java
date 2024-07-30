package podpivasniki.shortify.site.axontest.axon.module.events;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserConfirmedEvent {

    String login;

}
