package model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BugImpact {

    Integer bugId;
    String bugMethodName;
    Integer bugLineNumber;
    //Add Filename;

    List<AffectedItem> apiAffected = new ArrayList<>();
    List<AffectedItem> functionAffected = new ArrayList<>();
    List<AffectedItem> fileAffected = new ArrayList<>();

    Float apiImpactPercentage;
    Float functionImpactPercentage;
    Float fileImpactPercentage;

    public Integer getBugId() {
        return bugId;
    }

    public void setBugId(Integer bugId) {
        this.bugId = bugId;
    }

    public String getBugMethodName() {
        return bugMethodName;
    }

    public void setBugMethodName(String bugMethodName) {
        this.bugMethodName = bugMethodName;
    }

    public Integer getBugLineNumber() {
        return bugLineNumber;
    }

    public void setBugLineNumber(Integer bugLineNumber) {
        this.bugLineNumber = bugLineNumber;
    }

    public List<AffectedItem> getApiAffected() {
        return apiAffected;
    }

    public void setApiAffected(List<AffectedItem> apiAffected) {
        this.apiAffected = apiAffected;
    }

    public List<AffectedItem> getFunctionAffected() {
        return functionAffected;
    }

    public void setFunctionAffected(List<AffectedItem> functionAffected) {
        this.functionAffected = functionAffected;
    }

    public List<AffectedItem> getFileAffected() {
        return fileAffected;
    }

    public void setFileAffected(List<AffectedItem> fileAffected) {
        this.fileAffected = fileAffected;
    }

    public Float getApiImpactPercentage() {
        return apiImpactPercentage;
    }

    public void setApiImpactPercentage(Float apiImpactPercentage) {
        this.apiImpactPercentage = apiImpactPercentage;
    }

    public Float getFunctionImpactPercentage() {
        return functionImpactPercentage;
    }

    public void setFunctionImpactPercentage(Float functionImpactPercentage) {
        this.functionImpactPercentage = functionImpactPercentage;
    }

    public Float getFileImpactPercentage() {
        return fileImpactPercentage;
    }

    public void setFileImpactPercentage(Float fileImpactPercentage) {
        this.fileImpactPercentage = fileImpactPercentage;
    }

    @Override
    public String toString() {
        return "BugImpact{" +
                "bugId=" + bugId +
                ", bugMethodName='" + bugMethodName + '\'' +
                ", bugLineNumber=" + bugLineNumber +
                ", apiAffected=" + apiAffected +
                ", functionAffected=" + functionAffected +
                ", fileAffected=" + fileAffected +
                ", apiImpactPercentage=" + apiImpactPercentage +
                ", functionImpactPercentage=" + functionImpactPercentage +
                ", fileImpactPercentage=" + fileImpactPercentage +
                '}';
    }
}
