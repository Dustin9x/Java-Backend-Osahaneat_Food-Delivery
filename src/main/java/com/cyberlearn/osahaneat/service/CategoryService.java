package com.cyberlearn.osahaneat.service;

import com.cyberlearn.osahaneat.dto.CategoryDTO;
import com.cyberlearn.osahaneat.dto.MenuDTO;
import com.cyberlearn.osahaneat.entity.Category;
import com.cyberlearn.osahaneat.entity.Food;
import com.cyberlearn.osahaneat.repository.CategoryRepository;
import com.cyberlearn.osahaneat.service.imp.CategoryServiceImp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService implements CategoryServiceImp {

    @Autowired
    CategoryRepository categoryRepository;
//    @Cacheable("CategoryHome")

    @Autowired
    RedisTemplate redisTemplate;

    private Gson gson = new Gson();
    @Override
    public List<CategoryDTO> getCategoryHomepage() {

        String dataRedis = (String) redisTemplate.opsForValue().get("category");
        List<CategoryDTO> listCategoryDTOS = new ArrayList<>();

        if (dataRedis == null){
            PageRequest pageRequest = PageRequest.of(0,3);
            Page<Category> listCategory = categoryRepository.findAll(pageRequest);
            for (Category data : listCategory) {
                CategoryDTO categoryDTO = new CategoryDTO();
                categoryDTO.setName(data.getNameCate());

                List<MenuDTO> menuDTOS = new ArrayList<>();
                for (Food dataFood : data.getListFood()) {
                    MenuDTO menuDTO = new MenuDTO();
                    menuDTO.setTitle(dataFood.getTitle());
                    menuDTO.setFreeship(dataFood.isFreeship());
                    menuDTO.setImage(dataFood.getImage());

                    menuDTOS.add(menuDTO);
                }
                categoryDTO.setMenus(menuDTOS);

                listCategoryDTOS.add(categoryDTO);
            }
            String dataJson = gson.toJson(listCategoryDTOS);
            redisTemplate.opsForValue().set("category",dataJson);
        } else {
            Type listType = new TypeToken<List<CategoryDTO>>(){}.getType();
            listCategoryDTOS = gson.fromJson(dataRedis,listType);
        }

        return listCategoryDTOS;
    }
}
