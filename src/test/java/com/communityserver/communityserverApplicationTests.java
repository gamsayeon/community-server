package com.communityserver;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
/**
 * @SpringBootApplication 을 찾아서 테스트를 위한 Bean 을 생성한다.
 * @MockBean 으로 정의된 Bean 을 찾아서 대체시킨다.
 * @RunWith(SpringRunner.class) 와 같이 정의하여야 동작한다. (Junit5에서 생략가능)
 * 통합 테스틑를 제공하는 기본적인 스프링부트 테스트 어노테이션
 */
class communityserverApplicationTests {

	@Test
	void contextLoads() {

	}

}
