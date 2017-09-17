package org.sch.issecurity.iam.tools.ACMetricsManager.dao;

import org.sch.issecurity.iam.tools.ACMetricsManager.model.Operation;

import java.util.List;

/**
 * Created by XiChen on 9/16/2017.
 */
public interface OperationDAO {

    Operation getOperationByID(int OperationID);

    List<Operation> listOperation();

}
