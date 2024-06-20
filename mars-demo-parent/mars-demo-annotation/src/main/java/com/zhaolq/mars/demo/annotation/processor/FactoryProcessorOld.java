package com.zhaolq.mars.demo.annotation.processor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import com.google.auto.service.AutoService;
import com.zhaolq.mars.demo.annotation.annotation.Factory;

/**
 * 继承AbstractProcessor类实现自定义注解处理器 -- 工厂注解处理器
 *
 * @Author zhaolq
 * @Date 2020/7/8 10:04
 */
@AutoService(Processor.class)
public class FactoryProcessorOld extends AbstractProcessor {

    /**
     * 用于对类型进行操作的实用方法。
     */
    private Types typeUtils;
    /**
     * 用于对元素进行操作的实用方法。
     */
    private Elements elementUtils;
    /**
     * 用于创建新的源文件、类文件或辅助文件的文件管理器。
     */
    private Filer filer;
    /**
     * 为注释处理器提供了一种报告错误消息、警告和其他通知的方法。可以传递元素、注解和注解值以提供消息的位置提示。但是，此类位置提示可能不可用或仅是近似的。
     */
    private Messager messager;

    private int i = 1;

    /**
     * 这是Processor接口中提供的一个方法，在编译期间注解处理器工具会调用此方法并且提供实现ProcessingEnvironment接口的对象
     * 作为参数，该对象提供一些工具类。
     *
     * @param processingEnv
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        // 返回实现Types接口的对象
        typeUtils = processingEnv.getTypeUtils();
        // 返回实现Elements接口的对象
        elementUtils = processingEnv.getElementUtils();
        // 返回实现Filer接口的对象
        filer = processingEnv.getFiler();
        // 返回实现Messager接口的对象
        messager = processingEnv.getMessager();
    }

    /**
     * 在这里，您可以编写代码来扫描、评估和处理注解，以及生成java文件。使用RoundEnvironment参数，可以查询被特定注解标注的元素。
     *
     * @param annotations 已扫描到的要求处理的注释类型
     * @param roundEnv    使用此参数可以查询被特定注解标注的元素
     * @return boolean 如果返回{@code true}，则会声明注释类型，并且不会要求后续处理器对其进行处理；
     * 如果返回{@code false}，则将取消声明注释类型，并可能要求后续处理器(其他注解处理器)对其进行处理。
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        // printNoteMsg("日志开始-----第" + i + "轮");

        // 给定注解类型注释的元素；如果没有，则为空集
        Set<? extends Element> eleStrSet = roundEnv.getElementsAnnotatedWith(Factory.class);

        if (eleStrSet.size() > 0) {

            StringBuilder generateStr = new StringBuilder();

            for (Element element : eleStrSet) {
                if (element.getKind() == ElementKind.CLASS) {
                    TypeElement typeElement = (TypeElement) element;

                    // 以下三行代码也可使用 String classQualifiedName = typeElement.getQualifiedName().toString()
                    PackageElement packageElement = elementUtils.getPackageOf(element);
                    String packagePath = packageElement.getQualifiedName().toString();
                    String className = typeElement.getSimpleName().toString();

                    Factory factory = typeElement.getAnnotation(Factory.class);

                    generateStr.append("        if (\"" + factory.id() + "\".equals(id)) {\n" +
                            "            return new " + packagePath + "." + className + "();\n" +
                            "        }\n");
                }
            }

            String packageName = "com.zhaolq.mars.demo.annotation.sample.processor";
            String factoryName = "MealFactoryOld";
            StringBuilder content;

            /**
             * javapoet:生成java源文件的库。https://github.com/square/javapoet
             */

            content = new StringBuilder();
            content.append("package  " + packageName + ";\n");
            content.append("\n");
            content.append("public class " + factoryName + " {\n");
            content.append("\n");
            content.append("    public Meal create(String id) {\n");
            content.append("        if (id == null) {\n");
            content.append("            throw new IllegalArgumentException(\"id is null!\");\n");
            content.append("        }\n");
            content.append(generateStr + "\n");
            content.append("        throw new IllegalArgumentException(\"Unknown id = \" + id);\n");
            content.append("    }\n");
            content.append("}\n");
            // 创建 MealFactoryOld 食物工厂类
            createSourceFile(packageName, factoryName, content.toString());
        }

        // printNoteMsg("日志结束-----第" + i++ + "轮");

        return false;
    }

    /**
     * 在此处定义要为其注册注解处理器的注解。
     *
     * @param
     * @return java.util.Set<java.lang.String> 返回类型是String集合，其中包含要使用此注解处理器处理的注解类型的全限定名称。
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotataions = new LinkedHashSet<>();
        annotataions.add(Factory.class.getCanonicalName());
        return annotataions;
    }

    /**
     * 指定支持的Java版本
     *
     * @param
     * @return javax.lang.model.SourceVersion
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private void createSourceFile(String packageName, String className, String content) {
        // 创建 MealFactoryOld 食物工厂类
        try {
            JavaFileObject sourceFile = filer.createSourceFile(packageName + "." + className);
            Writer writer = sourceFile.openWriter();

            writer.write(content);
            writer.append("\n");

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            printErrorMsg(e.toString());
        }
    }

    /**
     * 将内容输出到文件
     *
     * @param content
     */
    private void createFile(String content) {
        try {
            // append为true时，内容打印两遍
            FileOutputStream fos = new FileOutputStream("D:\\PizzaStore.java", false);
            fos.write(content.getBytes());
            fos.write("\r\n".getBytes());

            fos.flush();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
            printErrorMsg(e.toString());
        }
    }

    /**
     * 打印
     *
     * @param msg
     */
    private void printErrorMsg(String msg) {
        messager.printMessage(Diagnostic.Kind.ERROR, msg);
    }

    private void printNoteMsg(String msg) {
        messager.printMessage(Diagnostic.Kind.NOTE, msg);
    }


}