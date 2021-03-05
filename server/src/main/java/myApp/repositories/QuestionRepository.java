package myApp.repositories;

import myApp.serverobjects.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("QuestionRepository")
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findAll();

}
