package com.frontend.Ketthuchocphan.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.frontend.Ketthuchocphan.entity.CartItem;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByCustomerId(Integer customerId);

    CartItem findByCustomerIdAndProductId(Integer customerId, Integer productId);

    // Cập nhật số lượng sản phẩm trong giỏ hàng
    @Modifying
    @Query("UPDATE CartItem c SET c.quantity = :quantity WHERE c.product.id = :productId AND c.customer.id = :customerId")
    void updateProductQuantity(Integer customerId, Integer productId, Integer quantity);

    CartItem findByProductId(Integer productId);
}
