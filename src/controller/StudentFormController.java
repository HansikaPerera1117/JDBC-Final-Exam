package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import view.tm.StudentTM;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

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

    public void initialize(){
        loadDateAndTime();
        initialUI();
    }

    private void initialUI() {
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

    private String generateNewStudentId() {
        return null;
    }

    public void btnNewStudentOnAction(ActionEvent actionEvent) {
    }

    public void btnSaveStudentOnAction(ActionEvent actionEvent) {
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {

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
