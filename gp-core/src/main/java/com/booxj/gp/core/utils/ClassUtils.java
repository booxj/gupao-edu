package com.booxj.gp.core.utils;

import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

import java.util.Arrays;
import java.util.TreeMap;

public class ClassUtils {

    /**
     * 获取方法的参数名
     *
     * @param className
     * @param methodName
     * @return
     * @throws ClassNotFoundException
     * @throws NotFoundException
     */
    public static String[] getFieldsName(String className, String methodName) throws ClassNotFoundException, NotFoundException {
        Class<?> clazz = Class.forName(className);
        className = clazz.getName();
        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(clazz);
        pool.insertClassPath(classPath);

        CtClass ctClass = pool.get(className);
        CtMethod ctMethod = ctClass.getDeclaredMethod(methodName);
        MethodInfo methodInfo = ctMethod.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
            return null;
        }
        String[] paramsArgsName = new String[ctMethod.getParameterTypes().length];
        int pos = Modifier.isStatic(ctMethod.getModifiers()) ? 0 : 1;

        /**
         * Java虚拟机使用局部变量表来完成方法调用时的参数传递，当一个方法被调用的时候，它的参数将会传递至从0开始的连续的局部变量表位置上。
         * 特别地，当一个实例方法被调用的时候，第0个局部变量一定是用来存储被调用的实例方法所在的对象的引用（即Java语言中的“this”关键字）。
         * 后续的其他参数将会传递至从1开始的连续的局部变量表位置上。
         */
//        for (int i = 0; i < paramsArgsName.length; i++) {
//            paramsArgsName[i] = attr.variableName(i + pos);
//        }


//        for (int i = 0; i < attr.tableLength(); i++) {
//            if (attr.index(i) >= pos && attr.index(i) < paramsArgsName.length + pos)
//                paramsArgsName[attr.index(i) - pos] = attr.variableName(i);
//        }

        /**
         * 由于 long 和 double 等类型单个变量占用两个 slot，于是 attr.index(i) 是跳跃的，
         */
        TreeMap<Integer, String> sortMap = new TreeMap<>();
        for (int i = 0; i < attr.tableLength(); i++) {
            sortMap.put(attr.index(i), attr.variableName(i));
        }
        paramsArgsName = Arrays.copyOfRange(sortMap.values().toArray(new String[0]), pos, paramsArgsName.length + pos);
        return paramsArgsName;
    }
}
