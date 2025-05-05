package com.javarush.javarushfinal.aspect;

import com.javarush.javarushfinal.service.part.PartService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ViewCountAspect {
    private final PartService partService;

    public ViewCountAspect(PartService partService) {
        this.partService = partService;
    }

    @Before("execution(* com.javarush.javarushfinal.controller.PartController.viewPartDetails(..))")
    public void logBeforeDetailView(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Long partId = (Long) args[0];

        partService.incrementViewCount(partId);

        Long currentViewCount = partService.getViewCount(partId);
        System.out.println("Запчастина переглянута: ID = " + partId
                           + " | Кількість переглядів: " + currentViewCount
                           + " | Час: " + java.time.LocalDateTime.now());

    }
}