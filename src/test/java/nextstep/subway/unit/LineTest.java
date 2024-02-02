package nextstep.subway.unit;

import static org.assertj.core.api.Assertions.assertThat;

import nextstep.subway.lines.Line;
import nextstep.subway.section.Section;
import nextstep.subway.station.Station;
import org.junit.jupiter.api.Test;

class LineTest {
    @Test
    void addSection() {
        // line 인스턴스를 만들고
        // line.addSection을 호출하면
        // sections에 새로운 구간이 추가 된다.

        final Long 강남역Id = new Station("강남역").getId();
        final Long 역삼역Id = new Station("역삼역").getId();
        final Long 선릉역Id = new Station("선릉역").getId();
        final Line line = new Line("2호선", "green", 강남역Id, 역삼역Id, 10L);

        final Section 구간1 = new Section(line, 강남역Id, 역삼역Id, 10L);
        final Section 구간2 = new Section(line, 역삼역Id, 선릉역Id, 10L);

        line.addSection(구간1);
        line.addSection(구간2);

        assertThat(line.getSections()).hasSize(2);
    }

    @Test
    void getStations() {
    }

    @Test
    void removeSection() {
    }
}
