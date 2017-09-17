package org.sch.issecurity.iam.tools.ACMetricsManager.dao;

import org.sch.issecurity.iam.tools.ACMetricsManager.model.ACMetrics;
import org.sch.issecurity.iam.tools.ACMetricsManager.model.Analyst;
import org.sch.issecurity.iam.tools.ACMetricsManager.model.Application;
import org.sch.issecurity.iam.tools.ACMetricsManager.model.Operation;

import java.sql.Date;
import java.util.List;

/**
 * Created by XiChen on 7/20/2017.
 */
public interface ACMetricsDAO {

    ACMetrics getACMetricsByID(long acmID);

    List<ACMetrics> listACMetricssByDate(Date tranDate);

    void addACMetrics(ACMetrics acMetrics);

    void updateACMetrics(ACMetrics acMetrics);

    void deleteACMetrics(long acmID);

    boolean findACMetrics(Date tranDate, Analyst analyst, String SNOWID, Application application, Operation operation);

    boolean isACMetricsExist (ACMetrics acMetrics);
}
