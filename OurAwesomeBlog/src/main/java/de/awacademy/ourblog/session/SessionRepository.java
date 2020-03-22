package de.awacademy.ourblog.session;

import de.awacademy.ourblog.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface SessionRepository extends CrudRepository<Session, String> {

    Optional<Session> findByIdAndExpiresAtAfter(String id, Instant expiresAt);

}
