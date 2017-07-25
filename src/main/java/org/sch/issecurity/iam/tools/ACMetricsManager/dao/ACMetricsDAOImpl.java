package org.sch.issecurity.iam.tools.ACMetricsManager.dao;

import org.sch.issecurity.iam.tools.ACMetricsManager.model.ACMetrics;

import java.sql.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Created by XiChen on 7/23/2017.
 */
@Repository("acMetricsDAO")
public class ACMetricsDAOImpl extends AbstractDao<Long, ACMetrics> implements ACMetricsDAO{
    @Override
    public ACMetrics getACMetricsByID(long acmID) {
        return getByKey(acmID);
    }

    @Override
    public List<ACMetrics> getAllACMetricssByDate(Date tranDate) {
        return null;
    }

    @Override
    public void addACMetrics(ACMetrics acMetrics) {

    }

    @Override
    public void updateACMetrics(ACMetrics acMetrics) {

    }

    @Override
    public void deleteACMetrics(long acmID) {

    }

    @Override
    public boolean findACMetrics(Date tranDate, int analystID, String SNOWID, int appID, int operationID) {
        return false;
    }
}
