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

public class FunctionCallAnalyzer {

    private Map<String, Set<String>> functionCallMap = new HashMap<>();

    private Set<String> uniqueFunctionSignatures = new HashSet<>(); // Store unique function signatures
    private Map<String, String> methodFileMap = new HashMap<>();


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

                                    uniqueFunctionSignatures.add(methodSignature); // Add to unique signatures

                                    method.accept(new JavaRecursiveElementVisitor() {
                                        @Override
                                        public void visitMethodCallExpression(PsiMethodCallExpression expression) {
                                            super.visitMethodCallExpression(expression);
                                            PsiMethod calledMethod = expression.resolveMethod();
                                            if (calledMethod != null) {
                                                String calledMethodSignature = getMethodSignature(calledMethod);
                                                String currentMethodSignature = getMethodSignature(method);
                                                functionCallMap.computeIfAbsent(calledMethodSignature, k -> new HashSet<>()).add(currentMethodSignature);

                                                // Update the map with the method's file location
                                                methodFileMap.put(methodSignature, file.getPath());
                                                System.out.println("methodFileMap : "+methodSignature+"<----->"+file.getPath());


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

    // New method to get the total count of unique function signatures
    public int getTotalUniqueSignaturesCount() {
        return uniqueFunctionSignatures.size();
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

    public Map<String, String> findFunctionReach(String functionSignature) {
        // Preprocess the functionSignature to match the internal format
        String processedSignature = preprocessSignature(functionSignature);
        System.out.println("Finding reachable functions for: " + processedSignature);
        Set<String> reachableFunctions = new HashSet<>();



        if (functionCallMap.containsKey(processedSignature)) {
            findReachableFunctions(processedSignature, reachableFunctions);
        }

        Map<String, String> reachableFunctionsWithLocations = new HashMap<>();
        for (String reachableFunction : reachableFunctions) {
            reachableFunctionsWithLocations.put(reachableFunction, methodFileMap.get(reachableFunction));
        }

        // Print the total count of unique function signatures
        int uniqueSignatureCount = getTotalUniqueSignaturesCount();
        System.out.println("Total unique function signatures: " + uniqueSignatureCount);
        System.out.println("Reachable functions: " + reachableFunctions);
        System.out.println("reachableFunctionsWithLocations : " + reachableFunctionsWithLocations);

        reachableFunctionsWithLocations.put("totalUniqueSignatureCount", String.valueOf(uniqueSignatureCount));

        return reachableFunctionsWithLocations;
//        return reachableFunctions;
    }

    private String preprocessSignature(String signature) {
        // Trim any whitespace
        signature = signature.trim();

        // Extract the method name and parameters
        int paramStartIndex = signature.indexOf('(');
        String methodName = signature.substring(0, paramStartIndex).trim();
        String params = signature.substring(paramStartIndex);

        // Standardize the parameters
        params = standardizeParameters(params);

        // Reconstruct the signature
        return methodName + params;
    }

    private String standardizeParameters(String params) {
        StringBuilder standardizedParams = new StringBuilder("(");
        // Match parameter patterns and extract only the types
        Pattern pattern = Pattern.compile("\\s*([\\w\\[\\]]+)(\\s+\\w+)?\\s*");
        Matcher matcher = pattern.matcher(params);
        while (matcher.find()) {
            standardizedParams.append(matcher.group(1)).append(",");
        }
        // Remove the trailing comma, if any
        if (standardizedParams.length() > 1) {
            standardizedParams.deleteCharAt(standardizedParams.length() - 1);
        }
        standardizedParams.append(")");
        return standardizedParams.toString();
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
