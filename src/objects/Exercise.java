package objects;

public class Exercise {
    private Lift lift;
    private String name;
    private int weight;
    private int sets;
    private int reps;
    private String comments;

    public Exercise(Lift lift, int weight, int sets, int reps){
        this.lift = lift;
        this.name = lift.getName();
        this.sets = sets;
        this.weight = weight;
        this.reps = reps;
        this.comments = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Lift getLift() {
        return lift;
    }

    public void setLift(Lift lift) {
        this.lift = lift;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
