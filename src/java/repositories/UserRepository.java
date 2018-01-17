/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositories;

import java.util.List;
import models.User;

/**
 *
 * @author MaliszewskiDorian
 */
public class UserRepository {
    public static User find(int id) {
        return (User) AbstractRepository.find(User.class, id);
    }

    public static List findAll() {
        return AbstractRepository.findAll(User.class);
    }
    
    public static boolean add(User u) {
        return AbstractRepository.add(u);
    }

    public static boolean delete(User u) {
        return AbstractRepository.delete(u);
    }

    public static boolean edit(User u) {
        return AbstractRepository.edit(u);
    }

    public static User findByUsername(String parameter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
