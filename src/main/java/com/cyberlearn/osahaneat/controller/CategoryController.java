package com.cyberlearn.osahaneat.controller;

import com.cyberlearn.osahaneat.payload.ResponseData;
import com.cyberlearn.osahaneat.service.imp.CategoryServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    CategoryServiceImp categoryServiceImp;

    @GetMapping()
    public ResponseEntity<?> getHomeCategory(){
        ResponseData responseData = new ResponseData();
        responseData.setData(categoryServiceImp.getCategoryHomepage());
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @CacheEvict(value = "CategoryHome",allEntries = true)
    @GetMapping("/clearcache")
    public String clearCache(){
        return "Clear cache success";
    }

}
