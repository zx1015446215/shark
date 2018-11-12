package com.zx.shark.log;

import com.zx.shark.model.Log;
import com.zx.shark.model.MyException;
import com.zx.shark.repository.LogRepository;
import com.zx.shark.utils.JSONResult;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
public class ControllerLog {
    private final static Logger logger = LoggerFactory.getLogger(ControllerLog.class);
    private Log log;
    private Long startTime;
    private Long endTime;
    @Autowired
    LogRepository logRepository;


    @Pointcut("execution(* com.zx.shark.controller..*.*(..))")
    public void cut(){
    }

    /**
     * 在方法执行前执行
     * @param joinPoint
     */
    @Before("cut()")
    public void doBefore(JoinPoint joinPoint){
        log = new Log();
        startTime = System.currentTimeMillis();
        log.setStartDate(new Date(startTime));

        //获取用户凭证
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.setPrincipal(principal.toString());
        //url
        log.setUrl(request.getRequestURI());
        //method
        log.setMethod(request.getMethod());
        //ip
        log.setIp(request.getRemoteAddr());
        //类方法
        log.setClass_method(joinPoint.getSignature().getDeclaringTypeName()+'.'+ joinPoint.getSignature().getName());
        //参数
        log.setArgs(joinPoint.getArgs()==null?joinPoint.getArgs():null);
        logger.info("执行"+log.getClass_method()+" 方法\n"+"方法类型: "+log.getMethod());
    }

    /**
     * 在方法返回和抛出异常时执行
     */
//    @After("cut()")//无论Controller中调用方法以何种方式结束，都会执行
//        public void doAfter(){
//
//    }

    /**
     * 在返回值时返回内容
     * @param obj
     */
    @AfterReturning(returning = "obj",pointcut = "cut()")//在调用上面 @Pointcut标注的方法后执行。用于获取返回值
    public void doAfterReturning(Object obj){
        if (obj instanceof ModelAndView){  //若返回的是view，则存储其名称
            log.setResponse(((ModelAndView) obj).getViewName());
        }else if (obj instanceof JSONResult){  //若返回的是JSONResult
            log.setResponse(obj.toString());
        }else if (obj instanceof List){  //若返回的是List集合
            log.setResponse(obj.toString());
        }else{
            log.setResponse(obj.toString());
        }
        endTime = System.currentTimeMillis();
        log.setEndDate(new Date(endTime));   //结束时间
        log.setDate(endTime-startTime);  //消耗时间
        logger.info("用时:"+log.getDate());
        //将内容插入mongodb
        logRepository.insert(log);
    }

    /**
     * 抛出已超
     * @param joinPoint
     * @param e
     * @return
     */
    @AfterThrowing(pointcut = "cut()", throwing = "e")//cut()
        public void handleThrowing(JoinPoint joinPoint, Exception e) {//controller类抛出的异常在这边捕获
        logger.info("异常抛出:"+e.toString());
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        List<String > lists = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            lists.add(String.valueOf(args[i]));
        }
        MyException myException = new MyException(new Date(System.currentTimeMillis()),e.toString(),className,methodName,lists);
        log.setE(myException);      //错误信息
        logRepository.insert(log);  //存入mongodb
        return ;
    }

}
