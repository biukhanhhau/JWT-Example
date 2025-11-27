package org.biukhanhhau.springsercurity.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.biukhanhhau.springsercurity.service.JwtService;
import org.biukhanhhau.springsercurity.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class jwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtService jwtService;

    @Autowired
    ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);             // Token bắt đầu ở vị trí thứ 7
            username = jwtService.extractUserName(token);     // Lấy cái username ra
        }
                                    // Phải check là vì nếu
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                            // Load ra một thằng userDetails để sử dụng, nhưng thường thì
                        // trong hệ thống lớn hoặc microservice sẽ set Claims trong đó cho lẹ
            UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(username);

            if (jwtService.validateToken(token, userDetails)){           // check xem token còn xài được không
                UsernamePasswordAuthenticationToken authToken =         // tạo thẻ xanh, xác định người này là ai, trao quyền
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));   // thêm thông tin phụ
                SecurityContextHolder.getContext().setAuthentication(authToken);     //set quyền hạn cho nó
            }
        }
        filterChain.doFilter(request, response);           // chuyển tiếp đến cái filter tiếp theo
    }
}
