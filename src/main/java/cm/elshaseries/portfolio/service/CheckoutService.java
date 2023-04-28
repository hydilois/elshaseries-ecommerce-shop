package cm.elshaseries.portfolio.service;

import cm.elshaseries.portfolio.dto.Purchase;
import cm.elshaseries.portfolio.dto.PurchaseResponse;

public interface CheckoutService {
    PurchaseResponse placeOrder(Purchase purchase);
}
