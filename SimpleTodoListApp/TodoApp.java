package Java;

import java.io.*;
import java.util.*;

public class TodoApp {
    private static final String FILE_NAME = "todo.txt";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n== TODO APPLICATION ==");
            System.out.println("1. View Todo List");
            System.err.println("2. Add Task");
            System.err.println("3. Update Task");
            System.err.println("4. Delete Task");
            System.err.println("5. Exit");
            System.err.println("Choose (1-5): ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> viewTodoList();
                case "2" -> addTask();
                case "3" -> updateTask();
                case "4" -> deleteTask();
                case "5" -> {
                    System.err.println("Exiting... Goodbye!");
                    return;
                }
                default -> System.err.println("Invalid choice. Try again.");
            }
        }
    }

    private static List<String> readTasksFromFile() {
        List<String> tasks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                tasks.add(line);
            }
        } catch (IOException e) {
            // File might not exist yet — that's okay
        }
        return tasks;
    }

    private static void writeTasksToFile(List<String> tasks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String task : tasks) {
                writer.write(task);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    private static void viewTodoList() {
        List<String> tasks = readTasksFromFile();
        if (tasks.isEmpty()) {
            System.out.println("Your todo list is empty.");
        } else {
            System.out.println("\nYour Todo List:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
    }

    private static void addTask() {
        System.out.print("Enter a new task: ");
        String newTask = scanner.nextLine();

        List<String> tasks = readTasksFromFile();
        tasks.add(newTask);
        writeTasksToFile(tasks);
        System.out.println("Task added!");
    }

    private static void updateTask() {
        List<String> tasks = readTasksFromFile();
        viewTodoList();

        if (tasks.isEmpty()) return;

        System.out.print("Enter the task number to update: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;

        if (index < 0 || index >= tasks.size()) {
            System.out.println("Invalid task number.");
            return;
        }

        System.out.print("Enter new task content: ");
        String updatedTask = scanner.nextLine();
        tasks.set(index, updatedTask);
        writeTasksToFile(tasks);
        System.out.println("Task updated!");
    }

    private static void deleteTask() {
        List<String> tasks = readTasksFromFile();
        viewTodoList();

        if (tasks.isEmpty()) return;

        System.out.print("Enter the task number to delete: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;

        if (index < 0 || index >= tasks.size()) {
            System.out.println("Invalid task number.");
            return;
        }

        tasks.remove(index);
        writeTasksToFile(tasks);
        System.out.println("Task deleted!");
    }
}


