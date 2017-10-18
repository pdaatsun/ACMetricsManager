package org.sch.issecurity.iam.tools.ACMetricsManager.configuration;

/**
 * Created by XiChen on 9/24/2017.
 */
import java.util.Collection;
import java.util.HashSet;

import org.sch.issecurity.iam.tools.ACMetricsManager.dao.AnalystDAO;
import org.sch.issecurity.iam.tools.ACMetricsManager.model.Analyst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.stereotype.Component;

@Component
public class CustomLdapAuthoritiesPopulator implements LdapAuthoritiesPopulator {
    @Autowired
    AnalystDAO analystDAO;

    @Override
    public Collection<? extends GrantedAuthority> getGrantedAuthorities(DirContextOperations userData, String username) {

        Collection<GrantedAuthority> gas = new HashSet<GrantedAuthority>();

        Analyst analyst = analystDAO.getAnalystByADID(username);

        if (analyst != null) {
            gas.add(new SimpleGrantedAuthority(analyst.getUserRole()));
        }
        return gas;
    }
}
