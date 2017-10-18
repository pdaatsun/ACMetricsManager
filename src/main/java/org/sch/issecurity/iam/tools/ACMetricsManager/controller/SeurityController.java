package org.sch.issecurity.iam.tools.ACMetricsManager.controller;

import org.sch.issecurity.iam.tools.ACMetricsManager.dao.AnalystDAO;
import org.sch.issecurity.iam.tools.ACMetricsManager.model.Analyst;
import org.sch.issecurity.iam.tools.ACMetricsManager.utlity.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by XiChen on 10/18/2017.
 */
@RestController
public class SeurityController {
    @Autowired
    AnalystDAO analystDAO;

    @RequestMapping(value = "/security/account", method = RequestMethod.GET)
    public @ResponseBody
    Analyst getUserAccount()  {
        Analyst analyst = analystDAO.getAnalystByADID(SecurityUtils.getCurrentLogin());
        return analyst;
    }
}
