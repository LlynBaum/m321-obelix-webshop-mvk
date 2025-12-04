package ch.bbw.obelix.webshop;

import ch.bbw.obelix.quarry.api.MenhirDto;
import ch.bbw.obelix.webshop.dto.BasketDto;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;

class ObelixWebshopApplicationTests {

	private final WebTestClient webTestClient = createWebTestClient();

	@Test
	void buyMenhir() {
		var anyId = webTestClient.get()
				.uri("/api/menhirs")
				.exchange()
				.expectBodyList(MenhirDto.class)
				.returnResult()
				.getResponseBody()
				.getFirst()
				.id();
		webTestClient.post().uri("/api/basket/buy/{id}", anyId).exchange().expectStatus().isBadRequest();
		webTestClient.put().uri("/api/basket/offer").bodyValue(new BasketDto.BasketItem("boar", 2)).exchange().expectStatus().isOk();
		webTestClient.post().uri("/api/basket/buy/{id}", anyId).exchange().expectStatus().isOk();
		webTestClient.post().uri("/api/basket/buy/{id}", anyId).exchange().expectStatus().isBadRequest();
	}

	private static WebTestClient createWebTestClient(){
		return WebTestClient.bindToServer()
			.baseUrl("http://localhost:8080")
			.build();
	}
}
