package org.ordersService.dao;

import jakarta.persistence.EntityTransaction;
import org.ordersService.entity.Client;

import java.util.List;
import java.util.Optional;

public class ClientDAO extends JPA{

    public ClientDAO() {
    }

    public Client saveClient(Client client){
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(client);
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
        }
        return client;
    }

    public Optional<Client> getClientById(Long id){
        return Optional.ofNullable(em.find(Client.class, id));
    }

    public List<Client> getAllClients(){
        return em.createQuery("select x from Client x", Client.class).getResultList();
    }

    public boolean deleteClientById(Long id){
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Client client = em.find(Client.class, id);
            em.remove(client);
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            return false;
        }
        return true;
    }
}
