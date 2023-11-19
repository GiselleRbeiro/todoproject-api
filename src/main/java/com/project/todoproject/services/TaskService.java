package com.project.todoproject.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.transaction.annotation.Transactional;

import com.project.todoproject.models.Task;
import com.project.todoproject.repositories.TaskRepository;
import com.project.todoproject.repositories.UserRepository;

public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    @Autowired
    public Task findById(Long id) { 
        Optional<Task> task = this.taskRepository.findById(id);
        return task.orElseThrow (  new RuntimeException(
     "Tarefa não encontrada! Id:" + id + ", Tipo: " + UserRepository.class.getName()));
    }
    
    public List<Task> findByUserId(Long userId){
        List<Task> tasks = this.taskRepository.findByUser_Id(userId);
        return tasks;

    }
    @Transactional
    public Task create(Task obj) {
        final User user = this.userService.findById(obj.getUser.getId);
        obj.setId(null);
        obj.setUser(user);
        obj = this.taskRepository.save(obj);
        return obj;
    }
    @Transactional
    public Task update(Task obj){
        Task newObj = findById(obj.getId());
        newObj.setDescription(obj.getDescription());
        return this.taskRepository.save(newObj);

    }
    

    public void delete(Long id) {
        findById(id);
        try {
            this.taskRepository.deleteById(id);

        } catch (Exception e) {
            throw new RuntimeException( 
     "Não é possível excluir pois há entidades relacionadas! Id:"
      + id + ", Tipo: " + UserRepository.class.getName());
       
    }
    }

    public List<Task> findAllByUserId(Long userId) {
        return null;
    }
}
