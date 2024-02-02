package nextstep.subway.lines;


import java.net.URI;
import java.util.List;
import nextstep.subway.section.SectionAddRequest;
import nextstep.subway.section.SectionDeleteRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LinesController {

    private LineService lineService;

    public LinesController(LineService lineService) {
        this.lineService = lineService;
    }

    @PostMapping("/lines")
    public ResponseEntity<LineResponse> createLines(
        @RequestBody LineCreateRequest lineCreateRequest
    ) {
        LineResponse lines = lineService.saveLine(lineCreateRequest);

        return ResponseEntity.created(URI.create("/lines/" + lines.getId())).body(lines);
    }

    @GetMapping("/lines")
    public ResponseEntity<List<LineResponse>> getLinesList() {
        return ResponseEntity.ok().body(lineService.getLines());
    }

    @GetMapping("/lines/{id}")
    public ResponseEntity<LineResponse> getLines(@PathVariable Long id) {
        return ResponseEntity.ok().body(lineService.getLine(id));
    }

    @PostMapping("/lines/{id}/sections")
    public ResponseEntity<LineResponse> addSection(@PathVariable Long id, @RequestBody
    SectionAddRequest sectionAddRequest) {

        return ResponseEntity.ok().body(lineService.addSection(id, sectionAddRequest));
    }

    @PutMapping("/lines/{id}")
    public ResponseEntity<Void> updateLines(
        @PathVariable Long id,
        @RequestBody LineUpdateRequest lineUpdateRequest
    ) {
        lineService.updateLines(id, lineUpdateRequest);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/lines/{id}")
    public ResponseEntity<Void> deleteLines(@PathVariable Long id) {
        lineService.deleteLines(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/lines/{id}/sections")
    public ResponseEntity<Void> deleteSection(@PathVariable Long id, @RequestParam Long stationId) {
        lineService.deleteSection(id, new SectionDeleteRequest(stationId));

        return ResponseEntity.noContent().build();
    }
}
