package entities;

import dao.*;
import ui.*;

import static app.InputLiterals.*;
import static app.InputLiterals.DELETE;

import java.util.List;

public class WorkWithEntities {

    private boolean correctCreation = true;

    public WorkWithEntities() {
    }

    public WorkWithEntities(Ui ui, String crudCommand, Order order, User user, Category category, OrderMap orderMap, Product product, OrderDao orderDao, UserDao userDao, CategoryDao categoryDao, OrderMapDao orderMapDao, ProductDao productDao) {
        if (crudCommand.equals(CREATE)) {
            createOrder(ui, order, user, orderDao, userDao);
            createOrderMap(ui, orderMap, order, productDao, orderMapDao);
        }
        if (crudCommand.equals(READ)) {
            if (ui.getOneOrder()) {
                order.setOrderId(ui.setIntegerField(ORDER_ID));
                orderMap.setOrder(order);
                List<OrderMap> orderMapList = orderMapDao.getOrderMap(orderMap);
                System.out.println("order_id\t date\t first_name\t last_name\t category_name\t product_name\t quantity\t total_price\t address\t status");
                for (OrderMap orders : orderMapList) {
                    System.out.println(orders);
                }
            } else {
                List<OrderMap> orderMapList = orderMapDao.getAllOrderMap(orderMap);
                System.out.println("order_id\t date\t first_name\t last_name\t category_name\t product_name\t quantity\t total_price\t address\t status");
                for (OrderMap orders : orderMapList) {
                    System.out.println(orders);
                }
            }
        }
        if (crudCommand.equals(UPDATE)) {
            order = updateOrder(ui, order, orderDao);
            if (order != null) {
                updateOrderMap(ui, orderMap, order, orderDao, orderMapDao, productDao);
            } else {
                System.out.println("Заказа с таким id не существует!");
            }
        }
        if (crudCommand.equals(DELETE)) {
            System.out.println("Такой функционал не заложен в приложение");
        }
    }

    private void updateOrderMap(Ui ui, OrderMap orderMap, Order order, OrderDao orderDao, OrderMapDao orderMapDao, ProductDao productDao) {
        orderMap.setOrder(order);
        List<OrderMap> orderMapList = orderMapDao.getOrderMap(orderMap);
        System.out.println("order_id\t date\t first_name\t last_name\t category_name\t product_name\t quantity\t total_price\t address\t status");
        for (OrderMap orders : orderMapList) {
            System.out.println(orders);
        }
        if (ui.changeField(ORDER_STATUS)) {
            order = orderMapList.get(0).getOrder();
            OrderStatus orderStatus = new OrderStatus();
            orderStatus.setStatusId(ui.setStatusField());
            orderDao.updateOrderStatus(order, orderStatus);
        }
        if (ui.changeField("содержимое заказа")) {
            if (ui.addProduct()) {
                do {
                    boolean containProduct = false;
                    Product product = new Product();
                    product.setProductName(ui.setStringField(PRODUCT_NAME));
                    product = productDao.getProductByName(product);
                    if (product.getProductId() != 0) {
                        for (OrderMap orders : orderMapList) {
                            if (orders.getProduct().getProductId() == product.getProductId()) {
                                containProduct = true;
                            }
                        }
                        if (containProduct) {
                            System.out.println("Такой продукт уже есть в заказе!");
                        } else {
                            orderMap.setOrder(order);
                            orderMap.setProduct(product);
                            orderMap.setQuantity(ui.setIntegerField(QUANTITY));
                            orderMapDao.createOrderMap(orderMap);
                        }
                    } else {
                        System.out.println("Продукта с таким именем не существует!");
                    }
                } while (ui.oneMoreProductInBasket());
            }

            while (ui.updateProductQuantity()) {
                orderMapList = orderMapDao.getOrderMap(orderMap);
                boolean quantityChanged = false;
                Product product = new Product();
                product.setProductName(ui.setStringField(PRODUCT_NAME));
                for (OrderMap orders : orderMapList) {
                    if (orders.getProduct().getProductName().equals(product.getProductName())) {
                        orders.setQuantity(ui.setIntegerField(QUANTITY));
                        orderMapDao.updateOrderMap(orders);
                        quantityChanged = true;
                    }
                }
                if (!quantityChanged) {
                    System.out.println("Такого продукта в заказе нет!");
                }
            }

            while (ui.deleteProductFromOrder()) {
                orderMapList = orderMapDao.getOrderMap(orderMap);
                boolean productDeleted = false;
                Product product = new Product();
                product.setProductName(ui.setStringField(PRODUCT_NAME));
                for (OrderMap orders : orderMapList) {
                    if (orders.getProduct().getProductName().equals(product.getProductName())) {
                        orderMapDao.deleteOrderMap(orders);
                        productDeleted = true;
                    }
                }
                if (!productDeleted) {
                    System.out.println("Такого продукта в заказе нет!");
                }
            }
        }

        if (orderMapDao.orderMapCheck(orderMap)) {
            OrderStatus orderStatus = new OrderStatus();
            orderStatus.setStatusId(4);
             orderDao.updateOrderStatus(order, orderStatus);
        }
    }


    private Order updateOrder(Ui ui, Order order, OrderDao orderDao) {
        order.setOrderId(ui.setIntegerField(ORDER_ID));
        order = orderDao.getOrderById(order);
        return order;
    }

    private void createOrderMap(Ui ui, OrderMap orderMap, Order order, ProductDao productDao, OrderMapDao
            orderMapDao) {
        if (!correctCreation) {
            return;
        }
        orderMap.setOrder(order);
        do {
            Product product = new Product();
            product.setProductName(ui.setStringField(PRODUCT_NAME));
            product = productDao.getProductByName(product);
            if (product.getProductId() != 0) {
                orderMap.setProduct(product);
                orderMap.setQuantity(ui.setIntegerField(QUANTITY));
                orderMapDao.createOrderMap(orderMap);
            } else {
                System.out.println("Продукта с таким именем не существует!");
            }
        } while (ui.oneMoreProductInBasket());
    }

    private void createOrder(Ui ui, Order order, User user, OrderDao orderDao, UserDao userDao) {
        user.setUserId(ui.setIntegerField(USER_ID));
        order.setUser(userDao.getUserById(user));
        if (order.getUser() == null) {
            correctCreation = false;
            if (Ui.noRecordInDB(USERS)) {
                user = new User();
                user.setFirstName(ui.setStringField(USER_FIRST_NAME));
                user.setLastName(ui.setStringField(USER_LAST_NAME));
                user.setAddress(ui.setStringField(USER_ADDRESS));
                userDao.createUser(user);
                user = userDao.getUser(user);
                order.setUser(user);
                correctCreation = true;
                orderDao.createOrder(order);
            }
        } else {
            correctCreation = true;
            orderDao.createOrder(order);
        }
    }

    private void createProduct(Ui ui, Product product, ProductDao productDao, Category category, CategoryDao
            categoryDao) {
        product.setProductName(ui.setStringField(PRODUCT_NAME));
        product.setPrice(ui.setBigDecimalField(PRODUCT_PRICE));
        String categoryName = ui.setStringField(CATEGORY_NAME);
        category.setCategoryName(categoryName);
        category = categoryDao.getCategoryByName(category);
        if (category == null) {
            if (Ui.noRecordInDB(CATEGORIES)) {
                category = new Category();
                category.setCategoryName(categoryName);
                categoryDao.createCategory(category);
                category = categoryDao.getCategoryByName(category);
                product.setCategory(category);
                correctCreation = true;
                productDao.createProduct(product);
            } else {
                correctCreation = false;
            }
        } else {
            product.setCategory(category);
            correctCreation = true;
            productDao.createProduct(product);
        }
    }

    public WorkWithEntities(Ui ui, String crudCommand, Category category, CategoryDao categoryDao) {
        if (crudCommand.equals(CREATE)) {
            category.setCategoryName(ui.setStringField(CATEGORY_NAME));
            categoryDao.createCategory(category);
        }
        if (crudCommand.equals(READ)) {
            List<Category> categoriesList = categoryDao.getCategories();
            System.out.println("category_id\t category_name");
            for (Category categories : categoriesList) {
                System.out.println(categories);
            }
        }
        if (crudCommand.equals(UPDATE)) {
            System.out.println("Такой функционал не заложен в приложение");
        }
        if (crudCommand.equals(DELETE)) {
            System.out.println("Такой функционал не заложен в приложение");
        }
    }

    public WorkWithEntities(Ui ui, String crudCommand, Product product, ProductDao productDao, Category category, CategoryDao categoryDao) {
        if (crudCommand.equals(CREATE)) {
            createProduct(ui, product, productDao, category, categoryDao);
        }
        if (crudCommand.equals(READ)) {
            List<Product> productList = productDao.getAllProducts();
            System.out.println("product_id\t category_name\t product_name\t price");
            for (Product products : productList) {
                System.out.println(products);
            }
        }
        if (crudCommand.equals(UPDATE)) {
            updateProduct(ui, product, productDao, category, categoryDao);
        }
        if (crudCommand.equals(DELETE)) {
            System.out.println("Такой функционал не заложен в приложение");
        }
    }

    public WorkWithEntities(Ui ui, String crudCommand, User user, UserDao userDao) {
        if (crudCommand.equals(CREATE)) {
            user.setFirstName(ui.setStringField(USER_FIRST_NAME));
            user.setLastName(ui.setStringField(USER_LAST_NAME));
            user.setAddress(ui.setStringField(USER_ADDRESS));
            userDao.createUser(user);
        }
        if (crudCommand.equals(READ)) {
            List<User> userList = userDao.getAllUsers();
            System.out.println("user_id\t first_name\t last_name\t address");
            for (User users : userList) {
                System.out.println(users);
            }
        }
        if (crudCommand.equals(UPDATE)) {
            updateUser(ui, user, userDao);
        }
        if (crudCommand.equals(DELETE)) {
            deleteUser(ui, user, userDao);
        }
    }

    private void updateProduct(Ui ui, Product product, ProductDao productDao, Category category, CategoryDao
            categoryDao) {
        product.setProductId(ui.setIntegerField(PRODUCT_ID));
        product = productDao.getProductById(product);
        if (product == null) {
            System.out.println("Продукта с таким id не существует!");
            return;
        } else {
            boolean wasChanged = false;
            System.out.println(product);
            if (ui.changeField(PRODUCT_NAME)) {
                product.setProductName(ui.setStringField(PRODUCT_NAME));
                wasChanged = true;
            }
            if (ui.changeField(PRODUCT_PRICE)) {
                product.setPrice(ui.setBigDecimalField(PRODUCT_PRICE));
                wasChanged = true;
            }
            if (ui.changeField(PRODUCT_CATEGORY)) {
                String categoryName = ui.setStringField(CATEGORY_NAME);
                category.setCategoryName(categoryName);
                category = categoryDao.getCategoryByName(category);
                if (category == null) {
                    if (Ui.noRecordInDB(CATEGORIES)) {
                        category = new Category();
                        category.setCategoryName(categoryName);
                        categoryDao.createCategory(category);
                        category = categoryDao.getCategoryByName(category);
                        product.setCategory(category);
                        wasChanged = true;
                    }
                } else {
                    product.setCategory(category);
                    wasChanged = true;
                }
            }
            if (wasChanged) {
                productDao.updateProduct(product);
                System.out.println("Продукт изменен успешно!");
            } else {
                System.out.println("Вы не внесли изменений!");
            }
        }
    }

    private void deleteUser(Ui ui, User user, UserDao userDao) {
        do {
            System.out.println("Введите id пользователя:");
            int userId;
            while ((userId = ui.setIntegerField(USER_ID)) == 1) {
                System.out.println("Этот id зарезервирован и не может быть использован!");
            }
            user.setUserId(userId);
            user = userDao.getUserById(user);
            if (user == null) {
                System.out.println("Пользователь с таким id не существует!");
            }
        } while (user == null);
        userDao.deleteUser(user);
    }


    private void updateUser(Ui ui, User user, UserDao userDao) {
        int userId;
        while ((userId = ui.setIntegerField(USER_ID)) == 1) {
            System.out.println("Этот id зарезервирован и не может быть использован!");
        }
        user.setUserId(userId);
        user = userDao.getUserById(user);
        if (user == null) {
            System.out.println("Пользователь с таким id не существует!");
            return;
        } else {
            boolean wasChanged = false;
            System.out.println(user);
            if (ui.changeField(USER_FIRST_NAME)) {
                user.setFirstName(ui.setStringField(USER_FIRST_NAME));
                wasChanged = true;
            }
            if (ui.changeField(USER_LAST_NAME)) {
                user.setLastName(ui.setStringField(USER_LAST_NAME));
                wasChanged = true;
            }
            if (ui.changeField(USER_ADDRESS)) {
                user.setAddress(ui.setStringField(USER_ADDRESS));
                wasChanged = true;
            }
            if (wasChanged) {
                userDao.updateUser(user);
                System.out.println("Пользователь изменен успешно!");
            } else {
                System.out.println("Вы не внесли изменений!");
            }
        }

    }

    public boolean isCorrectCreation() {
        return correctCreation;
    }
}
