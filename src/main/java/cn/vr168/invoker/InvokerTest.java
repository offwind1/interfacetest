package cn.vr168.invoker;

import org.testng.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;


public class InvokerTest {

    public String test(String packagePath) {
        Set<Class<?>> classSet = ClassUtil.getClasses(packagePath);
        StringBuffer buffer = new StringBuffer();
        buffer.append("\n");
        for (Class cl : classSet) {
            buffer.append(runTestFunc(cl));
            buffer.append("———————————————————————————————————————\n");
        }
        return buffer.toString();
    }


    public String runTestFunc(Class cl) {
        StringBuffer buffer = new StringBuffer();
        try {
            Method state_method = cl.getMethod("of", null);
            Method route_method = cl.getMethod("route", null);
            Object o = state_method.invoke(null, null);
            String interFacePath = (String) route_method.invoke(o);

            Method[] methods = cl.getMethods();
            for (Method method : methods) {
                if (method.getName().startsWith("test")) {
                    String method_name;
                    Test annotation = method.getAnnotation(Test.class);
                    if (annotation != null && annotation.description().length() > 1) {
                        method_name = annotation.description();
                    } else {
                        method_name = method.getName();
                    }
                    String response;
                    String message;
                    try {
                        method.invoke(o);
                        response = "通过";
                        message = "";
                    } catch (Exception e) {
                        response = "失败";
                        message = e.toString();
                    }
                    buffer.append(String.format("|%-40s|%-20s\t\t|%-4s|%-10s|\n", interFacePath, method_name, response, message));
                }
            }
            return buffer.toString();
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public String interfacePath(Class cl) {
        try {
            Method method = cl.getMethod("route", null);
            return (String) method.invoke(cl.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        System.out.println(new InvokerTest().test("cn/vr168/interfacetest/inter"));
    }

}
