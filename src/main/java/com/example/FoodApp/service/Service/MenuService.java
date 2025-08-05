package com.example.FoodApp.service.Service;

import com.example.FoodApp.dto.MenuDTO;

import java.util.List;

public interface MenuService {
    MenuDTO createMenu(MenuDTO menuDTO);
    MenuDTO getMenuById(Long menuId);
    List<MenuDTO> getAllMenus();
    MenuDTO updateMenu(Long menuId, MenuDTO menuDTO);
    String deleteMenu(Long menuId);
}
