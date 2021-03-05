package myApp.endpoints;


import myApp.serverobjects.Question;
import myApp.services.DataBaseLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping(path = "question")
public class Questions {

//curl localhost:8080/question/

    private final DataBaseLoader questionService;


    /**
     *
     * 1) GET all questions from a lecture
     * 2) GET all questions
     * 3) POST a question
     * 4) DELETE a question
     * 5) PUT a question (edit)
     *
     */

    @Autowired
    public Questions(DataBaseLoader questionService){
        this.questionService = questionService;
    }

    @GetMapping //question/
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @PostMapping
    public void insertQuestion(@RequestBody String q) {

        long lectureId = 42;
        long userId =   420;
        String text = q;
        int votes = 0;
        SimpleDateFormat time = new SimpleDateFormat();
        String answer = "";

        questionService.insertQuestion(new Question(lectureId,userId,text,votes,time,answer));
    }
}


