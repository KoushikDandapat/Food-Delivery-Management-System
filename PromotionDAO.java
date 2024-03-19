package com.fooddeliverymanagementsystem;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PromotionDAO {

    // Create (Add) a new promotion
    public void addPromotion(Promotion promotion) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(promotion);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Read (Get) a promotion by ID
    public Promotion getPromotionById(Long promotionId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Promotion.class, promotionId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Update an existing promotion
    public void updatePromotion(Promotion promotion) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(promotion);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Delete a promotion
    public void deletePromotion(Long promotionId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Promotion promotion = session.get(Promotion.class, promotionId);
            if (promotion != null) {
                session.delete(promotion);
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