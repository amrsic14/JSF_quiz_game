/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.security.MessageDigest;

/**
 *
 * @author acamr
 */
public class PasswordUtil {
    public static MessageDigest digest;
    
    static{
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
    }
    
}
