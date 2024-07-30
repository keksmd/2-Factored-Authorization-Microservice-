package podpivasniki.shortify.site.axontest.axon.module.events;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
@Builder
@Data
public class UserRegistartionCancelEvent {

    String login;
}
