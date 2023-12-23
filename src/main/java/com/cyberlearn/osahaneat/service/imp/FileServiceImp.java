package com.cyberlearn.osahaneat.service.imp;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface FileServiceImp {
    boolean savaFile(MultipartFile file);

    Resource loadFile(String filename);
}
