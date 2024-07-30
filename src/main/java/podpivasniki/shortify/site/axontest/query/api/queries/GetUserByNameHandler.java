package podpivasniki.shortify.site.axontest.query.api.queries;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import podpivasniki.shortify.site.axontest.axon.module.dto.UserDTO;
import podpivasniki.shortify.site.axontest.axon.module.queries.UserGetByNameQuery;
import podpivasniki.shortify.site.axontest.query.api.UserQueryEntity;
import podpivasniki.shortify.site.axontest.query.api.service.UserQueryDataBaseService;

@Component
public class GetUserByNameHandler {
    final UserQueryDataBaseService userQueryDataBaseService;

    public GetUserByNameHandler(UserQueryDataBaseService userQueryDataBaseService) {
        this.userQueryDataBaseService = userQueryDataBaseService;
    }

    @QueryHandler
    public UserDTO handle(UserGetByNameQuery query){
        return userQueryDataBaseService.find(query.getLogin());
    }


}
