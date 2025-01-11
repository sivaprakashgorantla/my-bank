package com.sivaprakash.auth.service;

// can write based on controller requirement to implement the business logic
// can use repository to interact with database
// can use model to get the data from controller
// LoginRequest and LoginResponse are the model classes which are used to get the data from controller and send the data to controller
// can use the service to implement the business logic




import org.springframework.stereotype.Service;
@Service
public class AuthService {
        public boolean authenticate(String username, String password) {
            // Implement your authentication logic here
            // For example, you can check the username and password against a database
            if ("admin".equals(username) && "password".equals(password)) {
                return true;
            }
            return false;
        }
    }

