package ch.bbw.obelix.webshop.controller;

import java.util.List;
import java.util.UUID;

import ch.bbw.obelix.quarry.api.MenhirDto;
import ch.bbw.obelix.webshop.dto.BasketDto;
import ch.bbw.obelix.webshop.service.MenhirWebClientService;
import ch.bbw.obelix.webshop.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ObelixWebshopController {

	private final BasketService obelixWebshopService;

	private final MenhirWebClientService menhirWebClientService;

	@GetMapping("/api")
	public String welcome() {
		return "Welcome to Obelix's Menhir Shop! The finest menhirs in all of Gaul! Ces Romains sont fous!";
	}

	@GetMapping("/api/menhirs")
	public List<MenhirDto> getAllMenhirs() {
		return menhirWebClientService.createClient().getAllMenhirs();
	}

	@GetMapping("/api/menhirs/{menhirId}")
	public MenhirDto getMenhirById(@PathVariable UUID menhirId) {
		return menhirWebClientService.createClient().getMenhirById(menhirId);
	}

	/**
	 * Note that this should only be called by Asterix himself.
	 * Hopefully, no customer will ever find this endpoint...
	 */
	@DeleteMapping("/api/quarry/{menhirId}")
	public void deleteById(@PathVariable UUID menhirId) {
		menhirWebClientService.createClient().deleteById(menhirId);
	}

	/**
	 * Customer adds even more shinies in exchange for a beautiful menhir.
	 */
	@PutMapping("/api/basket/offer")
	public BasketDto offer(@RequestBody BasketDto.BasketItem basketItem) {
		return obelixWebshopService.offer(basketItem);
	}

	/**
	 * In case the customer doesn't want to offer more and leaves.
	 */
	@DeleteMapping("/api/basket")
	public void leave() {
		obelixWebshopService.leave();
	}

	/**
	 * Decide if the current basket is worthy enough for a beautiful menhir.
	 *
	 * @param menhirId the menhir to buy
	 * @throws BasketService.BadOfferException in case the basket is tiny
	 */
	@PostMapping("/api/basket/buy/{menhirId}")
	public void exchangeFor(@PathVariable UUID menhirId) {
		obelixWebshopService.exchange(menhirId);
	}
}
