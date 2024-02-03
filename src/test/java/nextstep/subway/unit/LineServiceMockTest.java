package nextstep.subway.unit;

import java.util.Optional;
import nextstep.subway.lines.Line;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LineServiceMockTest {
    @Mock
    private LineRepository lineRepository;

    @Mock
    private StationRepository stationRepository;

    @Test
    void addSection() {
        // given
        // lineRepository, stationService stub 설정을 통해 초기값 셋팅
        final LineService lineService = new LineService(lineRepository, stationRepository);
        final Long 강남역_id = 1L;
        final Long 역삼역_id = 2L;
        final Long 선릉역_id = 3L;
        final Long line_id = 1L;

        final Station 강남역 = new Station(강남역_id,"강남역");
        final Station 역삼역 = new Station(역삼역_id,"역삼역");
        final Station 선릉역 = new Station(선릉역_id,"선릉역");
        final Line line = new Line("2호선", "green", 강남역, 역삼역, 10L);

        when(stationRepository.findById(역삼역_id)).thenReturn(Optional.of(역삼역));
        when(stationRepository.findById(선릉역_id)).thenReturn(Optional.of(선릉역));
        when(lineRepository.findById(line_id)).thenReturn(Optional.of(line));

        // when
        // lineService.addSection 호출
        lineService.addSection(line_id, new SectionAddRequest(역삼역_id, 선릉역_id,10L));

        // then
        // lineService.findLineById 메서드를 통해 검증
        assertThat(lineService.findLineById(line_id).getName()).isEqualTo("2호선");
    }
}
