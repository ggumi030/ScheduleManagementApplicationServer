package org.sparta.todoappserver.exception;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j(topic="AuthFilterException")
public class FilterExceptionHandler {
   public static <T extends Exception> void handleExceptionInFilter(HttpServletResponse servletResponse, T e)throws IOException {
       log.error(e.getMessage());

       //서블릿 응답 UTF-8 인코딩
       servletResponse.setContentType("text/plain; charset=UTF-8");
       servletResponse.setCharacterEncoding("UTF-8");

       servletResponse.setStatus(400);
       servletResponse.getWriter().write("error :"+e.getMessage());
   }
}
