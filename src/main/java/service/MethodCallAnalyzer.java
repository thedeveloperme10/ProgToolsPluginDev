package service;

import java.io.*;
import java.util.regex.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MethodCallAnalyzer {
    public static void main1() {
        System.out.println("**********");
        // Specify the directory containing Java files in the project
        String projectDirectory = "C:/Users/sriha/OneDrive/Documents/GitHub/Employee-Management-System-Using-SpringBoot/emsApplicationService/src/main/java/com/emsmanagementsystem";
        // Specify the method name to be searched for
        String targetMethod = "getAllEmployees";

        // Read all Java files and find methods calling the target method
        Map<String, List<MethodCallInfo>> methodCalls = new HashMap<>();
        searchForMethodCalls(projectDirectory, targetMethod, methodCalls);

        // Print the results
        for (Map.Entry<String, List<MethodCallInfo>> entry : methodCalls.entrySet()) {
            String methodName = entry.getKey();
            List<MethodCallInfo> callers = entry.getValue();
            System.out.println("Method: " + methodName);
            System.out.println("Called by:");
            for (MethodCallInfo caller : callers) {
                System.out.println("  - Caller Method: " + caller.getCallerMethodName());
                System.out.println("  - File Path: " + caller.getFilePath());
                System.out.println("-----------------------");
            }
        }
    }

    private static void searchForMethodCalls(String directoryPath, String targetMethod,
                                             Map<String, List<MethodCallInfo>> methodCalls) {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".java")) {
                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            // Regular expression to match method calls in Java
                            String regex = "\\b([a-zA-Z_]\\w*)\\s*\\(\\s*\\)";

                            Pattern pattern = Pattern.compile(regex);
                            Matcher matcher = pattern.matcher(line);
                            while (matcher.find()) {
                                String callingMethod = matcher.group(1);
                                if (callingMethod.equals(targetMethod)) {
                                    // Get the name of the method where the call is made
                                    String methodName = getMethodName(line);
                                    if (!methodCalls.containsKey(methodName)) {
                                        methodCalls.put(methodName, new ArrayList<>());
                                    }
                                    methodCalls.get(methodName).add(new MethodCallInfo(callingMethod, file.getAbsolutePath()));
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (file.isDirectory()) {
                    // Recursively search in subdirectories
                    searchForMethodCalls(file.getAbsolutePath(), targetMethod, methodCalls);
                }
            }
        }
    }

    // Helper function to extract the name of the method from the given line
    private static String getMethodName(String line) {
        String[] tokens = line.trim().split("\\s*\\(\\s*\\)", 2);
        return tokens[0].replaceAll("[a-zA-Z_]\\w*\\s*", "");
    }

    // Helper class to store method call information
    static class MethodCallInfo {
        private final String callerMethodName;
        private final String filePath;

        public MethodCallInfo(String callerMethodName, String filePath) {
            this.callerMethodName = callerMethodName;
            this.filePath = filePath;
        }

        public String getCallerMethodName() {
            return callerMethodName;
        }

        public String getFilePath() {
            return filePath;
        }
    }
}