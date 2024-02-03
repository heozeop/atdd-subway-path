package nextstep.subway.unit;

import static org.assertj.core.api.Assertions.assertThat;

import nextstep.subway.lines.Line;
import nextstep.subway.lines.LineRepository;
import nextstep.subway.lines.LineService;
import nextstep.subway.section.SectionAddRequest;
import nextstep.subway.station.Station;
import nextstep.subway.station.StationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class LineServiceTest {
    @Autowired
    private StationRepository stationRepository;
    @Autowired
    private LineRepository lineRepository;

    @Autowired
    private LineService lineService;

    @Test
    void addSection() {
        // given
        // stationRepository와 lineRepository를 활용하여 초기값 셋팅
        final Station 강남역 = stationRepository.save(new Station("강남역"));
        final Station 역삼역 = stationRepository.save(new Station("역삼역"));
        final Station 선릉역 = stationRepository.save(new Station("선릉역"));

        final Line line = lineRepository.save(new Line("2호선", "green", 강남역, 역삼역, 10L));

        // when
        // lineService.addSection 호출
        lineService.addSection(line.getId(), new SectionAddRequest(역삼역.getId(), 선릉역.getId(), 10L));


        // then
        // line.getSections 메서드를 통해 검증
        assertThat(line.getSections()).hasSize(2);
    }
}
