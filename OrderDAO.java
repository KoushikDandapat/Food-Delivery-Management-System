package com.fooddeliverymanagementsystem;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class OrderDAO {

    // Create (Add) a new order
    public void addOrder(Order order) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(order);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Read (Get) an order by ID
    public Order getOrderById(Long orderId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Order.class, orderId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Update an existing order
    public void updateOrder(Order order) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(order);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Delete an order
    public void deleteOrder(Long orderId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Order order = session.get(Order.class, orderId);
            if (order != null) {
                session.delete(order);
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