package fr.lyes.gds.Buisness.Admin.Repository;

import fr.lyes.gds.Buisness.Admin.Data.Entities.Menu;
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
public class MenuRepo implements Serializable {

    @PersistenceContext
    private EntityManager entityManger;

    public List<User> findLazyMenus(String label, String url, String icon, Boolean parents, int first, int max) {
        StringBuilder st = new StringBuilder();
        st.append("select menu from Menu menu");
        if (label != null && !label.equals("")) {
            st.append("where menu.label =:label");
        }
        if (url != null && !url.equals("")) {
            st.append("and where menu.url =:url ");
        }
        if (icon != null && !icon.equals("")) {
            st.append(" and  where menu.icon =:icon");
        }
        if (parents != null) {
            st.append(" and  menu.parents =:parents");
        }
        Query q = entityManger.createQuery(st.toString());
        if (label != null && !label.equals("")) {
            q.setParameter("label", label);
        }
        if (url != null && !url.equals("")) {
            q.setParameter("url", url);
        }
        if (icon != null && !icon.equals("")) {
            q.setParameter("icon", icon);
        }
        if (parents != null && !parents.equals("")) {
            q.setParameter("parents", parents);
        }
        q.setFirstResult(first);
        q.setMaxResults(max);
        return (List<User>) q.getResultList();
    }

    public Page<Menu> findPageLazyMenus(String label, String url, String icon, Boolean parents, int first, int max) {
        StringBuilder st = new StringBuilder();
        st.append("select menu from Menu menu ");
        if (label == null
                && url == null
                && icon == null
                && parents == null ) {
        } else {
            st.append("where");
            if (label != null && !label.equals("")) {
                if (st.toString().endsWith("where")) {
                    st.append(" menu.label =:label");
                } else {
                    st.append(" and menu.label =:label");
                }
            }
            if (url != null && !url.equals("")) {
                if (st.toString().endsWith("where")) {
                    st.append(" menu.url =:url ");
                } else {
                    st.append(" and menu.url =:url ");
                }
            }
            if (icon != null && !icon.equals("")) {
                if (st.toString().endsWith("where")) {
                    st.append(" menu.icon =:icon ");
                } else {
                    st.append(" and menu.icon =:icon ");
                }
            }
            if (parents != null) {
                if (st.toString().endsWith("where")) {
                    st.append(" menu.parents =:parents");
                } else {
                    st.append(" and menu.parents =:parents ");
                }
            }
        }
        Query q = entityManger.createQuery(st.toString());
        if (label != null && !label.equals("")) {
            q.setParameter("label", label);
        }
        if (url != null && !url.equals("")) {
            q.setParameter("url", url);
        }
        if (icon != null && !icon.equals("")) {
            q.setParameter("icon", icon);
        }
        if (parents != null) {
            q.setParameter("parents", parents);
        }
        PageRequest pageRequest = PageRequest.of(first, max);
        StringBuilder countReq = new StringBuilder();
        countReq.append("select count (menu) from Menu menu");
        Query coun = entityManger.createQuery(countReq.toString());
        long total = (long) coun.getSingleResult();
        q.setFirstResult(first * max);
        q.setMaxResults(max);
        return new PageImpl<Menu>(q.getResultList(), pageRequest, total);
    }

    public Page<Menu> findAndFilterPageLazyMenus(String label, String url, String icon, Boolean parents, int first, int max, String sort, String field) {
        StringBuilder st = new StringBuilder();
        st.append("select menu from Menu menu ");
        if (label == null
                && url == null
                && icon == null
                && parents == null ) {
        } else {
            st.append("where");
            if (label != null && !label.equals("")) {
                if (st.toString().endsWith("where")) {
                    st.append(" menu.label =:label");
                } else {
                    st.append(" and menu.label =:label");
                }
            }
            if (url != null && !url.equals("")) {
                if (st.toString().endsWith("where")) {
                    st.append(" menu.url =:url ");
                } else {
                    st.append(" and menu.url =:url ");
                }
            }
            if (icon != null && !icon.equals("")) {
                if (st.toString().endsWith("where")) {
                    st.append(" menu.icon =:icon ");
                } else {
                    st.append(" and menu.icon =:icon ");
                }
            }
            if (parents != null) {
                if (st.toString().endsWith("where")) {
                    st.append(" menu.parents =:parents");
                } else {
                    st.append(" and menu.parents =:parents ");
                }
            }
        }
        st.append(" Order By menu." + field + " " + sort);
        Query q = entityManger.createQuery(st.toString());
        if (label != null && !label.equals("")) {
            q.setParameter("label", label);
        }
        if (url != null && !url.equals("")) {
            q.setParameter("url", url);
        }
        if (icon != null && !icon.equals("")) {
            q.setParameter("icon", icon);
        }
        if (parents != null) {
            q.setParameter("parents", parents);
        }
        StringBuilder countReq = new StringBuilder();
        countReq.append("select count (menu) from Menu menu");
        Query coun = entityManger.createQuery(countReq.toString());
        long total = (long) coun.getSingleResult();
        q.setFirstResult(first * max);
        q.setMaxResults(max);
        return new PageImpl<Menu>(q.getResultList(), PageRequest.of(first, max, Sort.by(Sort.Direction.DESC, field)), total);
    }
}
