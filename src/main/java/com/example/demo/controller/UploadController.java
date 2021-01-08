package com.example.demo.controller;

import com.example.demo.dto.UploadResultDto;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Log4j2
public class UploadController {

    @Value("${file.upload.path}")
    private String uploadPath;

    @PostMapping("/upload")
    public ResponseEntity<List<UploadResultDto>> uploadFile(MultipartFile[] files){

        List<UploadResultDto> uploadResults = new ArrayList<>();

        for(MultipartFile file : files){

            if(file.getContentType().startsWith("image") == false) {
                log.warn("이미지 파일이 아닙니다.");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            String originalName = file.getOriginalFilename();
            String fileName = originalName.substring(originalName.lastIndexOf("//")+1);

            String folderPath = makeFolder();
            String uuid = UUID.randomUUID().toString();

            String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" + fileName;
            Path savePath = Paths.get(saveName);

            try{
                file.transferTo(savePath);

                String thumbnailSaveName = uploadPath + File.separator + folderPath + File.separator + "s_" + uuid + "_" + fileName;
                File thumbnailFile = new File(thumbnailSaveName);
                Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile, 100, 100);

                uploadResults.add(new UploadResultDto(fileName, uuid, folderPath));
            }catch (IOException ex) {
                log.error(ex.getStackTrace());
            }
        }
        return new ResponseEntity<>(uploadResults, HttpStatus.CREATED);
    }

    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(String fileNameEncoded, String size){
        ResponseEntity<byte[]> result = null;
        try {
            String fileName = URLDecoder.decode(fileNameEncoded, "UTF-8");
            File file = new File(uploadPath + File.separator + fileName);

            if(size != null && size.equals("1")){
                file = new File(file.getParent(), file.getName().substring(2));
            }

            HttpHeaders header = new HttpHeaders();
            header.add("Content-Type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @PostMapping("/removeFile")
    public ResponseEntity<Boolean> removeFile(String fileNameEncoded){
        String fileName = "";
        try {
            fileName = URLDecoder.decode(fileNameEncoded, "UTF-8");
            File file = new File(uploadPath + File.separator + fileName);
            boolean result = file.delete();

            File thumbNail = new File(file.getParent(), "s_"+file.getName());
            result = thumbNail.delete();

            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String makeFolder(){
        String dateYMD = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderPath = dateYMD.replace("//", File.separator);
        File uploadFolderPath = new File(uploadPath, folderPath);
        if(uploadFolderPath.exists() == false){
            uploadFolderPath.mkdirs();
        }
        return folderPath;
    }

}
