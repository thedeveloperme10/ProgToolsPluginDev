package service;

import service.FunctionDetails;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FunctionCallAnalyzer {

    private static Map<String, FunctionDetails> functionCallMap = new HashMap<>();

    public static void main1() throws IOException {

        System.out.println("**********");
        analyzeSourceFile("D:/JavProject/src/main/java");

        printFunctionDetails("testFunc");

    }

    private static void analyzeProject(String projectPath) throws IOException {

        Path dir = Paths.get(projectPath);

        Files.walk(dir).forEach(filePath -> {

            if(Files.isRegularFile(filePath) && filePath.toString().endsWith(".java")) {

                try {
                    analyzeSourceFile(filePath.toString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }

        });

    }

    private static void analyzeSourceFile(String fileName) throws IOException {

        String fileContent = Files.readString(Paths.get(fileName));

        Pattern pattern = Pattern.compile("\\w+\\((.*)\\)");

        Matcher matcher = pattern.matcher(fileContent);

        while(matcher.find()) {

            String calledFunction = matcher.group(1);
            String callingFunction = getCallingFunction(matcher);

            updateFunctionDetails(calledFunction, callingFunction);

        }

    }

    private static void updateFunctionDetails(String calledFunction, String callingFunction) {

        FunctionDetails details = functionCallMap.get(calledFunction);

        if(details == null) {
            details = new FunctionDetails();
        }

        details.incrementTotalCalls();
        details.addCallingFunction(callingFunction);

        functionCallMap.put(calledFunction, details);

    }

    private static void printFunctionDetails(String functionName) {

        FunctionDetails details = functionCallMap.get(functionName);

        System.out.println("Total calls to " + functionName + ": " + details.getTotalCalls());

        System.out.println("Calling functions:");

        details.getCallingFunctions().forEach((key, value) -> System.out.println(key));

    }

    private static String getCallingFunction(Matcher matcher) {
        return matcher.group(0).split("\\(")[0].trim();
    }


}
