package ch.bbw.obelix.quarry.api;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

public interface QuarryApi {
    @GetMapping("/api/menhirs")
    List<MenhirDto> getAllMenhirs();

    @GetMapping("/api/menhirs/{menhirId}")
    MenhirDto getMenhirById(@PathVariable UUID menhirId);

    @DeleteMapping("/api/quarry/{menhirId}")
    void deleteById(@PathVariable UUID menhirId);
}
