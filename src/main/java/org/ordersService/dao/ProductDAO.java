package org.ordersService.dao;

import jakarta.persistence.EntityTransaction;
import org.ordersService.entity.Product;

import java.util.*;

public class ProductDAO extends AbstractJpaDao {

    public boolean save(Product product) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(product);
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            return false;
        }
        return true;
    }

    public List<Product> getAll() {
        return em.createQuery("SELECT x FROM Product x", Product.class).getResultList();
    }

    public Optional<Product> getById(Long id) {
        return Optional.ofNullable(em.find(Product.class, id));
    }
}
