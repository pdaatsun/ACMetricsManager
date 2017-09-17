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
import org.sch.issecurity.iam.tools.ACMetricsManager.model.Analyst;
import org.sch.issecurity.iam.tools.ACMetricsManager.model.Application;
import org.sch.issecurity.iam.tools.ACMetricsManager.model.Operation;
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
    public boolean findACMetrics(Date tranDate, Analyst analyst, String SNOWID, Application application, Operation operation) {
        Criteria criteria = createEntityCriteria();
        if(tranDate!=null){
            criteria.add(Restrictions.eq("tranDate",tranDate));
        }
        criteria.add(Restrictions.eq("analyst", analyst));
        criteria.add(Restrictions.eq("SNOWID",SNOWID));
        criteria.add(Restrictions.eq("application",application));
        criteria.add(Restrictions.eq("operation",operation));

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
        return findACMetrics(acMetrics.getTranDate(), acMetrics.getAnalyst(), acMetrics.getSNOWID(), acMetrics.getApplication(), acMetrics.getOperation());
    }
}
