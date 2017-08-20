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

    @Column(name="analystID")
    int analystID;

    @Column(name="SNOWID")
    String SNOWID;

    @Column(name="appID")
    int appID;

    @Column(name="operationID")
    int operationID;

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

    public int getAnalystID() {
        return analystID;
    }

    public void setAnalystID(int analystID) {
        this.analystID = analystID;
    }

    public String getSNOWID() {
        return SNOWID;
    }

    public void setSNOWID(String SNOWID) {
        this.SNOWID = SNOWID;
    }

    public int getAppID() {
        return appID;
    }

    public void setAppID(int appID) {
        this.appID = appID;
    }

    public int getOperationID() {
        return operationID;
    }

    public void setOperationID(int operationID) {
        this.operationID = operationID;
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
                ", analystID=" + analystID +
                ", SNOWID='" + SNOWID + '\'' +
                ", appID=" + appID +
                ", operationID=" + operationID +
                ", numOfUsers=" + numOfUsers +
                ", uploadDate=" + uploadDate +
                '}';
    }
}
