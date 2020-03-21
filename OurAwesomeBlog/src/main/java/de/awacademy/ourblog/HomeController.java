package de.awacademy.ourblog;

import de.awacademy.ourblog.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HomeController {

    /**
     * This method displays the home page
     *
     * @param sessionUser is the logged-in user
     * @return the return value is the home.html
     */
    @GetMapping("/")
    public String home(@ModelAttribute("sessionUser") User sessionUser) {
        return "redirect:/post";
    }
}