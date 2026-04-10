package com.staynomadly.api;

import com.staynomadly.api.entity.User;
import com.staynomadly.api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
public class AuthTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void dumpPasswords() {
        System.out.println("\n\n======== BEGIN DB DUMP ========");
        List<User> users = userRepository.findAll();
        for (User user : users) {
            System.out.println("User ID: " + user.getId());
            System.out.println("Email: '" + user.getEmail() + "'");
            System.out.println("Hash: '" + user.getPassword() + "'");
            System.out.println("Hash Length: " + user.getPassword().length());
            
            if (user.getEmail().equals("backendtest@test.com")) {
                boolean match = passwordEncoder.matches("MySecurePassword@123", user.getPassword());
                System.out.println("MATCHES 'MySecurePassword@123'? " + match);
            }
            System.out.println("---------------------------------");
        }
        System.out.println("======== END DB DUMP ========\n\n");
    }
}
