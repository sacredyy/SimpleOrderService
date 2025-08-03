package org.ordersService.dao;

import jakarta.persistence.*;
import org.ordersService.entity.Order;

import java.util.*;

public class OrderDAO extends AbstractJpaDao {
    public Order saveOrder(Order order) {
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(order);
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
        }
        return order;
    }

    public Optional<Order> getOrderById(Long id) {
        return Optional.ofNullable(em.find(Order.class, id));
    }

    public boolean deleteOrder(Long id) {
        Order order = em.find(Order.class, id);
        EntityTransaction tx = em.getTransaction();
        try {
            if (order != null) {
                tx.begin();
                em.remove(order);
                tx.commit();
            }
        }  catch (Exception ex) {
            tx.rollback();
            return false;
        }
        return true;
    }

    public List<Order> getAllOrders() {
        return em.createQuery("SELECT o FROM Order o", Order.class).getResultList();
    }

    public List<Order> getAllOrdersByClientId(Long id) {
        return em.createQuery("SELECT o FROM Order o WHERE o.client.id = :clientId", Order.class)
                .setParameter("clientId", id)
                .getResultList();
    }
}
