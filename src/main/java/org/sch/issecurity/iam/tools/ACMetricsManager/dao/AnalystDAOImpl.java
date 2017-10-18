package org.sch.issecurity.iam.tools.ACMetricsManager.dao;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.sch.issecurity.iam.tools.ACMetricsManager.model.ACMetrics;
import org.sch.issecurity.iam.tools.ACMetricsManager.model.Analyst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

/**
 * Created by XiChen on 9/9/2017.
 */
@Repository("analystDAO")
public class AnalystDAOImpl extends AbstractDao<Long, Analyst> implements AnalystDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public AnalystDAOImpl() {

    }

    public AnalystDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Analyst getAnalystByID(int analystID) {
        return getByKey(new Long(analystID));
    }

    @Override
    public Analyst getAnalystByADID(String adID) {

        Analyst foundAnalyst = null;
        if ((adID != null) && (adID.length() > 0)) {
            Criteria criteria = createEntityCriteria();
            criteria.add(Restrictions.eq("adID", adID).ignoreCase());
            foundAnalyst = (Analyst) criteria.uniqueResult();
        }

        return foundAnalyst;
    }

    @Override
    public List<Analyst> listAnalyst() {
        Criteria criteria = createEntityCriteria();
        return (List<Analyst>) criteria.list();
    }
}
