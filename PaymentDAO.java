package com.fooddeliverymanagementsystem;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class PaymentDAO {

    // Create (Add) a new payment
    public void addPayment(Payment payment) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(payment);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Read (Get) a payment by ID
    public Payment getPaymentById(Long paymentId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Payment.class, paymentId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Update an existing payment
    public void updatePayment(Payment payment) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(payment);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Delete a payment
    public void deletePayment(Long paymentId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Payment payment = session.get(Payment.class, paymentId);
            if (payment != null) {
                session.delete(payment);
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