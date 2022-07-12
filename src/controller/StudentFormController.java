package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Student;
import util.CrudUtil;
import views.tm.StudentTM;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentFormController {
    public JFXTextField txtStudentID;
    public JFXTextField txtStudentName;
    public JFXTextField txtEmail;
    public JFXTextField txtContact;
    public JFXTextField txtAddress;
    public JFXTextField txtNIC;
    public TableView<StudentTM> tblStudent;
    public TableColumn colStudentID;
    public TableColumn colStudentName;
    public TableColumn colEmail;
    public TableColumn colContact;
    public TableColumn colAddress;
    public TableColumn colNIC;


    public void btnAddStudentOnAction(ActionEvent actionEvent) {
        Student stu = new Student(
                txtStudentID.getText(),txtStudentName.getText(), txtEmail.getText(),txtContact.getText(),txtAddress.getText(),txtNIC.getText()
        );

        try {
            if (CrudUtil.execute("INSERT INTO Student VALUES (?,?,?,?,?,?)",stu.getId(),stu.getName(),stu.getEmail(),stu.getContact(),stu.getAddress(),stu.getNic())){
                new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    public void menuUpdateOnAction(ActionEvent actionEvent) {
    }

    public void menuDeleteOnAction(ActionEvent actionEvent) {
    }
}
