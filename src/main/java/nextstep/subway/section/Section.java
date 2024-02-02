package nextstep.subway.section;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import nextstep.subway.lines.Line;
import nextstep.subway.station.Station;

@Entity()
public class Section {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lineId")
    private Line line;

    @OneToOne
    @JoinColumn(name = "upStationId")
    private Station upStation;

    @OneToOne
    @JoinColumn(name = "downStationId")
    private Station downStation;

    private Long distance;

    public Section(Line line, Station upStation, Station downStation, Long distance) {
        this.line = line;
        this.upStation = upStation;
        this.downStation = downStation;
        this.distance = distance;
    }

    public Section() {

    }

    public Long getId() {
        return id;
    }

    public Line getLine() {
        return line;
    }

    public Station getUpStation() {
        return upStation;
    }

    public Long getUpStationId() {
        return upStation.getId();
    }

    public Station getDownStation() {
        return downStation;
    }

    public Long getDownStationId() {
        return downStation.getId();
    }

    public Long getDistance() {
        return distance;
    }

}
