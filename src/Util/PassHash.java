/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joena
 */
public class PassHash {
    public static String passHash(String pass){
        try { 
            MessageDigest md =  MessageDigest.getInstance("SHA");
            md.update(pass.getBytes());
            byte[] ubt = md.digest();
            StringBuilder sb = new StringBuilder();
            
            for(byte b: ubt){
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(PassHash.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }   
}
