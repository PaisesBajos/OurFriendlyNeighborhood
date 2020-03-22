package de.awacademy.ourblog.utils;

import de.awacademy.ourblog.task.Task;
import de.awacademy.ourblog.user.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CustomQueries {
    public static List<Task> sortByDistance(List<Task> taskList, User user){
        taskList.sort(Comparator.comparingDouble((Task o) -> o.getDistance(user)));
        return taskList;
    }
}
