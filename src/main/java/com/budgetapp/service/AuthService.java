package com.budgetapp.service;

import com.budgetapp.dao.UserDAO;
import com.budgetapp.model.User;
import java.util.Optional;

public class AuthService {
    private static AuthService instance;
    private final UserDAO userDAO;
    private User currentUser;

    private AuthService() { userDAO = new UserDAO(); }
    public static synchronized AuthService getInstance() {
        if (instance == null) instance = new AuthService();
        return instance;
    }

    public boolean register(String name, String email, String password) {
        if (userDAO.findByEmail(email).isPresent()) return false;
        User u = new User();
        u.setName(name);
        u.setEmail(email);
        u.setPasswordHash(PasswordHasher.hash(password));
        int id = userDAO.create(u);
        if (id > 0) {
            u.setUserId(id);
            currentUser = u;
            return true;
        }
        return false;
    }

    public boolean login(String email, String password) {
        Optional<User> user = userDAO.findByEmail(email);
        if (user.isPresent() && PasswordHasher.verify(password, user.get().getPasswordHash())) {
            currentUser = user.get();
            return true;
        }
        return false;
    }

    public void logout() { currentUser = null; }
    public Optional<User> getCurrentUser() { return Optional.ofNullable(currentUser); }
}