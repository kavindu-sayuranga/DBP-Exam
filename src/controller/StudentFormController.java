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
    public TableView<Student> tblStudent;
    public TableColumn colStudentID;
    public TableColumn colStudentName;
    public TableColumn colEmail;
    public TableColumn colContact;
    public TableColumn colAddress;
    public TableColumn colNIC;

    public void initialize(){

        colStudentID.setCellValueFactory(new PropertyValueFactory("id"));
        colStudentName.setCellValueFactory(new PropertyValueFactory("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));
        colContact.setCellValueFactory(new PropertyValueFactory("contact"));
        colAddress.setCellValueFactory(new PropertyValueFactory("address"));
        colNIC.setCellValueFactory(new PropertyValueFactory("nic"));


        try {
            loadAllStudent();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnAddStudentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Student stu = new Student(
                txtStudentID.getText(),txtStudentName.getText(), txtEmail.getText(),txtContact.getText(),txtAddress.getText(),txtNIC.getText()
        );

        try {
            if (CrudUtil.execute("INSERT INTO Student VALUES (?,?,?,?,?,?)",stu.getId(),stu.getName(),stu.getEmail(),stu.getContact(),stu.getAddress(),stu.getNic())){
                new Alert(Alert.AlertType.CONFIRMATION, "Saved Student!..").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        loadAllStudent();
    }

    private void loadAllStudent() throws ClassNotFoundException, SQLException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Student");
        ObservableList<Student> obList = FXCollections.observableArrayList();

        while (result.next()){
            obList.add(
                    new Student(
                            result.getString("student_id"),
                            result.getString("student_name"),
                            result.getString("email"),
                            result.getString("contact"),
                            result.getString("address"),
                            result.getString("nic")

                            )
            );
        }
        tblStudent.setItems(obList);

    }

    public void menuUpdateOnAction(ActionEvent actionEvent) {
        
    }

    public void menuDeleteOnAction(ActionEvent actionEvent) {
    }
}
