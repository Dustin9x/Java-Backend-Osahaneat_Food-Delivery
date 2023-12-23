package com.cyberlearn.osahaneat.controller;

import com.cyberlearn.osahaneat.payload.ResponseData;
import com.cyberlearn.osahaneat.service.imp.FileServiceImp;
import com.cyberlearn.osahaneat.service.imp.RestauranServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("*")
@RestController
@RequestMapping("/restaurant")
public class RestauranController {

    @Autowired
    FileServiceImp fileServiceImp;

    @Autowired
    RestauranServiceImp restauranServiceImp;

    @PostMapping()
    public ResponseEntity<?> createRestaurant(
            @RequestParam MultipartFile file,
            @RequestParam String title,
            @RequestParam String subtitle,
            @RequestParam String description,
            @RequestParam boolean is_freeship,
            @RequestParam String address,
            @RequestParam String open_date
    ){
        ResponseData responseData = new ResponseData();
        boolean isSuccess = restauranServiceImp.insertRestaurant(file, title, subtitle, description, is_freeship, address, open_date );
        responseData.setData(isSuccess);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> getHomeRestaurants(){
        ResponseData responseData = new ResponseData();
        responseData.setData(restauranServiceImp.getHomeRestaurants());
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/file/{filename:.+}")
    public ResponseEntity<?> getFileRestaurant(@PathVariable String filename){
        Resource resource = fileServiceImp.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }

    @GetMapping("/detail")
    public ResponseEntity<?> getDetailRestaurants(@RequestParam int id){
        ResponseData responseData = new ResponseData();
        responseData.setData(restauranServiceImp.getDetailRestaurant(id));
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
