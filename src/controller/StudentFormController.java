package controller;

import com.jfoenix.controls.JFXButton;
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
    public JFXButton btnAddStudent;

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
//        Student stu = new Student(
//                txtStudentID.getText(),txtStudentName.getText(), txtEmail.getText(),txtContact.getText(),txtAddress.getText(),txtNIC.getText()
//        );
//
//        try {
//            if (CrudUtil.execute("INSERT INTO Student VALUES (?,?,?,?,?,?)",stu.getId(),stu.getName(),stu.getEmail(),stu.getContact(),stu.getAddress(),stu.getNic())){
//                new Alert(Alert.AlertType.CONFIRMATION, "Saved Student!..").show();
//            }
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
//        }
//
//        loadAllStudent();

        if (btnAddStudent.getText().equals("Add Student")) {
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
        }else{
            Student student = new Student(txtStudentID.getText(),txtStudentName.getText(),txtEmail.getText(),txtContact.getText(),txtAddress.getText(),txtNIC.getText());

            boolean isUpdated = CrudUtil.execute("UPDATE Student SET student_name = ?, email = ?, contact = ? , address = ? , nic = ?  WHERE student_id = ? ",student.getName(),student.getEmail(),student.getContact(),student.getAddress(),student.getNic(),student.getId());

            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"Updated !...").show();
                btnAddStudent.setText("Add Student");
                txtStudentID.setEditable(true);
                loadAllStudent();
                //clearText();
            }else {
                new Alert(Alert.AlertType.ERROR,"Something Wrong").show();
            }
        }
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

        Student selectedItem = tblStudent.getSelectionModel().getSelectedItem();

        txtStudentID.setText(selectedItem.getId());
        txtStudentName.setText(selectedItem.getName());
        txtEmail.setText(selectedItem.getEmail());
        txtContact.setText(selectedItem.getContact());
        txtAddress.setText(selectedItem.getAddress());
        txtNIC.setText(selectedItem.getNic());

        btnAddStudent.setText("Update");

    }

    public void menuDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        Student selectedItem = tblStudent.getSelectionModel().getSelectedItem();

        if (CrudUtil.execute("DELETE FROM Student WHERE student_id = ?",selectedItem.getId())){
            new Alert(Alert.AlertType.CONFIRMATION,"Deleted.").show();
            loadAllStudent();
        }else{
            new Alert(Alert.AlertType.WARNING,"Try Again!").show();
        }    }

}
