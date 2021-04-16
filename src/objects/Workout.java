package objects;

import java.util.ArrayList;
import java.util.Date;

public class Workout {
    private Date date;
    private String notes;
    private ArrayList<Record<Exercise,Integer,Integer,Integer,Date>> recordsList;
}
