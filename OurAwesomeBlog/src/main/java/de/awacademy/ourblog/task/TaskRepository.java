package de.awacademy.ourblog.task;

import de.awacademy.ourblog.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {

    // find a task by ID
    List<Task> findAllByHelpUserIsNull();

    Optional<Task> findById(Long id);

    // Find all tasks


    List<Task> findAllByOrderByCreatedAtDesc();

    List<Task> findAllByOrderByCreatedAtAsc();

    List<Task> findAllByOrderByDueDateAsc();

    List<Task> findAllByOrderByDueDateDesc();

    List<Task> findAllByOrderByRequestUser_AdressUser_Plz();

//    List<Task> findAllByOrderByPlzAsc();
//
//    List<Task> findAllByOrderByPlzDesc();


    // find all tasks from a specific request user

    List<Task> findAllByRequestUser(User requestUser);

    List<Task> findAllByRequestUserOrderByCreatedAtDesc(User requestUser);

    List<Task> findAllByRequestUserOrderByCreatedAtAsc(User requestUser);

    List<Task> findAllByRequestUserOrderByDueDateAsc(User requestUser);

    List<Task> findAllByRequestUserOrderByDueDateDesc(User requestUser);

//    List<Task> findAllByRequestUserOrderByPlzAsc(User requestUser);
//
//    List<Task> findAllByRequestUserOrderByPlzDesc(User requestUser);


    // find all tasks from a specific help user

    List<Task> findAllByHelpUser(User helpUser);

    List<Task> findAllByHelpUserOrderByCreatedAtDesc(User helpUser);

    List<Task> findAllByHelpUserOrderByCreatedAtAsc(User helpUser);

    List<Task> findAllByHelpUserOrderByDueDateAsc(User helpUser);

    List<Task> findAllByHelpUserOrderByDueDateDesc(User helpUser);

//    List<Task> findAllByHelpUserOrderByPlzAsc(User helpUser);
//
//    List<Task> findAllByHelpUserOrderByPlzDesc(User helpUser);

}
