package podpivasniki.shortify.site.axontest.command.api.agregate;

import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class UserAgregate {
    @AggregateIdentifier
    String login;
    String password;
}
