package com.example.marketboro.security;

import com.example.marketboro.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String exception = (String)request.getAttribute("exception");
        ErrorCode errorCode;

        if (exception == null) {
            errorCode = ErrorCode.NO_AUTHENTICATION_ERROR;
            setResponse(response, errorCode);
            return;
        }

        if (exception.equals("ExpiredJwtException")) {
            errorCode = ErrorCode.EXPIRED_JWT_ERROR;
            setResponse(response, errorCode);
            return;
        }

    }

    private void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        json.put("status", errorCode.getStatusCode());
        json.put("code", errorCode.getErrorCode());
        json.put("message", errorCode.getMessage());

        response.getWriter().print(json);
    }

}