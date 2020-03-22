package de.awacademy.ourblog.user;


import de.awacademy.ourblog.session.SessionRepository;
import de.awacademy.ourblog.task.Task;
import de.awacademy.ourblog.task.TaskDTO;
import de.awacademy.ourblog.task.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private UserRepository userRepository;
    private TaskRepository taskRepository;
    private SessionRepository sessionRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository; this.taskRepository = taskRepository; this.sessionRepository = sessionRepository;}

    /**
     * This method displays a register form
     *
     * @param model contains the attributes used for communication between the Java code and the
     *              HTML template
     * @return the return value is the register.html
     */
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("registration", new RegistrationDTO("", "", ""));
        return "layoutRegister";
    }

    /**
     * This method registers a new user
     *
     * @param registration  is a RegisterDTO object,  that collects the input from the registration form
     * @param bindingResult is the result of the form validation
     * @return the return value is a register.html (in case the form contains errors), or a
     * redirect to the login page
     */
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registration") RegistrationDTO registration, BindingResult bindingResult) {

        if (!registration.getPassword1().equals(registration.getPassword2())) {
            bindingResult.addError(new FieldError("registration", "password2", "The passwords do not match"));
        }

        if (userRepository.existsByUsername(registration.getUsername())) {
            bindingResult.addError(new FieldError("registration", "username", "The username is already in use"));
        }

        if (bindingResult.hasErrors()) {
            return "layoutRegister";
        }

        User user = new User(registration.getUsername(), registration.getPassword1());
        userRepository.save(user);

        return "redirect:/login";
    }

    /**
     * This method displays the profile page
     *
     * @param sessionUser is the logged-in user
     * @param model       contains the attributes used for communication between the Java code and the
     *                    HTML template
     * @return the return value is the layoutAdmin.html, or a redirect to the post page, in case
     * the sessionUser is not an admin
     */
    @GetMapping("/profile")
    public String user(@ModelAttribute("sessionUser") User sessionUser, Model model) {
        if (sessionUser != null && sessionUser.getHelper()) {
//            Post post = new Post();
//            if (postImage != null) {
//                post.setUrlToImage(postImage);
//            }
//            model.addAttribute("postImage", postImage);
//            model.addAttribute("post", post);
            User user = userRepository.findById(sessionUser.getId()).get();
            model.addAttribute("user", user);
            model.addAttribute("task", new TaskDTO("", ""));
            return "layoutUser";
        }
        return "redirect:/";
    }

    /**
     * This method defines a user as an admin
     *
     * @param sessionUser is the logged-in user
     * @param userId      is the ID of a user that will be defined as admin
     * @return the return value is a redirect to the admin page, or a redirect to the post page,
     * in case the sessionUser is not an admin
     */
    @PostMapping("/admin")
    public String makeAdmin(@ModelAttribute("sessionUser") User sessionUser, @ModelAttribute("userId") Long userId) {
        if (sessionUser != null && sessionUser.getAdmin()) {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isPresent()) {
                optionalUser.get().setAdmin(true);
                userRepository.save(optionalUser.get());
                return "redirect:/admin";
            }
        }
        return "redirect:/task";
    }
}
