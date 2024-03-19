package com.fooddeliverymanagementsystem;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class AdminDAO {

    // Create (Add) a new admin
    public void addAdmin(Admin admin) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(admin);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Read (Get) an admin by ID
    public Admin getAdminById(String newUsername) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Admin.class, newUsername);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    // Update an existing admin
    public void updateAdmin(Admin admin) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(admin);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
         
            }
            e.printStackTrace();
        }
    }

	public void deleteAdmin(long adminId) {
		// TODO Auto-generated method stub
		
	}

	public Admin getAdminById(long adminId) {
		// TODO Auto-generated method stub
		return null;
	}
}
        
                