package taskmanager;

import java.util.ArrayList;
import java.util.Scanner;

public class TaskManager {

    private final PriorityQueues<Task> taskQueue = new PriorityQueues<>();
    private final Scanner sc = new Scanner(System.in);

    private PriorityQueues<Task> copyQueue(PriorityQueues<Task> source) {
        PriorityQueues<Task> copy = new PriorityQueues<>(Math.max(11, source.size() + 1));
        Iterators<Task> it = source.iterator();
        while (it.hasNext()) {
            copy.add(it.next());
        }
        return copy;
    }

    public void addTask() {
        System.out.print("\nEnter Task Name : ");
        String name = sc.nextLine();

        System.out.print("Enter Priority (Lower Number = Higher Priority) : ");
        if (!sc.hasNextInt()) {
            System.out.println("Invalid input! Please enter a number for priority.");
            sc.nextLine();
            return;
        }
        int priority = sc.nextInt();
        sc.nextLine();

        Task task = new Task(name, priority);
        taskQueue.add(task);
        System.out.println("Task added: " + task);

    }

    public void viewNextTask() {

        if (taskQueue.isEmpty()) {
            System.out.println("No tasks available");
            return;
        }

        System.out.println("Next task: " + taskQueue.peek());
    }

    public void completeTask() {

        if (taskQueue.isEmpty()) {
            System.out.println("No tasks to complete");
            return;
        }

        PriorityQueues<Task> temp = copyQueue(taskQueue);
        ArrayList<Task> tasks = new ArrayList<>();
        while (!temp.isEmpty()) {
            tasks.add(temp.poll());
        }

        System.out.println("Available tasks:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }

        System.out.print("Enter the task number to complete: ");
        if (!sc.hasNextInt()) {
            System.out.println("Invalid input! Please enter a number.");
            sc.nextLine();
            return;
        }
        int choice = sc.nextInt();
        sc.nextLine();

        if (choice < 1 || choice > tasks.size()) {
            System.out.println("Invalid task number!");
            return;
        }

        Task toComplete = tasks.get(choice - 1);
        taskQueue.remove(toComplete);
        System.out.println("Completed: " + toComplete);
    }

    public void listTasks() {
        if (taskQueue.isEmpty()) {
            System.out.println("No tasks available");
            return;
        }

        PriorityQueues<Task> temp = copyQueue(taskQueue);
        System.out.println("All tasks:");
        while (!temp.isEmpty()) {
            System.out.println("- " + temp.poll());
        }
    }

    public void changePriority() {

        if (taskQueue.isEmpty()) {
            System.out.println("No tasks to Added");
            return;
        }

        PriorityQueues<Task> temp = copyQueue(taskQueue);
        ArrayList<Task> tasks = new ArrayList<>();
        while (!temp.isEmpty()) {
            tasks.add(temp.poll());
        }

        System.out.println("Available tasks:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
        System.out.print("Enter the task number to change Priority: ");
        if (!sc.hasNextInt()) {
            System.out.println("Invalid input! Please enter a number.");
            sc.nextLine();
            return;
        }
        int choice = sc.nextInt();
        sc.nextLine();

        if (choice < 1 || choice > tasks.size()) {
            System.out.println("Invalid task number!");
            return;
        }

        System.out.print("Enter new Priority  :");
        if (!sc.hasNextInt()) {
            System.out.println("Invalid input! Please enter a number.");
            sc.nextLine();
            return;
        }
        int newPriority = sc.nextInt();
        sc.nextLine();
        Task toRemove = tasks.get(choice - 1);
        String newTask = toRemove.getName();
        taskQueue.remove(toRemove);
        Task changeTask = new Task(newTask, newPriority);
        taskQueue.add(changeTask);
    }

    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        boolean running = true;

        System.out.println("========== Welcome to Task Manager ==========");

        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Task");
            System.out.println("2. View Next Task");
            System.out.println("3. Complete Task");
            System.out.println("4. List All Tasks");
            System.out.println("5.Change Priority");
            System.out.println("6. Exit");
            System.out.print("\nEnter your choice: ");

            int choice;
            if (manager.sc.hasNextInt()) {
                choice = manager.sc.nextInt();
                manager.sc.nextLine();
            } else {
                System.out.println("Please enter a valid number!");
                manager.sc.nextLine();
                continue;
            }

            switch (choice) {
                case 1 -> manager.addTask();
                case 2 -> manager.viewNextTask();
                case 3 -> manager.completeTask();
                case 4 -> manager.listTasks();
                case 5 -> manager.changePriority();
                case 6 -> {
                    System.out.println("\nExiting Task Manager. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
}

class Task implements Comparable<Task> {

    public String name;
    public int priority;

    public Task(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public int compareTo(Task other) {
        return Integer.compare(this.priority, other.priority);
    }

    @Override
    public String toString() {
        return name + " | Priority : " + priority;
    }
}
