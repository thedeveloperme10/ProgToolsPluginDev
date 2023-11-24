package service;

import model.AffectedItem;
import model.BugImpact;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BugImpactAnalysis {

    List<BugImpact> bugImpactList = new ArrayList<>();

    FunctionCallAnalyzer functionCallAnalyzer = new FunctionCallAnalyzer();

    public List<BugImpact> getBugImpactList() {
        return bugImpactList;
    }

    public void setBugImpactList(List<BugImpact> bugImpactList) {
        this.bugImpactList = bugImpactList;
    }

    public void updateBugImpactAnalysis() throws IOException {

        for(BugImpact bug: bugImpactList) {
            List<AffectedItem> functionAffectedList = bug.getFunctionAffected();
            AffectedItem affectedItem = new AffectedItem();
            affectedItem.setAffected("affectedMethod1");
            functionAffectedList.add(affectedItem);
            AffectedItem affectedItem1 = new AffectedItem();
            affectedItem1.setAffected("affectedMethod2");
            functionAffectedList.add(affectedItem1);
            bug.setFunctionAffected(functionAffectedList);

            List<AffectedItem> fileAffectedList = bug.getFileAffected();
            AffectedItem af_file1 = new AffectedItem();
            af_file1.setAffected("FileName1");
            fileAffectedList.add(af_file1);
            bug.setFileAffected(fileAffectedList);

            bug.setFunctionImpactPercentage(55.5f);

            System.out.println("HardCoded AffectedFunc in BugImpactAnalysis");

//            System.out.println(affectedItem.toString());

//            functionCallAnalyzer.main1();

            MethodCallAnalyzer methodCallAnalyzer = new MethodCallAnalyzer();
            methodCallAnalyzer.main1();


        }

    }

}
