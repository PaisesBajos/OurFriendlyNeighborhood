package de.awacademy.ourblog.task;


import de.awacademy.ourblog.user.User;
import de.awacademy.ourblog.user.UserRepository;
import de.awacademy.ourblog.utils.Adress;
import de.awacademy.ourblog.utils.AdressRepository;
import de.awacademy.ourblog.utils.CustomQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;


@Controller
public class TaskController {

    private TaskRepository taskRepository;
    private UserRepository userRepository;
    private AdressRepository adressRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository, UserRepository userRepository,
                          AdressRepository adressRepository) {
        this.taskRepository = taskRepository;

        this.userRepository = userRepository;
        this.adressRepository = adressRepository;
        //todo: delete before final release
        generateDummyData();
    }

    /**
     * This method is used for displaying the post page (list of tasks, and a form for
     * creating a new post)
     *
     * @param model contains the attributes used for communication between the Java code and the
     *              HTML template
     * @return the return value is the post.html
     */
    @GetMapping("/task")
    public String task(Model model, @ModelAttribute("sessionUser") User sessionUser, @RequestParam(required = false) Integer sortingOption) {



        List<Task> tasks = taskRepository.findAllByOrderByCreatedAtDesc();

        if(sortingOption!=null){
            if(sortingOption==1 && sessionUser!=null){
                tasks = CustomQueries.sortByDistance(tasks,sessionUser);
            }

            if(sortingOption==2){
                tasks = taskRepository.findAllByOrderByDueDateAsc();
            }

            if(sortingOption==3){
                tasks = taskRepository.findAllByOrderByCreatedAtDesc();
            }

            if(sortingOption==4){
                tasks = taskRepository.findAllByOrderByRequestUser_AdressUser_Plz();

            }
        }

        model.addAttribute("task", new TaskDTO("", ""));
        model.addAttribute("tasks", tasks);
        return "layoutTasks";
    }



    @PostMapping("/task")
    public String task(@ModelAttribute("task") TaskDTO taskDTO, @ModelAttribute("sessionUser") User sessionUser,
                       Model model) {

        Task task = taskRepository.findById(taskDTO.getTaskId()).get();
        task.setHelpUser(sessionUser);
        taskRepository.save(task);

        return "redirect:/task";
    }


    /**
     * This method handels the helper user accepting a selected task
     *
     * @param taskDTO            is a task DTO object, that collects the input from the task form
     * @param sessionUser        is the logged-in user
     * @return the return value is a redirect to the task page
     */
    @PostMapping("/acceptTask")
    public String acceptTask(@ModelAttribute("taskDTO") TaskDTO taskDTO, @ModelAttribute("sessionUser") User sessionUser) {
        if (sessionUser!= null && sessionUser.getHelper()) {
            Optional<Task> optionalTask = taskRepository.findById(taskDTO.getTaskId());
            if (optionalTask.isPresent()) {
                Task task = optionalTask.get();
                task.setHelpUser(sessionUser);
                task.setAccepted(true);
                taskRepository.save(task);
            }
        }
        return "redirect:/task";
    }

    @PostMapping("/cancelTask")
    public String cancelTask(@ModelAttribute("taskDTO") TaskDTO taskDTO, @ModelAttribute("sessionUser") User sessionUser) {
        if (sessionUser!= null && sessionUser.getHelper()) {
            Optional<Task> optionalTask = taskRepository.findById(taskDTO.getTaskId());
            if (optionalTask.isPresent()) {
                Task task = optionalTask.get();
                task.setHelpUser(null);
                task.setAccepted(false);
                taskRepository.save(task);
                return "redirect:/profile";
            }
        }
        return "redirect:/task";
    }

    private void generateDummyData() {
        User userMaja = new User();
        userMaja.setUsername("Maja");
        userMaja.setPassword("Dummy");
        userMaja.setFirstName("Maja");
        userMaja.setLastName("Francetic");
        userMaja.setHelper(true);

        Adress adress = new Adress("Germany", "München", "85748", "Walther-von-Dyck-Str", "6", "");
        adressRepository.save(adress);
        userMaja.setAdressUser(adress);

        User userMichael = new User();
        userMichael.setUsername("Michael");
        userMichael.setPassword("Dummy");
        userMichael.setFirstName("Michael");
        userMichael.setLastName("Holland");
        userMichael.setHelper(true);

        Adress adress1 = new Adress("Germany", "München", "80335", "Seidlstraße", "8", "");
        adressRepository.save(adress1);
        userMichael.setAdressUser(adress1);

        User userMatze = new User();
        userMatze.setUsername("Matthias");
        userMatze.setPassword("Dummy");
        userMatze.setFirstName("Matthias");
        userMatze.setLastName("Hetz");
        Adress adress2 = new Adress("Germany", "Karlsruhe", "76124", "Karl-Friedrich-Str", "10", "");
        adressRepository.save(adress2);
        userMatze.setAdressUser(adress2);
        userMatze.setHelper(true);

        User userGerline = new User();
        userGerline.setUsername("Gerline");
        userGerline.setPassword("Dummy");
        userGerline.setFirstName("Gerlinde");
        userGerline.setLastName("Musterfrau");
        userGerline.setHelper(false);
        Adress adress3 = new Adress("Germany", "München", "80331", "Viktualienmarkt", "3", "");
        adressRepository.save(adress3);
        userGerline.setAdressUser(adress3);

        userRepository.save(userMaja);
        userRepository.save(userMichael);
        userRepository.save(userMatze);
        userRepository.save(userGerline);

        Task taskBlumenGiessen = new Task();
        taskBlumenGiessen.setTitle("Blumen Gießen");
        taskBlumenGiessen.setText("Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.");
        taskBlumenGiessen.setRequestUser(userMaja);
        taskBlumenGiessen.setCreatedAt(Instant.now().minusSeconds(500));
        taskBlumenGiessen.setDueDate(Instant.now().plusSeconds(90000));
        taskBlumenGiessen.setAccepted(false);

        Task taskblumengiessenZwei = new Task();
        taskblumengiessenZwei.setTitle("Blumen Gießen");
        taskblumengiessenZwei.setText("Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.");
        taskblumengiessenZwei.setRequestUser(userMichael);
        taskblumengiessenZwei.setCreatedAt(Instant.now().minusSeconds(5000));
        taskblumengiessenZwei.setDueDate(Instant.now().plusSeconds(600000));
        taskblumengiessenZwei.setAccepted(false);

        Task taskEinkaufen = new Task();
        taskEinkaufen.setTitle("Kleiner Einkauf");
        taskEinkaufen.setText("Liebe Nachbarn, kann mir jemand einen kleinen Einkauf erledigen? 1 Stück Butter, 1 kg Mehl und 6 Eier.Liebe Nachbarn, kann mir jemand einen kleinen Einkauf erledigen? 1 Stück Butter, 1 kg Mehl und 6 Eier.Liebe Nachbarn, kann mir jemand einen kleinen Einkauf erledigen? 1 Stück Butter, 1 kg Mehl und 6 Eier.Liebe Nachbarn, kann mir jemand einen kleinen Einkauf erledigen? 1 Stück Butter, 1 kg Mehl und 6 Eier.Liebe Nachbarn, kann mir jemand einen kleinen Einkauf erledigen? 1 Stück Butter, 1 kg Mehl und 6 Eier.Liebe Nachbarn, kann mir jemand einen kleinen Einkauf erledigen? 1 Stück Butter, 1 kg Mehl und 6 Eier.Liebe Nachbarn, kann mir jemand einen kleinen Einkauf erledigen? 1 Stück Butter, 1 kg Mehl und 6 Eier.Liebe Nachbarn, kann mir jemand einen kleinen Einkauf erledigen? 1 Stück Butter, 1 kg Mehl und 6 Eier.");
        taskEinkaufen.setRequestUser(userMatze);
        taskEinkaufen.setCreatedAt(Instant.now().minusSeconds(1000));
        taskEinkaufen.setDueDate(Instant.now().plusSeconds(400000));
        taskEinkaufen.setAccepted(false);

        taskRepository.save(taskEinkaufen);
        taskRepository.save(taskblumengiessenZwei);
        taskRepository.save(taskBlumenGiessen);

    }
}
