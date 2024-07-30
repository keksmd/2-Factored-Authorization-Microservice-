package podpivasniki.shortify.site.axontest.axon.module.commands;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Builder
@Data
public class UserCanceledRegistrationCommand {
    @TargetAggregateIdentifier

    String login;
}
