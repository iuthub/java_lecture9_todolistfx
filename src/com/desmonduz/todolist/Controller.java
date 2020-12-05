package com.desmonduz.todolist;

import com.desmonduz.todolist.datamodel.TodoItem;
import com.desmonduz.todolist.datamodel.TodoRepository;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class Controller {
    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private Label lblDeadline;

    @FXML
    private ListView lstToDoItems;

    @FXML
    private TextArea txtDescription;

    @FXML
    public void initialize(){
        lstToDoItems.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                //another way to implement item selection changed
                handleClickListView();
            }
        });

        lstToDoItems.setItems(TodoRepository.getInstance().getTodoItems());
        lstToDoItems.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    public void deleteItem(TodoItem item){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Delete To Item");
        alert.setHeaderText("Delete Item: " + item.getShortDescription());
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            TodoRepository.getInstance().deleteTodoItem(item);
        }
    }

    @FXML
    public void handleClickListView(){
        lstToDoItems.refresh();
        TodoItem selectedItem = (TodoItem) lstToDoItems.getSelectionModel().getSelectedItem();
        if (selectedItem!=null){
            txtDescription.setText(selectedItem.getDetails());
            lblDeadline.setText(selectedItem.getDeadline().toString());
        }


    }

    @FXML
    public void handleDeleteItem(ActionEvent actionEvent) {
                TodoItem item = (TodoItem) lstToDoItems.getSelectionModel().getSelectedItem();
                deleteItem(item);
    }

    @FXML
    public void showEditItemDialog() throws IOException {
        TodoItem item = (TodoItem) lstToDoItems.getSelectionModel().getSelectedItem();

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Edit To Do Item");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("todoItemDialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        DialogController controller = fxmlLoader.getController();
        controller.setItem(item);

        Optional<ButtonType> result = dialog.showAndWait();

        if(result.isPresent() && result.get()==ButtonType.OK) {
            item.load(controller.getItem());
            handleClickListView();
            TodoRepository.getInstance().storeTodoItems();
        }
    }

    @FXML
    public void showNewItemDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Add New To Do Item");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("todoItemDialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();

        if(result.isPresent() && result.get()==ButtonType.OK) {
            DialogController controller = fxmlLoader.getController();
            TodoItem item=controller.getItem();
            TodoRepository.getInstance().addTodoItem(item);
            lstToDoItems.getSelectionModel().select(item);
        }
    }

    @FXML
    public void handleClose() {
        Platform.exit();
    }
}
