package io.github.ollama4j;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BatchAutoRunner {

    public static void main(String[] args) throws Exception {
        //  Specify the directory where the classes are located
        String classesDir =
                System.getProperty("user.dir")
                        + File.separator
                        + "target"
                        + File.separator
                        + "classes";

        System.out.println("Finding runnable classes...");

        // Get the list of classes with a main method
        List<Class<?>> mainClasses = findMainClasses(classesDir);

        System.out.println("Found " + mainClasses.size() + " classes. Here is the list:");
        for (Class<?> cls : mainClasses) {
            System.out.println("\t- " + cls.getName());
        }

        // Run each main method in sequence
        for (Class<?> cls : mainClasses) {
            System.out.println(
                    "============================================================================");
            System.out.println("[Running]: " + cls.getName());
            runMainMethod(cls);
            System.out.println("[Finished running]: " + cls.getName());
        }
    }

    public static List<Class<?>> findMainClasses(String classesDir) throws Exception {
        List<Class<?>> mainClasses = new ArrayList<>();
        File dir = new File(classesDir);

        if (dir.exists() && dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                if (file.isDirectory()) {
                    mainClasses.addAll(findMainClasses(file.getPath()));
                } else if (file.getName().endsWith(".class")) {
                    String className =
                            file.getPath()
                                    .replace(classesDir + File.separator, "")
                                    .replace(".class", "")
                                    .replace(File.separator, ".");

                    // ✅ Exclude current class
                    if (className.endsWith(BatchAutoRunner.class.getSimpleName())) {
                        continue; // Skip this class
                    }

                    try {
                        String clsName =
                                file.getPath()
                                        .replaceAll(
                                                System.getProperty("user.dir")
                                                        + File.separator
                                                        + "target"
                                                        + File.separator
                                                        + "classes",
                                                "")
                                        .replaceAll(".class", "")
                                        .replaceAll(File.separator, ".");
                        if (clsName.startsWith(".")) {
                            clsName = clsName.substring(1);
                        }
                        Class<?> cls = Class.forName(clsName);
                        if (hasMainMethod(cls)) {
                            mainClasses.add(cls);
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return mainClasses;
    }

    public static boolean hasMainMethod(Class<?> cls) {
        try {
            Method mainMethod = cls.getMethod("main", String[].class);
            return (mainMethod.getModifiers() & java.lang.reflect.Modifier.STATIC) != 0
                    && mainMethod.getReturnType() == void.class
                    && mainMethod.getParameterCount() == 1
                    && mainMethod.getParameterTypes()[0] == String[].class;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    public static void runMainMethod(Class<?> cls) throws Exception {
        Method mainMethod = cls.getMethod("main", String[].class);
        mainMethod.invoke(
                null, (Object) new String[] {}); // Run the main method with an empty argument array
    }
}
