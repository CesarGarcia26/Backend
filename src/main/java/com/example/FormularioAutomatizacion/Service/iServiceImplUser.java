package com.example.FormularioAutomatizacion.Service;

import com.example.FormularioAutomatizacion.Entity.EntityUser;
import com.example.FormularioAutomatizacion.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class iServiceImplUser {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public EntityUser saveUser(EntityUser user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

}
