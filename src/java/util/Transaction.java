/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import org.hibernate.Session;

/**
 *
 * @author acamr
 */
public class Transaction {
    private static Session session;

    public static Session openSession() {
        session = db.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        return session;
    }

    public static void closeSession() {
        session.getTransaction().commit();
        session.close();
    }

}
