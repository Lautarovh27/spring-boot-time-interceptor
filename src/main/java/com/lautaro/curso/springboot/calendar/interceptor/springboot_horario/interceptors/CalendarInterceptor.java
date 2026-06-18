package com.lautaro.curso.springboot.calendar.interceptor.springboot_horario.interceptors;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

@Component("calendarInterceptor")
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
        System.out.println("Hora actual: " + hour);

        if (hour >= open && hour < close) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            StringBuilder message = new StringBuilder();
            message.append("El horario de atención es de ");
            message.append(open);
            message.append(" a ");
            message.append(close);
            message.append(" horas, por favor, respete el horario de atención");
            request.setAttribute("message", message.toString());
            return true;
        }

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> data = new HashMap<>();
        StringBuilder message = new StringBuilder();
        message.append("El sistema se encuentra cerrado, el horario de atención es de ");
        message.append(open);
        message.append(" a ");
        message.append(close);
        message.append(" horas, gracias por su comprensión");
        data.put("message", message.toString());
        data.put("date", new Date().toString());
        response.setContentType("application/json");
        response.setStatus(401);
        response.getWriter().write(mapper.writeValueAsString(data));

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            @Nullable ModelAndView modelAndView) throws Exception {
        
        
    }

    

}
