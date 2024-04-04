package com.zhaolq.mars.demo.annotation.processor;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;

import org.springframework.util.StringUtils;

import com.zhaolq.mars.demo.annotation.annotation.Factory;

/**
 * 包含有关用{@link Factory}注解标注的类的信息
 *
 * @Author zhaolq
 * @Date 2020/7/10 10:54
 */
public class FactoryAnnotatedClass {

    /**
     * 用{@link Factory}注解的原始元素
     */
    private TypeElement annotatedClassElement;
    /**
     * {@link Factory#type()} 中指定的类型的全限定名称。
     */
    private String qualifiedGroupClassName;
    /**
     * {@link Factory#type()} 中指定的类型的简单名称，也是工厂类的名称。
     */
    private String simpleFactoryGroupName;
    /**
     * {@link Factory#id()} 中指定的id。
     */
    private String id;

    /**
     * @throws ProcessingException 如果注解中的id()为null
     */
    public FactoryAnnotatedClass(TypeElement classElement) throws ProcessingException {
        this.annotatedClassElement = classElement;
        Factory annotation = classElement.getAnnotation(Factory.class);
        id = annotation.id();

        if (StringUtils.isEmpty(id)) {
            // 注解@Factory中类的id()不允许为null或空
            throw new ProcessingException(classElement, "id() in @%s for class %s is null or empty! that's not allowed",
                    Factory.class.getSimpleName(), classElement.getQualifiedName().toString());
        }

        // 获取完整的QualifiedTypeName
        try {
            Class<?> clazz = annotation.type();
            qualifiedGroupClassName = clazz.getCanonicalName();
            simpleFactoryGroupName = clazz.getSimpleName();
        } catch (MirroredTypeException mte) {
            DeclaredType classTypeMirror = (DeclaredType) mte.getTypeMirror();
            TypeElement classTypeElement = (TypeElement) classTypeMirror.asElement();
            qualifiedGroupClassName = classTypeElement.getQualifiedName().toString();
            simpleFactoryGroupName = classTypeElement.getSimpleName().toString();
        }
    }

    /**
     * 获取 {@link Factory#id()} 中指定的id
     * return id
     */
    public String getId() {
        return id;
    }

    /**
     * 获取 {@link Factory#type()} 中指定的类型的全限定名称。
     *
     * @return qualified name
     */
    public String getQualifiedFactoryGroupName() {
        return qualifiedGroupClassName;
    }

    /**
     * 获取 {@link Factory#type()} 中指定的类型的简单名称，也是工厂类的名称。
     *
     * @return qualified name
     */
    public String getSimpleFactoryGroupName() {
        return simpleFactoryGroupName;
    }

    /**
     * The original element that was annotated with @Factory
     * 用@Factory注解的原始元素
     */
    public TypeElement getTypeElement() {
        return annotatedClassElement;
    }
}