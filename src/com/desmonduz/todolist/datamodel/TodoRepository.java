package com.desmonduz.todolist.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class TodoRepository {
    private static TodoRepository instance = new TodoRepository();

    public ObservableList<TodoItem> getTodoItems() {
        return todoItems;
    }

    public void setTodoItems(ObservableList<TodoItem> todoItems) {
        this.todoItems = todoItems;
    }

    private static String filename = "TodoListItems.txt";

    private ObservableList<TodoItem> todoItems;
    private DateTimeFormatter formatter;

    public static TodoRepository getInstance(){
        return instance;
    }

    private TodoRepository(){
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    public void loadTodoItems() throws IOException {
        todoItems = FXCollections.observableArrayList();
        Path path = Paths.get(filename);

        BufferedReader br = Files.newBufferedReader(path);
        String input;

        try {
            while((input = br.readLine())!=null) {
                String[] itemPieces = input.split("\t");
                String shortDescription = itemPieces[0];
                String details = itemPieces[1];
                String dateString = itemPieces[2];

                LocalDate date = LocalDate.parse(dateString, formatter);
                TodoItem todoItem = new TodoItem(shortDescription, details, date);
                todoItems.add(todoItem);
            }
        } finally {
            if (br != null) br.close();
        }
    }
    
    public void storeTodoItems() throws IOException {
        Path path = Paths.get(filename);
        BufferedWriter bw = Files.newBufferedWriter(path);
        try {
            for (TodoItem item:todoItems) {
                bw.write(String.format("%s\t%s\t%s",
                        item.getShortDescription(),
                        item.getDetails(),
                        item.getDeadline().format(formatter)));
                bw.newLine();
            }
        } finally {
            if (bw != null) bw.close();
        }
    }

    public void addTodoItem(String shortDesc, String details, LocalDate deadline){
        todoItems.add(new TodoItem(shortDesc, details, deadline));
    }

    public void addTodoItem(TodoItem item){
        todoItems.add(item);
    }

    public void deleteTodoItem(TodoItem item){
        todoItems.remove(item);
    }

}
