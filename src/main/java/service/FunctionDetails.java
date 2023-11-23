package service;

import java.util.Map;

public class FunctionDetails {

    private int totalCalls;

    private Map<String, Integer> callingFunctions;

    public int getTotalCalls() {
        return totalCalls;
    }

    public void setTotalCalls(int totalCalls) {
        this.totalCalls = totalCalls;
    }

    public Map<String, Integer> getCallingFunctions() {
        return callingFunctions;
    }

    public void setCallingFunctions(Map<String, Integer> callingFunctions) {
        this.callingFunctions = callingFunctions;
    }

    public void incrementTotalCalls() {
        this.totalCalls++;
    }

    public void addCallingFunction(String callingFunction) {
        this.callingFunctions.put(callingFunction, callingFunctions.getOrDefault(callingFunction, 0)+ 1);
    }

}