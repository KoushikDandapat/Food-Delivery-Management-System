package com.fooddeliverymanagementsystem;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import antlr.collections.List;

public class MainApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize DAO classes
        RestaurantDAO restaurantDAO = new RestaurantDAO();
        MenuItemDAO menuItemDAO = new MenuItemDAO();
        CustomerDAO customerDAO = new CustomerDAO();
        OrderDAO orderDAO = new OrderDAO();
        OrderItemDAO orderItemDAO = new OrderItemDAO();
        DeliveryDriverDAO deliveryDriverDAO = new DeliveryDriverDAO();
        DeliveryDAO deliveryDAO = new DeliveryDAO();
        PaymentDAO paymentDAO = new PaymentDAO();
        PromotionDAO promotionDAO = new PromotionDAO();
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        AdminDAO adminDAO = new AdminDAO();

        int choice;

        do {
            System.out.println("Menu:");
            System.out.println("1. Manage Restaurants");
            System.out.println("2. Manage Menu Items");
            System.out.println("3. Manage Customers");
            System.out.println("4. Manage Orders");
            System.out.println("5. Manage Order Items");
            System.out.println("6. Manage Delivery Drivers");
            System.out.println("7. Manage Deliveries");
            System.out.println("8. Manage Payments");
            System.out.println("9. Manage Promotions");
            System.out.println("10. Manage Feedback");
            System.out.println("11. Manage Admins");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    manageRestaurants(restaurantDAO, scanner);
                    break;
                case 2:
                    manageMenuItems(menuItemDAO, restaurantDAO, scanner);
                    break;
                case 3:
                    manageCustomers(customerDAO, scanner);
                    break;
                case 4:
                    manageOrders(orderDAO, customerDAO, restaurantDAO, scanner);
                    break;
                case 5:
                    manageOrderItems(orderItemDAO, orderDAO, menuItemDAO, scanner);
                    break;
                case 6:
                    manageDeliveryDrivers(deliveryDriverDAO, scanner);
                    break;
                case 7:
                    manageDeliveries(deliveryDAO, orderDAO, deliveryDriverDAO, scanner);
                    break;
                case 8:
                    managePayments(paymentDAO, orderDAO, scanner);
                    break;
                case 9:
                    managePromotions(promotionDAO, menuItemDAO, orderDAO, scanner);
                    break;
                case 10:
                    manageFeedback(feedbackDAO, customerDAO, orderDAO, scanner);
                    break;
                case 11:
                    manageAdmins(adminDAO, scanner);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);

        
    }
    private static void manageMenuItems(MenuItemDAO menuItemDAO, RestaurantDAO restaurantDAO, Scanner scanner) {
        int menuItemChoice;

        do {
            System.out.println("Menu Item Management:");
            System.out.println("1. Add Menu Item");
            System.out.println("2. View Menu Item Details");
            System.out.println("3. Update Menu Item");
            System.out.println("4. Delete Menu Item");
            System.out.println("0. Back to Previous Menu");
            System.out.print("Enter your choice: ");
            menuItemChoice = scanner.nextInt();

            switch (menuItemChoice) {
                case 1:
                    addMenuItem(menuItemDAO, restaurantDAO, scanner);
                    break;
                case 2:
                    viewMenuItem(menuItemDAO, scanner);
                    break;
                case 3:
                    updateMenuItem(menuItemDAO, restaurantDAO, scanner);
                    break;
                case 4:
                    deleteMenuItem(menuItemDAO, scanner);
                    break;
                case 0:
                    System.out.println("Returning to Previous Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (menuItemChoice != 0);
    }

    private static void addMenuItem(MenuItemDAO menuItemDAO, RestaurantDAO restaurantDAO, Scanner scanner) {
        System.out.print("Enter Restaurant ID for the Menu Item: ");
        long restaurantId = scanner.nextLong();

        // Check if the restaurant exists
        if (restaurantDAO.getRestaurantById(restaurantId) == null) {
            System.out.println("Restaurant with ID " + restaurantId + " does not exist.");
            return;
        }

        scanner.nextLine(); // Consume the newline character
        System.out.print("Enter Menu Item Name: ");
        String itemName = scanner.nextLine();

        System.out.print("Enter Menu Item Description: ");
        String itemDescription = scanner.nextLine();

        System.out.print("Enter Menu Item Price: ");
        double itemPrice = scanner.nextDouble();

        scanner.nextLine(); // Consume the newline character
        System.out.print("Enter Menu Item Category: ");
        String itemCategory = scanner.nextLine();

        MenuItem menuItem = new MenuItem();
        menuItem.setRestaurant(restaurantDAO.getRestaurantById(restaurantId));
        menuItem.setName(itemName);
        menuItem.setDescription(itemDescription);
        menuItem.setPrice(itemPrice);
        menuItem.setCategory(itemCategory);

        menuItemDAO.addMenuItem(menuItem);
        System.out.println("Menu Item added successfully!");
    }

    private static void viewMenuItem(MenuItemDAO menuItemDAO, Scanner scanner) {
        System.out.print("Enter Menu Item ID: ");
        long menuItemId = scanner.nextLong();

        MenuItem menuItem = menuItemDAO.getMenuItemById(menuItemId);

        if (menuItem != null) {
            System.out.println("Menu Item Details:");
            System.out.println("ID: " + menuItem.getItemId());
            System.out.println("Name: " + menuItem.getName());
            System.out.println("Description: " + menuItem.getDescription());
            System.out.println("Price: " + menuItem.getPrice());
            System.out.println("Category: " + menuItem.getCategory());
            System.out.println("Restaurant: " + menuItem.getRestaurant().getName());
        } else {
            System.out.println("Menu Item with ID " + menuItemId + " not found.");
        }
    }

    private static void updateMenuItem(MenuItemDAO menuItemDAO, RestaurantDAO restaurantDAO, Scanner scanner) {
        System.out.print("Enter Menu Item ID to update: ");
        long menuItemId = scanner.nextLong();

        MenuItem menuItem = menuItemDAO.getMenuItemById(menuItemId);

        if (menuItem != null) {
            System.out.print("Enter new Menu Item Name (press Enter to keep the current value): ");
            String newName = scanner.nextLine().trim();
            if (!newName.isEmpty()) {
                menuItem.setName(newName);
            }

            System.out.print("Enter new Menu Item Description (press Enter to keep the current value): ");
            String newDescription = scanner.nextLine().trim();
            if (!newDescription.isEmpty()) {
                menuItem.setDescription(newDescription);
            }

            System.out.print("Enter new Menu Item Price (press Enter to keep the current value): ");
            String newPriceString = scanner.nextLine().trim();
            if (!newPriceString.isEmpty()) {
                try {
                    double newPrice = Double.parseDouble(newPriceString);
                    menuItem.setPrice(newPrice);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid price format. Price not updated.");
                }
            }

            System.out.print("Enter new Menu Item Category (press Enter to keep the current value): ");
            String newCategory = scanner.nextLine().trim();
            if (!newCategory.isEmpty()) {
                menuItem.setCategory(newCategory);
            }

            // Update the menu item
            menuItemDAO.updateMenuItem(menuItem);
            System.out.println("Menu Item updated successfully!");
        } else {
            System.out.println("Menu Item with ID " + menuItemId + " not found.");
        }
    }

    private static void deleteMenuItem(MenuItemDAO menuItemDAO, Scanner scanner) {
        System.out.print("Enter Menu Item ID to delete: ");
        long menuItemId = scanner.nextLong();

        MenuItem menuItem = menuItemDAO.getMenuItemById(menuItemId);

        if (menuItem != null) {
            // Delete the menu item
            menuItemDAO.deleteMenuItem(menuItemId);
            System.out.println("Menu Item deleted successfully!");
        } else {
            System.out.println("Menu Item with ID " + menuItemId + " not found.");
        }
    }
    
    private static void manageCustomers(CustomerDAO customerDAO, Scanner scanner) {
        int customerChoice;

        do {
            System.out.println("Customer Management:");
            System.out.println("1. Add Customer");
            System.out.println("2. View Customer Details");
            System.out.println("3. Update Customer");
            System.out.println("4. Delete Customer");
            System.out.println("0. Back to Previous Menu");
            System.out.print("Enter your choice: ");
            customerChoice = scanner.nextInt();

            switch (customerChoice) {
                case 1:
                    addCustomer(customerDAO, scanner);
                    break;
                case 2:
                    viewCustomer(customerDAO, scanner);
                    break;
                case 3:
                    updateCustomer(customerDAO, scanner);
                    break;
                case 4:
                    deleteCustomer(customerDAO, scanner);
                    break;
                case 0:
                    System.out.println("Returning to Previous Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (customerChoice != 0);
    }

    private static void addCustomer(CustomerDAO customerDAO, Scanner scanner) {
        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter Contact Number: ");
        String contactNumber = scanner.nextLine();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Address: ");
        String address = scanner.nextLine();

        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setContactNumber(contactNumber);
        customer.setEmail(email);
        customer.setAddress(address);

        customerDAO.addCustomer(customer);
        System.out.println("Customer added successfully!");
    }

    private static void viewCustomer(CustomerDAO customerDAO, Scanner scanner) {
        System.out.print("Enter Customer ID: ");
        long customerId = scanner.nextLong();

        Customer customer = customerDAO.getCustomerById(customerId);

        if (customer != null) {
            System.out.println("Customer Details:");
            System.out.println("ID: " + customer.getCustomerId());
            System.out.println("First Name: " + customer.getFirstName());
            System.out.println("Last Name: " + customer.getLastName());
            System.out.println("Contact Number: " + customer.getContactNumber());
            System.out.println("Email: " + customer.getEmail());
            System.out.println("Address: " + customer.getAddress());
        } else {
            System.out.println("Customer with ID " + customerId + " not found.");
        }
    }

    private static void updateCustomer(CustomerDAO customerDAO, Scanner scanner) {
        System.out.print("Enter Customer ID to update: ");
        long customerId = scanner.nextLong();

        Customer customer = customerDAO.getCustomerById(customerId);

        if (customer != null) {
            scanner.nextLine(); // Consume the newline character

            System.out.print("Enter new First Name (press Enter to keep the current value): ");
            String newFirstName = scanner.nextLine().trim();
            if (!newFirstName.isEmpty()) {
                customer.setFirstName(newFirstName);
            }

            System.out.print("Enter new Last Name (press Enter to keep the current value): ");
            String newLastName = scanner.nextLine().trim();
            if (!newLastName.isEmpty()) {
                customer.setLastName(newLastName);
            }

            System.out.print("Enter new Contact Number (press Enter to keep the current value): ");
            String newContactNumber = scanner.nextLine().trim();
            if (!newContactNumber.isEmpty()) {
                customer.setContactNumber(newContactNumber);
            }

            System.out.print("Enter new Email (press Enter to keep the current value): ");
            String newEmail = scanner.nextLine().trim();
            if (!newEmail.isEmpty()) {
                customer.setEmail(newEmail);
            }

            System.out.print("Enter new Address (press Enter to keep the current value): ");
            String newAddress = scanner.nextLine().trim();
            if (!newAddress.isEmpty()) {
                customer.setAddress(newAddress);
            }

            // Update the customer
            customerDAO.updateCustomer(customer);
            System.out.println("Customer updated successfully!");
        } else {
            System.out.println("Customer with ID " + customerId + " not found.");
        }
    }

    private static void deleteCustomer(CustomerDAO customerDAO, Scanner scanner) {
        System.out.print("Enter Customer ID to delete: ");
        long customerId = scanner.nextLong();

        Customer customer = customerDAO.getCustomerById(customerId);

        if (customer != null) {
            // Delete the customer
            customerDAO.deleteCustomer(customerId);
            System.out.println("Customer deleted successfully!");
        } else {
            System.out.println("Customer with ID " + customerId + " not found.");
        }
    }
    
    private static void manageOrders(OrderDAO orderDAO, CustomerDAO customerDAO, RestaurantDAO restaurantDAO, Scanner scanner) {
        int orderChoice;

        do {
            System.out.println("Order Management:");
            System.out.println("1. Add Order");
            System.out.println("2. View Order Details");
            System.out.println("3. Update Order");
            System.out.println("4. Delete Order");
            System.out.println("0. Back to Previous Menu");
            System.out.print("Enter your choice: ");
            orderChoice = scanner.nextInt();

            switch (orderChoice) {
                case 1:
                    addOrder(orderDAO, customerDAO, restaurantDAO, scanner);
                    break;
                case 2:
                    viewOrder(orderDAO, scanner);
                    break;
                case 3:
                    updateOrder(orderDAO, customerDAO, restaurantDAO, scanner);
                    break;
                case 4:
                    deleteOrder(orderDAO, scanner);
                    break;
                case 0:
                    System.out.println("Returning to Previous Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (orderChoice != 0);
    }

    private static void addOrder(OrderDAO orderDAO, CustomerDAO customerDAO, RestaurantDAO restaurantDAO, Scanner scanner) {
        System.out.print("Enter Customer ID for the Order: ");
        long customerId = scanner.nextLong();

        // Check if the customer exists
        if (customerDAO.getCustomerById(customerId) == null) {
            System.out.println("Customer with ID " + customerId + " does not exist.");
            return;
        }

        System.out.print("Enter Restaurant ID for the Order: ");
        long restaurantId = scanner.nextLong();

        // Check if the restaurant exists
        if (restaurantDAO.getRestaurantById(restaurantId) == null) {
            System.out.println("Restaurant with ID " + restaurantId + " does not exist.");
            return;
        }

        System.out.print("Enter Order Date (in yyyy-MM-dd format): ");
        String orderDateStr = scanner.next();

        LocalDate orderDate;
        try {
            orderDate = LocalDate.parse(orderDateStr);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Order not added.");
            return;
        }

        Order order = new Order();
        order.setCustomer(customerDAO.getCustomerById(customerId));
        order.setRestaurant(restaurantDAO.getRestaurantById(restaurantId));
        order.setOrderDate(orderDate);
        order.setStatus("Placed"); // Initial status

        orderDAO.addOrder(order);
        System.out.println("Order added successfully!");
    }

    private static void viewOrder(OrderDAO orderDAO, Scanner scanner) {
        System.out.print("Enter Order ID: ");
        long orderId = scanner.nextLong();

        Order order = orderDAO.getOrderById(orderId);

        if (order != null) {
            System.out.println("Order Details:");
            System.out.println("ID: " + order.getOrderId());
            System.out.println("Customer: " + order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName());
            System.out.println("Restaurant: " + order.getRestaurant().getName());
            System.out.println("Order Date: " + order.getOrderDate());
            System.out.println("Status: " + order.getStatus());
            System.out.println("Total Amount: " + order.getTotalAmount());
        } else {
            System.out.println("Order with ID " + orderId + " not found.");
        }
    }

    private static void updateOrder(OrderDAO orderDAO, CustomerDAO customerDAO, RestaurantDAO restaurantDAO, Scanner scanner) {
        System.out.print("Enter Order ID to update: ");
        long orderId = scanner.nextLong();

        Order order = orderDAO.getOrderById(orderId);

        if (order != null) {
            System.out.print("Enter new Customer ID (press Enter to keep the current value): ");
            long newCustomerId;
            try {
                newCustomerId = Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid customer ID format. Order not updated.");
                return;
            }

            // Check if the new customer ID is valid
            if (newCustomerId != 0 && customerDAO.getCustomerById(newCustomerId) == null) {
                System.out.println("Customer with ID " + newCustomerId + " does not exist.");
                return;
            }

            System.out.print("Enter new Restaurant ID (press Enter to keep the current value): ");
            long newRestaurantId;
            try {
                newRestaurantId = Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid restaurant ID format. Order not updated.");
                return;
            }

            // Check if the new restaurant ID is valid
            if (newRestaurantId != 0 && restaurantDAO.getRestaurantById(newRestaurantId) == null) {
                System.out.println("Restaurant with ID " + newRestaurantId + " does not exist.");
                return;
            }

            System.out.print("Enter new Order Date (in yyyy-MM-dd format, press Enter to keep the current value): ");
            String newOrderDateStr = scanner.nextLine().trim();

            LocalDate newOrderDate = null;
            if (!newOrderDateStr.isEmpty()) {
                try {
                    newOrderDate = LocalDate.parse(newOrderDateStr);
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Order not updated.");
                    return;
                }
            }

            // Update the order
            if (newCustomerId != 0) {
                order.setCustomer(customerDAO.getCustomerById(newCustomerId));
            }

            if (newRestaurantId != 0) {
                order.setRestaurant(restaurantDAO.getRestaurantById(newRestaurantId));
            }

            if (newOrderDate != null) {
                order.setOrderDate(newOrderDate);
            }

            orderDAO.updateOrder(order);
            System.out.println("Order updated successfully!");
        } else {
            System.out.println("Order with ID " + orderId + " not found.");
        }
    }

    private static void deleteOrder(OrderDAO orderDAO, Scanner scanner) {
        System.out.print("Enter Order ID to delete: ");
        long orderId = scanner.nextLong();

        Order order = orderDAO.getOrderById(orderId);

        if (order != null) {
            // Delete the order
            orderDAO.deleteOrder(orderId);
            System.out.println("Order deleted successfully!");
        } else {
            System.out.println("Order with ID " + orderId + " not found.");
        }
    }
    
    private static void manageOrderItems(OrderItemDAO orderItemDAO, OrderDAO orderDAO, MenuItemDAO menuItemDAO, Scanner scanner) {
        int orderItemChoice;

        do {
            System.out.println("Order Item Management:");
            System.out.println("1. Add Order Item");
            System.out.println("2. View Order Item Details");
            System.out.println("3. Update Order Item");
            System.out.println("4. Delete Order Item");
            System.out.println("0. Back to Previous Menu");
            System.out.print("Enter your choice: ");
            orderItemChoice = scanner.nextInt();

            switch (orderItemChoice) {
                case 1:
                    addOrderItem(orderItemDAO, orderDAO, menuItemDAO, scanner);
                    break;
                case 2:
                    viewOrderItem(orderItemDAO, scanner);
                    break;
                case 3:
                    updateOrderItem(orderItemDAO, orderDAO, menuItemDAO, scanner);
                    break;
                case 4:
                    deleteOrderItem(orderItemDAO, scanner);
                    break;
                case 0:
                    System.out.println("Returning to Previous Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (orderItemChoice != 0);
    }

    private static void addOrderItem(OrderItemDAO orderItemDAO, OrderDAO orderDAO, MenuItemDAO menuItemDAO, Scanner scanner) {
        System.out.print("Enter Order ID for the Order Item: ");
        long orderId = scanner.nextLong();

        // Check if the order exists
        if (orderDAO.getOrderById(orderId) == null) {
            System.out.println("Order with ID " + orderId + " does not exist.");
            return;
        }

        System.out.print("Enter Menu Item ID for the Order Item: ");
        long menuItemId = scanner.nextLong();

        // Check if the menu item exists
        if (menuItemDAO.getMenuItemById(menuItemId) == null) {
            System.out.println("Menu Item with ID " + menuItemId + " does not exist.");
            return;
        }

        System.out.print("Enter Quantity for the Order Item: ");
        int quantity = scanner.nextInt();

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(orderDAO.getOrderById(orderId));
        orderItem.setMenuItem(menuItemDAO.getMenuItemById(menuItemId));
        orderItem.setQuantity(quantity);

        orderItemDAO.addOrderItem(orderItem);
        System.out.println("Order Item added successfully!");
    }

    private static void viewOrderItem(OrderItemDAO orderItemDAO, Scanner scanner) {
        System.out.print("Enter Order Item ID: ");
        long orderItemId = scanner.nextLong();

        OrderItem orderItem = orderItemDAO.getOrderItemById(orderItemId);

        if (orderItem != null) {
            System.out.println("Order Item Details:");
            System.out.println("ID: " + orderItem.getOrderItemId());
            System.out.println("Order ID: " + orderItem.getOrder().getOrderId());
            System.out.println("Menu Item: " + orderItem.getMenuItem().getName());
            System.out.println("Quantity: " + orderItem.getQuantity());
            System.out.println("Subtotal: " + orderItem.getSubtotal());
        } else {
            System.out.println("Order Item with ID " + orderItemId + " not found.");
        }
    }

    private static void updateOrderItem(OrderItemDAO orderItemDAO, OrderDAO orderDAO, MenuItemDAO menuItemDAO, Scanner scanner) {
        System.out.print("Enter Order Item ID to update: ");
        long orderItemId = scanner.nextLong();

        OrderItem orderItem = orderItemDAO.getOrderItemById(orderItemId);

        if (orderItem != null) {
            System.out.print("Enter new Order ID (press Enter to keep the current value): ");
            long newOrderId;
            try {
                newOrderId = Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid order ID format. Order Item not updated.");
                return;
            }

            // Check if the new order ID is valid
            if (newOrderId != 0 && orderDAO.getOrderById(newOrderId) == null) {
                System.out.println("Order with ID " + newOrderId + " does not exist.");
                return;
            }

            System.out.print("Enter new Menu Item ID (press Enter to keep the current value): ");
            long newMenuItemId;
            try {
                newMenuItemId = Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid menu item ID format. Order Item not updated.");
                return;
            }

            // Check if the new menu item ID is valid
            if (newMenuItemId != 0 && menuItemDAO.getMenuItemById(newMenuItemId) == null) {
                System.out.println("Menu Item with ID " + newMenuItemId + " does not exist.");
                return;
            }

            System.out.print("Enter new Quantity (press Enter to keep the current value): ");
            String newQuantityString = scanner.nextLine().trim();
            if (!newQuantityString.isEmpty()) {
                try {
                    int newQuantity = Integer.parseInt(newQuantityString);
                    orderItem.setQuantity(newQuantity);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid quantity format. Order Item not updated.");
                }
            }

            // Update the order item
            if (newOrderId != 0) {
                orderItem.setOrder(orderDAO.getOrderById(newOrderId));
            }

            if (newMenuItemId != 0) {
                orderItem.setMenuItem(menuItemDAO.getMenuItemById(newMenuItemId));
            }

            orderItemDAO.updateOrderItem(orderItem);
            System.out.println("Order Item updated successfully!");
        } else {
            System.out.println("Order Item with ID " + orderItemId + " not found.");
        }
    }

    private static void deleteOrderItem(OrderItemDAO orderItemDAO, Scanner scanner) {
        System.out.print("Enter Order Item ID to delete: ");
        long orderItemId = scanner.nextLong();

        OrderItem orderItem = orderItemDAO.getOrderItemById(orderItemId);

        if (orderItem != null) {
            // Delete the order item
            orderItemDAO.deleteOrderItem(orderItemId);
            System.out.println("Order Item deleted successfully!");
        } else {
            System.out.println("Order Item with ID " + orderItemId + " not found.");
        }
    }
    
    private static void manageDeliveries(DeliveryDAO deliveryDAO, OrderDAO orderDAO, DeliveryDriverDAO deliveryDriverDAO, Scanner scanner) {
        int deliveryChoice;

        do {
            System.out.println("Delivery Management:");
            System.out.println("1. Add Delivery");
            System.out.println("2. View Delivery Details");
            System.out.println("3. Update Delivery");
            System.out.println("4. Delete Delivery");
            System.out.println("0. Back to Previous Menu");
            System.out.print("Enter your choice: ");
            deliveryChoice = scanner.nextInt();

            switch (deliveryChoice) {
                case 1:
                    addDelivery(deliveryDAO, orderDAO, deliveryDriverDAO, scanner);
                    break;
                case 2:
                    viewDelivery(deliveryDAO, scanner);
                    break;
                case 3:
                    updateDelivery(deliveryDAO, orderDAO, deliveryDriverDAO, scanner);
                    break;
                case 4:
                    deleteDelivery(deliveryDAO, scanner);
                    break;
                case 0:
                    System.out.println("Returning to Previous Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (deliveryChoice != 0);
    }

    private static void addDelivery(DeliveryDAO deliveryDAO, OrderDAO orderDAO, DeliveryDriverDAO deliveryDriverDAO, Scanner scanner) {
        System.out.print("Enter Order ID for the Delivery: ");
        long orderId = scanner.nextLong();

        // Check if the order exists
        Order order = orderDAO.getOrderById(orderId);
        if (order == null) {
            System.out.println("Order with ID " + orderId + " does not exist.");
            return;
        }

        System.out.print("Enter Delivery Driver ID for the Delivery: ");
        long driverId = scanner.nextLong();

        // Check if the delivery driver exists
        DeliveryDriver driver = deliveryDriverDAO.getDeliveryDriverById(driverId);
        if (driver == null) {
            System.out.println("Delivery Driver with ID " + driverId + " does not exist.");
            return;
        }

        System.out.print("Enter Delivery Date (in yyyy-MM-dd format): ");
        String deliveryDateStr = scanner.next();

        LocalDate deliveryDate;
        try {
            deliveryDate = LocalDate.parse(deliveryDateStr);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Delivery not added.");
            return;
        }

        System.out.print("Enter Delivery Status (Out for Delivery/Delivered): ");
        String deliveryStatus = scanner.next();

        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter Delivery Address: ");
        String deliveryAddress = scanner.nextLine();

        Delivery delivery = new Delivery();
        delivery.setOrder(order);
        delivery.setDriver(driver);
        delivery.setDeliveryDate(deliveryDate);
        delivery.setStatus(deliveryStatus);
        delivery.setDeliveryAddress(deliveryAddress);

        deliveryDAO.addDelivery(delivery);
        System.out.println("Delivery added successfully!");
    }

    private static void viewDelivery(DeliveryDAO deliveryDAO, Scanner scanner) {
        System.out.print("Enter Delivery ID: ");
        long deliveryId = scanner.nextLong();

        Delivery delivery = deliveryDAO.getDeliveryById(deliveryId);

        if (delivery != null) {
            System.out.println("Delivery Details:");
            System.out.println("ID: " + delivery.getDeliveryId());
            System.out.println("Order ID: " + delivery.getOrder().getOrderId());
            System.out.println("Driver: " + delivery.getDriver().getFirstName() + " " + delivery.getDriver().getLastName());
            System.out.println("Delivery Date: " + delivery.getDeliveryDate());
            System.out.println("Status: " + delivery.getStatus());
            System.out.println("Delivery Address: " + delivery.getDeliveryAddress());
        } else {
            System.out.println("Delivery with ID " + deliveryId + " not found.");
        }
    }

    private static void updateDelivery(DeliveryDAO deliveryDAO, OrderDAO orderDAO, DeliveryDriverDAO deliveryDriverDAO, Scanner scanner) {
        System.out.print("Enter Delivery ID to update: ");
        long deliveryId = scanner.nextLong();

        Delivery delivery = deliveryDAO.getDeliveryById(deliveryId);

        if (delivery != null) {
            System.out.print("Enter new Order ID (press Enter to keep the current value): ");
            long newOrderId;
            try {
                newOrderId = Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid order ID format. Delivery not updated.");
                return;
            }

            // Check if the new order ID is valid
            if (newOrderId != 0 && orderDAO.getOrderById(newOrderId) == null) {
                System.out.println("Order with ID " + newOrderId + " does not exist.");
                return;
            }

            System.out.print("Enter new Delivery Driver ID (press Enter to keep the current value): ");
            long newDriverId;
            try {
                newDriverId = Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid driver ID format. Delivery not updated.");
                return;
            }

            // Check if the new driver ID is valid
            if (newDriverId != 0 && deliveryDriverDAO.getDeliveryDriverById(newDriverId) == null) {
                System.out.println("Delivery Driver with ID " + newDriverId + " does not exist.");
                return;
            }

            System.out.print("Enter new Delivery Date (in yyyy-MM-dd format, press Enter to keep the current value): ");
            String newDeliveryDateStr = scanner.nextLine().trim();

            LocalDate newDeliveryDate = null;
            if (!newDeliveryDateStr.isEmpty()) {
                try {
                    newDeliveryDate = LocalDate.parse(newDeliveryDateStr);
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Delivery not updated.");
                    return;
                }
            }

            System.out.print("Enter new Delivery Status (Out for Delivery/Delivered, press Enter to keep the current value): ");
            String newDeliveryStatus = scanner.nextLine().trim();

            System.out.print("Enter new Delivery Address (press Enter to keep the current value): ");
            String newDeliveryAddress = scanner.nextLine().trim();

            // Update the delivery
            if (newOrderId != 0) {
                delivery.setOrder(orderDAO.getOrderById(newOrderId));
            }

            if (newDriverId != 0) {
                delivery.setDriver(deliveryDriverDAO.getDeliveryDriverById(newDriverId));
            }

            if (newDeliveryDate != null) {
                delivery.setDeliveryDate(newDeliveryDate);
            }

            if (!newDeliveryStatus.isEmpty()) {
                delivery.setStatus(newDeliveryStatus);
            }

            if (!newDeliveryAddress.isEmpty()) {
                delivery.setDeliveryAddress(newDeliveryAddress);
            }

            deliveryDAO.updateDelivery(delivery);
            System.out.println("Delivery updated successfully!");
        } else {
            System.out.println("Delivery with ID " + deliveryId + " not found.");
        }
    }
    
    private static void managePayments(PaymentDAO paymentDAO, OrderDAO orderDAO, Scanner scanner) {
        int paymentChoice;

        do {
            System.out.println("Payment Management:");
            System.out.println("1. Add Payment");
            System.out.println("2. View Payment Details");
            System.out.println("3. Update Payment");
            System.out.println("4. Delete Payment");
            System.out.println("0. Back to Previous Menu");
            System.out.print("Enter your choice: ");
            paymentChoice = scanner.nextInt();

            switch (paymentChoice) {
                case 1:
                    addPayment(paymentDAO, orderDAO, scanner);
                    break;
                case 2:
                    viewPayment(paymentDAO, scanner);
                    break;
                case 3:
                    updatePayment(paymentDAO, orderDAO, scanner);
                    break;
                case 4:
                    deletePayment(paymentDAO, scanner);
                    break;
                case 0:
                    System.out.println("Returning to Previous Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (paymentChoice != 0);
    }

    private static void addPayment(PaymentDAO paymentDAO, OrderDAO orderDAO, Scanner scanner) {
        System.out.print("Enter Order ID for the Payment: ");
        long orderId = scanner.nextLong();

        // Check if the order exists
        Order order = orderDAO.getOrderById(orderId);
        if (order == null) {
            System.out.println("Order with ID " + orderId + " does not exist.");
            return;
        }

        System.out.print("Enter Payment Amount: ");
        double amount = scanner.nextDouble();

        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter Payment Date (in yyyy-MM-dd format): ");
        String paymentDateStr = scanner.nextLine();

        LocalDate paymentDate;
        try {
            paymentDate = LocalDate.parse(paymentDateStr);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Payment not added.");
            return;
        }

        System.out.print("Enter Payment Status (Paid/Pending): ");
        String paymentStatus = scanner.nextLine();

        System.out.print("Enter Payment Method: ");
        String paymentMethod = scanner.nextLine();

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(amount);
        payment.setPaymentDate(paymentDate);
        payment.setPaymentStatus(paymentStatus);
        payment.setPaymentMethod(paymentMethod);

        paymentDAO.addPayment(payment);
        System.out.println("Payment added successfully!");
    }

    private static void viewPayment(PaymentDAO paymentDAO, Scanner scanner) {
        System.out.print("Enter Payment ID: ");
        long paymentId = scanner.nextLong();

        Payment payment = paymentDAO.getPaymentById(paymentId);

        if (payment != null) {
            System.out.println("Payment Details:");
            System.out.println("ID: " + payment.getPaymentId());
            System.out.println("Order ID: " + payment.getOrder().getOrderId());
            System.out.println("Amount: " + payment.getAmount());
            System.out.println("Payment Date: " + payment.getPaymentDate());
            System.out.println("Payment Status: " + payment.getPaymentStatus());
            System.out.println("Payment Method: " + payment.getPaymentMethod());
        } else {
            System.out.println("Payment with ID " + paymentId + " not found.");
        }
    }

    private static void updatePayment(PaymentDAO paymentDAO, OrderDAO orderDAO, Scanner scanner) {
        System.out.print("Enter Payment ID to update: ");
        long paymentId = scanner.nextLong();

        Payment payment = paymentDAO.getPaymentById(paymentId);

        if (payment != null) {
            System.out.print("Enter new Order ID (press Enter to keep the current value): ");
            long newOrderId;
            try {
                newOrderId = Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid order ID format. Payment not updated.");
                return;
            }

            // Check if the new order ID is valid
            if (newOrderId != 0 && orderDAO.getOrderById(newOrderId) == null) {
                System.out.println("Order with ID " + newOrderId + " does not exist.");
                return;
            }

            System.out.print("Enter new Amount (press Enter to keep the current value): ");
            String newAmountString = scanner.nextLine().trim();
            if (!newAmountString.isEmpty()) {
                try {
                    double newAmount = Double.parseDouble(newAmountString);
                    payment.setAmount(newAmount);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid amount format. Payment not updated.");
                    return;
                }
            }

            System.out.print("Enter new Payment Date (in yyyy-MM-dd format, press Enter to keep the current value): ");
            String newPaymentDateStr = scanner.nextLine().trim();

            LocalDate newPaymentDate = null;
            if (!newPaymentDateStr.isEmpty()) {
                try {
                    newPaymentDate = LocalDate.parse(newPaymentDateStr);
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Payment not updated.");
                    return;
                }
            }

            System.out.print("Enter new Payment Status (Paid/Pending, press Enter to keep the current value): ");
            String newPaymentStatus = scanner.nextLine().trim();

            System.out.print("Enter new Payment Method (press Enter to keep the current value): ");
            String newPaymentMethod = scanner.nextLine().trim();

            // Update the payment
            if (newOrderId != 0) {
                payment.setOrder(orderDAO.getOrderById(newOrderId));
            }

            if (newPaymentDate != null) {
                payment.setPaymentDate(newPaymentDate);
            }

            if (!newPaymentStatus.isEmpty()) {
                payment.setPaymentStatus(newPaymentStatus);
            }

            if (!newPaymentMethod.isEmpty()) {
                payment.setPaymentMethod(newPaymentMethod);
            }

            paymentDAO.updatePayment(payment);
            System.out.println("Payment updated successfully!");
        } else {
            System.out.println("Payment with ID " + paymentId + " not found.");
        }
    }

    private static void deletePayment(PaymentDAO paymentDAO, Scanner scanner) {
        System.out.print("Enter Payment ID to delete: ");
        long paymentId = scanner.nextLong();

        Payment payment = paymentDAO.getPaymentById(paymentId);

        if (payment != null) {
            // Delete the payment
            paymentDAO.deletePayment(paymentId);
            System.out.println("Payment deleted successfully!");
        } else {
            System.out.println("Payment with ID " + paymentId + " not found.");
        }
    }
    
    private static void deleteDelivery(DeliveryDAO deliveryDAO, Scanner scanner) {
        System.out.print("Enter Delivery ID to delete: ");
        long deliveryId = scanner.nextLong();

        Delivery delivery = deliveryDAO.getDeliveryById(deliveryId);

        if (delivery != null) {
            // Delete the delivery
            deliveryDAO.deleteDelivery(deliveryId);
            System.out.println("Delivery deleted successfully!");
        } else {
            System.out.println("Delivery with ID " + deliveryId + " not found.");
        }
    }
    
    private static void managePromotions(PromotionDAO promotionDAO, MenuItemDAO menuItemDAO, OrderDAO orderDAO, Scanner scanner) {
        int promotionChoice;

        do {
            System.out.println("Promotion Management:");
            System.out.println("1. Add Promotion");
            System.out.println("2. View Promotion Details");
            System.out.println("3. Update Promotion");
            System.out.println("4. Delete Promotion");
            System.out.println("0. Back to Previous Menu");
            System.out.print("Enter your choice: ");
            promotionChoice = scanner.nextInt();

            switch (promotionChoice) {
                case 1:
                    addPromotion(promotionDAO, menuItemDAO, orderDAO, scanner);
                    break;
                case 2:
                    viewPromotion(promotionDAO, scanner);
                    break;
                case 3:
                    updatePromotion(promotionDAO, menuItemDAO, orderDAO, scanner);
                    break;
                case 4:
                    deletePromotion(promotionDAO, scanner);
                    break;
                case 0:
                    System.out.println("Returning to Previous Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (promotionChoice != 0);
    }

    private static void addPromotion(PromotionDAO promotionDAO, MenuItemDAO menuItemDAO, OrderDAO orderDAO, Scanner scanner) {
        System.out.print("Enter Promotion Name: ");
        String name = scanner.next();

        System.out.print("Enter Promotion Description: ");
        String description = scanner.next();

        System.out.print("Enter Discount Percentage: ");
        double discountPercentage = scanner.nextDouble();

        System.out.print("Enter Start Date (in yyyy-MM-dd format): ");
        String startDateStr = scanner.next();

        LocalDate startDate;
        try {
            startDate = LocalDate.parse(startDateStr);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Promotion not added.");
            return;
        }

        System.out.print("Enter End Date (in yyyy-MM-dd format): ");
        String endDateStr = scanner.next();

        LocalDate endDate;
        try {
            endDate = LocalDate.parse(endDateStr);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Promotion not added.");
            return;
        }

        System.out.print("Enter Applicable Items (Menu Items, Orders): ");
        String applicableItems = scanner.next();

        Promotion promotion = new Promotion();
        promotion.setName(name);
        promotion.setDescription(description);
        promotion.setDiscountPercentage(discountPercentage);
        promotion.setStartDate(startDate);
        promotion.setEndDate(endDate);
        promotion.setApplicableItems(applicableItems);

        promotionDAO.addPromotion(promotion);
        System.out.println("Promotion added successfully!");
    }

    private static void viewPromotion(PromotionDAO promotionDAO, Scanner scanner) {
        System.out.print("Enter Promotion ID: ");
        long promotionId = scanner.nextLong();

        Promotion promotion = promotionDAO.getPromotionById(promotionId);

        if (promotion != null) {
            System.out.println("Promotion Details:");
            System.out.println("ID: " + promotion.getPromotionId());
            System.out.println("Name: " + promotion.getName());
            System.out.println("Description: " + promotion.getDescription());
            System.out.println("Discount Percentage: " + promotion.getDiscountPercentage());
            System.out.println("Start Date: " + promotion.getStartDate());
            System.out.println("End Date: " + promotion.getEndDate());
            System.out.println("Applicable Items: " + promotion.getApplicableItems());
        } else {
            System.out.println("Promotion with ID " + promotionId + " not found.");
        }
    }

    private static void updatePromotion(PromotionDAO promotionDAO, MenuItemDAO menuItemDAO, OrderDAO orderDAO, Scanner scanner) {
        System.out.print("Enter Promotion ID to update: ");
        long promotionId = scanner.nextLong();

        Promotion promotion = promotionDAO.getPromotionById(promotionId);

        if (promotion != null) {
            System.out.print("Enter new Promotion Name (press Enter to keep the current value): ");
            String newName = scanner.nextLine().trim();

            System.out.print("Enter new Promotion Description (press Enter to keep the current value): ");
            String newDescription = scanner.nextLine().trim();

            System.out.print("Enter new Discount Percentage (press Enter to keep the current value): ");
            String newDiscountPercentageString = scanner.nextLine().trim();
            double newDiscountPercentage;
            if (!newDiscountPercentageString.isEmpty()) {
                try {
                    newDiscountPercentage = Double.parseDouble(newDiscountPercentageString);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid discount percentage format. Promotion not updated.");
                    return;
                }
                promotion.setDiscountPercentage(newDiscountPercentage);
            }

            System.out.print("Enter new Start Date (in yyyy-MM-dd format, press Enter to keep the current value): ");
            String newStartDateStr = scanner.nextLine().trim();
            LocalDate newStartDate = null;
            if (!newStartDateStr.isEmpty()) {
                try {
                    newStartDate = LocalDate.parse(newStartDateStr);
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Promotion not updated.");
                    return;
                }
                promotion.setStartDate(newStartDate);
            }

            System.out.print("Enter new End Date (in yyyy-MM-dd format, press Enter to keep the current value): ");
            String newEndDateStr = scanner.nextLine().trim();
            LocalDate newEndDate = null;
            if (!newEndDateStr.isEmpty()) {
                try {
                    newEndDate = LocalDate.parse(newEndDateStr);
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Promotion not updated.");
                    return;
                }
                promotion.setEndDate(newEndDate);
            }

            System.out.print("Enter new Applicable Items (press Enter to keep the current value): ");
            String newApplicableItems = scanner.nextLine().trim();

            // Update the promotion
            if (!newName.isEmpty()) {
                promotion.setName(newName);
            }

            if (!newDescription.isEmpty()) {
                promotion.setDescription(newDescription);
            }

            if (!newApplicableItems.isEmpty()) {
                promotion.setApplicableItems(newApplicableItems);
            }

            promotionDAO.updatePromotion(promotion);
            System.out.println("Promotion updated successfully!");
        } else {
            System.out.println("Promotion with ID " + promotionId + " not found.");
        }
    }

    private static void deletePromotion(PromotionDAO promotionDAO, Scanner scanner) {
        System.out.print("Enter Promotion ID to delete: ");
        long promotionId = scanner.nextLong();

        Promotion promotion = promotionDAO.getPromotionById(promotionId);

        if (promotion != null) {
            // Delete the promotion
            promotionDAO.deletePromotion(promotionId);
            System.out.println("Promotion deleted successfully!");
        } else {
            System.out.println("Promotion with ID " + promotionId + " not found.");
        }
    }
    
    private static void manageFeedback(FeedbackDAO feedbackDAO, CustomerDAO customerDAO, OrderDAO orderDAO, Scanner scanner) {
        int feedbackChoice;

        do {
            System.out.println("Feedback Management:");
            System.out.println("1. Add Feedback");
            System.out.println("2. View Feedback Details");
            System.out.println("3. Update Feedback");
            System.out.println("4. Delete Feedback");
            System.out.println("0. Back to Previous Menu");
            System.out.print("Enter your choice: ");
            feedbackChoice = scanner.nextInt();

            switch (feedbackChoice) {
                case 1:
                    addFeedback1(feedbackDAO, customerDAO, orderDAO, scanner);
                    break;
                case 2:
                    viewFeedback(feedbackDAO, scanner);
                    break;
                case 3:
                    updateFeedback(feedbackDAO, customerDAO, orderDAO, scanner);
                    break;
                case 4:
                    deleteFeedback(feedbackDAO, scanner);
                    break;
                case 0:
                    System.out.println("Returning to Previous Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (feedbackChoice != 0);
    }

    private static void addFeedback1(FeedbackDAO feedbackDAO, CustomerDAO customerDAO, OrderDAO orderDAO, Scanner scanner) {
        System.out.print("Enter Customer ID: ");
        long customerId = scanner.nextLong();

        // Check if the customer exists
        Customer customer = customerDAO.getCustomerById(customerId);
        if (customer == null) {
            System.out.println("Customer with ID " + customerId + " does not exist.");
            return;
        }

        System.out.print("Enter Order ID: ");
        long orderId = scanner.nextLong();

        // Check if the order exists
        Order order = orderDAO.getOrderById(orderId);
        if (order == null) {
            System.out.println("Order with ID " + orderId + " does not exist.");
            return;
        }

        System.out.print("Enter Rating (1-5): ");
        int rating = scanner.nextInt();

        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter Comments: ");
        String comments = scanner.nextLine();

        Feedback feedback = new Feedback();
        feedback.setCustomer(customer);
        feedback.setOrder(order);
        feedback.setRating(rating);
        feedback.setComments(comments);

        feedbackDAO.addFeedback(feedback);
        System.out.println("Feedback added successfully!");
    }

    private static void viewFeedback(FeedbackDAO feedbackDAO, Scanner scanner) {
        System.out.print("Enter Feedback ID: ");
        long feedbackId = scanner.nextLong();

        Feedback feedback = feedbackDAO.getFeedbackById(feedbackId);

        if (feedback != null) {
            System.out.println("Feedback Details:");
            System.out.println("ID: " + feedback.getFeedbackId());
            System.out.println("Customer ID: " + feedback.getCustomer().getCustomerId());
            System.out.println("Order ID: " + feedback.getOrder().getOrderId());
            System.out.println("Rating: " + feedback.getRating());
            System.out.println("Comments: " + feedback.getComments());
        } else {
            System.out.println("Feedback with ID " + feedbackId + " not found.");
        }
    }

    private static void updateFeedback(FeedbackDAO feedbackDAO, CustomerDAO customerDAO, OrderDAO orderDAO, Scanner scanner) {
        System.out.print("Enter Feedback ID to update: ");
        long feedbackId = scanner.nextLong();

        Feedback feedback = feedbackDAO.getFeedbackById(feedbackId);

        if (feedback != null) {
            System.out.print("Enter new Customer ID (press Enter to keep the current value): ");
            long newCustomerId;
            try {
                newCustomerId = Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid customer ID format. Feedback not updated.");
                return;
            }

            // Check if the new customer ID is valid
            if (newCustomerId != 0 && customerDAO.getCustomerById(newCustomerId) == null) {
                System.out.println("Customer with ID " + newCustomerId + " does not exist.");
                return;
            }

            System.out.print("Enter new Order ID (press Enter to keep the current value): ");
            long newOrderId;
            try {
                newOrderId = Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid order ID format. Feedback not updated.");
                return;
            }

            // Check if the new order ID is valid
            if (newOrderId != 0 && orderDAO.getOrderById(newOrderId) == null) {
                System.out.println("Order with ID " + newOrderId + " does not exist.");
                return;
            }

            System.out.print("Enter new Rating (1-5, press Enter to keep the current value): ");
            String newRatingString = scanner.nextLine().trim();
            int newRating = 0;
            if (!newRatingString.isEmpty()) {
                try {
                    newRating = Integer.parseInt(newRatingString);
                    if (newRating < 1 || newRating > 5) {
                        System.out.println("Invalid rating. Rating must be between 1 and 5. Feedback not updated.");
                        return;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid rating format. Feedback not updated.");
                    return;
                }
            }

            System.out.print("Enter new Comments (press Enter to keep the current value): ");
            String newComments = scanner.nextLine().trim();

            // Update the feedback
            if (newCustomerId != 0) {
                feedback.setCustomer(customerDAO.getCustomerById(newCustomerId));
            }

            if (newOrderId != 0) {
                feedback.setOrder(orderDAO.getOrderById(newOrderId));
            }

            if (!newComments.isEmpty()) {
                feedback.setComments(newComments);
            }

            if (!newRatingString.isEmpty()) {
                feedback.setRating(newRating);
            }

            feedbackDAO.updateFeedback(feedback);
            System.out.println("Feedback updated successfully!");
        } else {
            System.out.println("Feedback with ID " + feedbackId + " not found.");
        }
    }

    private static void deleteFeedback(FeedbackDAO feedbackDAO, Scanner scanner) {
        System.out.print("Enter Feedback ID to delete: ");
        long feedbackId = scanner.nextLong();

        Feedback feedback = feedbackDAO.getFeedbackById(feedbackId);

        if (feedback != null) {
            // Delete the feedback
            feedbackDAO.deleteFeedback(feedbackId);
            System.out.println("Feedback deleted successfully!");
        } else {
            System.out.println("Feedback with ID " + feedbackId + " not found.");
        }
    }
    

    
    private static void manageAdmins(AdminDAO adminDAO, Scanner scanner) {
        int adminChoice;

        do {
            System.out.println("Admin Management:");
            System.out.println("1. Add Admin");
            System.out.println("2. View Admin Details");
            System.out.println("3. Update Admin");
            System.out.println("4. Delete Admin");
            System.out.println("0. Back to Previous Menu");
            System.out.print("Enter your choice: ");
            adminChoice = scanner.nextInt();

            switch (adminChoice) {
                case 1:
                    addAdmin(adminDAO, scanner);
                    break;
                case 2:
                    viewAdmin(adminDAO, scanner);
                    break;
                case 3:
                    updateAdmin(adminDAO, scanner);
                    break;
                case 4:
                    deleteAdmin(adminDAO, scanner);
                    break;
                case 0:
                    System.out.println("Returning to Previous Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (adminChoice != 0);
    }

    private static void addAdmin(AdminDAO adminDAO, Scanner scanner) {
        System.out.print("Enter Username: ");
        String username = scanner.next();

        // Check if the username already exists
        if (adminDAO.getAdminById(username) != null) {
            System.out.println("Admin with username '" + username + "' already exists. Cannot add duplicate admin.");
            return;
        }

        System.out.print("Enter Password: ");
        String password = scanner.next();

        System.out.print("Enter First Name: ");
        String firstName = scanner.next();

        System.out.print("Enter Last Name: ");
        String lastName = scanner.next();

        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(password);
        admin.setFirstName(firstName);
        admin.setLastName(lastName);

        adminDAO.addAdmin(admin);
        System.out.println("Admin added successfully!");
    }

    private static void viewAdmin(AdminDAO adminDAO, Scanner scanner) {
        System.out.print("Enter Admin ID: ");
        long adminId = scanner.nextLong();

        Admin admin = adminDAO.getAdminById(adminId);

        if (admin != null) {
            System.out.println("Admin Details:");
            System.out.println("ID: " + admin.getAdminId());
            System.out.println("Username: " + admin.getUsername());
            System.out.println("First Name: " + admin.getFirstName());
            System.out.println("Last Name: " + admin.getLastName());
        } else {
            System.out.println("Admin with ID " + adminId + " not found.");
        }
    }

    private static void updateAdmin(AdminDAO adminDAO, Scanner scanner) {
        System.out.print("Enter Admin ID to update: ");
        long adminId = scanner.nextLong();

        Admin admin = adminDAO.getAdminById(adminId);

        if (admin != null) {
            System.out.print("Enter new Username (press Enter to keep the current value): ");
            String newUsername = scanner.next().trim();

            // Check if the new username already exists
            if (!newUsername.isEmpty() && adminDAO.getAdminById(newUsername) != null) {
                System.out.println("Admin with username '" + newUsername + "' already exists. Cannot update to a duplicate username.");
                return;
            }

            System.out.print("Enter new Password (press Enter to keep the current value): ");
            String newPassword = scanner.next().trim();

            System.out.print("Enter new First Name (press Enter to keep the current value): ");
            String newFirstName = scanner.next().trim();

            System.out.print("Enter new Last Name (press Enter to keep the current value): ");
            String newLastName = scanner.next().trim();

            // Update the admin
            if (!newUsername.isEmpty()) {
                admin.setUsername(newUsername);
            }

            if (!newPassword.isEmpty()) {
                admin.setPassword(newPassword);
            }

            if (!newFirstName.isEmpty()) {
                admin.setFirstName(newFirstName);
            }

            if (!newLastName.isEmpty()) {
                admin.setLastName(newLastName);
            }

            adminDAO.updateAdmin(admin);
            System.out.println("Admin updated successfully!");
        } else {
            System.out.println("Admin with ID " + adminId + " not found.");
        }
    }

    private static void deleteAdmin(AdminDAO adminDAO, Scanner scanner) {
        System.out.print("Enter Admin ID to delete: ");
        long adminId = scanner.nextLong();

        Admin admin = adminDAO.getAdminById(adminId);

        if (admin != null) {
            // Delete the admin
            adminDAO.deleteAdmin(adminId);
            System.out.println("Admin deleted successfully!");
        } else {
            System.out.println("Admin with ID " + adminId + " not found.");
        }
    }
    
    
    private static void manageRestaurants(RestaurantDAO restaurantDAO, Scanner scanner) {
        while (true) {
            System.out.println("==== Restaurant Menu ====");
            System.out.println("1. Add Restaurant");
            System.out.println("2. View Restaurants");
            System.out.println("3. Update Restaurant");
            System.out.println("4. Delete Restaurant");
            System.out.println("5. Back to Main Menu");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addRestaurant(restaurantDAO);
                    break;
                case 2:
                    viewRestaurants(restaurantDAO);
                    break;
                case 3:
                    updateRestaurant(restaurantDAO, scanner);
                    break;
                case 4:
                    deleteRestaurant(restaurantDAO, scanner);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addRestaurant(RestaurantDAO restaurantDAO) {
        System.out.println("==== Add Restaurant ====");
        Restaurant restaurant = new Restaurant();

        // Collect restaurant attributes like name, cuisine, address, contactNumber, etc.
        System.out.print("Enter Restaurant Name: ");
        Scanner scanner = null;
		String name = scanner.next();
        restaurant.setName(name);

        // Collect other restaurant attributes

        restaurantDAO.addRestaurant(restaurant);
        System.out.println("Restaurant added successfully!");
    }

    private static void viewRestaurants(RestaurantDAO restaurantDAO) {
        System.out.println("==== View Restaurants ====");
        java.util.List<Restaurant> restaurants = restaurantDAO.getAllRestaurants();

        if (!restaurants.isEmpty()) {
            // Display details of all restaurants
            for (Restaurant restaurant : restaurants) {
                System.out.println("Restaurant ID: " + restaurant.getRestaurantId());
                System.out.println("Name: " + restaurant.getName());
                // Display other restaurant attributes
                System.out.println("------------------------------");
            }
        } else {
            System.out.println("No restaurants found!");
        }
    }

    private static void updateRestaurant(RestaurantDAO restaurantDAO, Scanner scanner) {
        System.out.println("==== Update Restaurant ====");
        System.out.print("Enter Restaurant ID: ");
        Long restaurantId = scanner.nextLong();
        Restaurant restaurant = restaurantDAO.getRestaurantById(restaurantId);

        if (restaurant != null) {
            // Prompt user for updated restaurant attributes
            // Update the restaurant object accordingly
            restaurantDAO.updateRestaurant(restaurant);
            System.out.println("Restaurant updated successfully!");
        } else {
            System.out.println("Restaurant not found!");
        }
    }

    private static void deleteRestaurant(RestaurantDAO restaurantDAO, Scanner scanner) {
        System.out.println("==== Delete Restaurant ====");
        System.out.print("Enter Restaurant ID: ");
        Long restaurantId = scanner.nextLong();
        Restaurant restaurant = restaurantDAO.getRestaurantById(restaurantId);

        if (restaurant != null) {
            restaurantDAO.deleteRestaurant(restaurantId);
            System.out.println("Restaurant deleted successfully!");
        } else {
            System.out.println("Restaurant not found!");
        }
    }
    
    private static void manageDeliveryDrivers(DeliveryDriverDAO deliveryDriverDAO, Scanner scanner) {
        while (true) {
            System.out.println("==== Delivery Driver Menu ====");
            System.out.println("1. Add Delivery Driver");
            System.out.println("2. View Delivery Driver");
            System.out.println("3. Update Delivery Driver");
            System.out.println("4. Delete Delivery Driver");
            System.out.println("5. Back to Main Menu");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addDeliveryDriver(deliveryDriverDAO, scanner);
                    break;
                case 2:
                    viewDeliveryDriver(deliveryDriverDAO, scanner);
                    break;
                case 3:
                    updateDeliveryDriver(deliveryDriverDAO, scanner);
                    break;
                case 4:
                    deleteDeliveryDriver(deliveryDriverDAO, scanner);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addDeliveryDriver(DeliveryDriverDAO deliveryDriverDAO, Scanner scanner) {
        System.out.println("==== Add Delivery Driver ====");
        DeliveryDriver deliveryDriver = new DeliveryDriver();

        // Collect delivery driver attributes like first name, last name, contact number, email, vehicle number, and current location
        System.out.print("Enter First Name: ");
        deliveryDriver.setFirstName(scanner.next());

        // Collect other delivery driver attributes

        deliveryDriverDAO.addDeliveryDriver(deliveryDriver);
        System.out.println("Delivery Driver added successfully!");
    }

    private static void viewDeliveryDriver(DeliveryDriverDAO deliveryDriverDAO, Scanner scanner) {
        System.out.println("==== View Delivery Driver ====");
        System.out.print("Enter Delivery Driver ID: ");
        Long driverId = scanner.nextLong();
        DeliveryDriver deliveryDriver = deliveryDriverDAO.getDeliveryDriverById(driverId);

        if (deliveryDriver != null) {
            // Display delivery driver details
            System.out.println("Delivery Driver ID: " + deliveryDriver.getDriverId());
            System.out.println("First Name: " + deliveryDriver.getFirstName());
            // Display other delivery driver attributes
        } else {
            System.out.println("Delivery Driver not found!");
        }
    }

    private static void updateDeliveryDriver(DeliveryDriverDAO deliveryDriverDAO, Scanner scanner) {
        System.out.println("==== Update Delivery Driver ====");
        System.out.print("Enter Delivery Driver ID: ");
        Long driverId = scanner.nextLong();
        DeliveryDriver deliveryDriver = deliveryDriverDAO.getDeliveryDriverById(driverId);

        if (deliveryDriver != null) {
            // Prompt user for updated delivery driver attributes
            // Update the delivery driver object accordingly
            deliveryDriverDAO.updateDeliveryDriver(deliveryDriver);
            System.out.println("Delivery Driver updated successfully!");
        } else {
            System.out.println("Delivery Driver not found!");
        }
    }

    private static void deleteDeliveryDriver(DeliveryDriverDAO deliveryDriverDAO, Scanner scanner) {
        System.out.println("==== Delete Delivery Driver ====");
        System.out.print("Enter Delivery Driver ID: ");
        Long driverId = scanner.nextLong();
        DeliveryDriver deliveryDriver = deliveryDriverDAO.getDeliveryDriverById(driverId);

        if (deliveryDriver != null) {
            deliveryDriverDAO.deleteDeliveryDriver(driverId);
            System.out.println("Delivery Driver deleted successfully!");
        } else {
            System.out.println("Delivery Driver not found!");
        }
    }

}


