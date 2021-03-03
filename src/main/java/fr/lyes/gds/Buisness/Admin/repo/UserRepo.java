package fr.lyes.gds.Buisness.Admin.repo;

import fr.lyes.gds.Buisness.Admin.Data.Entities.Module;
import fr.lyes.gds.Buisness.Admin.Data.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

@Service
@Transactional
public class UserRepo implements Serializable {

    @PersistenceContext
    private EntityManager entityManger;

    public List<User> findLazyUsers(String code, String label, String name, int first, int max) {
        StringBuilder st = new StringBuilder();
        st.append("select module from Module module");
        if (code != null && !code.equals("")) {
            st.append(" where module.code =:code ");
        }
        if (label != null && !label.equals("")) {
            st.append("and where module.label =:label");
        }
        if (name != null && !name.equals("")) {
            st.append(" and  where module.name =:name");
        }
        Query q = entityManger.createQuery(st.toString());
        if (code != null && !code.equals("")) {
            q.setParameter("code", code);
        }
        if (label != null && !label.equals("")) {
            q.setParameter("label", label);
        }
        if (name != null && !name.equals("")) {
            q.setParameter("name", name);
        }
        q.setFirstResult(first);
        q.setMaxResults(max);
        return (List<User>) q.getResultList();
    }

    public Page<User> findPageLazyUsers(String username, String lastName, String firstName, Boolean valid, String email, int first, int max) {
        StringBuilder st = new StringBuilder();
        st.append("select user from User user ");
        if (username == null
                && lastName == null
                && firstName == null
                && valid == null &&
                email == null) {
        } else {
            st.append("where");
            if (username != null && !username.equals("")) {
                if(st.toString().endsWith("where")){
                    st.append(" user.username =:username");
                }else{
                    st.append(" and user.username =:username");
                }
            }
            if (lastName != null && !lastName.equals("")) {
                if(st.toString().endsWith("where")){
                    st.append(" user.lastName =:lastName ");
                }else{
                    st.append(" and user.lastName =:lastName ");
                }
            }
            if (firstName != null && !firstName.equals("")) {
                if(st.toString().endsWith("where")){
                    st.append(" user.firstName =:firstName ");
                }else{
                    st.append(" and user.firstName =:firstName ");
                }
            }
            if (valid != null) {
                if(st.toString().endsWith("where")){
                    st.append(" user.valid =:valid");
                }else{
                    st.append(" and user.valid =:valid ");
                }
            }
            if (email != null && !email.equals("")) {
                if(st.toString().endsWith("where")){
                    st.append(" user.email =:email");
                }else{
                    st.append(" and user.email =:email");
                }
            }
        }
        Query q = entityManger.createQuery(st.toString());
        if (username != null && !username.equals("")) {
            q.setParameter("username", username);
        }
        if (lastName != null && !lastName.equals("")) {
            q.setParameter("lastName", lastName);
        }
        if (firstName != null && !firstName.equals("")) {
            q.setParameter("firstName", firstName);
        }
        if (valid != null) {
            q.setParameter("valid", valid);
        }
        if (email != null && !email.equals("")) {
            q.setParameter("email", email);
        }
        PageRequest pageRequest = PageRequest.of(first, max);
        StringBuilder countReq = new StringBuilder();
        countReq.append("select count (user) from User user");
        Query coun = entityManger.createQuery(countReq.toString());
        long total = (long) coun.getSingleResult();
        q.setFirstResult(first * max);
        q.setMaxResults(max);
        return new PageImpl<User>(q.getResultList(), pageRequest, total);
    }

    public Page<User> findAndFilterPageLazyUsers(String username, String lastName, String firstName, Boolean valid, String email, int first, int max, String sort, String field) {
        StringBuilder st = new StringBuilder();
        st.append("select user from User user ");
        if (username == null
                && lastName == null
                && firstName == null
                && valid == null &&
                email == null) {
        } else {
            st.append("where");
            if (username != null && !username.equals("")) {
                if(st.toString().endsWith("where")){
                    st.append(" user.username =:username");
                }else{
                    st.append(" and user.username =:username");
                }
            }
            if (lastName != null && !lastName.equals("")) {
                if(st.toString().endsWith("where")){
                    st.append(" user.lastName =:lastName ");
                }else{
                    st.append(" and user.lastName =:lastName ");
                }
            }
            if (firstName != null && !firstName.equals("")) {
                if(st.toString().endsWith("where")){
                    st.append(" user.firstName =:firstName ");
                }else{
                    st.append(" and user.firstName =:firstName ");
                }
            }
            if (valid != null) {
                if(st.toString().endsWith("where")){
                    st.append(" user.valid =:valid");
                }else{
                    st.append(" and user.valid =:valid ");
                }
            }
            if (email != null && !email.equals("")) {
                if(st.toString().endsWith("where")){
                    st.append(" user.email =:email");
                }else{
                    st.append(" and user.email =:email");
                }
            }
        }
        st.append(" Order By user." + field + " " + sort);
        Query q = entityManger.createQuery(st.toString());
        if (username != null && !username.equals("")) {
            q.setParameter("username", username);
        }
        if (lastName != null && !lastName.equals("")) {
            q.setParameter("lastName", lastName);
        }
        if (firstName != null && !firstName.equals("")) {
            q.setParameter("firstName", firstName);
        }
        if (valid != null) {
            q.setParameter("valid", valid);
        }
        if (email != null && !email.equals("")) {
            q.setParameter("email", email);
        }
        StringBuilder countReq = new StringBuilder();
        countReq.append("select count (user) from User user");
        Query coun = entityManger.createQuery(countReq.toString());
        long total = (long) coun.getSingleResult();
        q.setFirstResult(first * max);
        q.setMaxResults(max);
        return new PageImpl<User>(q.getResultList(), PageRequest.of(first, max, Sort.by(Sort.Direction.DESC, field)), total);
    }
}
