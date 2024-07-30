package podpivasniki.shortify.site.axontest.command.api.controller;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import podpivasniki.shortify.site.axontest.axon.module.commands.UserConfirmedCommand;
import podpivasniki.shortify.site.axontest.axon.module.commands.UserCreateCommad;
import podpivasniki.shortify.site.axontest.axon.module.commands.UserPasswordUpdateCommad;
import podpivasniki.shortify.site.axontest.axon.module.dto.UserDTO;
import podpivasniki.shortify.site.axontest.axon.module.events.UserConfirmedEvent;

@RestController
@RequestMapping("/api/v1/users")
public class UserCommandController {
    private final CommandGateway commandGateway;

    public UserCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public Object createUser(@AuthenticationPrincipal Jwt jwt, @RequestBody UserDTO userDto) {
        UserCreateCommad userCreateCommad = UserCreateCommad.builder()
                .login(userDto.getLogin())
                .password(userDto.getPassword())
                .build();
       return commandGateway.send(userCreateCommad).join();
    }
    @PostMapping("/update")
    public void updateUser(@AuthenticationPrincipal Jwt jwt, @RequestBody UserDTO userDto) {
        UserPasswordUpdateCommad userUpdatePasswordCommad = UserPasswordUpdateCommad.builder()
                .login(userDto.getLogin())
                .password(userDto.getPassword())
                .build();
        commandGateway.send(userUpdatePasswordCommad).join();
    }
    @PostMapping("/confirm")
    public void confirmUser(@AuthenticationPrincipal Jwt jwt, @PathVariable String login) {
        commandGateway.send(UserConfirmedCommand.builder().login(login).build()).join();
    }



}
