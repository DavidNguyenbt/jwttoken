package tap.david.jwttoken.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    final AuthenticationService service;
    String token;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.register(request));
    }

//    @PostMapping("/authenticate")
//    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
//        token = service.authenticate(request).getToken();
//        return ResponseEntity.ok(service.authenticate(request));
//    }
    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request, HttpServletResponse response){
        token = service.authenticate(request).getToken();
//        return ResponseEntity.ok(service.authenticate(request));

        String msg;
        if(token == null) msg="Log in failed !!!";
        else {
            msg="Log in succeeded !!!";

            Cookie cookie = new Cookie("accessToken", token);
            cookie.setMaxAge(60); // Thời gian sống của cookie (đơn vị là giây)
            cookie.setPath("/"); // Đặt đường dẫn cookie, "/" có nghĩa là toàn bộ ứng dụng
            response.addCookie(cookie);
        }

        return ResponseEntity.ok(msg);
    }
}
