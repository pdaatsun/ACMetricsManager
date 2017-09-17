package org.sch.issecurity.iam.tools.ACMetricsManager.dao;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.sch.issecurity.iam.tools.ACMetricsManager.model.Analyst;
import org.sch.issecurity.iam.tools.ACMetricsManager.model.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by XiChen on 9/16/2017.
 */
@Repository("applicationDAO")
public class ApplicationDAOImpl extends AbstractDao<Long, Application> implements ApplicationDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public ApplicationDAOImpl() {

    }

    public ApplicationDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Application getApplicationByID(int appID) {
        return getByKey(new Long(appID));
    }

    @Override
    public List<Application> listApplication() {
        Criteria criteria = createEntityCriteria();
        return (List<Application>) criteria.list();
    }
}
