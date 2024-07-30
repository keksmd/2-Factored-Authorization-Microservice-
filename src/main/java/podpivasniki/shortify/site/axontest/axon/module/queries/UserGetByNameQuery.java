package podpivasniki.shortify.site.axontest.axon.module.queries;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserGetByNameQuery {
    String login;
}
