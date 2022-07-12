package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import model.Student;
import util.CrudUtil;
import util.ValidationUtil;
import view.tm.StudentTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Pattern;

public class StudentFormController {
    public Label lblDate;
    public Label lblTime;
    public JFXTextField txtStudentName;
    public JFXTextField txtAddress;
    public JFXTextField txtContactNo;
    public TableView<StudentTM> tblStudent;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colNIC;
    public TableColumn colAddress;
    public TableColumn colContact;
    public TableColumn colEmail;
    public TableColumn colDelete;
    public JFXButton btnSave;
    public JFXButton btnAddNewStudent;
    public Label lblStudentID;
    public JFXTextField txtEmail;
    public JFXTextField txtNIC;

    private String StudentID;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    public void initialize(){

        colId.setCellValueFactory(new PropertyValueFactory("student_id"));
        colName.setCellValueFactory(new PropertyValueFactory("student_name"));
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));
        colContact.setCellValueFactory(new PropertyValueFactory("contact"));
        colAddress.setCellValueFactory(new PropertyValueFactory("address"));
        colNIC.setCellValueFactory(new PropertyValueFactory("nic"));
        colDelete.setCellValueFactory(new PropertyValueFactory("btn"));


        tblStudent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnSave.setText(newValue != null ? "Update Student" : "Save Student");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                txtStudentName.setText(newValue.getStudent_name());
                txtNIC.setText(newValue.getNic());
                txtAddress.setText(newValue.getAddress());
                txtContactNo.setText(newValue.getContact());
                txtEmail.setText(newValue.getEmail());

                txtStudentName.setDisable(false);
                txtNIC.setDisable(false);
                txtAddress.setDisable(false);
                txtContactNo.setDisable(false);
                txtEmail.setDisable(false);
            }
        });

        //-------------------validation pattern---------------------------------
        Pattern namePattern = Pattern.compile("^[A-z ]{3,25}$");
        Pattern NICPattern = Pattern.compile("^([0-9]{10}V)$|^([0-9]{12})$");
        Pattern addressPattern = Pattern.compile("^[A-z0-9 ,/]{4,40}$");
        Pattern contactNoPattern = Pattern.compile("^(011|070|071|072|074|075|076|077|078)[0-9]{7}$");
        Pattern emailPattern = Pattern.compile("^[a-z0-9]{5,30}(@gmail.com|@yahoo.com)$");



        map.put(txtStudentName,namePattern);
        map.put(txtNIC,NICPattern);
        map.put(txtAddress,addressPattern);
        map.put(txtContactNo,contactNoPattern);
        map.put(txtEmail,emailPattern);


        try {
            loadAllStudents();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        loadDateAndTime();

        try {
            initialUI();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadAllStudents() throws SQLException, ClassNotFoundException {
        ResultSet result= CrudUtil.execute("SELECT * FROM student");
        ObservableList<StudentTM> obList = FXCollections.observableArrayList();

        while (result.next()){
            Button btn = new Button("Delete");
            btn.setOnAction(e -> {
                         String student_id = tblStudent.getSelectionModel().getSelectedItem().getStudent_id();

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure?", ButtonType.YES, ButtonType.NO);
                        Optional<ButtonType> buttonType = alert.showAndWait();

                        if (buttonType.get().equals(ButtonType.YES)) {
                            try {
                                if (CrudUtil.execute("DELETE FROM student WHERE student_id=?", student_id)) {
                                    new Alert(Alert.AlertType.CONFIRMATION, "Student Deleted Successfully!").show();
                                    initialUI();
                                    loadAllStudents();
                                }
                            } catch (SQLException | ClassNotFoundException exception) {
                                exception.printStackTrace();
                                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
                            }

                        }
            });
            obList.add(new StudentTM(
                        result.getString("student_id"),
                        result.getString("student_name"),
                        result.getString("email"),
                        result.getString("contact"),
                        result.getString("address"),
                        result.getString("nic"),
                        btn
                        )
                     );
               }
        tblStudent.setItems(obList);
    }

    private void initialUI() throws SQLException, ClassNotFoundException {
        StudentID = generateNewStudentId();
        lblStudentID.setText("Student ID :" + StudentID);
        txtStudentName.clear();
        txtNIC.clear();
        txtAddress.clear();
        txtContactNo.clear();
        txtEmail.clear();
        txtStudentName.setDisable(true);
        txtNIC.setDisable(true);
        txtAddress.setDisable(true);
        txtContactNo.setDisable(true);
        txtEmail.setDisable(true);
        btnSave.setDisable(true);
    }

    private String generateNewStudentId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT student_id FROM student ORDER BY student_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("student_id");
            int co=id.length();
            String text = id.substring(0,2);
            String num= id.substring(2,co);
            int n=Integer.parseInt(num);
            n++;
            String numInString = Integer.toString(n);
            String GenerateId = text+numInString;

            return GenerateId;

        } else {
            return "S001";
        }

    }

    public void btnNewStudentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        StudentID = generateNewStudentId();
        lblStudentID.setText("Student ID :" + StudentID);
        txtStudentName.setDisable(false);
        txtNIC.setDisable(false);
        txtAddress.setDisable(false);
        txtContactNo.setDisable(false);
        txtEmail.setDisable(false);
        txtStudentName.clear();
        txtNIC.clear();
        txtAddress.clear();
        txtContactNo.clear();
        txtEmail.clear();
        txtStudentName.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save Student");
        tblStudent.getSelectionModel().clearSelection();
    }

    public void btnSaveStudentOnAction(ActionEvent actionEvent) {

        if (btnSave.getText().equalsIgnoreCase("Save Student")) {
            //---------------------save--------------------------------------
            try {
                if (CrudUtil.execute("INSERT INTO student VALUES (?,?,?,?,?,?)",StudentID,txtStudentName.getText(),txtEmail.getText(),txtContactNo.getText(),txtAddress.getText(),txtNIC.getText())){
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved Student Successfully!..").show();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

        }else {
            //---------------------update--------------------------------------
            Student s = new Student(
                    lblStudentID.getText(), txtStudentName.getText(), txtEmail.getText(), txtContactNo.getText(), txtAddress.getText(),txtNIC.getText()
            );
            try {
                if (CrudUtil.execute("UPDATE student SET student_name=? , email=? , contact=? , address=? , nic=? WHERE student_id=?",s.getStudent_name(),s.getEmail(),s.getContact(),s.getAddress(),s.getNic(),s.getStudent_id())){
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated Student Successfully!..").show();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }

        try {
            loadAllStudents();
            initialUI();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map,btnSave);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response =  ValidationUtil.validate(map,btnSave);;

            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();
            }
        }
    }

    private void loadDateAndTime() {
        lblDate.setText("Date :" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText("Time :" + currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond());
        }), new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
}
