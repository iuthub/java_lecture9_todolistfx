package com.desmonduz.todolist;

import com.desmonduz.todolist.datamodel.TodoItem;
import com.desmonduz.todolist.datamodel.TodoRepository;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

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
//        TodoItem item1 = new TodoItem("Mail birthday card", "Buy birthday card", LocalDate.of(2019, Month.AUGUST, 20));
//        TodoItem item2 = new TodoItem("Doctors Appointment", "See doctor at 123 Main Street", LocalDate.of(2019, Month.AUGUST, 22));
//
//        todoItems= new ArrayList<>();
//
//        todoItems.add(item1);
//        todoItems.add(item2);
//        TodoRepository.getInstance().setTodoItems(todoItems);
//        MenuItem deleteMenuItem = new MenuItem("Remove");
//        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                TodoItem item = (TodoItem) lstToDoItems.getSelectionModel().getSelectedItem();
//                deleteItem(item);
//            }
//        });
//
//        listContextMenu.getItems().addAll(deleteMenuItem);


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
        TodoItem selectedItem = (TodoItem) lstToDoItems.getSelectionModel().getSelectedItem();
        if (selectedItem!=null){
            txtDescription.setText(selectedItem.getDetails());
            lblDeadline.setText(selectedItem.getDeadline().toString());
        }


    }

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
            TodoItem item=controller.processResults();
//            lstToDoItems.getItems().setAll(TodoRepository.getInstance().getTodoItems());
            lstToDoItems.getSelectionModel().select(item);
        }
    }
}
