package com.cyberlearn.osahaneat.service;

import com.cyberlearn.osahaneat.dto.CategoryDTO;
import com.cyberlearn.osahaneat.dto.MenuDTO;
import com.cyberlearn.osahaneat.dto.RestaurantDTO;
import com.cyberlearn.osahaneat.entity.Food;
import com.cyberlearn.osahaneat.entity.MenuRestaurant;
import com.cyberlearn.osahaneat.entity.RatingRestaurant;
import com.cyberlearn.osahaneat.entity.Restaurant;
import com.cyberlearn.osahaneat.repository.RestaurantRepository;
import com.cyberlearn.osahaneat.service.imp.FileServiceImp;
import com.cyberlearn.osahaneat.service.imp.RestauranServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RestaurantService implements RestauranServiceImp {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    FileServiceImp fileServiceImp;
    @Override
    public boolean insertRestaurant(MultipartFile file, String title, String subtitle, String description, boolean is_freeship, String address, String open_date) {
        boolean isInsertSuccess = false;
        try{
            boolean isSaveFileSuccess = fileServiceImp.savaFile(file);
            if(isSaveFileSuccess) {
                Restaurant restaurant = new Restaurant();
                restaurant.setTitle(title);
                restaurant.setSubtitle(subtitle);
                restaurant.setDescription(description);
                restaurant.setAddress(address);
                restaurant.setImage(file.getOriginalFilename());
                restaurant.setFreeShip(is_freeship);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                Date openDate = simpleDateFormat.parse(open_date);
                restaurant.setOpenDate(openDate);

                restaurantRepository.save(restaurant);
                isInsertSuccess = true;
            }
        } catch (Exception e){
            System.out.println("Error create restaurant: " + e.getMessage());
        }

        return isInsertSuccess;
    }

    @Override
    public List<RestaurantDTO> getHomeRestaurants() {
        List<RestaurantDTO> restaurantDTOS = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(0,6);
        Page<Restaurant> listData = restaurantRepository.findAll(pageRequest);

        for(Restaurant data : listData) {
            RestaurantDTO restaurantDTO = new RestaurantDTO();
            restaurantDTO.setId(data.getId());
            restaurantDTO.setImage(data.getImage());
            restaurantDTO.setTitle(data.getTitle());
            restaurantDTO.setSubtitle(data.getSubtitle());
            restaurantDTO.setFreeship(data.isFreeShip());
            restaurantDTO.setRating(calculateRating(data.getListRatingRestaurant()));

            restaurantDTOS.add(restaurantDTO);
        }
        return restaurantDTOS;
    }

    private double calculateRating(Set<RatingRestaurant> listRating){
        double totalPoint = 0;
        for(RatingRestaurant data: listRating) {
            totalPoint += data.getRatePoint();
        }

        return totalPoint/listRating.size();
    }

    @Override
    public RestaurantDTO getDetailRestaurant(int id) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        if (restaurant.isPresent()){
            List<CategoryDTO> categoryDTOList = new ArrayList<>();
            Restaurant data = restaurant.get();

            restaurantDTO.setTitle(data.getTitle());
            restaurantDTO.setSubtitle(data.getSubtitle());
            restaurantDTO.setImage(data.getImage());
            restaurantDTO.setRating(calculateRating(data.getListRatingRestaurant()));
            restaurantDTO.setFreeship(data.isFreeShip());
            restaurantDTO.setOpenDate(data.getOpenDate());
            restaurantDTO.setDescription(data.getDescription());

            for (MenuRestaurant menuRestaurant : data.getListMenuRestaurant()) {
                List<MenuDTO> menuDTOList = new ArrayList<>();
                CategoryDTO categoryDTO = new CategoryDTO();

                categoryDTO.setName(menuRestaurant.getCategory().getNameCate());
                for (Food food: menuRestaurant.getCategory().getListFood()) {
                    MenuDTO menuDTO = new MenuDTO();
                    menuDTO.setId(food.getId());
                    menuDTO.setImage(food.getImage());
                    menuDTO.setTitle(food.getTitle());
                    menuDTO.setFreeship(food.isFreeship());
                    menuDTO.setPrice(food.getPrice());
                    menuDTO.setDescription(food.getDescription());

                    menuDTOList.add(menuDTO);
                }
                categoryDTO.setMenus(menuDTOList);
                categoryDTOList.add(categoryDTO);
            }
            restaurantDTO.setCategories(categoryDTOList);
        }
        return restaurantDTO;
    }
}
