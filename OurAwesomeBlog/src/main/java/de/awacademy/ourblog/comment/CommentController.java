//package de.awacademy.ourblog.comment;
//
//
//import de.awacademy.ourblog.task.TaskRepository;
//import de.awacademy.ourblog.user.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.time.Instant;
//
//@Controller
//public class CommentController {
//
////    private CommentRepository commentRepository;
//    private TaskRepository taskRepository;
//
//    @Autowired
//    public CommentController( TaskRepository taskRepository) {
//
//        this.taskRepository = taskRepository;
//    }
//
//    /**
//     * This method creates a new comment
//     *
//     * @param commentDTO is a comment DTO object, that collects the input from the comment form
//     * @param bindingResult is the result of the form validation
//     * @param sessionUser is the logged-in user
//     * @param postId is the ID of the new post that will be created
//     * @return the method returns a redirect to the post page
//     */
////    @PostMapping("/post/comment/{postId}")
////    public String newComment(@Valid @ModelAttribute("comment") CommentDTO commentDTO, BindingResult bindingResult, @ModelAttribute("sessionUser") User sessionUser, @PathVariable long postId) {
////        if (bindingResult.hasErrors()) {
////            return "redirect:/post";
////        }
////        if (taskRepository.findById(postId).isPresent()) {
////            Task task = taskRepository.findById(postId).get();
////            Comment comment = new Comment(sessionUser, commentDTO.getText(), Instant.now(), post);
////            commentRepository.save(comment);
////        }
////
////        return "redirect:/post";
////    }
//
////    /**
////     * This method deletes the comment
////     *
////     * @param commentDTO is a comment DTO object, that collects the input from the comment form
////     * @param sessionUser is the logged-in user
////     * @return the method returns a redirect to the post page
////     */
////    @PostMapping("/commentDelete")
////    public String delete(@ModelAttribute("commentDTO") CommentDTO commentDTO, @ModelAttribute("sessionUser") User sessionUser) {
////        Comment comment = commentRepository.findById(commentDTO.getCommentId()).orElseThrow();
////        if (comment.getUser() == sessionUser || sessionUser.getAdmin()) {
////            commentRepository.delete(comment);
////        }
////
////        return "redirect:/post";
////    }
//}
