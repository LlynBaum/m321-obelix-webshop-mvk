package ch.bbw.obelix.quarry;

import ch.bbw.obelix.quarry.api.MenhirDto;
import ch.bbw.obelix.quarry.api.QuarryApi;
import ch.bbw.obelix.quarry.entity.MenhirEntity;
import ch.bbw.obelix.quarry.repository.MenhirRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.experimental.StandardException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MenhirController implements QuarryApi {

    private final MenhirRepository menhirRepository;

    @GetMapping("/api/menhirs")
    public List<MenhirDto> getAllMenhirs() {
        return menhirRepository.findAll()
                .stream().map(MenhirEntity::toDto).toList();
    }

    @GetMapping("/api/menhirs/{menhirId}")
    public MenhirDto getMenhirById(@PathVariable UUID menhirId) {
        return menhirRepository.findById(menhirId)
                .map(MenhirEntity::toDto)
                .orElseThrow(() -> new UnknownMenhirException("unknwon menhir with id " + menhirId));
    }

    @DeleteMapping("/api/quarry/{menhirId}")
    public void deleteById(@PathVariable UUID menhirId) {
        menhirRepository.deleteById(menhirId);
    }

    @PostConstruct
    public void initializeMenhirs() {
        // Only initialize if the database is empty
        if (menhirRepository.count() == 0) {
            createDefaultMenhirs();
        }
    }

    public void createDefaultMenhirs() {
        menhirRepository.deleteAll();

        var obelixSpecial = new MenhirEntity();
        obelixSpecial.setWeight(2.5);
        obelixSpecial.setStoneType("Granite Gaulois");
        obelixSpecial.setDecorativeness(MenhirEntity.Decorativeness.DECORATED);
        obelixSpecial.setDescription("Obelix's personal favorite! Perfect for throwing at Romans. ");
        menhirRepository.save(obelixSpecial);

        var getafixMasterpiece = new MenhirEntity();
        getafixMasterpiece.setWeight(4.2);
        getafixMasterpiece.setStoneType("Mystical Dolmen Stone");
        getafixMasterpiece.setDecorativeness(MenhirEntity.Decorativeness.MASTERWORK);
        getafixMasterpiece.setDescription("Blessed by Getafix himself! This menhir is rumored to " +
                "enhance magic potion brewing. Side effects may include: sudden urge to fight Romans.");
        menhirRepository.save(getafixMasterpiece);

        var touristTrap = new MenhirEntity();
        touristTrap.setWeight(1.0);
        touristTrap.setStoneType("Imported Roman Marble");
        touristTrap.setDecorativeness(MenhirEntity.Decorativeness.PLAIN);
        touristTrap.setDescription("Budget-friendly option! Made from 'liberated' Roman materials. " +
                "Perfect for beginners or those who just want to annoy Caesar. Asterix approved!");
        menhirRepository.save(touristTrap);
    }

    @StandardException
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class UnknownMenhirException extends RuntimeException {}
}
