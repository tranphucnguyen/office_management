package com.frontend.Ketthuchocphan.service;

import com.frontend.Ketthuchocphan.Repository.CartItemRepository;
import com.frontend.Ketthuchocphan.entity.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    public List<CartItem> getCartItemsByCustomerId(Integer customerId) {
        return cartItemRepository.findByCustomerId(customerId);
    }

    public CartItem getCartItemByCustomerIdAndProductId(Integer customerId, Integer productId) {
        return cartItemRepository.findByCustomerIdAndProductId(customerId, productId);
    }

    public void saveCartItem(CartItem cartItem) {
        cartItemRepository.save(cartItem);
    }

    public void deleteCartItem(Integer cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    public Optional<CartItem> getCartItemById(Integer cartItemId) {
        return cartItemRepository.findById(cartItemId);
    }
    public void updateProductQuantity(Integer customerId,Integer productId, Integer quantity) {
        // Cập nhật số lượng sản phẩm trong giỏ hàng
        cartItemRepository.updateProductQuantity(customerId,productId, quantity);
    }

    public void updateCartItemQuantity(Integer customerId, Integer productId, Integer quantity) {
        CartItem cartItem = cartItemRepository.findByCustomerIdAndProductId(customerId, productId);
        if (cartItem != null) {
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        } else {
            throw new RuntimeException("CartItem not found for Customer ID: " + customerId + " and Product ID: " + productId);
        }
    }

    public void removeCartItem(Integer customerId, Integer productId) {
        CartItem cartItem = cartItemRepository.findByCustomerIdAndProductId(customerId, productId);
        if (cartItem != null) {
            cartItemRepository.delete(cartItem);
        } else {
            throw new RuntimeException("CartItem not found for Customer ID: " + customerId + " and Product ID: " + productId);
        }
    }
}
