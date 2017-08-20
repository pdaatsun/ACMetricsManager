package org.sch.issecurity.iam.tools.ACMetricsManager.dao;

import org.sch.issecurity.iam.tools.ACMetricsManager.model.ACMetrics;

import java.sql.Date;
import java.util.Calendar;
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

    boolean findACMetrics(Date tranDate, int analystID, String SNOWID, int appID, int operationID);

    boolean isACMetricsExist (ACMetrics acMetrics);
}
