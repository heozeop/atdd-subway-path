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

    public Line(String name, String color, Station upStation, Station downStation, Long distance) {
        this.name = name;
        this.color = color;
        this.distance = distance;

        upStationId = upStation.getId();
        downStationId = downStation.getId();
        sectionList.addSection(this, upStation, downStation, distance);
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

    public void removeSection(Section section) {
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
        sectionList.addSection(this, upStation, downStation, distance);
        downStationId = downStation.getId();
        this.distance += distance;
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
