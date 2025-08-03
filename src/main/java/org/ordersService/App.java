package org.ordersService;


import org.ordersService.dao.ClientDAO;
import org.ordersService.dao.OrderDAO;
import org.ordersService.dao.ProductDAO;
import org.ordersService.entity.Client;
import org.ordersService.entity.Order;
import org.ordersService.entity.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class App
{
    public static void main( String[] args )
    {
        ClientDAO clientDAO = new ClientDAO();
        ProductDAO productDAO = new ProductDAO();
        OrderDAO orderDAO = new OrderDAO();
        Scanner sc = new Scanner(System.in);
        Client client = auth(sc, clientDAO);
        try {
            while (true) {
                System.out.println("\n=== Welcome to Orders Service ===");
                System.out.println("1: Get all products");
                System.out.println("2: Add product");
                System.out.println("3: Buy product");
                System.out.println("4: Get my orders");
                System.out.println("5: Create new Client");

                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        List<Product> products = productDAO.getAll();
                        for (Product p : products) {
                            System.out.println( p.getId() + ": " + p);
                        }
                        break;
                    case 2:
                        newProduct(sc, productDAO);
                        break;
                    case 3:
                        buyProduct(sc, client, productDAO, orderDAO);
                        break;
                    case 4:
                        List<Order> usersOrders = orderDAO.getAllOrdersByClientId(client.getId());
                        for (Order order : usersOrders) {
                            System.out.println(order);
                        }
                        break;
                    case 5:
                        newClient(sc, clientDAO);
                        break;
                }
            }
        } finally {
            sc.close();
        }
    }

    public static Client auth(Scanner sc, ClientDAO clientDAO) {
    System.out.print("Enter first name: ");
    try {
        String firstName = sc.nextLine();
        System.out.print("Enter last name: ");
        String lastName = sc.nextLine();
        Client client = new Client(firstName, lastName);
        clientDAO.saveClient(client);
        return client;

    } catch (Exception e) {
        e.printStackTrace();
    }
        return null;
    }

    public static void buyProduct(Scanner sc, Client client, ProductDAO productDAO, OrderDAO orderDAO) {
        System.out.print("Enter product ID: ");
        Long productID = sc.nextLong();
        sc.nextLine();
        Product product = productDAO.getById(productID)
                        .orElseThrow(() -> new IllegalArgumentException("Product ID " + productID + " not found."));
        System.out.print("Enter quantity: ");
        int quantity = sc.nextInt();
        sc.nextLine();
        Order order = new Order(product, BigDecimal.valueOf(quantity), client);
        orderDAO.saveOrder(order);
        System.out.println("Order has been saved successfully.");
    }

    public static void  newProduct(Scanner sc, ProductDAO productDAO) {
        System.out.print("Enter name of product:");
        String productName = sc.nextLine();
        System.out.print("Enter price of product:");
        BigDecimal price = sc.nextBigDecimal();
        sc.nextLine();
        Product product = new Product(productName, price);
        productDAO.save(product);
        System.out.println("Product has been saved successfully.");
    }

    public static void newClient(Scanner sc, ClientDAO clientDAO) {
        System.out.print("Enter name of client:");
        String clientName = sc.nextLine();
        System.out.print("Enter last name:");
        String lastName = sc.nextLine();
        Client client = new Client(clientName, lastName);
        clientDAO.saveClient(client);
        System.out.println("Client has been saved successfully.");
    }
}
