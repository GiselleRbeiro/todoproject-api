package com.project.todoproject.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.todoproject.models.User;
import com.project.todoproject.repositories.TaskRepository;
import com.project.todoproject.repositories.UserRepository;

@Service
public class UserService {
    
    private static final int RuntimeException = 0;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;


public User findById(Long id) {
    Optional<User> user = this.userRepository.findById (id);
    return user.orElseThrow( new java.lang.RuntimeException(
     "Usuário não encontrado! Id:" + id + ", Tipo: " + UserRepository.class.getName()));
}    


@Transactional
public User create(User obj){
    obj.setId(null);
    obj = this.userRepository.save(obj);
    this.taskRepository.saveAll(obj.getTasks);
    return obj;

}
@Transactional
public User update(User obj) {
    User newObj = findById(obj.getId);
    newObj.setPassword(obj.getPassword());
    return this.userRepository.save(newObj);
}

public void delete(Long id){
   findById(id);
   try {
    this.taskRepository.deleteById(id);
   } catch (Exception e) {
    throw new RuntimeException("Não é possível excluir pois há entidades relacionadas");
   
   }
}
}