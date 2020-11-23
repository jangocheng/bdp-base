package com.platform.config.aop;

import com.alibaba.fastjson.JSON;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * description: 实现Web层的日志切面
 * author: wlhbdp
 */
@Aspect
@Component
@Order(-5)
@Log4j2
public class WebLogAspect {
    private final ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Resource
    private HttpServletRequest request;
    /**
     * 定义一个切入点：
     *  第一个 * 代表任意修饰符及任意返回值
     *  第二个 *
     *  第三个 *
     *  第四个 *
     *  .. 匹配任意数量的参数
     */
    @Pointcut(value = "execution(public * com.platform.base-model-deploy.web.controller*..*(..))")
    public void webLog() {}

    @Before(value = "webLog()")
    public void doBefore(JoinPoint joinPoint) {
    }

    @AfterReturning(value="webLog()", returning = "resultMap")
    public void doAfter(JoinPoint joinPoint, Object resultMap) {
    }

    @Around(value = "webLog()")
    public Object doAround(ProceedingJoinPoint pjPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        //request controller
        String controllerName = pjPoint.getTarget().getClass().getName();

        sb.append("\n-----------REQUEST LOG-----------");
        sb.append("\nController:\t").append(controllerName);
        //request methodName
        String methodName = pjPoint.getSignature().getName();
        sb.append("\nMethodName:\t\t").append(methodName);
        //request url
        String url = request.getRequestURI();
        sb.append("\nUrl:\t\t").append(url);
        //request parameters
        sb.append("\nParameter: \t");
        Object[] args = pjPoint.getArgs();
        String classType = pjPoint.getTarget().getClass().getName();
        Class<?> clazz = Class.forName(classType);
        String clazzName = clazz.getName();
        Map<String, Object> nameArgsPair = getFieldsName(this.getClass(), clazzName, methodName, args);
        sb.append(argsArrayToString(nameArgsPair));

        /*Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String name = paramNames.nextElement();
            String[] values = request.getParameterValues(name);
            sb.append(name).append("=").append(JSONArray.toJSONString(values)).append(" ");
        }*/
        sb.append("\n-----------------------------------");

        Object object = pjPoint.proceed();
        long duration = System.currentTimeMillis() - startTime;
        sb.append("\nResponse:\t").append(object.toString());
        sb.append("\nCost time:\t").append(duration).append(" ms");
        sb.append("\n---------------------------------\n");
        log.info(sb.toString());
        return object;
    }

    private String argsArrayToString(Map<String, Object> args) {
        StringBuilder sb = new StringBuilder();
        if (args != null && args.size() > 0) {
            int count = 0;
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                String paramName = arg.getKey();
                Object paramValue = arg.getValue();
                sb.append(paramName).append("=>").append(JSON.toJSON(paramValue));
                if (count++ < args.size() - 1) {
                    sb.append(", ");
                }
            }
        }
        return sb.toString().trim();
    }

    private Map<String, Object> getFieldsName(Class cls, String clazzName, String methodName, Object[] args)
            throws NotFoundException {
        Map<String, Object> map = new HashMap<String, Object>();

        ClassPool pool = ClassPool.getDefault();
        //ClassClassPath classPath = new ClassClassPath(this.getClass());
        ClassClassPath classPath = new ClassClassPath(cls);
        pool.insertClassPath(classPath);

        CtClass cc = pool.get(clazzName);
        CtMethod cm = cc.getDeclaredMethod(methodName);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr != null) {
            int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
            for (int i = 0; i < cm.getParameterTypes().length; i++) {
                map.put(attr.variableName(i + pos), args[i]);//paramNames即参数名
            }
        }

        return map;
    }

}
