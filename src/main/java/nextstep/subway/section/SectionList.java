package nextstep.subway.section;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import nextstep.subway.lines.Line;
import nextstep.subway.station.Station;

@Embeddable
public class SectionList {

    @OneToMany(mappedBy = "line", cascade={CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Section> sections = new ArrayList<>();

    public List<Section> getSections() {
        return sections;
    }

    public void addSection(Section section) {
        sections.add(section);
    }

    public void addSection(Line line,Station upStation, Station downStation, Long distance) {
        sections.add(new Section(line, upStation, downStation, distance));
    }

    public void deleteSection(Section section) {
        sections.remove(section);
    }
}
