package MiniProject;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {

    private PriorityQueues<Task> taskQueue = new PriorityQueues<>();

    private int nextIndex = 0;
    public void addTask() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter Task Name : ");
        String name = sc.nextLine();

        System.out.print("Enter Priority (Lower Number = Higher Priority) : ");
        int priority = sc.nextInt();
        sc.nextLine();

        Task task = new Task(name,priority);
        taskQueue.add(task);
        System.out.println("Task added: " + task);
      
    }

    public void viewNextTask() {

        if (taskQueue.isEmpty()) {
            System.out.println("No tasks available");
            nextIndex = 0;
            return;
        }

        Object[] arr = taskQueue.toArray();
        Task[] tasks = new Task[arr.length];

        for(int i=0;i<arr.length;i++){
            tasks[i] = (Task) arr[i];
        }
        Arrays.sort(tasks);
        if (nextIndex >= tasks.length) {
            System.out.println("No more tasks to show");
            return;
        }

        System.out.println("Next task: " + tasks[nextIndex]);
        nextIndex++;
    }

    public void completeTask() {

        if (taskQueue.isEmpty()) {
            System.out.println("No tasks to complete");
            return;
        }

        Object[] arr = taskQueue.toArray();
        Arrays.sort(arr);
        Task[] tasks = new Task[arr.length];

        for(int i=0;i<arr.length;i++){
            tasks[i] = (Task) arr[i];
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("Available tasks:");
        for (int i = 0; i < tasks.length; i++) {
            System.out.println((i + 1) + ". " + tasks[i]);
        }

        System.out.print("Enter the task number to complete: ");
        int choice = sc.nextInt();

        if (choice < 1 || choice > tasks.length) {
            System.out.println("Invalid task number!");
            return;
        }

        Task toRemove = tasks[choice - 1];
        taskQueue.remove(toRemove);

        System.out.println("Completed: " + toRemove);

        nextIndex = 0;
    }

    public void listTasks() {
        if (taskQueue.isEmpty()) {
            System.out.println("No tasks available");
            return;
        }

        Object[] tasks = taskQueue.toArray();
        Arrays.sort(tasks);
        System.out.println("All tasks:");
        for (Object t : tasks) {
            System.out.println("- " + t);
        }
    }
    
    public void changePriority() {
		
    	if (taskQueue.isEmpty()) {
            System.out.println("No tasks to Added");
            return;
        }

        Object[] arr = taskQueue.toArray();
        Arrays.sort(arr);
        Task[] tasks = new Task[arr.length];
        for(int i=0;i<arr.length;i++){
            tasks[i] = (Task) arr[i];
        }

        @SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
        System.out.println("Available tasks:");
        for (int i = 0; i < tasks.length; i++) {
            System.out.println((i + 1) + ". " + tasks[i]);
        }
        System.out.print("Enter the task number to change Priority: ");
        int choice = sc.nextInt();

        if (choice < 1 || choice > tasks.length) {
            System.out.println("Invalid task number!");
            return;
        }
        
        System.out.print("Enter new Priority  :");
        int newPriority = sc.nextInt();
        Task toRemove = tasks[choice - 1];
        String newTask = toRemove.getName();
        taskQueue.remove(toRemove);
        Task changeTask = new Task(newTask,newPriority);
        taskQueue.add(changeTask); 

        nextIndex = 0;
	}

    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        Scanner sc = new Scanner(System.in);
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
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine();
            } else {
                System.out.println("Please enter a valid number!");
                sc.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    manager.addTask();
                    break;

                case 2:
                    manager.viewNextTask();
                    break;

                case 3:
                    manager.completeTask();
                    break;

                case 4:
                    manager.listTasks();
                    break;
                    
                case 5:
                	manager.changePriority();
                	break;

                case 6:
                    System.out.println("\nExiting Task Manager. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        sc.close();
    }
}

class Task implements Comparable<Task>{

    public String name;
    public int priority;

    public Task(String name,int priority){
        this.name = name;
        this.priority = priority;
    }

    public String getName(){
        return name;
    }

    public int getPriority(){
        return priority;
    }
    
    public void setPriority(int priority) {
    	this.priority = priority;
    }

    @Override
    public int compareTo(Task other){
        return Integer.compare(this.priority,other.priority);
    }

    @Override
    public String toString(){
        return name+" | Priority : "+priority;
    }
}