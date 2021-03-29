package app;

import dao.*;
import entities.WorkWithEntities;
import entities.*;
import ui.Ui;

import static app.InputLiterals.*;

public class App {

    private static boolean AppIsRunning = true;

    public static void main(String[] args) {
        Ui ui = new Ui();
        WorkWithEntities entity = new WorkWithEntities();

        String crudCommand;
        String table;

        User user;
        Category category;
        Order order;
        OrderMap orderMap;
        Product product;

        UserDao userDao = new UserDao();
        CategoryDao categoryDao = new CategoryDao();
        OrderDao orderDao = new OrderDao();
        OrderMapDao orderMapDao = new OrderMapDao();
        ProductDao productDao = new ProductDao();

        while (AppIsRunning) {
            while (true) {

                user = new User();
                category = new Category();
                order = new Order();
                orderMap = new OrderMap();
                product = new Product();

                table = ui.choiceTable();

                if (appState(table)) {
                    break;
                }

                crudCommand = ui.choiceCRUD();

                switch (table) {
//
                    case (USERS):
                        entity = new WorkWithEntities(ui, crudCommand, user, userDao);
                        break;
                    case (PRODUCTS):
                        entity = new WorkWithEntities(ui, crudCommand, product, productDao, category, categoryDao);
                        break;
                    case (CATEGORIES):
                        entity = new WorkWithEntities(ui, crudCommand, category, categoryDao);
                        break;
                    case (ORDERS):
                        entity = new WorkWithEntities(ui, crudCommand, order, user, category, orderMap, product, orderDao, userDao, categoryDao, orderMapDao, productDao);
                }
                if (!entity.isCorrectCreation()) {
                    System.out.println("Объект создан неверно!\nПопробуйте еще раз!");
                    break;
                }
            }
        }
    }


    public static boolean appState(String command) {
        if (command.equals(EXIT)) {
            AppIsRunning = false;
            return true;
        } else return false;
    }


    //
//        User user = new User();
//        user.setUserId(2);
//
//        user.setFirstName("Serg");
//        user.setLastName("Gurkov");
//        user.setAddress("Noway st");
//
//        UserDao userDao = new UserDao();
//        userDao.createUser(user);

//        Category category = new Category();
//        category.setCategoryName("food");
//
//        CategoryDao categoryDao = new CategoryDao();
//        categoryDao.findByName(category.getCategoryName());

//        Product product = new Product();
//        product.setProductId(3);
//        product.setProductName("eggs");
//        product.setPrice(new BigDecimal("7.1"));
//        product.setCategory(category);
//
//
//
//        ProductDao productDao = new ProductDao();

    //      product = productDao.getProduct(product);
    //       System.out.println(product);
//        productDao.deleteProduct(product);
    //    productDao.createProduct(product);

//        OrderStatus orderStatus = new OrderStatus();
//        Order order = new Order();
//        order.setOrderId(4);
//        OrderDao orderDao = new OrderDao();
//        order = orderDao.getOrder(order);
//        System.out.println(order);

//        ProductQuantity productQuantity = new ProductQuantity();
//        productQuantity.setProductId(1);
//        productQuantity.setOrderId(4);
//
//        ProductQuantityDao productQuantityDao = new ProductQuantityDao();
//        productQuantityDao.getProductQuantity(productQuantity);

//        Category category = new Category();
//        category.setCategoryName("food");
//        CategoryDao categoryDao = new CategoryDao();
//        categoryDao.createCategory(category);

//        Product product = new Product();
//        product.setProductId(1);
    //product.setProductName("milk");
    //   product.setCategory(category);
    //  product.setPrice(new BigDecimal("3.7"));
//        ProductDao productDao = new ProductDao();
//        productDao.createProduct(product);

//        User user = new User();
//        user.setUserId(2);
//        user.setFirstName("Serg");
//        user.setLastName("Gurkov");
//        user.setAddress("Noway st");
//        UserDao userDao = new UserDao();
//        userDao.createUser(user);

//        OrderStatus orderStatus = new OrderStatus();
//        orderStatus.setStatusId(1);

//        Order order = new Order();
//        order.setOrderId(1);
    //    order.setOrderStatus(orderStatus);
//        OrderDao orderDao = new OrderDao();
//        orderDao.createOrder(order);

//        OrderMap orderMap = new OrderMap();
//        orderMap.setOrder(order);
//        orderMap.setProduct(product);
//        orderMap.setQuantity(3);
//        OrderMapDao orderMapDao = new OrderMapDao();
//        orderMapDao.createOrderMap(orderMap);


//        Order order = new Order();
//        order.setOrderId(1);
//        OrderStatus orderStatus = new OrderStatus();
//        orderStatus.setStatusId(4);
//        OrderDao orderDao = new OrderDao();
//        orderDao.updateOrderStatus(order, orderStatus);


}

