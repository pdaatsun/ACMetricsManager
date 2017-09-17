package org.sch.issecurity.iam.tools.ACMetricsManager.dao;

import org.sch.issecurity.iam.tools.ACMetricsManager.model.Application;

import java.util.List;

/**
 * Created by XiChen on 9/16/2017.
 */
public interface ApplicationDAO {

    Application getApplicationByID(int appID);

    List<Application> listApplication();

}
