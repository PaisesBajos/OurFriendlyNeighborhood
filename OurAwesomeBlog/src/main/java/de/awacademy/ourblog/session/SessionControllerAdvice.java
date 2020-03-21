package de.awacademy.ourblog.session;

import de.awacademy.ourblog.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.Instant;
import java.util.Optional;

@ControllerAdvice
public class SessionControllerAdvice {

    private SessionRepository sessionRepository;

    @Autowired
    public SessionControllerAdvice(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    /**
     * This method returns a sessionUser
     *
     * @param sessionId is the ID of a session of the logged-in user
     * @return the return value is a logged-in user
     */
    @ModelAttribute("sessionUser")
    public User sessionUser(@CookieValue(value = "sessionId", defaultValue = "") String sessionId) {
        if(!sessionId.isEmpty()) {
            Optional<Session> optionalSession = sessionRepository.findByIdAndExpiresAtAfter(
                sessionId, Instant.now());
            if (optionalSession.isPresent()) {
                Session session = optionalSession.get();

                session.setExpiresAt(Instant.now().plusSeconds(7*24*60*60));
                sessionRepository.save(session);

                return session.getUser();
            }
        }
        return null;
    }
}