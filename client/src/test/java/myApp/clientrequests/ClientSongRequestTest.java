package myApp.clientrequests;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;


public class ClientSongRequestTest {

    @Test
    public void testRandomQuote() {
        assertNotNull(SongRequest.getSongs("One time for my L.A. sisters"));
    }
}
