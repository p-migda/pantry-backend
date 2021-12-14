package pk.group.storagebapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pk.group.storagebapp.entities.Client;
import pk.group.storagebapp.entities.Pantry;
import pk.group.storagebapp.entities.Product;
import pk.group.storagebapp.entities.User;
import pk.group.storagebapp.model.LoginModel;
import pk.group.storagebapp.model.RegisterModel;
import pk.group.storagebapp.repo.ClientRepo;
import pk.group.storagebapp.repo.PantryRepo;
import pk.group.storagebapp.repo.ProductRepo;
import pk.group.storagebapp.repo.UserRepo;

import java.util.List;

@Service
public class StorageService {

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Autowired
    private ClientRepo clientRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PantryRepo pantryRepo;

    public List<Client> getClients(){
        return clientRepo.findAll();
    }

    public List<Product> getProducts(){
        return productRepo.findAll();
    }

    public void registerUser(RegisterModel registerModel) {
        Client client = Client.builder()
                .firstname(registerModel.getName())
                .lastname(registerModel.getLastname())
                .build();
        clientRepo.save(client);

        User user = User.builder()
                .login(registerModel.getLogin())
                .email(registerModel.getEmail())
                .password(PASSWORD_ENCODER.encode(registerModel.getPassword()))
                .permission(registerModel.getPermission())
                .client(client)
                .build();

        userRepo.save(user);
    }

    public String loginUser(LoginModel loginModel){
        User user = userRepo.findByLogin(loginModel.getLogin());
        if(user == null){
            return "2";
        }
        if(PASSWORD_ENCODER.matches(loginModel.getPassword(), user.getPassword())){
            return "0";
        }else {
            return "1";
        }
    }

    public Pantry getPantry(Long id){
        return pantryRepo.findByUserId(id);
    }

}
