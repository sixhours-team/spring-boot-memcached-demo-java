package io.sixhours.memecached.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private CacheManager cacheManager;

	@BeforeEach
	void setUp() {
		cacheManager.getCache("books").clear();
	}

	@Test
	void thatSearchReturnsAllBooks() {
		String expected = "[{\"id\":1,\"title\":\"Kotlin in Action\",\"year\":2017},{\"id\":2,\"title\":\"Spring Boot in Action\",\"year\":2016},{\"id\":3,\"title\":\"Programming Kotlin\",\"year\":2017},{\"id\":4,\"title\":\"Kotlin\",\"year\":2017}]";

		ResponseEntity<String> response = restTemplate.getForEntity("/books", String.class);

		assertThat(response.getBody()).isEqualTo(expected);
	}

	@Test
	void thatSearchByTitleReturnsBook() {
		String expected = "[{\"id\":4,\"title\":\"Kotlin\",\"year\":2017}]";

		ResponseEntity<String> response = restTemplate.getForEntity("/books/Kotlin", String.class);

		assertThat(response.getBody()).isEqualTo(expected);
	}

	@Test
	void thatDeleteAndRecacheReturnsBooksWithoutKotlinTitle() {
		String expected = "[{\"id\":1,\"title\":\"Kotlin in Action\",\"year\":2017},{\"id\":2,\"title\":\"Spring Boot in Action\",\"year\":2016},{\"id\":3,\"title\":\"Programming Kotlin\",\"year\":2017}]";

		restTemplate.delete("/books/Kotlin");

		ResponseEntity<String> response = restTemplate.getForEntity("/books", String.class);

		assertThat(response.getBody()).isEqualTo(expected);
	}

	@Test
	void thatCacheManagerIsLoaded() {
		assertThat(cacheManager).isInstanceOf(ConcurrentMapCacheManager.class);
	}
}
