package podpivasniki.shortify.site.axontest.query.api.controller;

import org.axonframework.queryhandling.QueryGateway;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import podpivasniki.shortify.site.axontest.axon.module.dto.UserDTO;
import podpivasniki.shortify.site.axontest.axon.module.queries.UserGetByNameQuery;

@RestController
@RequestMapping("/api/v1/users/")
public class UserQueryController {
    private  final QueryGateway queryGateway;

    public UserQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/name/{name}")
    public UserDTO getUserByName(@AuthenticationPrincipal Jwt jwt, @PathVariable String name) {
        UserGetByNameQuery userGetByNameQuery = UserGetByNameQuery
                .builder()
                .login(name)
                .build();
        return queryGateway.query(userGetByNameQuery, UserDTO.class).join();
    }
}
