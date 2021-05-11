package fr.lyes.gds.Buisness.Admin.Repository;

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
public class ModuleRepo implements Serializable {

    @PersistenceContext
    private EntityManager entityManger;

    public List<Module> findLazyModules(String code, String label, String name, int first, int max){
        StringBuilder st = new StringBuilder();
        st.append("select module from Module module");
        if(code != null && !code.equals("")){
            st.append(" where module.code =:code ");
        }
        if(label != null && !label.equals("")){
            st.append("and where module.label =:label");
        }
        if(name != null && !name.equals("")){
            st.append(" and  where module.name =:name");
        }
        Query q = entityManger.createQuery(st.toString());
        if(code != null && !code.equals("")){
            q.setParameter("code",code);
        }
        if(label != null && !label.equals("")){
            q.setParameter("label",label);
        }
        if(name != null && !name.equals("")){
            q.setParameter("name",name);
        }
        q.setFirstResult(first);
        q.setMaxResults(max);
        return (List<Module>) q.getResultList();
    }

    public Page<Module> findPageLazyModules(String code, String label, String name, int first, int max){
        StringBuilder st = new StringBuilder();
        st.append("select module from Module module");
        if(code != null && !code.equals("")){
            st.append(" where module.code =:code ");
        }
        if(label != null && !label.equals("")){
            st.append("and where module.label =:label");
        }
        if(name != null && !name.equals("")){
            st.append(" and  where module.name =:name");
        }
        Query q = entityManger.createQuery(st.toString());
        if(code != null && !code.equals("")){
            q.setParameter("code",code);
        }
        if(label != null && !label.equals("")){
            q.setParameter("label",label);
        }
        if(name != null && !name.equals("")){
            q.setParameter("name",name);
        }
        PageRequest pageRequest = PageRequest.of(first, max);
        StringBuilder countReq = new StringBuilder();
        countReq.append("select count (module) from Module module");
        Query coun = entityManger.createQuery(countReq.toString());
        long total = (long)coun.getSingleResult();
        q.setFirstResult(first*max);
        q.setMaxResults(max);
        return  new PageImpl<Module>( q.getResultList(), pageRequest,  total);
    }
    public Page<Module> findAndFilterPageLazyModules(String code, String label, String name, int first, int max , String sort,String field){
        StringBuilder st = new StringBuilder();
        st.append("select module from Module module");
        if(code != null && !code.equals("")){
            st.append(" where module.code =:code ");
        }
        if(label != null && !label.equals("")){
            st.append("and where module.label =:label");
        }
        if(name != null && !name.equals("")){
            st.append(" and  where module.name =:name");
        }
        st.append(" Order By module."+field+" "+sort);
        Query q = entityManger.createQuery(st.toString());
        if(code != null && !code.equals("")){
            q.setParameter("code",code);
        }
        if(label != null && !label.equals("")){
            q.setParameter("label",label);
        }
        if(name != null && !name.equals("")){
            q.setParameter("name",name);
        }
  StringBuilder countReq = new StringBuilder();
        countReq.append("select count (module) from Module module");
        Query coun = entityManger.createQuery(countReq.toString());
        long total = (long)coun.getSingleResult();
        q.setFirstResult(first*max);
        q.setMaxResults(max);
        return  new PageImpl<Module>( q.getResultList(), PageRequest.of(first, max,Sort.by(Sort.Direction.DESC, field)),  total);
    }
}
