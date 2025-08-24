package com.example.FoodApp.service.ServiceImpl;

import com.example.FoodApp.Exception.RestaurantNotFoundException;
import com.example.FoodApp.dto.MenuDTO;
import com.example.FoodApp.dto.RestaurantDTO;
import com.example.FoodApp.entity.Menu;
import com.example.FoodApp.entity.Restaurant;
import com.example.FoodApp.repository.RestaurantRepository;
import com.example.FoodApp.service.Service.RestaurantService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private static final String UPLOAD_DIR = "uploads/restaurants/";
    private final RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public RestaurantDTO createRestaurant(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = modelMapper.map(restaurantDTO, Restaurant.class);
        Restaurant saved = restaurantRepository.save(restaurant);
        return modelMapper.map(saved, RestaurantDTO.class);
    }

    public RestaurantDTO createRestaurant(RestaurantDTO dto, MultipartFile restaurantImage, MultipartFile[] menuImages) throws IOException {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantName(dto.getRestaurantName());
        restaurant.setCuisineType(dto.getCuisineType());
        restaurant.setRestaurantAddress(dto.getRestaurantAddress());
        restaurant.setRating(dto.getRating());
        restaurant.setOpenOrClosed(dto.getOpenOrClosed());

        // Save restaurant image
        if (restaurantImage != null && !restaurantImage.isEmpty()) {
            String restaurantFileName = System.currentTimeMillis() + "_" + restaurantImage.getOriginalFilename();
            Path restaurantPath = Paths.get(UPLOAD_DIR + restaurantFileName);
            Files.createDirectories(restaurantPath.getParent());
            Files.write(restaurantPath, restaurantImage.getBytes());
            restaurant.setRestaurantProfile("http://localhost:8080/" + UPLOAD_DIR + restaurantFileName);
        }

        // Map MenuDTO -> Menu entities
        if (dto.getMenuList() != null && !dto.getMenuList().isEmpty()) {
            for (int i = 0; i < dto.getMenuList().size(); i++) {
                MenuDTO menuDTO = dto.getMenuList().get(i);
                Menu menu = new Menu();
                menu.setMenuName(menuDTO.getMenuName());
                menu.setDescription(menuDTO.getDescription());
                menu.setPrice(menuDTO.getPrice());
                menu.setRating(menuDTO.getRating());

                // Save menu image if exists
                if (menuImages != null && menuImages.length > i && menuImages[i] != null) {
                    MultipartFile menuImage = menuImages[i];
                    String menuFileName = System.currentTimeMillis() + "_" + menuImage.getOriginalFilename();
                    Path menuPath = Paths.get(UPLOAD_DIR + menuFileName);
                    Files.createDirectories(menuPath.getParent());
                    Files.write(menuPath, menuImage.getBytes());
                    menu.setMenuProfile("http://localhost:8080/" + UPLOAD_DIR + menuFileName);
                }

                restaurant.getMenuList().add(menu);
            }
        }

        Restaurant saved = restaurantRepository.save(restaurant);

        return mapToDTO(saved);
    }

    // Convert Restaurant -> RestaurantDTO
    private RestaurantDTO mapToDTO(Restaurant restaurant) {
        RestaurantDTO dto = new RestaurantDTO();
        dto.setRestaurantId(restaurant.getRestaurantId());
        dto.setRestaurantName(restaurant.getRestaurantName());
        dto.setRestaurantProfile(restaurant.getRestaurantProfile());
        dto.setCuisineType(restaurant.getCuisineType());
        dto.setRestaurantAddress(restaurant.getRestaurantAddress());
        dto.setRating(restaurant.getRating());
        dto.setOpenOrClosed(restaurant.getOpenOrClosed());

        if (restaurant.getMenuList() != null) {
            dto.setMenuList(restaurant.getMenuList().stream().map(menu -> {
                MenuDTO menuDTO = new MenuDTO();
                menuDTO.setMenuId(menu.getMenuId());
                menuDTO.setMenuName(menu.getMenuName());
                menuDTO.setMenuProfile(menu.getMenuProfile());
                menuDTO.setDescription(menu.getDescription());
                menuDTO.setPrice(menu.getPrice());
                menuDTO.setRating(menu.getRating());
                return menuDTO;
            }).collect(Collectors.toList()));
        }

        return dto;
    }

//    @Transactional
//    public RestaurantDTO createRestaurantWithImages(RestaurantDTO restaurantDTO, MultipartFile restaurantImage, List<MultipartFile> menuImages) throws IOException {
//        // Handle restaurant profile image
//        if (restaurantImage != null && !restaurantImage.isEmpty()) {
//            String fileName = UUID.randomUUID().toString() + "_" + restaurantImage.getOriginalFilename();
//            Path filePath = Paths.get("uploads/restaurants/" + fileName);
//            Files.createDirectories(filePath.getParent());
//            Files.write(filePath, restaurantImage.getBytes());
//            restaurantDTO.setRestaurantProfile(filePath.toString());
//        }
//
//        // Handle menu images
//        if (menuImages != null && !menuImages.isEmpty() && restaurantDTO.getMenuList() != null) {
//            for (int i = 0; i < Math.min(menuImages.size(), restaurantDTO.getMenuList().size()); i++) {
//                MultipartFile menuImage = menuImages.get(i);
//                if (menuImage != null && !menuImage.isEmpty()) {
//                    String fileName = UUID.randomUUID().toString() + "_" + menuImage.getOriginalFilename();
//                    Path filePath = Paths.get("uploads/menus/" + fileName);
//                    Files.createDirectories(filePath.getParent());
//                    Files.write(filePath, menuImage.getBytes());
//                    restaurantDTO.getMenuList().get(i).setMenuProfile(filePath.toString());
//                }
//            }
//        }
//
//        // Save to database
//        Restaurant restaurant = modelMapper.map(restaurantDTO, Restaurant.class);
//        Restaurant saved = restaurantRepository.save(restaurant);
//        return modelMapper.map(saved, RestaurantDTO.class);
//    }

    @Override
    public List<RestaurantDTO> createBulkRestaurant(List<RestaurantDTO> restaurantDTO) {
        List<Restaurant> list = restaurantDTO.stream().map(dto -> modelMapper.map(dto, Restaurant.class)).toList();
        List<Restaurant> saved = restaurantRepository.saveAll(list);
        return saved.stream().map(restaurant -> modelMapper.map(restaurant, RestaurantDTO.class)).toList();
    }

    @Override
    public RestaurantDTO getRestaurantById(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
        return modelMapper.map(restaurant, RestaurantDTO.class);
    }

    @Override
    public List<RestaurantDTO> getAllRestaurants() {
        List<Restaurant> list = restaurantRepository.findAll();
        return list.stream().map(restaurant -> modelMapper.map(restaurant, RestaurantDTO.class)).toList();
    }

    @Transactional
    @Override
    public RestaurantDTO updateRestaurant(Long restaurantId, RestaurantDTO restaurantDTO) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
        modelMapper.map(restaurantDTO, restaurant);
        Restaurant update = restaurantRepository.save(restaurant);
        return modelMapper.map(update, RestaurantDTO.class);
    }

    @Override
    public String deleteRestaurantId(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
        restaurantRepository.delete(restaurant);
        return "Restaurant Details successfully Deleted";
    }

    @Override
    public List<MenuDTO> getAllMenu(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
        return restaurant.getMenuList().stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).toList();
    }

//    @Override
//    public void importRestaurantsFromExcel(MultipartFile file) throws IOException {
//        Workbook workbook = new XSSFWorkbook(file.getInputStream());
//
//        Sheet restaurantSheet = workbook.getSheet("Restaurants");
//        Sheet menuSheet = workbook.getSheet("Menus");
//
//        List<RestaurantDTO> restaurants = new ArrayList<>();
//
//        // Read Restaurants
//        for (Row row : restaurantSheet) {
//            if (row.getRowNum() == 0) continue; // Skip header
//            RestaurantDTO restaurant = new RestaurantDTO();
//            restaurant.setRestaurantId((long) row.getCell(0).getNumericCellValue());
//            restaurant.setRestaurantName(row.getCell(1).getStringCellValue());
//            restaurant.setRestaurantProfile(row.getCell(2).getStringCellValue());
//            restaurant.setCuisineType(row.getCell(3).getStringCellValue());
//            restaurant.setRestaurantAddress(row.getCell(4).getStringCellValue());
//            restaurant.setRating(row.getCell(5).getNumericCellValue());
//            restaurant.setOpenOrClosed(row.getCell(6).getBooleanCellValue());
//            restaurants.add(restaurant);
//
//            List<MenuDTO> menus = new ArrayList<>();
//            for (Row row1 : menuSheet) {
//                if (row1.getRowNum() == 0) continue; // Skip header
//                MenuDTO menu = new MenuDTO();
//                menu.setMenuId((long) row1.getCell(0).getNumericCellValue());
//                menu.setMenuName(row1.getCell(1).getStringCellValue());
//                menu.setMenuProfile(row1.getCell(2).getStringCellValue());
//                menu.setRating(row1.getCell(3).getNumericCellValue());
//                menu.setDescription(row1.getCell(4).getStringCellValue());
//                menu.setPrice(row1.getCell(5).getNumericCellValue());
//                menus.add(menu);
//            }
//            restaurants.getLast().setMenuList(menus);
//        }
//
//        List<Restaurant> uploaded = restaurantRepository.saveAll(
//                restaurants.stream().map(restaurantDTO -> modelMapper.map(restaurantDTO, Restaurant.class)).toList());
//        workbook.close();
//    }

    @Transactional
    @Override
    public void saveRestaurantsFromDTOList(List<RestaurantDTO> restaurants) {
        List<Restaurant> entities = restaurants.stream()
                .map(dto -> modelMapper.map(dto, Restaurant.class))
                .toList();
        restaurantRepository.saveAll(entities);
    }
}