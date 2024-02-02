package nextstep.subway.lines;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import nextstep.subway.station.Station;
import nextstep.subway.station.StationResponse;

public class LineResponse {

    private Long id;
    private String name;
    private String color;
    private List<StationResponse> stations = new ArrayList<>();

    public LineResponse() {

    }

    public LineResponse(Line line) {
        this.id = line.getId();
        this.name = line.getName();
        this.color = line.getColor();
        this.stations = line.getStations().stream().map(StationResponse::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<StationResponse> getStations() {
        return stations;
    }

    public String getColor() {
        return color;
    }
}
