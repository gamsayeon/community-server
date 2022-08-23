package com.gamecommunityserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 *
 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class GamecommunityserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GamecommunityserverApplication.class, args);
	}

}
