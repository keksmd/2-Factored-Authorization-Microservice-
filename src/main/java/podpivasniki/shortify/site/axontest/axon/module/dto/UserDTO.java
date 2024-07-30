package podpivasniki.shortify.site.axontest.axon.module.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    String login;
    String password;
}
