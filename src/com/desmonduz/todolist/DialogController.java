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
    public TextField txtShortDescription;
    @FXML
    public TextArea txtDetails;
    @FXML
    public DatePicker calDeadline;

    public TodoItem getItem(){
        String shortDesc = txtShortDescription.getText();
        String details = txtDetails.getText();
        LocalDate deadline = calDeadline.getValue();
        TodoItem newItem = new TodoItem(shortDesc,details, deadline);

        return newItem;
    }

    public void setItem(TodoItem item) {
        calDeadline.setValue(item.getDeadline());
        txtDetails.setText(item.getDetails());
        txtShortDescription.setText(item.getShortDescription());
    }
}
