package org.biukhanhhau.springsercurity;

import org.apache.coyote.Response;
import org.biukhanhhau.springsercurity.model.User;
import org.biukhanhhau.springsercurity.service.JwtService;
import org.biukhanhhau.springsercurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public AuthenticationManager manager;

    @Autowired
    JwtService jwtService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("register")
    public ResponseEntity<User> register(@RequestBody User user){
        User user1 = userService.register(user);
        return ResponseEntity.ok(user1);
    }

    @PostMapping("login")
    public String login(@RequestBody User user){
        Authentication authentication = manager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()));
        if (authentication.isAuthenticated()){          //để xác minh xem có thật sự tồn tại không
            return jwtService.generateToken(user.getUsername());   //gọi hàm để biến cái password đó thành token
        }
        return "login failed";
    }
}
