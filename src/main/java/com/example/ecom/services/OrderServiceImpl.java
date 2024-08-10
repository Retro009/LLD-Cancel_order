package com.example.ecom.services;

import com.example.ecom.exceptions.OrderCannotBeCancelledException;
import com.example.ecom.exceptions.OrderDoesNotBelongToUserException;
import com.example.ecom.exceptions.OrderNotFoundException;
import com.example.ecom.exceptions.UserNotFoundException;
import com.example.ecom.models.*;
import com.example.ecom.repositories.InventoryRepository;
import com.example.ecom.repositories.OrderDetailRepository;
import com.example.ecom.repositories.OrderRepository;
import com.example.ecom.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Override
    public Order cancelOrder(int orderId, int userId) throws UserNotFoundException, OrderNotFoundException, OrderDoesNotBelongToUserException, OrderCannotBeCancelledException {
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User Not Found"));
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new OrderNotFoundException("Order Not Found"));

        List<OrderDetail> orderDetails = orderDetailRepository.findByOrder(order);
        if(orderDetails == null || orderDetails.size()==0)
            throw new OrderNotFoundException("Order details not Found");

        if(order.getUser().getId() != userId)
            throw new OrderDoesNotBelongToUserException("Order doesn't belong to the user");

        OrderStatus orderStatus = order.getOrderStatus();

        if(orderStatus == OrderStatus.SHIPPED || orderStatus == OrderStatus.DELIVERED || orderStatus == OrderStatus.CANCELLED)
            throw new OrderCannotBeCancelledException("Order Can't be cancelled at this point");


        for(OrderDetail orderDetail:orderDetails){
            Product product = orderDetail.getProduct();
            int quantity = orderDetail.getQuantity();
            synchronized (product){
                Inventory inventory = inventoryRepository.findByProduct(product).get();
                if(inventory==null)
                    throw new OrderNotFoundException("Inventory Not available for given Product");
                inventory.setQuantity(inventory.getQuantity()+quantity);
            }
        }
        order.setOrderStatus(OrderStatus.CANCELLED);

        return orderRepository.save(order);
    }
}
