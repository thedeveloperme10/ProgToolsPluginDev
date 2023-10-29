package service;

import model.BugImpact;

import java.util.List;

public final class BugImpactAnalysis {

    public static void updateBugImpact(List<BugImpact> bugImpactList) {

        for(BugImpact bug: bugImpactList) {
            bug.setBugId(1);
            bug.setBugMethodName("problemFunction1");
            List<String> apiAffected = bug.getApiAffected();
            apiAffected.add("affectedMethod1");
            bug.setApiAffected(apiAffected);
            bug.setFunctionImpactPercentage(55.5f);
        }


    }

}
