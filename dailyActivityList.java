
import javafx.application.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.event.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Application.launch;
import javafx.print.PrinterJob;
import javafx.scene.paint.Color;

public class dailyActivityList extends Application {

    private final TableView<Task> tableList = new TableView<Task>();
    private final ObservableList<Task> data = FXCollections.observableArrayList();// FXCollection is javafx collection

    Label response;//  response is set the new value by calling setText()

    private Desktop desktop = Desktop.getDesktop();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        //To create: Add check box next to each task you add, delete and edit buttons, add a week and month view, and a menu on top so you can save different copies. 
        VBox vBox1 = new VBox();
        Scene scene1 = new Scene(vBox1);
        vBox1.setStyle("-fx-background-color:#777799");

        vBox1.setSpacing(30);

        response = new Label("Menu");

        MenuBar menuBar = new MenuBar();                           // declare menu bar object

        Menu fileMenu = new Menu("File");
        MenuItem open = new MenuItem("Open");
        MenuItem save = new MenuItem("Save As");

        MenuItem exit = new MenuItem("Exit");
        SeparatorMenuItem separator = new SeparatorMenuItem();      //  we declare sepratorMenuItem for separate the every menu items

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

        final FileChooser fileChooser = new FileChooser();           // to create a object for file

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

        save.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                File savesFile = fileChooser.showSaveDialog(primaryStage);
                if (savesFile != null) {
                    saveFile(savesFile);
                }
            }

        });

        Label label = new Label("Daily Actvity List");
        label.setStyle("-fx-font-color:white");
        label.setFont(new Font("italic", 30));
        label.setPadding(new Insets(10, 10, 10, 10));

        // we create a object for every content  to arrage the table 
        TableColumn taskCol = new TableColumn("Task");
        TableColumn dayCol = new TableColumn("Day");
        TableColumn timeCol = new TableColumn("Time");
        TableColumn deadlineCol = new TableColumn("Deadline");
        TableColumn mentorCol = new TableColumn("Faculty");
        TableColumn descriptionCol = new TableColumn("Task Description");

        // setCellValueFactory is used to associate a column in a TableView with a property of a class that contains the data to be displayed in the TableView's column
        taskCol.setCellValueFactory(new PropertyValueFactory<Task, String>("Task"));
        taskCol.setCellFactory(TextFieldTableCell.forTableColumn());
        taskCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Task, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Task, String> t) {
                ((Task) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setToDo(t.getNewValue());
            }
        }
        );

        dayCol.setCellValueFactory(
                new PropertyValueFactory<Task, String>("Day"));
        dayCol.setCellFactory(TextFieldTableCell.forTableColumn());
        dayCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Task, String>>() {
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

        taskCol.setMinWidth(200);
        dayCol.setMinWidth(70);
        timeCol.setMinWidth(60);
        deadlineCol.setMinWidth(60);
        mentorCol.setMinWidth(120);
        descriptionCol.setMinWidth(200);

        HBox rowBox = new HBox();
        rowBox.setSpacing(7);
        rowBox.setPadding(new Insets(10, 10, 10, 10));

        // Create text fields so the user can enter new tasks into the table.
        TextField addTask = new TextField();
        DatePicker datePicker = new DatePicker();
        TextField addDay = new TextField();
        TextField addTime = new TextField();
        TextField addDeadline = new TextField();
        TextField addMentor = new TextField();
        TextField addDescription = new TextField();

        // Set initial text in fields.
//        addTask.setText("Enter task");
//        addDay.setText("Enter day");
//        addTime.setText("Time");
//        addDeadline.setText("Deadline");
//        addMentor.setText("Give your faculty name");
//        addDescription.setText("add descriptions");

        addTask.setPrefWidth(60);
        addDay.setPrefWidth(60);
        addTime.setPrefWidth(65);
        addDeadline.setPrefWidth(65);
        addMentor.setPrefWidth(60);
        addDescription.setPrefWidth(100);
       addDescription.setPrefHeight(100);

        Button btnAdd = new Button("Add Task");
         btnAdd.setPrefWidth(100);
         btnAdd.setStyle("-fx-background-color:orange");
        btnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                data.add(new Task(
                        addTask.getText(),
                        addDay.getText(),
                        addTime.getText(),
                        addDeadline.getText(),
                        addMentor.getText(),
                        addDescription.getText()));

                addTask.clear();
                addDay.clear();
                addTime.clear();
                addDeadline.clear();
                addMentor.clear();
                addDescription.clear();

            }
        });

        // to take a data from user and add these data into the table 
        rowBox.getChildren().addAll(addTask, addDay, addTime, addDeadline, addMentor, addDescription, btnAdd);

        vBox1.getChildren().addAll(menuBar, label, tableList, rowBox);  //  all items is added  according to row

        primaryStage.setWidth(750);
        primaryStage.setHeight(500);
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

        private void setToDo(String newValue) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }

    public void openFile(File opensFile) {
        try {
            desktop.open(opensFile);
        } catch (IOException ex) {
            Logger.getLogger(
                    dailyActivityList.class.getName()).log(
                    Level.SEVERE, null, ex
            );
        }
    }

    public void saveFile(File savesFile) {
        try {
            desktop.open(savesFile);
        } catch (IOException ex) {
            Logger.getLogger(
                    dailyActivityList.class.getName()).log(
                    Level.SEVERE, null, ex
            );
        }
    }
}

