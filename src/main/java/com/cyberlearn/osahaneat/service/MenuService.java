package com.cyberlearn.osahaneat.service;

import com.cyberlearn.osahaneat.entity.Category;
import com.cyberlearn.osahaneat.entity.Food;
import com.cyberlearn.osahaneat.entity.Restaurant;
import com.cyberlearn.osahaneat.repository.FoodRepository;
import com.cyberlearn.osahaneat.service.imp.FileServiceImp;
import com.cyberlearn.osahaneat.service.imp.MenuServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class MenuService implements MenuServiceImp {

    @Autowired
    FileServiceImp fileServiceImp;

    @Autowired
    FoodRepository foodRepository;
    @Override
    public boolean createMenu(MultipartFile file, String title, boolean is_freeship, String time_ship, double price, int cate_id) {
        boolean isInsertSuccess = false;
        try{
            boolean isSaveFileSuccess = fileServiceImp.savaFile(file);
            if(isSaveFileSuccess) {
                Food food = new Food();
                food.setTitle(title);
                food.setImage(file.getOriginalFilename());
                food.setFreeship(is_freeship);
                food.setTimeShip(time_ship);
                food.setPrice(price);

                Category category = new Category();
                category.setId(cate_id);
                food.setCategory(category);

                foodRepository.save(food);

                isInsertSuccess = true;
            }
        } catch (Exception e){
            System.out.println("Error create Menu: " + e.getMessage());
        }

        return isInsertSuccess;
    }
}
