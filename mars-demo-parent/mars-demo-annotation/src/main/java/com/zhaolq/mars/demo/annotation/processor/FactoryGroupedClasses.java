package com.zhaolq.mars.demo.annotation.processor;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.zhaolq.mars.demo.annotation.annotation.Factory;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 此类包含属于同一个工厂的所有{@link FactoryAnnotatedClass}。
 * 换句话说，此类包含所有用@Factory注解标注的，{@link Factory#type()}相同的，类的列表。还检查每个类的{@link Factory#id()}是否唯一。
 *
 * @Author zhaolq
 * @Date 2020/7/10 10:56
 */
public class FactoryGroupedClasses {

    /**
     * 将添加到生成的工厂类的名称中
     */
    private static final String SUFFIX = "Factory";

    /**
     * 工厂类的全名称
     */
    private String qualifiedClassName;

    /**
     * 此工厂类包含的{@link FactoryAnnotatedClass}列表
     */
    private Map<String, FactoryAnnotatedClass> itemsMap = new LinkedHashMap<String, FactoryAnnotatedClass>();

    public FactoryGroupedClasses(String qualifiedClassName) {
        this.qualifiedClassName = qualifiedClassName;
    }

    /**
     * 向该工厂添加带注解的类
     *
     * @throws ProcessingException 如果已经存在另一个具有相同id的带注解的类。
     */
    public void add(FactoryAnnotatedClass toInsert) throws ProcessingException {

        FactoryAnnotatedClass existing = itemsMap.get(toInsert.getId());
        if (existing != null) {
            // 已存在，则抛出异常

            throw new ProcessingException(toInsert.getTypeElement(),
                    "Conflict: The class %s is annotated with @%s with id ='%s' but %s already uses the same id",
                    toInsert.getTypeElement().getQualifiedName().toString(),
                    Factory.class.getSimpleName(),
                    toInsert.getId(),
                    existing.getTypeElement().getQualifiedName().toString());
        }

        // 不存在，则添加
        itemsMap.put(toInsert.getId(), toInsert);
    }

    public void generateCode(Elements elementUtils, Filer filer) throws IOException {
        // 指定的@Factory#type()元素
        TypeElement superClassName = elementUtils.getTypeElement(qualifiedClassName);
        // 工厂类名称
        String factoryClassName = superClassName.getSimpleName() + SUFFIX;
        // 工厂类全名称
        String qualifiedFactoryClassName = qualifiedClassName + SUFFIX;
        // 指定的@Factory#type()元素包名
        PackageElement pkg = elementUtils.getPackageOf(superClassName);
        // 工厂类包名
        String packageName = pkg.isUnnamed() ? null : pkg.getQualifiedName().toString();

        MethodSpec.Builder method = MethodSpec.methodBuilder("create")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(String.class, "id")
                .returns(TypeName.get(superClassName.asType()));

        // 检查id是否为空
        method.beginControlFlow("if (id == null)")
                .addStatement("throw new IllegalArgumentException($S)", "id is null!")
                .endControlFlow();

        // Generate items map

        for (FactoryAnnotatedClass item : itemsMap.values()) {
            method.beginControlFlow("if ($S.equals(id))", item.getId())
                    .addStatement("return new $L()", item.getTypeElement().getQualifiedName().toString())
                    .endControlFlow();
        }

        method.addStatement("throw new IllegalArgumentException($S + id)", "Unknown id = ");

        TypeSpec typeSpec = TypeSpec.classBuilder(factoryClassName).addModifiers(Modifier.PUBLIC).addMethod(method.build()).build();

        // Write file
        JavaFile.builder("com.zhaolq.mars.demo.annotation.sample", typeSpec).build().writeTo(filer);
        JavaFile.builder(packageName, typeSpec).build().writeTo(filer);
    }

}