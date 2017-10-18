package org.sch.issecurity.iam.tools.ACMetricsManager.service;

/**
 * Created by XiChen on 10/13/2017.
 */
import org.sch.issecurity.iam.tools.ACMetricsManager.dao.AnalystDAO;
import org.sch.issecurity.iam.tools.ACMetricsManager.model.Analyst;
import org.sch.issecurity.iam.tools.ACMetricsManager.utlity.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Spring Security success handler, specialized for Ajax requests.
 */
@Component
public class RestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private AnalystDAO analystDAO;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
            throws ServletException, IOException {
        Analyst analyst = analystDAO.getAnalystByADID(authentication.getName());
        SecurityUtils.sendResponse(response, HttpServletResponse.SC_OK, analyst);
    }
}