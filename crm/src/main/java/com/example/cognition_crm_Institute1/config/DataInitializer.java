package com.example.cognition_crm_Institute1.config;

import com.example.cognition_crm_Institute1.entity.User;
import com.example.cognition_crm_Institute1.repository.UserRepository;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initializeDefaultUsers() {
        try {
            // Check if admin user already exists
            if (userRepository.findByUsername("admin").isEmpty()) {
                User adminUser = new User();
                adminUser.setUsername("admin");
                adminUser.setPassword("admin123");
                adminUser.setEmail("admin@cognitioncrm.com");
                adminUser.setFullName("Administrator");
                adminUser.setEnabled(true);  // ✅ Fixed: was setIsActive(), now setEnabled()
                adminUser.setRole("ADMIN");

                userRepository.save(adminUser);
                System.out.println("✅ Admin user created successfully!");
                System.out.println("   Username: admin");
                System.out.println("   Password: admin123");
            }

            // Check if demo user exists
            if (userRepository.findByUsername("demo").isEmpty()) {
                User demoUser = new User();
                demoUser.setUsername("demo");
                demoUser.setPassword("demo123");
                demoUser.setEmail("demo@cognitioncrm.com");
                demoUser.setFullName("Demo User");
                demoUser.setEnabled(true);  // ✅ Fixed: was setIsActive(), now setEnabled()
                demoUser.setRole("USER");

                userRepository.save(demoUser);
                System.out.println("✅ Demo user created successfully!");
                System.out.println("   Username: demo");
                System.out.println("   Password: demo123");
            }
        } catch (Exception e) {
            System.err.println("❌ Error initializing users: " + e.getMessage());
            e.printStackTrace();
        }
    }
}