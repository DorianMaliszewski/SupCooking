package providers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service permettant d'encrypter les mots de passe
 * @author MaliszewskiDorian
 */
public class EncryptionProvider {
    
    /**
     * Encrypte le mot de passe passé en paramètre et retourne en 0 le salt généré et en 1 le mot de passé encrypter
     * @param pass Le mot de passe à encrypter
     * @return Tableau contenant le salt et le mot de passe encrypter
     */
    public static String[] encrypt(String pass) {
        byte[] salt = null;
        String strSalt = "";
        try {
            salt = getSalt();
            strSalt = String.valueOf(salt);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(EncryptionProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new String[] { strSalt, securePassword(pass, strSalt)};
    }
    
    
    protected static String securePassword(String passwordToHash, String salt)
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
    protected static byte[] getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }
    
    /**
     * Vérifie le mot de passe passé en paramètre avec le mot de passe encrypter et le salt
     * @param encodedPass le mot de passé encrypté
     * @param pass le mot de passe à vérifier
     * @param salt le salt pour encrypter le mot de passe
     * @return true si les message sont les mêmes
     */
    public static boolean verifyPassword(String encodedPass, String pass, String salt){
        String password = securePassword(pass, salt);
        return password.equals(encodedPass);
    }
}
