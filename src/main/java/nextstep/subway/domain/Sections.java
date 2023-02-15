package nextstep.subway.domain;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static nextstep.subway.common.constants.ErrorConstant.*;

@Embeddable
public class Sections {

    @OneToMany(mappedBy = "line", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Section> sections = new ArrayList<>();

    public void addSection(Section section) {
        if (sections.isEmpty()) {
            sections.add(section);
            return;
        }

        addValidation(section);

        if (isFrontSection(section) || isLastSection(section)) {
            sections.add(section);
            return;
        }

        addMiddleStation(section);
    }

    private boolean isFrontSection(Section section) {
        return getDownStation().equals(section.getUpStation());
    }

    private boolean isLastSection(Section section) {
        return getUpStation().equals(section.getDownStation());
    }

    private void addMiddleStation(Section newSection) {
        for (Section section : sections) {
            if (addStation(section, newSection)) {
                return;
            }
        }
    }

    private boolean addStation(Section section, Section newSection) {
        return addDownStation(section, newSection) || addUpStation(section, newSection);
    }

    private boolean addDownStation(Section section, Section newSection) {
        if (section.getUpStation().equals(newSection.getUpStation())) {
            sections.add(section.addStation(newSection.getDownStation(), section.getDistance() - newSection.getDistance()));
            return true;
        }

        return false;
    }

    private boolean addUpStation(Section section, Section newSection) {
        if (section.getDownStation().equals(newSection.getDownStation())) {
            sections.add(section.addStation(newSection.getUpStation(), newSection.getDistance()));
            return true;
        }

        return false;
    }

    private void addValidation(Section newSection) {
        checkAlreadyEnrollSection(newSection);
        checkNotFoundSection(newSection);
    }

    private void checkNotFoundSection(Section newSection) {
        if (!(isContainStation(newSection.getUpStation()) || isContainStation(newSection.getDownStation()))) {
            throw new IllegalArgumentException(NOT_ENROLL_STATION);
        }
    }

    private void checkAlreadyEnrollSection(Section newSection) {
        if (isContainStation(newSection.getUpStation()) && isContainStation(newSection.getDownStation())) {
            throw new IllegalArgumentException(ALREADY_ENROLL_STATION);
        }
    }

    private boolean isContainStation(Station newStation) {
        return sections.stream().anyMatch(section -> section.isContainStation(newStation));
    }

    public List<Station> getStations() {
        if (sections.isEmpty()) {
            return Collections.emptyList();
        }

        List<Station> stations = new ArrayList<>();
        stations.add(getUpStation());
        Section next = findSectionByUpStation(getUpStation());
        while (next != null) {
            stations.add(next.getDownStation());
            next = findSectionByUpStation(next.getDownStation());
        }

        return stations;
    }

    private Station getUpStation() {
        Station cur = null;
        Section next = sections.get(0);

        while (next != null) {
            cur = next.getUpStation();
            next = findSectionByDownStation(next.getUpStation());
        }

        return cur;
    }

    private Section findSectionByDownStation(Station downStation) {
        for (Section section : sections) {
            if (section.getDownStation().equals(downStation)) {
                return section;
            }
        }

        return null;
    }

    private Station getDownStation() {
        Station cur = null;
        Section next = sections.get(0);

        while (next != null) {
            cur = next.getDownStation();
            next = findSectionByUpStation(next.getDownStation());
        }

        return cur;
    }

    private Section findSectionByUpStation(Station upStation) {
        for (Section section : sections) {
            if (section.getUpStation().equals(upStation)) {
                return section;
            }
        }

        return null;
    }

    public void removeSection(Station station) {
        removeValidation(station);

        if (getDownStation().equals(station)) {
            sections.remove(findSectionByDownStation(station));
            return;
        }

        if (getUpStation().equals(station)) {
            sections.remove(findSectionByUpStation(station));
            return;
        }

        removeMiddle(station);
    }

    private void removeMiddle(Station station) {
        Section section = findSectionByDownStation(station);
        Section nextSection = findSectionByUpStation(station);
        nextSection.changeUpStation(section.getUpStation());

        sections.remove(section);
    }

    private void removeValidation(Station station) {
        if (sections.size() <= 1) {
            throw new IllegalArgumentException(LESS_THAN_ONE_SECTION);
        }

        if (!isContainStation(station)) {
            throw new IllegalArgumentException(NOT_FOUND_STATION);
        }
    }

    public List<Section> getSections() {
        return sections;
    }
}
