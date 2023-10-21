package com.samia.ecole.services;

import com.samia.ecole.exceptions.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;

@Service
public class FileService {
    public String uploadImage(String folderpath, MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        String filenamewithtimestamp = "";
        if(filename.endsWith(".png") || filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
            Date d = new Date();
            filenamewithtimestamp = d.getTime()+"-"+filename;
        }else{
            throw new CustomException("The format is not supported", HttpStatus.BAD_REQUEST);
        }
        String fullfilepah = folderpath+File.separator+filenamewithtimestamp;
        File f = new File(folderpath);
        if(!f.exists()) {
            f.mkdir();
        }
        try{
            byte[] data = file.getBytes();
            FileOutputStream fos = new  FileOutputStream(fullfilepah);
            fos.write(data);
            fos.close();
        }catch (Exception e){
            System.out.println("Error Occured");
        }
        return filenamewithtimestamp;
    }

    public InputStream serveImage(String folderpath, String filename) throws FileNotFoundException {
        String fullfilepath = folderpath+File.separator+filename;
        InputStream inputStream = new FileInputStream(fullfilepath);
        return inputStream;
    }
}
