package online.gemfpt.BE.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import online.gemfpt.BE.entity.Account;
import online.gemfpt.BE.Service.TokenService;
import online.gemfpt.BE.exception.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.List;

@Component
public class Filter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    // list danh sach api valid
    private final List<String> AUTH_PERMISSION = List.of(
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/login",
            "/Register",
            "/register",
            "/send-mail",
            "/login_google",
            "/forgot_password",
            "/api/Gem/test",
            "/createAccount",
            "/staff-login-google",

            "/api/surveys/**",
            "/api/surveysubmit/**",
            "/api/appointments/**",
            "/api/programs/**",
            "/api/posts/**",
            "/api/reports/**"


    );

    private boolean isPermitted(String uri){
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return AUTH_PERMISSION.stream().anyMatch(pattern -> pathMatcher.match(pattern, uri));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String uri = request.getRequestURI(); //login... /register

        if (isPermitted(uri)) {
            // yêu cầu truy cập 1 api => ai cũng truy cập đc
            filterChain.doFilter(request, response); // cho phép truy cập dô controller
        } else {
            String token = getToken(request);
            if (token == null) {
                resolver.resolveException(request, response, null, new AuthException("Empty token!"));
                return;
            }

            Account account;
            try {
                // từ token tìm ra thằng đó là ai
                account = tokenService.extractAccount(token);
            } catch (ExpiredJwtException expiredJwtException) {
                // token het han
                resolver.resolveException(request, response, null, new AuthException("Expired Token!"));
                return;
            } catch (MalformedJwtException malformedJwtException) {
                resolver.resolveException(request, response, null, new AuthException("Invalid Token!"));
                return;
            }
            // token dung
            UsernamePasswordAuthenticationToken
                    authenToken =
                    new UsernamePasswordAuthenticationToken(account, token, account.getAuthorities());
            authenToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenToken);
            // token ok, cho vao`
            filterChain.doFilter(request, response);
        }


//        if(AUTH_PERMISSION.contains(uri)){
//            filterChain.doFilter(request,response);
//        }else{
//            String token = getToken(request);
//            if(token != null){
//                Account account = tokenService.extractAccount(token);
//                System.out.println(account.toString());
//            }
//        }



    }

    public String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.substring(7);
    }
}