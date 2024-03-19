package com.fooddeliverymanagementsystem;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class DeliveryDriverDAO {

    // Create (Add) a new delivery driver
    public void addDeliveryDriver(DeliveryDriver deliveryDriver) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(deliveryDriver);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Read (Get) a delivery driver by ID
    public DeliveryDriver getDeliveryDriverById(Long driverId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(DeliveryDriver.class, driverId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Update an existing delivery driver
    public void updateDeliveryDriver(DeliveryDriver deliveryDriver) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(deliveryDriver);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Delete a delivery driver
    public void deleteDeliveryDriver(Long driverId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            DeliveryDriver deliveryDriver = session.get(DeliveryDriver.class, driverId);
            if (deliveryDriver != null) {
                session.delete(deliveryDriver);
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