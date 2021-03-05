package myApp.services;

import myApp.repositories.QuestionRepository;
import myApp.serverobjects.Question;
import myApp.repositories.SongRepository;
import myApp.serverobjects.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Load postgre
 */

@Service
public class DataBaseLoader {
    private final QuestionRepository questionRepository;
    private SongRepository songRepository; //why final?

    @Autowired
    public DataBaseLoader(@Qualifier("QuestionRepository") QuestionRepository questionRepository, SongRepository songRepository) {
        this.questionRepository = questionRepository;
        this.songRepository = songRepository;
    }

    //Now we can make custom method names instead of relying on the JPA names
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public void insertQuestion(Question question) {
        Optional<Question> questionOptional = questionRepository.findById(question.getId());
        if(questionOptional.isPresent()){
            throw new IllegalArgumentException("id already in use");
        }
        questionRepository.save(question);
        System.out.println(question);
    }

    /**
     * Song queries
     */
    public List<Song> getAllSongs(){
        return songRepository.findAll();
    }

    public List<Song> searchSongs(String q){
        return songRepository.findAllByLyricsContains(q);
    }

    public void insertSong(Song s) {
        Optional<Song> songOptional = songRepository.findById(s.getId());
        if(songOptional.isPresent()){
            throw new IllegalArgumentException("id already in use");
        }
        songRepository.save(s);
        System.out.println(s);
    }

}


