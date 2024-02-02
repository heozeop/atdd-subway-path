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

        final Station 강남역 = new Station("강남역");
        final Station 역삼역 = new Station("역삼역");
        final Station 선릉역 = new Station("선릉역");
        final Line line = new Line("2호선", "green", 강남역.getId(), 역삼역.getId(), 10L);

        final Section 구간1 = new Section(line, 강남역, 역삼역, 10L);
        final Section 구간2 = new Section(line, 역삼역, 선릉역, 10L);

        line.addSection(구간1);
        line.addSection(구간2);

        assertThat(line.getSections()).hasSize(2);
    }

    @Test
    void getStations() {
        // line 인스턴스를 만들고
        // line.getStationIds를 호출하면
        // 해당 노선의 모든 역의 id가 반환된다.
        final Station 강남역 = new Station("강남역");
        final Station 역삼역 = new Station("역삼역");
        final Line line = new Line("2호선", "green", 강남역.getId(), 역삼역.getId(), 10L);
        final Section section = new Section(line, 강남역, 역삼역, 10L);

        line.addSection(section);

        assertThat(line.getStations()).hasSize(2);
    }


    @Test
    void removeSection() {
        // line 인스턴스를 만들고
        // line.removeSection을 호출하면
        // sections에 해당 구간이 삭제 된다.
        final Station 강남역 = new Station("강남역");
        final Station 역삼역 = new Station("역삼역");
        final Station 선릉역 = new Station("선릉역");
        final Line line = new Line("2호선", "green", 강남역.getId(), 역삼역.getId(), 10L);

        final Section 구간1 = new Section(line, 강남역, 역삼역, 10L);
        final Section 구간2 = new Section(line, 역삼역, 선릉역, 10L);

        line.addSection(구간1);
        line.addSection(구간2);

        line.removeSection(구간2);

        assertThat(line.getSections()).hasSize(1);
    }
}
