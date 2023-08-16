package hello.hellospring.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect

public class TimeTraceAop {

    private Object excute(ProceedingJoinPoint joinPoint ) throws  Throwable {
        long start = System.currentTimeMillis();

        System.out.println("START: " + joinPoint.toString());

        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMS = finish - start;

            System.out.println("END: " + joinPoint.toString() + ""+ timeMS + "timeMs");
        }
    }
}
