package service;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileVisitor;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FunctionCallAnalyzer1 {

    private Map<String, Set<String>> functionCallMap = new HashMap<>();

    public void analyzeProject() {
        Project[] projects = ProjectManager.getInstance().getOpenProjects();
        System.out.println("Analyzing projects...");
        for (Project project : projects) {
            System.out.println("Processing project: " + project.getName());
            PsiManager psiManager = PsiManager.getInstance(project);
            VirtualFile projectRoot = ProjectRootManager.getInstance(project).getContentRoots()[0];

            VfsUtilCore.visitChildrenRecursively(projectRoot, new VirtualFileVisitor() {
                @Override
                public boolean visitFile(@NotNull VirtualFile file) {
                    if ("java".equals(file.getExtension())) {
                        System.out.println("Visiting file: " + file.getPath());
                        PsiFile psiFile = psiManager.findFile(file);
                        if (psiFile != null) {
                            psiFile.accept(new JavaRecursiveElementVisitor() {
                                // Existing visitor implementation
                                @Override
                                public void visitMethod(PsiMethod method) {
                                    super.visitMethod(method);
                                    String methodSignature = getMethodSignature(method);
                                    System.out.println("Method Signature: " + methodSignature);
                                    method.accept(new JavaRecursiveElementVisitor() {
                                        @Override
                                        public void visitMethodCallExpression(PsiMethodCallExpression expression) {
                                            super.visitMethodCallExpression(expression);
                                            PsiMethod calledMethod = expression.resolveMethod();
                                            if (calledMethod != null) {
                                                String calledMethodSignature = getMethodSignature(calledMethod);
                                                String currentMethodSignature = getMethodSignature(method);
                                                functionCallMap.computeIfAbsent(calledMethodSignature, k -> new HashSet<>()).add(currentMethodSignature);
                                                System.out.println("Mapping: " + calledMethodSignature + " is called by " + currentMethodSignature);
                                            }
                                        }


                                    });
                                }
                            });
                        }
                    }
                    return true;
                }
            });
        }
    }

    private String getMethodSignature(PsiMethod method) {
        StringBuilder signature = new StringBuilder(method.getName());
        signature.append("(");
        for (PsiParameter parameter : method.getParameterList().getParameters()) {
            signature.append(parameter.getType().getPresentableText()).append(",");
        }
        if (signature.charAt(signature.length() - 1) == ',') {
            signature.deleteCharAt(signature.length() - 1); // Remove last comma if present
        }
        signature.append(")");
        System.out.println("Generated Signature: " + signature);
        return signature.toString();
    }
    public Set<String> findFunctionReach(String functionSignature) {
        // Preprocess the functionSignature to match the internal format
        String processedSignature = preprocessSignature(functionSignature);
        System.out.println("Finding reachable functions for: " + processedSignature);
        Set<String> reachableFunctions = new HashSet<>();
        if (functionCallMap.containsKey(processedSignature)) {
            findReachableFunctions(processedSignature, reachableFunctions);
        }
        System.out.println("Reachable functions: " + reachableFunctions);
        return reachableFunctions;
    }

//    private String preprocessSignature(String signature) {
//        // Trim any whitespace
//        signature = signature.trim();
//
//        // Extract the method name and parameters
//        int paramStartIndex = signature.indexOf('(');
//        String methodName = signature.substring(0, paramStartIndex).trim();
//        String params = signature.substring(paramStartIndex);
//
//        // Standardize the parameters
//        params = standardizeParameters(params);
//
//        // Reconstruct the signature
//        return methodName + params;
//    }
//
//    private String standardizeParameters(String params) {
//        StringBuilder standardizedParams = new StringBuilder("(");
//        // Match parameter patterns and extract only the types
//        Pattern pattern = Pattern.compile("\\s*([\\w\\[\\]]+)(\\s+\\w+)?\\s*");
//        Matcher matcher = pattern.matcher(params);
//        while (matcher.find()) {
//            standardizedParams.append(matcher.group(1)).append(",");
//        }
//        // Remove the trailing comma, if any
//        if (standardizedParams.length() > 1) {
//            standardizedParams.deleteCharAt(standardizedParams.length() - 1);
//        }
//        standardizedParams.append(")");
//        return standardizedParams.toString();
//    }

//    private String preprocessSignature(String signature) {
//        // Trim whitespace
//        signature = signature.trim();
//
//        // Extract method name and parameters
//        int paramStartIndex = signature.indexOf('(');
//        String methodName = signature.substring(0, paramStartIndex).trim();
//        String params = signature.substring(paramStartIndex);
//
//        // Standardize the parameters
//        params = standardizeParameters(params);
//
//        // Reconstruct the signature
//        return methodName + params;
//    }

//    private String standardizeParameters(String params) {
//        StringBuilder standardizedParams = new StringBuilder("(");
//        // Use regex to match parameter patterns and extract only the types
//        Pattern pattern = Pattern.compile("\\s*([\\w\\[\\]<>]+)(\\s+\\w+)?\\s*(,|$)");
//        Matcher matcher = pattern.matcher(params);
//        while (matcher.find()) {
//            standardizedParams.append(matcher.group(1)).append(matcher.find() ? "," : "");
//        }
//        standardizedParams.append(")");
//        return standardizedParams.toString();
//    }

    private String standardizeParameters(String params) {
        StringBuilder standardizedParams = new StringBuilder("(");
        // Use regex to match parameter patterns, including generics, and extract only the types
        Pattern pattern = Pattern.compile("\\s*([\\w<>\\[\\]]+)(\\s+\\w+)?\\s*(,|$)");
        Matcher matcher = pattern.matcher(params);
        while (matcher.find()) {
            standardizedParams.append(matcher.group(1)).append(matcher.find() ? "," : "");
        }
        if (standardizedParams.length() > 1 && standardizedParams.charAt(standardizedParams.length() - 1) == ',') {
            standardizedParams.deleteCharAt(standardizedParams.length() - 1); // Remove trailing comma if present
        }
        standardizedParams.append(")");
        return standardizedParams.toString();
    }

    private String preprocessSignature(String signature) {
        // Trim whitespace
        signature = signature.trim();

        // Extract method name and parameters
        int paramStartIndex = signature.indexOf('(');
        String methodName = signature.substring(0, paramStartIndex).trim();
        String params = signature.substring(paramStartIndex);

        // Standardize the parameters
        params = standardizeParameters(params);

        // Reconstruct the signature
        return methodName + params;
    }

    private void findReachableFunctions(String functionSignature, Set<String> reachableFunctions) {
        System.out.println("Traversing for signature: " + functionSignature);
        if (functionCallMap.containsKey(functionSignature)) {
            for (String caller : functionCallMap.get(functionSignature)) {
                System.out.println("Checking caller: " + caller);
                if (!reachableFunctions.contains(caller)) {
                    System.out.println("Found caller: " + caller + " for " + functionSignature);
                    reachableFunctions.add(caller);
                    findReachableFunctions(caller, reachableFunctions);
                }
            }
        }
        System.out.println("Current reachable functions: " + reachableFunctions);
    }

}
