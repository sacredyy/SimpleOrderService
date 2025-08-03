package org.ordersService.dao;

import jakarta.persistence.*;

import java.util.concurrent.Callable;

abstract class JPA {
    protected final EntityManagerFactory emf;
    protected final EntityManager em;

    public JPA() {
        this.emf = Persistence.createEntityManagerFactory("ordersDB");
        this.em = emf.createEntityManager();
    }

    public void close() {
        em.close();
        emf.close();
    }


}
