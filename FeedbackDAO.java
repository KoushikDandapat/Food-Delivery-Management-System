package com.fooddeliverymanagementsystem;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class FeedbackDAO {

    // Create (Add) a new feedback
    public void addFeedback(Feedback feedback) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(feedback);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Read (Get) a feedback by ID
    public Feedback getFeedbackById(Long feedbackId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Feedback.class, feedbackId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Update an existing feedback
    public void updateFeedback(Feedback feedback) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(feedback);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Delete a feedback
    public void deleteFeedback(Long feedbackId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Feedback feedback = session.get(Feedback.class, feedbackId);
            if (feedback != null) {
                session.delete(feedback);
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
