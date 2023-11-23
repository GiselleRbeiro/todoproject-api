package com.project.todoproject.services;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.todoproject.models.User;
import com.project.todoproject.repositories.TaskRepository;
import com.project.todoproject.repositories.UserRepository;
import com.project.todoproject.services.exceptions.DataBindingViolationException;
import com.project.todoproject.services.exceptions.ObjectNotFoundException;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    private UserRepository userRepository;
public User findById(Long id) {
        UserSpringSecurity userSpringSecurity = authenticated();
        if (!Objects.nonNull(userSpringSecurity)
                || !userSpringSecurity.hasRole(ProfileEnum.ADMIN) && !id.equals(userSpringSecurity.getId()))
            throw new AuthorizationException("Acesso negado!");
  

   Optional<User> user = this.userRepository.findById(id);
    return user.orElseThrow(() -> new ObjectNotFoundException(
        "Usuário não encontrado! Id: " + id + ", Tipo: " + User.class.getName()));
}


@Transactional
public User create(User obj){
    obj.setId(null);
    obj = this.userRepository.save(obj);
    this.userRepository.saveAll(obj.getTask());
    return obj;

}
@Transactional
public User update(User obj) {
    User newObj = findById(User.getId);
    newObj.setPassword(obj.getPassword());
    return this.userRepository.save(newObj);
}

public void delete(Long id){
   findById(id);
   try {
    this.userRepository.deleteById(id);
   } catch (Exception e) {
    throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas");
   
   }
}
}