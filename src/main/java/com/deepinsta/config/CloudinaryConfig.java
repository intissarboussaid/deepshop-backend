package com.deepinsta.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class CloudinaryConfig {
	
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dpumuatsh",
                "api_key", "446166275813845",
                "api_secret", "B-JXbvB0DNxrCxCQ9BZiSSe_OnA"));
    }

}
