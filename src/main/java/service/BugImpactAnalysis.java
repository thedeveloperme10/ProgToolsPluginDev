package service;

import model.AffectedItem;
import model.BugImpact;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import java.util.Map;
import java.util.Set;

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

        functionCallAnalyzer.analyzeProject(); // Analyze the project to build the function call map


        for (BugImpact bug : bugImpactList) {
            // Get the signature of the buggy function
            String buggyFunctionSignature = bug.getBugMethodName(); // Modify BugImpact to include this information

            System.out.println("buggyFunctionSignature : "+buggyFunctionSignature);
            // Find all functions that can reach the buggy function
            System.out.println("Analyzing impact for: " + buggyFunctionSignature);

            Map<String, String> affectedFunctions = functionCallAnalyzer.findFunctionReach(buggyFunctionSignature);
            int totalUniqueSignatureCount = Integer.parseInt(affectedFunctions.get("totalUniqueSignatureCount"));
            affectedFunctions.remove("totalUniqueSignatureCount");


            // Convert the affected functions to AffectedItem objects with file locations
            List<AffectedItem> functionAffectedList = convertToAffectedItems(affectedFunctions);
            bug.setFunctionAffected(functionAffectedList);

            // Convert keys to a list
            // List<String> keyList = new ArrayList<>(affectedFunctions.keySet());

            // Convert values to a list
            List<String> valueList = new ArrayList<>(affectedFunctions.values());

            // Update Files Affected
            List<AffectedItem> fileAffectedList = new ArrayList<>();
            for (String pathString: valueList) {
                Path p = Paths.get(pathString);
                String fileName = p.getFileName().toString();
                AffectedItem affectedItem = new AffectedItem();
                affectedItem.setAffected(fileName);
                fileAffectedList.add(affectedItem);
            }
            bug.setFileAffected(fileAffectedList);

            int functionsAffectedCount = functionAffectedList.size();
            float functionImpactPercentage = ((float)functionsAffectedCount/totalUniqueSignatureCount)*100;
            bug.setFunctionImpactPercentage(functionImpactPercentage);
        }
    }

    private List<AffectedItem> convertToAffectedItems(Map<String, String> functionNamesWithLocations) {
        List<AffectedItem> items = new ArrayList<>();
        for (Map.Entry<String, String> entry : functionNamesWithLocations.entrySet()) {
            AffectedItem item = new AffectedItem();
            item.setAffected(entry.getKey());
            items.add(item);
        }
        return items;
    }

}
