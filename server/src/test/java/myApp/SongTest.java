package myApp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import myApp.serverobjects.Song;
import myApp.repositories.SongRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class SongTest {
    @Autowired
    private SongRepository songRepository;

    @Test
    public void saveAndRetrieveQuote() {
        Song song = new Song(
                1,
                "No role modelz",
                "One time for the L.A. sisters",
                "KOD",
                2016
        );
        songRepository.save(song);

        Song song2 = songRepository.getOne((long) 1);
        assertEquals(song, song2);
    }
}