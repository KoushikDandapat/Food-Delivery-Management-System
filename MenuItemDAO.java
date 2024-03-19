package com.fooddeliverymanagementsystem;

import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class MenuItemDAO {

    public void addMenuItem(MenuItem menuItem) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(menuItem);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public MenuItem getMenuItemById(Long menuItemId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(MenuItem.class, menuItemId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<MenuItem> getAllMenuItems() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM MenuItem", MenuItem.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateMenuItem(MenuItem menuItem) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(menuItem);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void deleteMenuItem(Long menuItemId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            MenuItem menuItem = session.get(MenuItem.class, menuItemId);
            if (menuItem != null) {
                session.delete(menuItem);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}