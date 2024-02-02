package nextstep.subway.section;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import nextstep.subway.lines.Line;

public class SectionAddRequest {
    private Long upStationId;
    private Long downStationId;

    private Long distance;

    public SectionAddRequest(Long upStationId, Long downStationId, Long distance) {
        this.upStationId = upStationId;
        this.downStationId = downStationId;
        this.distance = distance;
    }

    public Long getUpStationId() {
        return upStationId;
    }

    public Long getDownStationId() {
        return downStationId;
    }

    public Long getDistance() {
        return distance;
    }

    public void validateSectionToAdd(Line line) {
        final Set<Long> stationIdSet = new HashSet<>();
        line.getSections().forEach(section -> {
            stationIdSet.add(section.getUpStationId());
            stationIdSet.add(section.getDownStationId());
        });

        if (stationIdSet.contains(downStationId)) {
            throw new IllegalArgumentException();
        }

        if (!Objects.equals(upStationId, line.getDownStationId())) {
            throw new IllegalArgumentException();
        }
    }
}
