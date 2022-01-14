package pk.group.storagebapp.controller;

import org.springframework.web.bind.annotation.*;
import pk.group.storagebapp.entities.Client;
import pk.group.storagebapp.entities.Order;
import pk.group.storagebapp.entities.Product;
import pk.group.storagebapp.entities.User;
import pk.group.storagebapp.model.*;
import pk.group.storagebapp.service.StorageService;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class StorageController {

    private final StorageService service;

    public StorageController(StorageService service) {
        this.service = service;
    }

    @GetMapping("/clients")
    public List<Client> getClients() {
        return service.getClients();
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return service.getUsers();
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        return service.getProducts();
    }

    @GetMapping("/product/{productId}")
    public Product getProduct(@PathVariable Long productId) {
        return service.getProduct(productId);
    }

    @GetMapping("/orders/history/{clientId}")
    public List<OrderListModel> getOrdersHistoryByClient(@PathVariable long clientId) {
        return service.getOrderHistory(clientId);
    }

    @GetMapping("/order/list")
    public List<OrderListModel> getOrderList() {
        return service.getOrdersList();
    }

    @GetMapping("/statistics")
    public StatisticsModel getStatistics() {
        return service.getStatistics();
    }


    @PostMapping("/user/edit/{userId}")
    public User editUser(@PathVariable long userId,
                         @RequestBody EditModel editModel) {
        return service.editUser(userId, editModel);
    }

    @PostMapping("/product/add")
    public Product addProduct(@RequestBody ProductRegisterModel productRegisterModel) {
        return service.addProduct(productRegisterModel);
    }

    @PostMapping("/order/edit")
    public Order editOrder(@RequestParam Long orderId,
                           @RequestParam String statusOrder,
                           @RequestParam String statusPayment) {
        return service.editOrder(orderId, statusOrder, statusPayment);
    }

    @PostMapping("/order/register")
    public Order registerOrder(@RequestBody Map<String, Integer> map,
                               @RequestParam Long clientId) {
        return service.registerOrder(map, clientId);
    }

    @PostMapping("/user/register")
    public User registerUser(@RequestBody RegisterModel registerModel) {
        return service.registerUser(registerModel);
    }

    @PostMapping("/user/login")
    public User loginUser(@RequestBody LoginModel loginModel) {
        return service.loginUser(loginModel);
    }

    @PostMapping("/product/rate")
    public Product rateProduct(@RequestParam Long productId,
                               @RequestParam Double rate) {
        return service.rateProduct(productId, rate);
    }

    @PostMapping("/product/edit")
    public Product editProduct(@RequestBody ProductEditModel productEditModel,
                               @RequestParam Long productId) {
        return service.editProduct(productEditModel, productId);
    }


    @DeleteMapping("/user/delete")
    public void deleteUser(@RequestParam long userId) {
        service.deleteUser(userId);
    }

    @DeleteMapping("/product/delete")
    public void deleteProduct(@RequestParam long productId) {
        service.deleteProduct(productId);
    }
}
