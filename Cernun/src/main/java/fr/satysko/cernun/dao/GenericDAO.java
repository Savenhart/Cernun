package fr.satysko.cernun.dao;


import fr.satysko.cernun.models.Entite;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


public class GenericDAO<T extends Entite> {
    EntityManagerFactory emf;
    EntityManager em;
    Class<T> clazz;

    public GenericDAO(String persistenceUnitName, Class<T> clazz) {
        emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        em = emf.createEntityManager();
        this.clazz = clazz;
    }

    public void close(){
        em.close();
        emf.close();
    }

    public void saveAll(Set<T> entities){
        EntityTransaction transac = em.getTransaction();

        try {
            transac.begin();

            for(T entite : entities){
                if(entite.getId() != 0){
                    em.merge(entite);
                }else{
                    em.persist(entite);
                }
            }

            transac.commit();
        }catch (Exception e){
            e.printStackTrace();
            transac.rollback();
        }
    }

    public void saveOrUpdate(T entitie){
        EntityTransaction transac = em.getTransaction();

        try{
            transac.begin();

            if(entitie.getId() != 0){
                em.merge(entitie);
            }else{
                em.persist(entitie);
            }

            transac.commit();
        }catch(Exception e){
            e.printStackTrace();
            transac.rollback();
        }
    }

    public Set<T> findAll(){
        TypedQuery<T> query = em.createQuery("SELECT entite FROM " + clazz.getName() + " entite", clazz);
        return new HashSet<>(query.getResultList());
    }

    public T findById(int id){
        return em.find(clazz, id);
    }

    public void deleteById(int id){
        EntityTransaction transac = em.getTransaction();

        try{
            transac.begin();

            T obj = findById(id);
            if(obj != null) {
                em.remove(obj);
            }

            transac.commit();
        }catch (Exception e){
            e.printStackTrace();
            transac.rollback();
        }
    }

}
