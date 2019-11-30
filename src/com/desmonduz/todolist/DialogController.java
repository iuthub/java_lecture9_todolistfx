package com.desmonduz.todolist;

import com.desmonduz.todolist.datamodel.TodoItem;
import com.desmonduz.todolist.datamodel.TodoRepository;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class DialogController {
    @FXML
    private TextField txtShortDescription;
    @FXML
    private TextArea txtDetails;
    @FXML
    private DatePicker calDeadline;

    public TodoItem processResults(){
        String shortDesc = txtShortDescription.getText();
        String details = txtDetails.getText();
        LocalDate deadline = calDeadline.getValue();
        TodoItem newItem =new TodoItem(shortDesc,details, deadline);
        TodoRepository.getInstance().addTodoItem(newItem);

        return newItem;
    }
}
