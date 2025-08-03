package org.ordersService.dao;

import jakarta.persistence.*;

abstract class AbstractJpaDao {
    protected final EntityManagerFactory emf;
    protected final EntityManager em;

    public AbstractJpaDao() {
        this.emf = Persistence.createEntityManagerFactory("ordersDB");
        this.em = emf.createEntityManager();
    }

    public void close() {
        em.close();
        emf.close();
    }


}
