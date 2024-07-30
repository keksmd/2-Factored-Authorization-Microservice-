package podpivasniki.shortify.site.axontest.query.api;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class UserQueryEntity {
    @Id
    String login;
    String password;



}
