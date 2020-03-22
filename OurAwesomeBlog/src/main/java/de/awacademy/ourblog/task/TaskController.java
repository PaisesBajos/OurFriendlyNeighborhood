package de.awacademy.ourblog.task;


//import de.awacademy.ourblog.comment.CommentRepository;

import de.awacademy.ourblog.user.User;
import de.awacademy.ourblog.user.UserRepository;
import de.awacademy.ourblog.utils.AddressForGPS;
import de.awacademy.ourblog.utils.Adress;
import de.awacademy.ourblog.utils.CustomQueries;
import de.awacademy.ourblog.utils.FileUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Instant;
import java.util.List;
import java.util.Optional;


@Controller
public class TaskController {

    private TaskRepository taskRepository;
    //    private CommentRepository commentRepository;
    private UserRepository userRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;

        this.userRepository = userRepository;
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

        /////For Testing till address is available ///
        if (sessionUser != null) {
            sessionUser.setAddress(new AddressForGPS("München", "Lindwurmstraße", 115));
        }

        List<Task> tasks = taskRepository.findAllByOrderByCreatedAtDesc();

        if(sortingOption!=null){
            if(sortingOption==1 && sessionUser!=null){
                tasks = CustomQueries.sortByDistance(tasks,sessionUser);
            }
            if(sortingOption==2){
                tasks = taskRepository.findAllByHelpUserIsNull();
            }

        }

        //model.addAttribute("comment", new CommentDTO(""));
        //model.addAttribute("commentDTO", new CommentDTO(""));
        model.addAttribute("task", new TaskDTO("", ""));
        model.addAttribute("tasks", tasks);
        return "layoutTasks";
    }

//    /**
//     * This method is used for creating a new post
//     *
//     * @param postDTO       is a post DTO object, that collects the input from the comment form
//     * @param bindingResult is the result of the form validation
//     * @param sessionUser   is the logged-in user
//     * @return the return value is either the layoutAdmin.html (if there are errors in the
//     * form) or the redirect to the post page
//     */
//    @PostMapping("/post")
//    public String post(@Valid @ModelAttribute("post") PostDTO postDTO, BindingResult bindingResult, @ModelAttribute("sessionUser") User sessionUser,
//                       Model model, @RequestParam(required = false) String postImage) {
//        if (sessionUser.getAdmin()) {
//            if (bindingResult.hasErrors()) {
//                List<User> users = userRepository.findAll();
//                model.addAttribute("users", users);
//                return "layoutAdmin";
//            }
//            Post post = new Post(sessionUser, postDTO.getTitle(), postDTO.getText(), Instant.now());
//            post.setId(postDTO.getPostId());
//            if (postImage != null) {
//                post.setUrlToImage(postImage);
//            }
//            postRepository.save(post);
//        }
//        return "redirect:/post";
//    }

    @PostMapping("/task")
    public String task(@ModelAttribute("task") TaskDTO taskDTO, @ModelAttribute("sessionUser") User sessionUser,
                       Model model) {

        Task task = taskRepository.findById(taskDTO.getTaskId()).get();
        task.setHelpUser(sessionUser);
        taskRepository.save(task);

        return "redirect:/task";
    }
//
//    /**
//     * This method deletes a post
//     *
//     * @param postId      is the ID of a post that will be deleted
//     * @param sessionUser is the logged-in user
//     * @return the return value is the redirect to the post page
//     */
//    @PostMapping("/postDelete")
//    public String delete(@RequestParam long postId, @ModelAttribute("sessionUser") User sessionUser) {
//        Post post = taskRepository.findById(postId).orElseThrow();
//        if (sessionUser == null || !sessionUser.getAdmin()) {
//            return "redirect:/post";
//        }
//        taskRepository.delete(post);
//        return "redirect:/post";
//    }

//    /**
//     * This method edits a selected post
//     *
//     * @param postDTO            is a post DTO object, that collects the input from the comment form
//     * @param sessionUser        is the logged-in user
//     * @param redirectAttributes is a postID attribute
//     * @return the return value is either a redirect to the postEdit page, or
//     * a redirect to the post page (in case the sessionUser is not an admin)
//     */
//    @PostMapping("/postEdit")
//    public String editPost(@ModelAttribute("postDTO") PostDTO postDTO, @ModelAttribute("sessionUser") User sessionUser, RedirectAttributes redirectAttributes) {
//        if (sessionUser.getAdmin()) {
//            Optional<Post> post = taskRepository.findById(postDTO.getPostId());
//            if (post.isPresent()) {
//                redirectAttributes.addAttribute("postId", post.get());
//                return "redirect:/postEdit/";
//            }
//        }
//        return "redirect:/post";
//    }
//
//    /**
//     * This method is used for displaying a form for editing a post
//     *
//     * @param postId      is the ID of a post
//     * @param sessionUser is the logged-in user
//     * @param model       contains the attributes used for communication between the Java code and the
//     *                    HTML template
//     * @return the return value is either the updatePost.html, or in case the sessionUser is not
//     * an admin, a redirect to the post page
//     */
//    @GetMapping("/postEdit/")
//    public String editGet(@RequestParam long postId, @ModelAttribute("sessionUser") User sessionUser, Model model, @RequestParam(required = false) String postImage) {
//        if (sessionUser.getAdmin()) {
//            Optional<Post> optionalPost = taskRepository.findById(postId);
//            if (optionalPost.isPresent()) {
//                Post post = optionalPost.get();
//                if (postImage != null) {
//                    post.setUrlToImage(postImage);
//                    taskRepository.save(post);
//                }
//                model.addAttribute("post", post);
//                return "layoutUpdatePost";
//            }
//        }
//        return "redirect:/post";
//    }

//    /**
//     * This method is used for uploading an image file to the post
//     *
//     * @param file               is a file to be uploaded
//     * @param redirectAttributes are the attributes passed on to the redirect to the admin page
//     * @return the return value is the redirect to the admin page
//     */
//    @PostMapping("/uploadFile")
//    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes
//    ) {
//        addFileToRedirectAttributes(redirectAttributes, file);
//        return "redirect:/admin";
//    }
//
//    @PostMapping("/updateFile")
//    public String updateFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes,
//                             @ModelAttribute("postDTO") PostDTO postDTO
//    ) {
//        addFileToRedirectAttributes(redirectAttributes, file);
//        redirectAttributes.addAttribute("postId", postDTO.getPostId());
//        return "redirect:/postEdit/";
//    }
//
//    private void addFileToRedirectAttributes(RedirectAttributes redirectAttributes, MultipartFile file) {
//        FileUploader.uploadFile(file);
//        redirectAttributes.addAttribute("postImage", file.getOriginalFilename());
//        redirectAttributes.addFlashAttribute("message",
//                "You successfully uploaded " + file.getOriginalFilename() + "!");
//    }

    private void generateDummyData() {
        User userMaja = new User();
        userMaja.setUsername("Maja");
        userMaja.setPassword("Dummy");
        userMaja.setFirstName("Maja");
        userMaja.setLastName("Francetic");
        userMaja.setLocation("80637, München");

        User userMichael = new User();
        userMichael.setUsername("Michael");
        userMichael.setPassword("Dummy");
        userMichael.setFirstName("Michael");
        userMichael.setFirstName("Holland");
        userMichael.setLocation("80333, München");


        userRepository.save(userMaja);
        userRepository.save(userMichael);


        Task taskBlumenGiessen = new Task();
        taskBlumenGiessen.setTitle("Blumen Gießen");
        taskBlumenGiessen.setText("Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.");
        taskBlumenGiessen.setHelpUser(userMichael);
        taskBlumenGiessen.setRequestUser(userMaja);
        taskBlumenGiessen.setCreatedAt(Instant.now().minusSeconds(100));
        taskBlumenGiessen.setDueDate(Instant.now().plusSeconds(90000));
        taskBlumenGiessen.setAccepted(false);
        taskBlumenGiessen.setAdressTask(new Adress("Germany", "München", "404060", "Seitelstraße", "5", "a"));

        Task taskblumengiessenZwei = new Task();
        taskblumengiessenZwei.setTitle("Blumen Gießen");
        taskblumengiessenZwei.setText("Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.Hallo liebe Nachbarschaft. Ich benötige Hilfe beim Blumen gießen.");
        taskblumengiessenZwei.setHelpUser(userMichael);
        taskblumengiessenZwei.setRequestUser(userMaja);
        taskblumengiessenZwei.setCreatedAt(Instant.now().minusSeconds(100));
        taskblumengiessenZwei.setDueDate(Instant.now().plusSeconds(90000));
        taskblumengiessenZwei.setAccepted(false);
        taskBlumenGiessen.setAdressTask(new Adress("Germany", "München", "404060", "Seitelstraße", "5", "a"));


        Task taskEinkaufen = new Task();
        taskEinkaufen.setTitle("Kleiner Einkauf");
        taskEinkaufen.setText("Liebe Nachbarn, kann mir jemand einen kleinen Einkauf erledigen? 1 Stück Butter, 1 kg Mehl und 6 Eier.Liebe Nachbarn, kann mir jemand einen kleinen Einkauf erledigen? 1 Stück Butter, 1 kg Mehl und 6 Eier.Liebe Nachbarn, kann mir jemand einen kleinen Einkauf erledigen? 1 Stück Butter, 1 kg Mehl und 6 Eier.Liebe Nachbarn, kann mir jemand einen kleinen Einkauf erledigen? 1 Stück Butter, 1 kg Mehl und 6 Eier.Liebe Nachbarn, kann mir jemand einen kleinen Einkauf erledigen? 1 Stück Butter, 1 kg Mehl und 6 Eier.Liebe Nachbarn, kann mir jemand einen kleinen Einkauf erledigen? 1 Stück Butter, 1 kg Mehl und 6 Eier.Liebe Nachbarn, kann mir jemand einen kleinen Einkauf erledigen? 1 Stück Butter, 1 kg Mehl und 6 Eier.Liebe Nachbarn, kann mir jemand einen kleinen Einkauf erledigen? 1 Stück Butter, 1 kg Mehl und 6 Eier.");
        taskEinkaufen.setHelpUser(userMaja);
        taskEinkaufen.setRequestUser(userMichael);
        taskEinkaufen.setCreatedAt(Instant.now().minusSeconds(100));
        taskEinkaufen.setDueDate(Instant.now().plusSeconds(10000));
        taskEinkaufen.setAccepted(false);
        taskBlumenGiessen.setAdressTask(new Adress("Germany", "München", "404060", "Seitelstraße", "5", "a"));

        taskRepository.save(taskEinkaufen);
        taskRepository.save(taskblumengiessenZwei);
        taskRepository.save(taskBlumenGiessen);

//
//        Post postPrague = new Post();
//        postPrague.setTitle("Prague");
//        postPrague.setUrlToImage("prague.jpg");
//        postPrague.setText("Prague is the capital and largest city in the Czech Republic, the 13th largest city in the European Union and the historical capital of Bohemia. Situated on the Vltava river, Prague is home to about 1.3 million people, while its metropolitan area is estimated to have a population of 2.7 million. The city has a temperate oceanic climate, with relatively warm summers and chilly winters. \n" +
//                "\n" +
//                "Prague is home to a number of well-known cultural attractions, many of which survived the violence and destruction of 20th-century Europe. Main attractions include Prague Castle, Charles Bridge, Old Town Square with the Prague astronomical clock, the Jewish Quarter, Petřín hill and Vyšehrad. Since 1992, the extensive historic centre of Prague has been included in the UNESCO list of World Heritage Sites.");
//        postPrague.setUser(userMichael);
//        postPrague.setPostedAt(Instant.now().minusSeconds(200));
//
//        Post postZellAmSee = new Post();
//        postZellAmSee.setTitle("Zell Am See");
//        postZellAmSee.setText("Zell am See is the administrative capital of the Zell am See District in the Austrian state of Salzburg. The town is an important tourist destination known as Zell am See-Kaprun and is a transportation hub for the region. \n" +
//                "\n" +
//                "The original Lake Zell reached somewhat further to the north and extended south to the Salzach river. The dimensions of the lake, however, have changed over time into marsh areas. The lake has the shape of a peanut, with an area of 4.7 square kilometres (1.8 sq mi). \n" +
//                "Zell am See provides winter skiing on the above Schmittenhöhe mountain. The skiable area is approximately 138 km, including the pistes on the Kitzsteinhorn and Kaprun Maiskogel. The ski pass covers the whole area including transport to and from the glacier which is open most of the year, dependent on snowfall. Zell am See is a low-altitude ski area and snow cover can suffer from higher temperatures, but the glacier has snow cover most of the year");
//        postZellAmSee.setUser(userMaja);
//        postZellAmSee.setUrlToImage("zellamsee.jpg");
//        postZellAmSee.setPostedAt(Instant.now().minusSeconds(300));
//
//
//        Post postUmag = new Post();
//        postUmag.setTitle("Umag");
//        postUmag.setText("Umag in Istria is the first city you will reach when approaching Istria from western Europe. Umag conquers with its allure, diversity and richness of the tourist offer. Umag is a town of around 13,000 inhabitants which with about 45 km of the coastline and interesting hinterland integrates special features of the whole region and might be the best introduction into a different experience of the Mediterranean.\n" +
//                "\n" +
//                " The remains of the 10th-century town walls include a gate and a drawbridge. Umag Town Museum, in a former defense tower, contains Roman-era objects such as amphorae and lamps, as well as contemporary local art. The Assumption of the Virgin Mary Church has an unfinished baroque facade and a 17th-century bell tower. A long sea wall from the 1800s shelters the harbor.");
//        postUmag.setUser(userMaja);
//        postUmag.setUrlToImage("umag.jpg");
//        postUmag.setPostedAt(Instant.now().minusSeconds(400));
//
//        Comment comment = new Comment();
//        comment.setPostedAt(Instant.now().minusSeconds(500));
//        comment.setText("Such a wonderful city!");
//        comment.setPost(postPrague);
//        comment.setUser(userMaja);
//
//        Comment commentTwo = new Comment();
//        commentTwo.setPostedAt(Instant.now().minusSeconds(600));
//        commentTwo.setText("Beautiful!");
//        commentTwo.setPost(postPrague);
//        commentTwo.setUser(userMaja);
//
//        Comment commentThree = new Comment();
//        commentThree.setPostedAt(Instant.now().minusSeconds(700));
//        commentThree.setText("Wonderful!");
//        commentThree.setPost(postUmag);
//        commentThree.setUser(userMaja);
//
//        Comment commentFour = new Comment();
//        commentFour.setPostedAt(Instant.now().minusSeconds(800));
//        commentFour.setText("A very nice photo!");
//        commentFour.setPost(postUmag);
//        commentFour.setUser(userMichael);
//
//        userRepository.save(userMichael);
//        userRepository.save(userMaja);
//
//        taskRepository.save(postParis);
//        taskRepository.save(postPrague);
//        taskRepository.save(postUmag);
//        taskRepository.save(postZellAmSee);
//
//        commentRepository.save(comment);
//        commentRepository.save(commentTwo);
//        commentRepository.save(commentThree);
//        commentRepository.save(commentFour);
    }
}
