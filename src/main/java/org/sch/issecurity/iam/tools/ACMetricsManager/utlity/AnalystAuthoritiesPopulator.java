package org.sch.issecurity.iam.tools.ACMetricsManager.utlity;

import org.sch.issecurity.iam.tools.ACMetricsManager.dao.AnalystDAO;
import org.sch.issecurity.iam.tools.ACMetricsManager.model.Analyst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by XiChen on 10/8/2017.
 */
@Component
public class AnalystAuthoritiesPopulator implements LdapAuthoritiesPopulator {

    @Autowired
    private AnalystDAO analystDAO;

    @Override
    public Collection<GrantedAuthority> getGrantedAuthorities(DirContextOperations userData, String username) {
        ArrayList<GrantedAuthority> analystRoles = new ArrayList<GrantedAuthority>();

        Analyst analyst = analystDAO.getAnalystByADID(username);
        if (analyst == null) return analystRoles;

        String roleString = analyst.getUserRole();
        if ((roleString == null) || (roleString.length() == 0)) return analystRoles;

        analystRoles.add(new SimpleGrantedAuthority(roleString));
        return analystRoles;
    }
}
