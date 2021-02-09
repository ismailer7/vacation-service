package org.vacation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LdapDirectoryService {

    @Autowired
    private LdapTemplate ldapTemplate;

    public void testLdapTemplate() {
        /**
         * TODO just for test purpose.
         */
    }

    public String getDnForUser(String uid) {
        List<String> result = ldapTemplate.search(
                LdapQueryBuilder.query().where("uid").is(uid),
                new AbstractContextMapper<String>() {
                    protected String doMapFromContext(DirContextOperations ctx) {
                        System.out.println("######## NameInNamespace -->"+ctx.getNameInNamespace());
                        return ctx.getNameInNamespace();
                    }
                });

        if(result.size() != 1) {
            throw new RuntimeException("User not found or not unique");
        }

        return result.get(0);
    }

    public List<String> getUserRoleFromLdif(String uid) {
        // return list of roles - for now i only have one user.
        /**
         * TODO will generate to retrieve the user when we will have many users and different groups.
         */
        List<String> list = ldapTemplate.search(
                LdapQueryBuilder.query().where("objectclass").is("groupOfUniqueNames"),
                (AttributesMapper<String>) attrs -> (String) attrs.get("cn").get());
        return list;
    }

    public List<String> testSearch() {
        List<String> list = ldapTemplate.search(
                LdapQueryBuilder.query().where("objectclass").is("groupOfUniqueNames"),
                (AttributesMapper<String>) attrs -> (String) attrs.get("cn").get());
        return list;
    }



}
