package pk.group.storagebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pk.group.storagebapp.entities.Client;
import pk.group.storagebapp.entities.Pantry;
import pk.group.storagebapp.entities.Product;
import pk.group.storagebapp.model.LoginModel;
import pk.group.storagebapp.model.RegisterModel;
import pk.group.storagebapp.service.StorageService;

import java.util.List;

@RestController
public class StorageController {

    @Autowired
    StorageService service;

    @GetMapping("/clients")
    public List<Client> getClients(){
        return service.getClients();
    }

    @GetMapping("/products")
    public List<Product> getProducts(){
        return service.getProducts();
    }

    @GetMapping("/user/pantry")
    public Pantry getPantryById(@RequestParam Long id){
        return service.getPantry(id);
    }


    @PostMapping("/user/register")
    public void registerUser(@RequestBody RegisterModel registerModel){
        service.registerUser(registerModel);
    }

    @PostMapping("/user/login")
    public String loginUser(@RequestBody LoginModel loginModel){
        return service.loginUser(loginModel);
    }
}
