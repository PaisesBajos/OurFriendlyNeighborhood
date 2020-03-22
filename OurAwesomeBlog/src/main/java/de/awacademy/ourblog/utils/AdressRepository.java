package de.awacademy.ourblog.utils;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdressRepository extends CrudRepository<Adress, Long> {

   List<Adress> findAllByOrderByPlzDesc();

}
