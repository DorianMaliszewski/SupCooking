/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package providers;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MaliszewskiDorian
 */
public class EncryptionProvider {
    
    public static String[] encrypt(String pass) {
        byte[] salt = null;
        String strSalt = "";
        try {
            salt = getSalt();
            strSalt = String.valueOf(salt);
            System.out.println("Salt str : " + strSalt);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(EncryptionProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new String[] { strSalt, securePassword(pass, strSalt)};
    }
    
    private static String securePassword(String passwordToHash, String salt)
    {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }
    
    //Add salt
    private static byte[] getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }
    
    public static boolean verifyPassword(String encodedPass, String pass, String salt){
        
        System.out.println("Password : " + encodedPass);
        System.out.println("Salt : " + salt);
        String password = securePassword(pass, salt);
        System.out.println("Input Encripted : " + password);
        return password.equals(encodedPass);
    }
}
