package com.example.FormularioAutomatizacion.Web.Controller;

import com.example.FormularioAutomatizacion.Entity.EntityUser;
import com.example.FormularioAutomatizacion.Service.iServiceImpl;
import com.example.FormularioAutomatizacion.Service.iServiceImplUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/ubicacion")
public class UserControler {

    @Autowired
    private iServiceImplUser iServiceImplUser;

    @PostMapping("/user")
    public EntityUser createUser(@RequestBody EntityUser user) {
        return iServiceImplUser.saveUser(user);
    }
}
