package cm.elshaseries.portfolio.service;

import cm.elshaseries.portfolio.dao.CustomerRepository;
import cm.elshaseries.portfolio.dto.Purchase;
import cm.elshaseries.portfolio.dto.PurchaseResponse;
import cm.elshaseries.portfolio.entity.Customer;
import cm.elshaseries.portfolio.entity.Order;
import cm.elshaseries.portfolio.entity.OrderItem;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final CustomerRepository customerRepository;

    public CheckoutServiceImpl(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        // retrieve the order info from the dto
        Order order = purchase.getOrder();
        // generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);
        // populate order with orderItems
        Set<OrderItem> orderItems = purchase.getOrderItems();
        //orderItems.forEach(order::add);
        orderItems.forEach(item -> order.add(item));
        // populate order with billingAddress and shippingAddress
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());
        // populate customer with order
        Customer customer = purchase.getCustomer();

        // Check if this is an existing customer
        String theEmail = customer.getEmail();

        Customer customerFromDB = customerRepository.findByEmail(theEmail);

        if (customerFromDB != null){
            // We found them ... Let's assign them accordingly
            customer = customerFromDB;
        }

        customer.add(order);
        // save to the database
        customerRepository.save(customer);
        // return a responserm
        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {
        // generate a random UUID number(UUID version 4)
        return UUID.randomUUID().toString();
    }
}
