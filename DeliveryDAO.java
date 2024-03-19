package com.fooddeliverymanagementsystem;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class DeliveryDAO {

    // Create (Add) a new delivery
    public void addDelivery(Delivery delivery) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(delivery);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Read (Get) a delivery by ID
    public Delivery getDeliveryById(Long deliveryId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Delivery.class, deliveryId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Update an existing delivery
    public void updateDelivery(Delivery delivery) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(delivery);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Delete a delivery
    public void deleteDelivery(Long deliveryId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Delivery delivery = session.get(Delivery.class, deliveryId);
            if (delivery != null) {
                session.delete(delivery);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}