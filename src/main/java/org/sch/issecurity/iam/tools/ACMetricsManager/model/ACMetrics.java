package org.sch.issecurity.iam.tools.ACMetricsManager.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by XiChen on 7/17/2017.
 */
@Entity
@Table(name="ACMetrics")
public class ACMetrics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long acmID;

    @Column(name="tranDate")
    Date tranDate;

    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "analystID")
    Analyst analyst;

    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "appID")
    Application application;

    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "operationID")
    Operation operation;

    @Column(name="SNOWID")
    String SNOWID;

    @Column(name="numOfUsers")
    int numOfUsers;

    @Column(name="uploadDate")
    Date uploadDate;

    public long getAcmID() {
        return acmID;
    }

    public void setAcmID(long acmID) {
        this.acmID = acmID;
    }

    public Date getTranDate() {
        return tranDate;
    }

    public void setTranDate(Date tranDate) {
        this.tranDate = tranDate;
    }

    public Analyst getAnalyst() {
        return analyst;
    }

    public void setAnalyst(Analyst analyst) {
        this.analyst = analyst;
    }

    public String getSNOWID() {
        return SNOWID;
    }

    public void setSNOWID(String SNOWID) {
        this.SNOWID = SNOWID;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public int getNumOfUsers() {
        return numOfUsers;
    }

    public void setNumOfUsers(int numOfUsers) {
        this.numOfUsers = numOfUsers;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    @Override
    public String toString() {
        return "ACMetrics{" +
                "acmID=" + acmID +
                ", tranDate=" + tranDate +
                ", analyst=" + analyst.getFirstName() + " " + analyst.getLastName() +
                ", SNOWID='" + SNOWID + '\'' +
                ", application=" + application.getAppCode() +
                ", operation=" + operation.getOperationType() +
                ", numOfUsers=" + numOfUsers +
                ", uploadDate=" + uploadDate +
                '}';
    }
}
