package javaproject;

import javafx.scene.*;
import javafx.stage.*;
import javafx.event.*;
import java.io.File;
import java.awt.Color;
import java.awt.Desktop;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.application.*;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.DatePicker;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.beans.property.SimpleStringProperty;
import static javafx.application.Application.launch;

public class dailyActivityList extends Application {

    private final TableView<Task> tableList = new TableView<Task>();
    private final ObservableList<Task> data = FXCollections.observableArrayList();// FXCollection is javafx collection

    Label response;            //  response is set the new value by calling setText()

    private Desktop desktop = Desktop.getDesktop();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // BorderPane layout = new BorderPane();

        //To create: Add check box next to each task you add, delete and edit buttons, add a week and month view, and a menu on top so you can save different copies. 
        response = new Label("Menu");

        MenuBar menuBar = new MenuBar();                           // declare menu bar object
        menuBar.setStyle("-fx-background-color:skyblue");
        Menu fileMenu = new Menu("File");
        fileMenu.setStyle("-fx-font:20 arial");

        MenuItem open = new MenuItem("Open");
        MenuItem save = new MenuItem("Save As");
        MenuItem exit = new MenuItem("Exit");

        SeparatorMenuItem separator = new SeparatorMenuItem();     //  we declare sepratorMenuItem for separate the every menu items
        fileMenu.getItems().add(open);
        fileMenu.getItems().add(save);
        fileMenu.getItems().add(separator);
        fileMenu.getItems().add(exit);
        menuBar.getMenus().add(fileMenu);

        // we create a another event handler to handle the manu bar
        EventHandler<ActionEvent> menuHandler = new EventHandler<ActionEvent>() {

            public void handle(ActionEvent ae) {
                String name = ((MenuItem) ae.getTarget()).getText();  //here getTarget indicate or target the values that take from getText()

                // we use a condition  if Exit is chosen, the execution of  program is  termineted.
                if (name.equals("Exit")) {
                    Platform.exit();                                  // if we select exit our pop up window will be closed
                }
                response.setText(name + " selected");                 // otherwise response is set the selected item by calling setText()
            }
        };

        open.setOnAction(menuHandler);
        save.setOnAction(menuHandler);
        exit.setOnAction(menuHandler);

        FileChooser fileChooser = new FileChooser();           // to create a object for file

        open.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
                File opensFile = fileChooser.showOpenDialog(primaryStage);
                if (opensFile != null) {
                    openFile(opensFile);
                }
            }

        });

        save.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                File savesFile = fileChooser.showSaveDialog(primaryStage);
                if (savesFile != null) {

                    // saveFile(savesFile);
                    //System.out.println(savesFile);
                }
            }

        });
        //  FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("text files", "*.txt","*.doc");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("text files", "*.txt", "*.doc"),
                new FileChooser.ExtensionFilter("pdf", "*.pdf"),
                new FileChooser.ExtensionFilter("images", "*.jpg", "*.png"));

        // we create a object for every content  to arrage the table 
        Label label1 = new Label("Daily Activity List");
        label1.setFont(Font.font("sanSerif", 25));
        label1.setPadding(new Insets(0, 0, 0, 260));
        label1.setStyle("-fx-background-color:skyblue");

        TableColumn taskCol = new TableColumn("Task");
        taskCol.setStyle("-fx-font: 16 sanSerif");
        taskCol.setMinWidth(190);

        TableColumn dayCol = new TableColumn("Day");
        dayCol.setStyle("-fx-font: 16 sanSerif");
        dayCol.setMinWidth(50);

        TableColumn timeCol = new TableColumn("Time");
        timeCol.setStyle("-fx-font: 16 sanSerif");
        timeCol.setMinWidth(30);

        TableColumn deadlineCol = new TableColumn("Deadline");
        deadlineCol.setStyle("-fx-font: 16 sanSerif");
        deadlineCol.setMinWidth(50);

        TableColumn mentorCol = new TableColumn("Faculty");
        mentorCol.setStyle("-fx-font: 16 sanSerif");
        mentorCol.setMinWidth(120);

        TableColumn descriptionCol = new TableColumn("Task Description");
        descriptionCol.setStyle("-fx-font: 16 sanSerif");
        descriptionCol.setMinWidth(180);

        // setCellValueFactory is used to associate a column in a TableView with a property of a class that contains the data to be displayed in the TableView's column
        taskCol.setCellValueFactory(new PropertyValueFactory<Task, String>("Task"));
        taskCol.setCellFactory(TextFieldTableCell.forTableColumn());
        taskCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Task, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Task, String> t) {
                ((Task) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setTask(t.getNewValue());
            }
        }
        );

        dayCol.setCellValueFactory(new PropertyValueFactory<Task, String>("Day"));
        dayCol.setCellFactory(TextFieldTableCell.forTableColumn());
        dayCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Task, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Task, String> t) {
                ((Task) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setDay(t.getNewValue());
            }
        }
        );

        timeCol.setCellValueFactory(new PropertyValueFactory<Task, String>("Time"));
        timeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        timeCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Task, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Task, String> t) {
                ((Task) t.getTableView().getItems().get(t.getTablePosition().getRow())).setTime(t.getNewValue());
            }
        }
        );

        deadlineCol.setCellValueFactory(new PropertyValueFactory<Task, String>("Deadline"));
        deadlineCol.setCellFactory(TextFieldTableCell.forTableColumn());
        deadlineCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Task, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Task, String> t) {
                ((Task) t.getTableView().getItems().get(t.getTablePosition().getRow())).setDeadline(t.getNewValue());
            }
        }
        );

        mentorCol.setCellValueFactory(new PropertyValueFactory<Task, String>("Mentor"));
        mentorCol.setCellFactory(TextFieldTableCell.forTableColumn());
        mentorCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Task, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Task, String> t) {
                ((Task) t.getTableView().getItems().get(t.getTablePosition().getRow())).setMentor(t.getNewValue());
            }
        }
        );

        descriptionCol.setCellValueFactory(new PropertyValueFactory<Task, String>("Description"));
        descriptionCol.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Task, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Task, String> t) {
                ((Task) t.getTableView().getItems().get(t.getTablePosition().getRow())).setDescription(t.getNewValue());
            }
        }
        );

        tableList.setItems(data);
        tableList.getColumns().addAll(taskCol, dayCol, timeCol, deadlineCol, mentorCol, descriptionCol); // Place the column headings in tableList.
        tableList.setEditable(true);

        // Create text fields so the user can enter new tasks into the table.
        Label label = new Label("Add your task");
        label.setFont(Font.font("sanSerif", 23));
        label.setMaxWidth(300);

        TextField addTask = new TextField();
        addTask.setPromptText("Enter Task");
        addTask.setFont(Font.font("sanSerif", 15));
        addTask.setMaxWidth(300);

        DatePicker addDay = new DatePicker();
        addDay.setPromptText("Enter Day");
        addDay.setMaxWidth(300);

        // DatePicker addTime = new DatePicker();
        TextField addTime = new TextField();
        addTime.setPromptText("Enter Time");
        addTime.setStyle(" 15 sanSerif ");
        addTime.setMaxWidth(300);

        //TextField addDeadline = new TextField();
        DatePicker addDeadline = new DatePicker();
        addDeadline.setPromptText("Enter Deadline");
        addDeadline.setMaxWidth(300);

        TextField addMentor = new TextField();
        addMentor.setPromptText("Mentor name");
        addMentor.setFont(Font.font("sanSerif", 15));
        addMentor.setMaxWidth(300);

        TextField addDescription = new TextField();
        addDescription.setPromptText("Enter Description");
        addDescription.setFont(Font.font("sanSerif", 15));
        addDescription.setMaxWidth(260);

        Button btnAdd = new Button("Add Task");
        btnAdd.setFont(Font.font("sanSerif", 15));
        btnAdd.setMaxWidth(300);
        btnAdd.setStyle("-fx-background-color:orange");

        btnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                data.add(new Task(
                        addTask.getText(),
                        ((TextField) addDay.getEditor()).getText(),
                        addTime.getText(),
                        ((TextField) addDeadline.getEditor()).getText(),
                        addMentor.getText(),
                        addDescription.getText()));

                addTask.clear();
                addDay.setValue(null);
                addTime.clear();
                addDeadline.setValue(null);
                addMentor.clear();
                addDescription.clear();

            }
        });

        // to take a data from user and add these data into the table 
        VBox vBox = new VBox();
        vBox.setSpacing(15);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setStyle("-fx-background-color:skyblue");
        vBox.getChildren().addAll(label, addTask, addDay, addTime, addDeadline, addMentor, addDescription, btnAdd);

        VBox vBox1 = new VBox();
        vBox1.setStyle("-fx-background-color:skyblue");
        VBox.setMargin(vBox1, new Insets(0, 0, 0, 30));
        vBox1.setSpacing(10);
        vBox1.getChildren().addAll(tableList);  //  all items is added  according to row
        vBox1.setPadding(new Insets(25, 25, 25, 25));

        GridPane root = new GridPane();
        root.add(menuBar, 0, 0);
        root.add(vBox, 0, 2);
        root.add(label1, 2, 0);
        root.add(vBox1, 2, 2);

        Scene scene1 = new Scene(root);
        primaryStage.setWidth(1000);
        root.setStyle("-fx-background-color:skyblue");

        primaryStage.setHeight(550);
        primaryStage.setTitle("Daily Activity ");
        primaryStage.setScene(scene1);
        primaryStage.show();
    }

    public static class Task {

        private final SimpleStringProperty task;
        private final SimpleStringProperty day;
        private final SimpleStringProperty time;
        private final SimpleStringProperty deadline;
        private final SimpleStringProperty mentor;
        private final SimpleStringProperty description;

        private Task(String task1, String day1, String time1, String deadline1, String mentor1, String description1) {

            this.task = new SimpleStringProperty(task1);
            this.day = new SimpleStringProperty(day1);
            this.time = new SimpleStringProperty(time1);
            this.deadline = new SimpleStringProperty(deadline1);
            this.mentor = new SimpleStringProperty(mentor1);
            this.description = new SimpleStringProperty(description1);
        }

        public void clearText() {
        }

        public String getTask() {
            return task.get();
        }

        public void setTask(String task1) {
            task.set(task1);
        }

        public String getDay() {
            return day.get();
        }

        public void setDay(String day1) {
            day.set(day1);
        }

        public String getTime() {
            return time.get();
        }

        public void setTime(String time1) {
            time.set(time1);
        }

        public String getDeadline() {
            return deadline.get();
        }

        public void setDeadline(String deadline1) {
            deadline.set(deadline1);
        }

        public String getMentor() {
            return mentor.get();
        }

        public void setMentor(String mentor1) {
            mentor.set(mentor1);
        }

        public String getDescription() {
            return description.get();
        }

        public void setDescription(String description1) {
            description.set(description1);
        }

    }

    private void openFile(File opensFile) {
        try {
            desktop.open(opensFile);
        } catch (IOException ex) {
            Logger.getLogger(dailyActivityList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void saveFile(File savesFile) {
        try {
            desktop.open(savesFile);
        } catch (IOException ex) {
            Logger.getLogger(dailyActivityList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
