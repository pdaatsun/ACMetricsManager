package org.sch.issecurity.iam.tools.ACMetricsManager.dao;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.sch.issecurity.iam.tools.ACMetricsManager.model.ACMetrics;

import java.sql.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by XiChen on 7/23/2017.
 */
@Repository("acMetricsDAO")
public class ACMetricsDAOImpl extends AbstractDao<Long, ACMetrics> implements ACMetricsDAO{

    @Autowired
    private SessionFactory sessionFactory;

    public ACMetricsDAOImpl() {

    }

    public ACMetricsDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public ACMetrics getACMetricsByID(long acmID) {
        return getByKey(acmID);
    }

    @Override
    public List<ACMetrics> listACMetricssByDate(Date tranDate) {
        Criteria criteria = createEntityCriteria();
        if(tranDate!=null){
            criteria.add(Restrictions.eq("tranDate",tranDate));
        }
        //criteria.addOrder(Order.desc("SNOWID"));

        return (List<ACMetrics>) criteria.list();
    }

    @Override
    public void addACMetrics(ACMetrics acMetrics) {
        save(acMetrics);
    }

    @Override
    public void updateACMetrics(ACMetrics acMetrics) {
        update(acMetrics);
    }

    @Override
    public void deleteACMetrics(long acmID) {
        ACMetrics acMetrics = null;
        acMetrics = getACMetricsByID(acmID);

        if (acMetrics != null) delete(acMetrics);
    }

    @Override
    public boolean findACMetrics(Date tranDate, int analystID, String SNOWID, int appID, int operationID) {
        Criteria criteria = createEntityCriteria();
        if(tranDate!=null){
            criteria.add(Restrictions.eq("tranDate",tranDate));
        }
        criteria.add(Restrictions.eq("analystID",analystID));
        criteria.add(Restrictions.eq("SNOWID",SNOWID));
        criteria.add(Restrictions.eq("appID",appID));
        criteria.add(Restrictions.eq("operationID",operationID));

        ACMetrics acMetrics = null;
        acMetrics = (ACMetrics) criteria.uniqueResult();

        if (acMetrics == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean isACMetricsExist (ACMetrics acMetrics) {
        if (acMetrics == null) return false;
        return findACMetrics(acMetrics.getTranDate(), acMetrics.getAnalystID(), acMetrics.getSNOWID(), acMetrics.getAppID(), acMetrics.getOperationID());
    }
}
