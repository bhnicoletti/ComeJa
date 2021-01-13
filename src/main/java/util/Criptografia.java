/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * <p>Abstract Criptografia class.</p>
 *
 * @author Nicoletti
 * @version $Id: $Id
 */
public abstract class Criptografia {
    
    /**
     * <p>criptografar.</p>
     *
     * @param senha a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String criptografar (String senha) {
        MessageDigest md;
        try {
            md= MessageDigest.getInstance("SHA-512");
 
            md.update(senha.getBytes());
            byte[] mb = md.digest();
            String out = "";
            for (int i = 0; i < mb.length; i++) {
                byte temp = mb[i];
                String s = Integer.toHexString(new Byte(temp));
                while (s.length() < 2) {
                    s = "0" + s;
                }
                s = s.substring(s.length() - 2);
                out += s;
            }
            System.out.println(out.length());
            return out;
 
        } catch (NoSuchAlgorithmException e) {
            System.out.println("ERROR: " + e.getMessage());
            return null;
        }
    }
}
