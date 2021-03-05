package myApp.repositories;

import myApp.serverobjects.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//You can see that we have a Song with ID of type Long

//Define db query for the SongRepository. This is a JPA based interface
//you can make up methods and JPA will create them

@Repository("SongRepository")
public interface SongRepository extends JpaRepository<Song, Long> {
    List<Song> findAllByLyricsContains(String query);  //This is an interface method. Therefore it does not have a body

    //Other examples
    List<Song> findAllByIdEquals(int q);
    List<Song> findAllByYearEquals(int q);

    List<Song> findAllByNameContains(String query);
    List<Song> findAllByNameEquals(String query);

    List<Song> findAllByAlbumContains(String query);
    List<Song> findAllByAlbumEquals(String query);
}
