package promo.bot.beedle_deals.adapters.in.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ApiKeyInterceptor implements HandlerInterceptor {
    @Value("${MASTER_API_KEY}")
    private String configuredApiKey;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response) {
        String requestApiKey = request.getHeader("X-API-KEY");

        if(configuredApiKey.equals(requestApiKey)) {
            return true;
        }

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return false;
    }
}
