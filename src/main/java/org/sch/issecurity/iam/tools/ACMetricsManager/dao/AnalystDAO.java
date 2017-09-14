package org.sch.issecurity.iam.tools.ACMetricsManager.dao;

import org.sch.issecurity.iam.tools.ACMetricsManager.model.Analyst;

import java.util.List;

/**
 * Created by XiChen on 9/9/2017.
 */
public interface AnalystDAO {

    Analyst getAnalystByID(int analystID);

    List<Analyst> listAnalyst();

}
