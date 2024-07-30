package podpivasniki.shortify.site.axontest.command.api.controller;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import podpivasniki.shortify.site.axontest.axon.module.commands.UserCreateCommad;
import podpivasniki.shortify.site.axontest.axon.module.dto.UserDTO;

@RestController
@RequestMapping("api/v1")
public class UserCommandController {
    private final CommandGateway commandGateway;

    public UserCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/user")
    public Object createUser(@AuthenticationPrincipal Jwt jwt, UserDTO userDto) {
        UserCreateCommad userCreateCommad = UserCreateCommad.builder()
                .login(userDto.getLogin())
                .password(userDto.getPassword())
                .build();
       return commandGateway.send(userCreateCommad).join();
    }


}
