package podpivasniki.shortify.site.axontest.axon.module.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserDTO {
    String login;
    String password;
}
