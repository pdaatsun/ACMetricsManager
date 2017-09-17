package org.sch.issecurity.iam.tools.ACMetricsManager.dao;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.sch.issecurity.iam.tools.ACMetricsManager.model.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by XiChen on 9/16/2017.
 */
@Repository("operationDAO")
public class OperationDAOImpl extends AbstractDao<Long, Operation> implements OperationDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public OperationDAOImpl() {

    }

    public OperationDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Operation getOperationByID(int OperationID) {
        return getByKey(new Long(OperationID));
    }

    @Override
    public List<Operation> listOperation() {
        Criteria criteria = createEntityCriteria();
        return (List<Operation>) criteria.list();
    }
}
