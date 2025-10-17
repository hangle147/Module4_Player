package com.example.players.aop;

import com.example.players.entity.Player;
import com.example.players.service.PlayerService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PlayerAspect {
    @Autowired
    private PlayerService playerService;

    @Before("@annotation(com.example.players.aop.CheckPlayerLimit) && args(player,..")
    public void checkPlayerLimit(JoinPoint joinPoint, Player player) {
        long registeredCount = playerService.countStatus("DA_DANG_KI");
        if ("DU_BI".equals(player.getStatus()) && registeredCount >= 11) {
            throw new IllegalArgumentException("Đủ đội hình, không thể đăng kí thêm!");
        }
    }

    @AfterReturning(pointcut = "execution(*com.example.players.service.PlayerService.updateStatus(..))", returning = "result")
    public void logStatusChange(JoinPoint joinPoint, Object result) {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof Player) {
            Player player = (Player) args[0];
            System.out.println("LOG: Cầu thủ " + player.getName() + " dã đổi trạng thái thành " + player.getStatus());
        }
    }
}
