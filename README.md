# 지하철 노선도 미션
[ATDD 강의](https://edu.nextstep.camp/c/R89PYi5H) 실습을 위한 지하철 노선도 애플리케이션

## 1단계 기능 요구사항 정리
### 요구 사항
- 노선에 역 추가시 노선 가운데 추가 할 수 있다.
- 노선에 역 추가시 노선 처음에 추가 할 수 있다.
- 이미 등록되어있는 역은 노선에 등록될 수 없다.

### 인수 조건
- upStation과 downStation 중 하나는 노선에 포함되어 있다.
- upStation과 downStation 중 하나는 노선에 포함되어 있지 않다.
- add시 기존 구간이 잘린다. 

### 인수 시나리오
- 노선에 역 추가시 노선 가운데 추가할 수 있다.
    - Given 1개의 구간을 가진 지하철 노선이 등록되어 있다.
    - When 가운데에 역을 추가한다.
    - Then 다시 조회한 노선의 역은 3개이다.
    - Then 구간은 2개다.
    - Then 역의 거리는 나뉘어져 있다.
- 둘다 기존 노선에 포함되는 경우
    - Given 1개의 구간을 가진 지하철 노선이 등록되어 있다.
    - When 같은 노선을 추가한다.
    - Then 실패한다.
- 둘다 기존 노선에 포함되지 않는 경우
    - Given 1개의 구간을 가진 지하철 노선이 등록되어 있다.
    - When 다른 노선을 추가한다.
    - Then 실패한다.
