package nextstep.subway.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import nextstep.subway.lines.LineResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;

@DisplayName("지하철 노선 기능")
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class SubwayLinesAcceptanceTest extends AcceptanceTest {

    private String 일호선 = "일호선";
    private String 이호선 = "이호선";

    private String 빨간색 = "bg-red-600";
    private String 파란색 = "bg-blue-600";


    @BeforeEach
    void setUpEnvironment() {
        StationApiRequester.createStation("강남역");
        StationApiRequester.createStation("역삼역");
    }

    /**
     * When 지하철 노선을 생성하면
     * Then 지하철 노선 목록 조회 시 생성한 노선을 찾을 수 있다
     */
    @Test
    void 지하철노선_생성() {
        // When
        final ExtractableResponse<Response> createResponse =SubwayLineApiRequester.createLines(일호선, 빨간색, 1L, 2L, 10L);

        // Then
        assertThat(createResponse.statusCode()).isEqualTo(HttpStatus.CREATED.value());

        // Then
        final LineResponse createdLines = createResponse.as(LineResponse.class);

        final LineResponse[] linesList = SubwayLineApiRequester.getLinesList().as(LineResponse[].class);

        final LineResponse foundLines = Arrays.stream(linesList)
            .filter(current -> Objects.equals(current.getId(), createdLines.getId()))
            .findFirst().orElse(null);

        assertThat(foundLines).isNotNull();
        isSameLines(foundLines, createdLines);
    }

    /**
     * Given 2개의 지하철 노선을 생성하고
     * When 지하철 노선 목록을 조회하면
     * Then 지하철 노선 목록 조회 시 2개의 노선을 조회할 수 있다.
     */
    @Test
    void 지하철노선_목록_조회() {
        // Given
        SubwayLineApiRequester.createLines(일호선, 빨간색, 1L, 2L, 10L);
        SubwayLineApiRequester.createLines(이호선, 빨간색, 1L, 2L, 10L);

        // When
        final ExtractableResponse<Response> listResponse = SubwayLineApiRequester.getLinesList();

        // Then
        assertThat(listResponse.statusCode()).isEqualTo(HttpStatus.OK.value());

        // Then
        final List<String> linesNameList = listResponse.jsonPath().getList("name", String.class);
        assertThat(linesNameList).contains(일호선, 이호선);
    }

    /**
     * Given 지하철 노선을 생성하고
     * When 생성한 지하철 노선을 조회하면
     * Then 생성한 지하철 노선의 정보를 응답받을 수 있다.
     */
    @Test
    void 지하철노선_조회() {
        // Given
        final LineResponse createdLines = SubwayLineApiRequester.createLines(일호선, 빨간색, 1L, 2L, 10L).as(
            LineResponse.class);

        // When
        final ExtractableResponse<Response> getResponse = SubwayLineApiRequester.getLines(
            createdLines.getId());

        // Then
        assertThat(getResponse.statusCode()).isEqualTo(HttpStatus.OK.value());

        // Then
        final LineResponse foundLines = getResponse.as(LineResponse.class);
        assertThat(foundLines).isNotNull();
        isSameLines(foundLines, createdLines);
    }

    /**
     * Given 지하철 노선을 생성하고
     * When 생성한 지하철 노선을 수정하면
     * Then 해당 지하철 노선 정보는 수정된다
     */
    @Test
    void 지하철노선_수정() {
        // Given
        final LineResponse createdLines = SubwayLineApiRequester.createLines(일호선, 빨간색, 1L, 2L, 10L).as(
            LineResponse.class);

        // When
        final ExtractableResponse<Response> updateResponse = SubwayLineApiRequester.updateLines(createdLines.getId(), 이호선,
            파란색);

        // Then
        assertThat(updateResponse.statusCode()).isEqualTo(HttpStatus.OK.value());

        // Then
        final LineResponse foundLine = SubwayLineApiRequester.getLines(createdLines.getId()).as(LineResponse.class);
        assertThat(foundLine).isNotNull();
        assertThat(foundLine.getName()).isEqualTo(이호선);
        assertThat(foundLine.getColor()).isEqualTo(파란색);
    }

    /**
     * Given 지하철 노선을 생성하고
     * When 생성한 지하철 노선을 삭제하면
     * Then 해당 지하철 노선 정보는 삭제된다
     */
    @Test
    void 지하철노선_삭제() {
        // Given
        final LineResponse createdLines = SubwayLineApiRequester.createLines(일호선, 빨간색, 1L, 2L, 10L)
            .as(LineResponse.class);

        // When
        final ExtractableResponse<Response> deleteResponse = SubwayLineApiRequester.deleteLines(createdLines.getId());

        // Then
        assertThat(deleteResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());

        // Then
        final List<Long> idList = SubwayLineApiRequester.getLinesList().jsonPath().getList("id", Long.class);
        assertThat(idList).doesNotContain(createdLines.getId());
    }

    private static void isSameLines(LineResponse foundLines, LineResponse createdLines) {
        assertThat(foundLines).usingRecursiveComparison().isEqualTo(createdLines);
    }
}
