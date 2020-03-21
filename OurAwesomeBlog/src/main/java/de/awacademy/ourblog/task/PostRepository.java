package de.awacademy.ourblog.task;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

    List<Post> findAllByOrderByPostedAtDesc();

    Optional<Post> findById(Long id);
}
