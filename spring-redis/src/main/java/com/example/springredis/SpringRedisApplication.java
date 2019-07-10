package com.example.springredis;

import com.example.springredis.hotkey.HotKey;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringRedisApplication.class, args);
	}


	@RequestMapping("/hotkey/{key}")
	public String getHotKey(@PathVariable String key){
		return HotKey.getHotValue(key);
	}
}
