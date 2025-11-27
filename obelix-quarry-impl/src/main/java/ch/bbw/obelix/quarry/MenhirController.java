package ch.bbw.obelix.quarry;

import ch.bbw.obelix.quarry.api.MenhirDto;
import ch.bbw.obelix.quarry.api.QuarryApi;
import ch.bbw.obelix.quarry.service.MenhirService;
import io.micrometer.core.instrument.Gauge;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import io.micrometer.core.instrument.MeterRegistry;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MenhirController implements QuarryApi {

    private final MenhirService menhirService;
    private final MeterRegistry meterRegistry;

    @GetMapping("/api/menhirs")
    public List<MenhirDto> getAllMenhirs() {
        Gauge.builder("menhir_count", menhirService::countMenhirs)
                .description("A current number of menhirs in the system")
                .register(meterRegistry);

        return menhirService.getAllMenhirs();
    }

    @GetMapping("/api/menhirs/{menhirId}")
    public MenhirDto getMenhirById(@PathVariable UUID menhirId) {
        return menhirService.getMenhirById(menhirId);
    }

    @DeleteMapping("/api/quarry/{menhirId}")
    public void deleteById(@PathVariable UUID menhirId) {
        menhirService.deleteById(menhirId);
    }
}
