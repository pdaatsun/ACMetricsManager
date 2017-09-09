package org.sch.issecurity.iam.tools.ACMetricsManager.model;

import javax.persistence.*;

/**
 * Created by XiChen on 9/8/2017.
 */
@Entity
@Table(name = "Analyst")
public class Analyst {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int analystID;

    @Column(name = "firstName")
    String firstName;

    @Column(name = "lastName")
    String lastName;

    @Column(name = "employeeID")
    String employeeID;

    @Column(name = "email")
    String email;

    @Column(name = "status")
    String status;

    public int getAnalystID() {
        return analystID;
    }

    public void setAnalystID(int analystID) {
        this.analystID = analystID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
