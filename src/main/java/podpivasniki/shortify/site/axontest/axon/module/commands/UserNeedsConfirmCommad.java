package podpivasniki.shortify.site.axontest.axon.module.commands;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder

public class UserNeedsConfirmCommad {
    @TargetAggregateIdentifier
    String login;

}
