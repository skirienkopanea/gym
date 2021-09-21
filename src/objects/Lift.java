package objects;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Lift {
    private String id;
    private String name;
    private String description;
    private double protraction;
    private double retraction;
    private double upwardRotation;
    private double downwardRotation;
    private double depression;
    private double elevation;
    private double internalRotation;
    private double externalRotation;
    private double hamstringEmphasis;
    private double quadricepsEmphasis;
    private String ratioReferenceExercise;
    private double ratioOverReference;
    private ArrayList<String> sources;

    public Lift(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getProtraction() {
        return protraction;
    }

    public void setProtraction(double protraction) {
        this.protraction = protraction;
    }

    public double getRetraction() {
        return retraction;
    }

    public void setRetraction(double retraction) {
        this.retraction = retraction;
    }

    public double getUpwardRotation() {
        return upwardRotation;
    }

    public void setUpwardRotation(double upwardRotation) {
        this.upwardRotation = upwardRotation;
    }

    public double getDownwardRotation() {
        return downwardRotation;
    }

    public void setDownwardRotation(double downwardRotation) {
        this.downwardRotation = downwardRotation;
    }

    public double getDepression() {
        return depression;
    }

    public void setDepression(double depression) {
        this.depression = depression;
    }

    public double getElevation() {
        return elevation;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    public double getInternalRotation() {
        return internalRotation;
    }

    public void setInternalRotation(double internalRotation) {
        this.internalRotation = internalRotation;
    }

    public double getExternalRotation() {
        return externalRotation;
    }

    public void setExternalRotation(double externalRotation) {
        this.externalRotation = externalRotation;
    }

    public double getHamstringEmphasis() {
        return hamstringEmphasis;
    }

    public void setHamstringEmphasis(double hamstringEmphasis) {
        this.hamstringEmphasis = hamstringEmphasis;
    }

    public double getQuadricepsEmphasis() {
        return quadricepsEmphasis;
    }

    public void setQuadricepsEmphasis(double quadricepsEmphasis) {
        this.quadricepsEmphasis = quadricepsEmphasis;
    }

    public String getRatioReferenceExercise() {
        return ratioReferenceExercise;
    }

    public void setRatioReferenceExercise(String ratioReferenceExercise) {
        this.ratioReferenceExercise = ratioReferenceExercise;
    }

    public double getRatioOverReference() {
        return ratioOverReference;
    }

    public void setRatioOverReference(double ratioOverReference) {
        this.ratioOverReference = ratioOverReference;
    }

    public ArrayList<String> getSources() {
        return sources;
    }

    public void setSources(ArrayList<String> sources) {
        this.sources = sources;
    }

    @Override
    public String toString(){
        return id;
    }
}
