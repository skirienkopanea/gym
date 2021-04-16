package objects;

public class Record<Exercise, Weight, Sets, Reps, Date> {
    private Exercise exercise;
    private Weight weight;
    private Sets sets;
    private Reps reps;
    private Date date;

    public Record(Exercise exercise, Weight weight, Sets sets, Reps reps, Date date){
        this.exercise = exercise;
        this.sets = sets;
        this.weight = weight;
        this.reps = reps;
        this.date = date;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Weight getWeight() {
        return weight;
    }

    public void setWeight(Weight weight) {
        this.weight = weight;
    }

    public Sets getSets() {
        return sets;
    }

    public void setSets(Sets sets) {
        this.sets = sets;
    }

    public Reps getReps() {
        return reps;
    }

    public void setReps(Reps reps) {
        this.reps = reps;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
