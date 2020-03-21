package de.awacademy.ourblog.session;

import de.awacademy.ourblog.user.User;
import de.awacademy.ourblog.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.Optional;

@Controller
public class SessionController {

    private SessionRepository sessionRepository;
    private UserRepository userRepository;

    @Autowired
    public SessionController(SessionRepository sessionRepository, UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }

    /**
     * This method displays a login form
     *
     * @param model contains the attributes used for communication between the Java code and the
     *              HTML template
     * @return the return value is a login.html
     */
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("login", new LoginDTO("", ""));
        return "layoutLogin";
    }

    /**
     * This method is used for logging
     *
     * @param login         is a LoginDTO object
     * @param bindingResult is the result of the form validation
     * @param response      is a HttpServletResponse, that is used for adding a cookie
     * @return the return value is the redirect to the home page; or a login.html, in case
     * there are errors in the form
     */
    @PostMapping("/login")
    public String login(@ModelAttribute("login") LoginDTO login, BindingResult bindingResult, HttpServletResponse response) {
        Optional<User> optionalUser = userRepository.findByUsernameAndPassword(login.getUsername(), login.getPassword());

        if (optionalUser.isPresent()) {
            Session session = new Session(optionalUser.get(), Instant.now().plusSeconds(7 * 24 * 60 * 60));
            sessionRepository.save(session);

            Cookie cookie = new Cookie("sessionId", session.getId());
            response.addCookie(cookie);

            return "redirect:/";
        }

        bindingResult.addError(new FieldError("login", "password", "Login unsuccessful"));
        return "layoutLogin";
    }

    /**
     * This method is used for logging a user out
     *
     * @param sessionId is the ID of a session of the logged-in user
     * @param response      is a HttpServletResponse, that is used for adding a cookie
     * @return the return value is a redirect to the home page
     */
    @PostMapping("/logout")
    public String logout(@CookieValue(value = "sessionId", defaultValue = "") String sessionId, HttpServletResponse response) {
        Optional<Session> optionalSession = sessionRepository.findByIdAndExpiresAtAfter(sessionId, Instant.now());
        optionalSession.ifPresent(session -> sessionRepository.delete(session));

        Cookie cookie = new Cookie("sessionId", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/";
    }
}