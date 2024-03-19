package com.fooddeliverymanagementsystem;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class OrderItemDAO {

    // Create (Add) a new order item
    public void addOrderItem(OrderItem orderItem) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(orderItem);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Read (Get) an order item by ID
    public OrderItem getOrderItemById(Long orderItemId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(OrderItem.class, orderItemId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Update an existing order item
    public void updateOrderItem(OrderItem orderItem) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(orderItem);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Delete an order item
    public void deleteOrderItem(Long orderItemId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            OrderItem orderItem = session.get(OrderItem.class, orderItemId);
            if (orderItem != null) {
                session.delete(orderItem);
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
