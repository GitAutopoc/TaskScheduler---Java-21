package com.task.assessment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Task {
    private String name;
    private List<Task> dependencies;

    public Task(String name) {
        this.name = name;
        dependencies = new ArrayList<>();
    }

    public void addDependency(Task dependency) {
        dependencies.add(dependency);
    }

    public String getName() {
        return name;
    }

    public List<Task> getDependencies() {
        return dependencies;
    }
}

public class TaskScheduler {
    private Map<String, Task> tasks;
    private Set<String> executedTasks;

    public TaskScheduler() {
        tasks = new HashMap<>();
        executedTasks = new HashSet<>();
    }

    public void addTask(String name) {
        tasks.put(name, new Task(name));
    }

    public void addDependency(String taskName, String dependencyName) {
        Task task = tasks.get(taskName);
        Task dependency = tasks.get(dependencyName);
        if (task != null && dependency != null) {
            task.addDependency(dependency);
        } else {
            System.out.println("Invalid task or dependency name.");
        }
    }

    public void scheduleTasks() {
        for (Task task : tasks.values()) {
            executeTask(task);
        }
    }

    private void executeTask(Task task) {
        if (executedTasks.contains(task.getName())) {
            return;
        }
        for (Task dependency : task.getDependencies()) {
            executeTask(dependency);
        }
        executedTasks.add(task.getName());
    }

    public Set<String> getExecutedTasks() {
        return executedTasks;
    }

    public static void main(String[] args) {
        TaskScheduler scheduler = new TaskScheduler();

        scheduler.addTask("Task1");
        scheduler.addTask("Task2");
        scheduler.addTask("Task3");
        scheduler.addTask("Task4");

        scheduler.addDependency("Task2", "Task1");
        scheduler.addDependency("Task3", "Task1");
        scheduler.addDependency("Task4", "Task2");
        scheduler.addDependency("Task4", "Task3");

        scheduler.scheduleTasks();

        for (String taskName : scheduler.getExecutedTasks()) {
            System.out.println(taskName);
        }
    }
}
