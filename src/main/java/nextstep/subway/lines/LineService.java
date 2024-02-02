package nextstep.subway.lines;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import nextstep.subway.section.SectionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import nextstep.subway.section.Section;
import nextstep.subway.section.SectionAddRequest;
import nextstep.subway.section.SectionDeleteRequest;
import nextstep.subway.station.Station;
import nextstep.subway.station.StationRepository;

@Service
@Transactional(readOnly = true)
public class LineService {

    private final LineRepository lineRepository;
    private final StationRepository stationRepository;
    private final SectionRepository sectionRepository;

    public LineService(LineRepository lineRepository, StationRepository stationRepository, SectionRepository sectionRepository) {
        this.lineRepository = lineRepository;
        this.stationRepository = stationRepository;
        this.sectionRepository = sectionRepository;
    }

    @Transactional
    public LineResponse saveLine(LineCreateRequest lineCreateRequest) {
        final Station upStation = stationRepository.findById(lineCreateRequest.getUpStationId()).orElseThrow(EntityNotFoundException::new);
        final Station downStation = stationRepository.findById(lineCreateRequest.getDownStationId()).orElseThrow(EntityNotFoundException::new);
        final Line line = lineRepository.save(lineCreateRequest.getLine());

        line.addSection(
            upStation,
            downStation,
            lineCreateRequest.getDistance()
        );

        return new LineResponse(line);
    }

    public List<LineResponse> getLines() {
        return lineRepository.findAll().stream().map(this::createLineResponse)
            .collect(Collectors.toList());
    }

    public LineResponse getLine(Long id) {
        final Line line = lineRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        return new LineResponse(line);
    }

    @Transactional
    public void updateLines(Long id, LineUpdateRequest lineUpdateRequest) {
        final Line line = lineRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        line.updateLine(lineUpdateRequest.getName(), lineUpdateRequest.getColor());
    }

    @Transactional
    public void deleteLines(Long id) {
        lineRepository.deleteById(id);
    }

    @Transactional
    public LineResponse addSection(Long id, SectionAddRequest sectionAddRequest) {
        final Line line = lineRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        sectionAddRequest.validateSectionToAdd(line);

        final Station upStation = stationRepository.findById(sectionAddRequest.getUpStationId()).orElseThrow(EntityNotFoundException::new);
        final Station downStation = stationRepository.findById(sectionAddRequest.getDownStationId()).orElseThrow(EntityNotFoundException::new);
        line.addSection(upStation, downStation, sectionAddRequest.getDistance());

        return new LineResponse(line);
    }

    @Transactional
    public void deleteSection(Long id, SectionDeleteRequest sectionDeleteRequest) {
        final Line line = lineRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        sectionDeleteRequest.validateToDelete(line);

        final Section sectionToDelete = sectionRepository.findByLineIdAndDownStationId(id, sectionDeleteRequest.getStationId()).orElseThrow(EntityNotFoundException::new);
        line.removeSection(sectionToDelete);
    }

    private LineResponse createLineResponse(Line line) {
        return new LineResponse(line);
    }

}
