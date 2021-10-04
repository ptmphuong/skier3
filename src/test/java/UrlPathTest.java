import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

class UrlPathTest {
    String url = "skiers/12/seasons/2019/days/1/skiers/123";
    String[] urlParts = url.split("/");
    UrlPath pathInfo;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        pathInfo = new UrlPath(urlParts);
    }

    @org.junit.jupiter.api.Test

    void setUpFail1() {
        String url1 = "skiers/12/season/2019/days/1/skiers/123";
        String[] urlParts1 = url1.split("/");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            UrlPath pathInfo1 = new UrlPath(urlParts1);
        });
    }

    @org.junit.jupiter.api.Test
    void getResortID() {
        assertTrue(pathInfo.getResortID().equals(12));
    }

    @org.junit.jupiter.api.Test
    void getSeasonID() {
        assertTrue(pathInfo.getSeasonID().equals("2019"));
    }

    @org.junit.jupiter.api.Test
    void getDayID() {
        assertTrue(pathInfo.getDayID().equals("1"));
    }
    @org.junit.jupiter.api.Test
    void getSkierID() {
        assertTrue(pathInfo.getSkierID().equals(123));
    }
}