package com.rsd.yaycha.interceptors;

import java.util.List;
import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.rsd.yaycha.utils.JwtService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtHandShakeInterceptor implements HandshakeInterceptor{

    
    private JwtService jwtService;
    private UserDetailsService userDetailsService;

    public JwtHandShakeInterceptor(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }
    
    @Override
    public boolean beforeHandshake(
        ServerHttpRequest request, 
        ServerHttpResponse response, 
        WebSocketHandler wsHandler,
        Map<String, Object> attributes
    ) throws Exception {
        List<String> authHeaders = request.getHeaders().get("Authorization");

        if (authHeaders != null && !authHeaders.isEmpty()) {
            String token = authHeaders.get(0).replace("Bearer ", "");
            String username = jwtService.extractUsername(token);
            
            if (username != null && jwtService.validateToken(token, userDetailsService.loadUserByUsername(username))) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        attributes.put("user", authentication);
                        log.info("WebSocket handshake successful. User: {}", username);
                        return true;
                    }
                }
                return false;
    }
            
    @Override
    public void afterHandshake(ServerHttpRequest arg0, ServerHttpResponse arg1, WebSocketHandler arg2, Exception arg3) {
        
    }
            
}