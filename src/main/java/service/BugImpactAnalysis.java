package service;

import model.BugImpact;

import java.util.ArrayList;
import java.util.List;

public class BugImpactAnalysis {

    List<BugImpact> bugImpactList = new ArrayList<>();

    public List<BugImpact> getBugImpactList() {
        return bugImpactList;
    }

    public void setBugImpactList(List<BugImpact> bugImpactList) {
        this.bugImpactList = bugImpactList;
    }

    public void updateBugImpactAnalysis() {

        for(BugImpact bug: bugImpactList) {
            List<String> apiAffected = bug.getApiAffected();
            apiAffected.add("affectedMethod1");
            bug.setApiAffected(apiAffected);
            bug.setFunctionImpactPercentage(55.5f);
        }

    }

    public void addBugForAnalysis(BugImpact bug) {
        bugImpactList.add(bug);
    }

}
