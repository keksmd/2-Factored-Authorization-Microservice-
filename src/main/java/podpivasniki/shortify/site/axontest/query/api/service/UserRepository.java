package podpivasniki.shortify.site.axontest.query.api.service;

import org.springframework.data.repository.CrudRepository;
import podpivasniki.shortify.site.axontest.query.api.UserQueryEntity;

public interface UserRepository extends CrudRepository<UserQueryEntity,String> {
}
