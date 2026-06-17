package com.lautaro.curso.springboot.calendar.interceptor.springboot_horario.interceptors;

import java.util.Calendar;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CalendarInterceptor implements HandlerInterceptor {
    
    @Value("${config.calendar.open}")
    private Integer open;
    @Value("${config.calendar.close}")
    private Integer close;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
                
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        if (hour >= open && hour < close) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            StringBuilder message = new StringBuilder();
            message.append("Bienvenido al sistema, el horario de atención es de ")
            .append(open)
            .append(" a ")
            .append(close)
            .append(" horas, por favor, respete el horario de atención");
            request.setAttribute("message", message.toString());
            return true;
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            @Nullable ModelAndView modelAndView) throws Exception {
        
        
    }

    

}
