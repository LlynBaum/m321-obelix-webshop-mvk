package ch.bbw.obelix.quarry;

import ch.bbw.obelix.quarry.api.MenhirDto;
import ch.bbw.obelix.quarry.api.QuarryApi;
import ch.bbw.obelix.quarry.service.MenhirService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MenhirController implements QuarryApi {

    private final MenhirService menhirService;

    @GetMapping("/api/menhirs")
    public List<MenhirDto> getAllMenhirs() {
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
