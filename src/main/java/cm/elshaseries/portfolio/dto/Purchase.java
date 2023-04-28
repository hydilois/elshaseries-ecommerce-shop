package cm.elshaseries.portfolio.dto;

import cm.elshaseries.portfolio.entity.Address;
import cm.elshaseries.portfolio.entity.Customer;
import cm.elshaseries.portfolio.entity.Order;
import cm.elshaseries.portfolio.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {
    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;
}
