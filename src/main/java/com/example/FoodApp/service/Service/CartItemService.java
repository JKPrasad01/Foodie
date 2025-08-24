package com.example.FoodApp.service.Service;


import com.example.FoodApp.dto.CartItemDTO;

public interface CartItemService {

    CartItemDTO createCartItem(CartItemDTO cartItemDTO);
    CartItemDTO getCartItemById(Long cartItemId);
}
