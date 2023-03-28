package com.communityserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * TODO:@SpringBootApplication
 *
 */
@SpringBootApplication
/**
 * TODO:@EnableAspectJAutoProxy
 *
 */
@EnableAspectJAutoProxy
/**
 * TODO:@EnableCaching
 *
 */
@EnableCaching
@EnableScheduling
public class communityserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(communityserverApplication.class, args);
	}

}
