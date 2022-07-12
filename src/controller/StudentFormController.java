package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;

public class StudentFormController {
    public Label lblDate;
    public Label lblTime;
    public JFXTextField txtStudentName;
    public JFXTextField txtAddress;
    public JFXTextField txtContactNo;
    public TableView tblStudent;
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

    public void btnNewStudentOnAction(ActionEvent actionEvent) {
    }

    public void btnSaveStudentOnAction(ActionEvent actionEvent) {
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        
    }
}
