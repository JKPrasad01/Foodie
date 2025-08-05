package com.example.FoodApp.service.ServiceImpl;

import com.example.FoodApp.Exception.MenuNotFoundException;
import com.example.FoodApp.dto.MenuDTO;
import com.example.FoodApp.entity.Menu;
import com.example.FoodApp.repository.MenuRepository;
import com.example.FoodApp.service.Service.MenuService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;


    @Override
    public MenuDTO createMenu(MenuDTO menuDTO){
        Menu menu=modelMapper.map(menuDTO,Menu.class);
        Menu saved = menuRepository.save(menu);
        return modelMapper.map(saved,MenuDTO.class);
    }

    @Override
    public MenuDTO getMenuById(Long menuId){
        Menu menu=menuRepository.findById(menuId).orElseThrow(()->new MenuNotFoundException(menuId));
        return modelMapper.map(menu,MenuDTO.class);

    }

    @Override
    public List<MenuDTO> getAllMenus(){
        List<Menu> list=menuRepository.findAll();
        return list.stream().map(menu -> modelMapper.map(menu,MenuDTO.class)).toList();
    }

    @Override
    public MenuDTO updateMenu(Long menuId, MenuDTO menuDTO){
        Menu menu=menuRepository.findById(menuId).orElseThrow(()->new MenuNotFoundException(menuId));
        modelMapper.map(menuDTO,menu);
        Menu updated = menuRepository.save(menu);
        return modelMapper.map(updated,MenuDTO.class);
    }

    @Override
    public String deleteMenu(Long menuId){
        Menu menu=menuRepository.findById(menuId).orElseThrow(()->new MenuNotFoundException(menuId));
        menuRepository.delete(menu);
        return "Menu Details successfully Deleted";
    }
}
