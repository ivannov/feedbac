package com.nosoftskills.feedback.user;

import com.nosoftskills.feedback.model.Employee;
import com.nosoftskills.feedback.model.User;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 * @author Ivan St. Ivanov
 */
@SessionScoped
public class UserContext implements Serializable {

    private final Employee loggedEmployee;

    public UserContext() {
        this.loggedEmployee = new Employee("ivan", "ivan", "Ivan", "Ivanov");
    }

    public Employee getLoggedEmployee() {
        return loggedEmployee;
    }
}
