package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieImageDto {
    private String uuid;
    private String imgName;
    private String path;

    public String getImageUrl(){
        try{
            return URLEncoder.encode(path+"/"+uuid+"_"+imgName, "UTF-8");
        }catch(UnsupportedEncodingException ex){
            ex.printStackTrace();
        }
        return "";
    }

    public String getThumbnailUrl(){
        try{
            return URLEncoder.encode(path+"/"+"s_"+uuid+"_"+imgName, "UTF-8");
        }catch(UnsupportedEncodingException ex){
            ex.printStackTrace();
        }
        return "";
    }
}
