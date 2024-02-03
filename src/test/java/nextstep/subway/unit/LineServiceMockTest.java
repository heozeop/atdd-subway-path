package nextstep.subway.unit;

import nextstep.subway.lines.LineCreateRequest;
import nextstep.subway.lines.LineRepository;
import nextstep.subway.lines.LineResponse;
import nextstep.subway.lines.LineService;
import nextstep.subway.section.SectionAddRequest;
import nextstep.subway.station.Station;
import nextstep.subway.station.StationRepository;
import nextstep.subway.station.StationRequest;
import nextstep.subway.station.StationResponse;
import nextstep.subway.station.StationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LineServiceMockTest {
    @Mock
    private LineRepository lineRepository;

    @Mock
    private StationRepository stationRepository;
    @Mock
    private StationService stationService;

    @Test
    void addSection() {
        // given
        // lineRepository, stationService stub 설정을 통해 초기값 셋팅
        final StationService stationService = new StationService(stationRepository);
        final LineService lineService = new LineService(lineRepository, stationRepository);

        final Long 강남역 = stationService.saveStation(new StationRequest("강남역")).getId();
        final Long 역삼역 = stationService.saveStation(new StationRequest("역삼역")).getId();
        final Long 선릉역 = stationService.saveStation(new StationRequest("선릉역")).getId();

        final LineResponse lineResponse = lineService.saveLine(new LineCreateRequest("2호선", "green", 강남역, 역삼역, 10L));

        // when
        // lineService.addSection 호출
        lineService.addSection(lineResponse.getId(), new SectionAddRequest(강남역, 역삼역, 10L));

        // then
        // lineService.findLineById 메서드를 통해 검증
        lineService.findLineById(lineResponse.getId());
    }
}
