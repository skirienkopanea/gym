package myApp.serverobjects;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Objects;

@Entity
@Table(name = "question")
public class Question {

    @Id
    private long  id;

    //FK
    private long  lectureId;

    //FK
    private long  userId;

    private String text;
    private int votes;
    private SimpleDateFormat time;
    private String answer;

    public Question() {
    }

    public Question(long  id, long  lectureId, long  userId, String text, int votes, SimpleDateFormat time, String answer) {
        this.id = id;
        this.lectureId = lectureId;
        this.userId = userId;
        this.text = text;
        this.votes = votes;
        this.time = time;
        this.answer = answer;
    }

    public Question(long  lectureId, long  userId, String text, int votes, SimpleDateFormat time, String answer) {
        this.lectureId = lectureId;
        this.userId = userId;
        this.text = text;
        this.votes = votes;
        this.time = time;
        this.answer = answer;
    }

    public long  getId() {
        return id;
    }

    public void setId(long  id) {
        this.id = id;
    }

    public long  getLectureId() {
        return lectureId;
    }

    public void setLectureId(long  lectureId) {
        this.lectureId = lectureId;
    }

    public long  getUserId() {
        return userId;
    }

    public void setUserId(long  userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public SimpleDateFormat getTime() {
        return time;
    }

    public void setTime(SimpleDateFormat time) {
        this.time = time;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", lectureId=" + lectureId +
                ", userId=" + userId +
                ", text='" + text + '\'' +
                ", votes=" + votes +
                ", time=" + time +
                ", answer='" + answer + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;
        Question question = (Question) o;
        return getId() == question.getId() &&
                getLectureId() == question.getLectureId() &&
                getUserId() == question.getUserId() &&
                getVotes() == question.getVotes() &&
                Objects.equals(getText(), question.getText()) &&
                Objects.equals(getTime(), question.getTime()) &&
                Objects.equals(getAnswer(), question.getAnswer());
    }
}
