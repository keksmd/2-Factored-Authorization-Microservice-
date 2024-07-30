package podpivasniki.shortify.site.axontest.query.api.controller;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import podpivasniki.shortify.site.axontest.axon.module.dto.UserDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import podpivasniki.shortify.site.axontest.axon.module.queries.UserGetByNameQuery;

@RestController
@RequestMapping("api/v1")
public class UserQueryController {
    private  final QueryGateway commandGateway;

    public UserQueryController(QueryGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @GetMapping("/user/name")
    public UserDTO getUserByName(@AuthenticationPrincipal Jwt jwt, String name) {
        UserGetByNameQuery userGetByNameQuery = UserGetByNameQuery.builder().login(name).build();
        return commandGateway.query(userGetByNameQuery, UserDTO.class).join();
    }
}
