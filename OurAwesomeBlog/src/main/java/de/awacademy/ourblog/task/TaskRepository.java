package de.awacademy.ourblog.task;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {

    List<Task> findAllByOrderByCreatedAtDesc();

    Optional<Task> findById(Long id);
}
