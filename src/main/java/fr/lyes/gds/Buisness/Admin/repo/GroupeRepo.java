package fr.lyes.gds.Buisness.Admin.repo;

import fr.lyes.gds.Buisness.Admin.Data.Entities.Groupe;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Menu;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Module;
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
public class GroupeRepo implements Serializable {

    @PersistenceContext
    private EntityManager entityManger;


    public Page<Groupe> findPageLazyGroupe(String code, String label, Boolean active, int first, int max) {
        StringBuilder st = new StringBuilder();
        st.append("select groupe from Groupe groupe");
        if (code == null
                && label == null
                && active == null) {
            System.out.println("all param are null");
        } else {
            st.append("where");
            if (code != null && !code.equals("")) {
                if (st.toString().endsWith("where")) {
                    st.append(" groupe.code =:code");
                } else {
                    st.append(" and groupe.code =:code");
                }
            }
            if (label != null && !label.equals("")) {
                if (st.toString().endsWith("where")) {
                    st.append(" groupe.label =:label ");
                } else {
                    st.append(" and groupe.label =:label ");
                }
            }
            if (active != null) {
                if (st.toString().endsWith("where")) {
                    st.append(" groupe.active =:active");
                } else {
                    st.append(" and groupe.active =:active ");
                }
            }
        }
        Query q = entityManger.createQuery(st.toString());
        if (code != null && !code.equals("")) {
            q.setParameter("code", code);
        }
        if (label != null && !label.equals("")) {
            q.setParameter("label", label);
        }
        if (active != null && !active.equals("")) {
            q.setParameter("active", active);
        }
        PageRequest pageRequest = PageRequest.of(first, max);
        StringBuilder countReq = new StringBuilder();
        countReq.append("select count (groupe) from Groupe groupe");
        Query coun = entityManger.createQuery(countReq.toString());
        long total = (long) coun.getSingleResult();
        q.setFirstResult(first * max);
        q.setMaxResults(max);
        return new PageImpl<Groupe>(q.getResultList(), pageRequest, total);
    }

    public Page<Groupe> findAndFilterPageLazyGroupes(String code, String label, Boolean active, int first, int max, String sort, String field) {
        StringBuilder st = new StringBuilder();
        st.append("select groupe from Groupe groupe");
        if (code == null
                && label == null
                && active == null) {
        } else {
            st.append("where");
            if (code != null && !code.equals("")) {
                if (st.toString().endsWith("where")) {
                    st.append(" groupe.code =:code");
                } else {
                    st.append(" and groupe.code =:code");
                }
            }
            if (label != null && !label.equals("")) {
                if (st.toString().endsWith("where")) {
                    st.append(" groupe.label =:label ");
                } else {
                    st.append(" and groupe.label =:label ");
                }
            }
            if (active != null) {
                if (st.toString().endsWith("where")) {
                    st.append(" groupe.active =:active");
                } else {
                    st.append(" and groupe.active =:active ");
                }
            }
        }
        st.append(" Order By groupe." + field + " " + sort);
        Query q = entityManger.createQuery(st.toString());
        if (code != null && !code.equals("")) {
            q.setParameter("code", code);
        }
        if (label != null && !label.equals("")) {
            q.setParameter("label", label);
        }
        if (active != null && !active.equals("")) {
            q.setParameter("active", active);
        }
        StringBuilder countReq = new StringBuilder();
        countReq.append("select count (groupe) from Groupe groupe");
        Query coun = entityManger.createQuery(countReq.toString());
        long total = (long) coun.getSingleResult();
        q.setFirstResult(first * max);
        q.setMaxResults(max);
        System.out.println("sort" + sort);
        return new PageImpl<Groupe>(q.getResultList(), PageRequest.of(first, max, Sort.by(Sort.Direction.DESC, field)), total);
    }

    public void editGroupe(Groupe groupe, Menu menu) {
        groupe.getMenuGroupe().add(menu);
        menu.getMenuGroupeList().add(groupe);
        //entityManger.merge(groupe);
        //entityManger.merge(menu);
       // entityManger.flush();
    }
}
