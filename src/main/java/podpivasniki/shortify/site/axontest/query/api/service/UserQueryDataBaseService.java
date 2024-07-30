package podpivasniki.shortify.site.axontest.query.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import podpivasniki.shortify.site.axontest.axon.module.commands.UserPasswordUpdateCommad;
import podpivasniki.shortify.site.axontest.axon.module.dto.UserDTO;
import podpivasniki.shortify.site.axontest.axon.module.events.UserCreatedEvent;
import podpivasniki.shortify.site.axontest.axon.module.events.UserPasswordUpdatedEvent;
import podpivasniki.shortify.site.axontest.query.api.UserQueryEntity;

@Service
public class UserQueryDataBaseService {
    UserRepository userRepository;

    public UserQueryDataBaseService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(UserCreatedEvent event){
        UserQueryEntity entity = new UserQueryEntity();
        BeanUtils.copyProperties(event,entity);
        userRepository.save(entity);
    }
    public void updateUser(UserPasswordUpdatedEvent event){
        UserQueryEntity entity = userRepository.findById(event.getLogin()).get();
        entity.setPassword(event.getPassword());
        userRepository.save(entity);
    }
    public UserDTO find(String login){
        UserQueryEntity entity =  userRepository.findById(login).get();
        return new UserDTO(entity.getLogin(),entity.getPassword());
    }
    public void deleteUser(String login){
        userRepository.deleteById(login);
    }


}
