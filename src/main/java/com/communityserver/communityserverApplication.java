package com.communityserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @SpringBootApplication 어노테이션은 스프링 부트의 가장 기본적인 설정을 선언
 * 내부를 확인하였을때 중심적으로 3개만 작성하였습니다.
 *
 * @SpringBootConfiguration은 애플리케이션의 구성을 제공하는 어노테이션입니다.
 * - @Configuration 을 통해 @Bean 을 생성하고 Spring Container 에 등록
 * - @Indexed 어노테이션은 @Bean 을 Spring Container 에 등록할 때 색인화(인덱스 설정)와 스테레오 타입으로 설정
 * 	- 스트레오 타입(stereotype) 기본적으로 해당 어노테이션을 선언한 인터페이스를 상속(extends)또는 구현(implements)할 경우 자동으로 기능들이 포함
 *
 * @EnableAutoConfiguration 은 사전에 정의한 라이브러리들을  Bean 으로 등록해 주는 어노테이션입니다.
 * - 사전에 정의한 라이브러리들 모두가 등록되는 것은 아니고 특정 Condition(조건)이 만족될 경우에 Bean 으로 등록
 * - base package 로 정의된 경로의 모든 Bean 을 자동으로 구성
 * - @SpringBootApplication 기준으로 package 를 모든 Bean 으로 자동 구성
 *
 * @ComponentScan 은 @component 어노테이션 및 @Service, @Repository, @Controller 등의 어노테이션을 스캔하여 Bean 으로 등록해주는 어노테이션
 */
/**
 * 위 어노테이션을 사용하면 AOP 를 사용할 수 있습니다.
 * 최상위 패키지에 있는 클래스에 annotation 을 적용해서 AOP 를 찾을 수 있게 도와줍니다.
 */
/**
 * 해당 애플리케이션의 캐싱 기능은 활성화 되어 DB 에서 데이터를 읽어오는 부분에서 다른 어노테이션을 사용해 캐싱 기능을 이용
 */
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableCaching
public class communityserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(communityserverApplication.class, args);
	}

}
