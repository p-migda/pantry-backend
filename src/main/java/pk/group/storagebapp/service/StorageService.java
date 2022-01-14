package pk.group.storagebapp.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pk.group.storagebapp.entities.*;
import pk.group.storagebapp.model.*;
import pk.group.storagebapp.repo.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StorageService {

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    private final ClientRepo clientRepo;
    private final ProductRepo productRepo;
    private final UserRepo userRepo;
    private final OrderRepo orderRepo;
    private final ProductOrderRepo productOrderRepo;
    private final WorkerRepo workerRepo;

    public StorageService(ClientRepo clientRepo, ProductRepo productRepo, UserRepo userRepo, OrderRepo orderRepo, ProductOrderRepo productOrderRepo, WorkerRepo workerRepo) {
        this.clientRepo = clientRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
        this.orderRepo = orderRepo;
        this.productOrderRepo = productOrderRepo;
        this.workerRepo = workerRepo;
    }

    public List<OrderListModel> getOrderHistory(long clientId) {
        Client client = clientRepo.getById(clientId);

        return orderRepo.findAllByClient(client).stream()
                .map(order -> getOrderList(order.getId()))
                .collect(Collectors.toList());
    }

    public List<OrderListModel> getOrdersList() {
        return orderRepo.findAll().stream()
                .map(order -> getOrderList(order.getId()))
                .collect(Collectors.toList());
    }

    public OrderListModel getOrderList(Long orderId) {
        Order order = orderRepo.findById(orderId).get();
        List<ProductOrder> productOrders = productOrderRepo.findAllByOrder(order);

        return OrderListModel.builder()
                .order(order)
                .productList(productOrders.stream().map(productOrder -> ProductModel.builder()
                                .product(productOrder.getProduct())
                                .quantity(productOrder.getQuantity())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    public List<Client> getClients() {
        return clientRepo.findAll();
    }

    public List<Product> getProducts() {
        return productRepo.findAll();
    }

    public Product getProduct(Long productId) {
        return productRepo.getById(productId);
    }

    public List<User> getUsers() {
        return userRepo.findAll();
    }

    public StatisticsModel getStatistics() {
        List<OrderListModel> ordersList = getOrdersList().stream()
                .filter(order -> !order.getOrder().getStatusOrder().equals("cancelled"))
                .collect(Collectors.toList());

        List<ProductListModel> productListModel = new ArrayList<>();

        ordersList.forEach(order -> {
            order.getProductList().forEach(o -> {

                ProductListModel model = ProductListModel.builder()
                        .product(o.getProduct())
                        .quantity(o.getQuantity())
                        .build();

                if (productListModel.contains(model)) {
                    ProductListModel model1 = productListModel.get(productListModel.indexOf(model));
                    model1.setQuantity(model.getQuantity() + model1.getQuantity());
                    productListModel.set(productListModel.indexOf(model), model1);
                } else {
                    productListModel.add(model);
                }
            });
        });

        Integer top = productListModel.stream().map(ProductListModel::getQuantity).max(Integer::compareTo).get();

        List<Product> productList = productListModel.stream()
                .filter(productListModel1 -> productListModel1.getQuantity() == top)
                .map(ProductListModel::getProduct)
                .collect(Collectors.toList());

        long cancelled = ordersList.stream().filter(order -> "cancelled".equals(order.getOrder().getStatusOrder())).count();

        return StatisticsModel.builder()
                .topProduct(productList)
                .productListModel(productListModel)
                .quantityOrder(ordersList.size())
                .quantityCancelledOrder((int) cancelled)
                .build();

    }


    @Transactional
    public Product addProduct(ProductRegisterModel productRegisterModel) {

        if (productRepo.findByName(productRegisterModel.getName()).isPresent()) {
            return Product.builder().build();
        }

        Product product = Product.builder()
                .name(productRegisterModel.getName())
                .imgUrl(productRegisterModel.getImgUrl())
                .value(productRegisterModel.getValue())
                .score(0.00)
                .scoreNumber(0)
                .quantity(10)
                .build();

        productRepo.save(product);

        return product;
    }

    @Transactional
    public Order editOrder(Long orderId, String statusOrder, String statusPayment) {
        Order order = orderRepo.findById(orderId).get();

        if (statusOrder.equals("cancelled")) {
            OrderListModel orderList = getOrderList(orderId);

            orderList.getProductList().forEach(productModel -> {
                Product product = productRepo.findById(productModel.getProduct().getId()).get();
                product.setQuantity(product.getQuantity() + productModel.getQuantity());
                productRepo.save(product);
            });
        }

        order.setStatusOrder(statusOrder);
        order.setStatusPayment(statusPayment);

        return orderRepo.save(order);
    }

    @Transactional
    public Order registerOrder(Map<String, Integer> orderMap, Long clientId) {

        Client client = clientRepo.getById(clientId);

        List<Product> productList = new ArrayList<>();

        orderMap.keySet().forEach(s -> {
            Product product = productRepo.findById(Long.valueOf(s)).get();
            product.setQuantity(product.getQuantity() - orderMap.get(s));
            productRepo.save(product);
            productList.add(product);
        });


        BigDecimal value = productList.stream()
                .map(product1 -> product1.getValue().multiply(BigDecimal.valueOf(orderMap.get(product1.getId().toString()))).setScale(2, RoundingMode.HALF_UP))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (client.getIsRegular()) {
            value = value.multiply(BigDecimal.valueOf(0.8)).setScale(2, RoundingMode.HALF_UP);
        }

        Order order = Order.builder()
                .statusOrder("placed")
                .ordertime(LocalDateTime.now())
                .client(client)
                .statusPayment("pending")
                .value(value)
                .build();

        orderRepo.save(order);

        productList.stream()
                .map(product -> ProductOrder.builder()
                        .order(order)
                        .product(product)
                        .quantity(orderMap.get(product.getId().toString()))
                        .build())
                .forEach(productOrderRepo::save);
        return order;
    }

    @Transactional
    public User registerUser(RegisterModel registerModel) {

        if (userRepo.getByLogin(registerModel.getLogin()).isPresent() || userRepo.getByEmail(registerModel.getEmail()).isPresent()) {
            return User.builder().build();
        }

        Client client = null;
        Worker worker = null;

        if (registerModel.getPermission() == 3) {
            client = Client.builder()
                    .firstname(registerModel.getName())
                    .lastname(registerModel.getLastname())
                    .isRegular(false)
                    .phoneNumber(registerModel.getPhoneNumber())
                    .build();
            clientRepo.save(client);
        } else if (registerModel.getPermission() == 2) {
            worker = Worker.builder()
                    .firstname(registerModel.getName())
                    .lastname(registerModel.getLastname())
                    .position("Pracownik")
                    .build();
            workerRepo.save(worker);
        } else if (registerModel.getPermission() == 1) {
            worker = Worker.builder()
                    .firstname(registerModel.getName())
                    .lastname(registerModel.getLastname())
                    .position("Menedżer")
                    .build();
            workerRepo.save(worker);
        } else {
            worker = Worker.builder()
                    .firstname(registerModel.getName())
                    .lastname(registerModel.getLastname())
                    .position(registerModel.getPosition())
                    .build();
            workerRepo.save(worker);
        }


        User user = User.builder()
                .login(registerModel.getLogin())
                .email(registerModel.getEmail())
                .password(PASSWORD_ENCODER.encode(registerModel.getPassword()))
                .permission(registerModel.getPermission())
                .client(client)
                .worker(worker)
                .build();

        userRepo.save(user);

        return user;
    }

    @Transactional
    public User loginUser(LoginModel loginModel) {
        User user = userRepo.findByLogin(loginModel.getLogin());
        if (user == null) {
            return User.builder()
                    .build();
        } else if (PASSWORD_ENCODER.matches(loginModel.getPassword(), user.getPassword())) {
            return user;
        } else {
            return User.builder()
                    .build();
        }
    }

    @Transactional
    public User editUser(Long userId, EditModel editModel) {
        User user = userRepo.findById(userId).get();

        if (editModel.getPassword() != null) {
            user.setPassword(PASSWORD_ENCODER.encode(editModel.getPassword()));
        }
        if (editModel.getEmail() != null) {
            user.setEmail(editModel.getEmail());
        }
        if (editModel.getPermission() != null) {
            user.setPermission(editModel.getPermission());
        }
        if (editModel.getPhoneNumber() != null) {
            user.getClient().setPhoneNumber(editModel.getPhoneNumber());
        }
        if (editModel.getIsRegular() != null) {
            user.getClient().setIsRegular(editModel.getIsRegular());
        }

        return userRepo.save(user);
    }

    @Transactional
    public Product rateProduct(Long productId, Double rate) {

        Product product = productRepo.findById(productId).get();
        Double newScore = (product.getScore() * product.getScoreNumber() + rate) / (product.getScoreNumber() + 1);

        product.setScore(newScore);
        product.setScoreNumber(product.getScoreNumber() + 1);

        return productRepo.save(product);
    }

    @Transactional
    public Product editProduct(ProductEditModel productEditModel, Long productId) {
        Product product = productRepo.findById(productId).get();

        if (productEditModel.getValue() != null) {
            product.setValue(productEditModel.getValue());
        }
        if (productEditModel.getQuantity() != null) {
            product.setQuantity(product.getQuantity() + productEditModel.getQuantity());
        }

        return productRepo.save(product);
    }


    public void deleteProduct(long productId) {
        Product product = productRepo.getById(productId);
        productRepo.delete(product);
    }

    public void deleteUser(Long userId) {
        User user = userRepo.getById(userId);
        userRepo.delete(user);
    }
}
