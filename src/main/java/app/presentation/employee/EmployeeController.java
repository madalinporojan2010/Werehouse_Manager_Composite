package app.presentation.employee;

import app.presentation.Observer;
import app.presentation.controller.MainController;

import java.awt.event.ActionListener;
import java.util.List;

public class EmployeeController {
    private Employee employee = new Employee();
    private MainController mainController;

    public Employee getEmployee() {
        return employee;
    }

    public EmployeeController(MainController mainController) {
        this.mainController = mainController;
        setEmployeeUIProprieties();
    }

    private void setEmployeeUIProprieties() {
        setEmployeeActionListeners();
    }

    private void setEmployeeActionListeners(){
        ActionListener closeButtonListener = e -> {
            employee.getFrame().dispose();
        };
        employee.getCloseButton().addActionListener(closeButtonListener);
    }

}
