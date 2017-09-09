package org.sch.issecurity.iam.tools.ACMetricsManager.model;

import javax.persistence.*;

/**
 * Created by XiChen on 9/8/2017.
 */
@Entity
@Table(name = "Operation")
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int operationID;

    @Column(name = "operationType")
    String operationType;

    @Column(name = "operationDesc")
    String operationDesc;

    public int getOperationID() {
        return operationID;
    }

    public void setOperationID(int operationID) {
        this.operationID = operationID;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getOperationDesc() {
        return operationDesc;
    }

    public void setOperationDesc(String operationDesc) {
        this.operationDesc = operationDesc;
    }
}

