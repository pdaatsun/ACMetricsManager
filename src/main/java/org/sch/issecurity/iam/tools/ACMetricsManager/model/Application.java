package org.sch.issecurity.iam.tools.ACMetricsManager.model;

import javax.persistence.*;

/**
 * Created by XiChen on 9/8/2017.
 */
@Entity
@Table(name = "Application")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int appID;

    @Column(name = "appCode")
    String appCode;

    @Column(name = "appName")
    String appName;

    @Column(name = "appDesc")
    String appDesc;

    public int getAppID() {
        return appID;
    }

    public void setAppID(int appID) {
        this.appID = appID;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppDesc() {
        return appDesc;
    }

    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }

}
