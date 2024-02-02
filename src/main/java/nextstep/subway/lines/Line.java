package nextstep.subway.lines;


import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import nextstep.subway.section.Section;
import nextstep.subway.section.SectionList;
import nextstep.subway.station.Station;

@Entity
public class Line {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    private String color;

    private Long upStationId;
    private Long downStationId;
    private Long distance;

    @Embedded
    private SectionList sectionList = new SectionList();

    public Line(String name, String color, Long upStationId, Long downStationId, Long distance) {
        this.name = name;
        this.color = color;
        this.upStationId = upStationId;
        this.downStationId = downStationId;
        this.distance = distance;
    }

    public Line() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public Long getDistance() {
        return distance;
    }

    public void updateLine(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public void deleteSection(Section section) {
        downStationId = section.getDownStationId();
        distance -= section.getDistance();
        sectionList.deleteSection(section);
    }

    public void addSection(Section section) {
        sectionList.addSection(section);
        downStationId = section.getDownStationId();
        distance += section.getDistance();
    }

    public void addSection(Station upStation, Station downStation, Long distance) {
        final Section section = new Section(this, upStation, downStation, distance);

        sectionList.addSection(section);
        downStationId = section.getDownStationId();
        this.distance += section.getDistance();
    }

    public Long getDownStationId() {
        return downStationId;
    }

    public List<Section> getSections() {
        return sectionList.getSections();
    }

    public List<Station> getStations() {
        final Set<Station> stationSet = new HashSet<>();
        sectionList.getSections().forEach(section -> {
            stationSet.addAll(
                Arrays.asList(
                    section.getUpStation(),
                    section.getDownStation()
                )
            );
        });

        return List.copyOf(stationSet);
    }
}
