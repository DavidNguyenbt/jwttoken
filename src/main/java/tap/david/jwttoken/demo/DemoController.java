package tap.david.jwttoken.demo;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tap.david.jwttoken.auth.AuthenticationResponse;
import tap.david.jwttoken.auth.AuthenticationService;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/demo-controller")
public class DemoController {
    private final AuthenticationService authenticationService;

    @GetMapping
    public ResponseEntity<String> sayHello(HttpServletRequest request) {
        final String token = authenticationService.getToken();
        final String refreshToken = refreshToken(request);

        if (Objects.equals(token, refreshToken)) return ResponseEntity.ok("Required is accepted !!!");
        else return ResponseEntity.ok("Required is denied !!! token : " + token + " - refeshtoken : " + refreshToken);
//        return ResponseEntity.ok("Hello from secured endpoint , token : " + authenticationService.getToken() + " - refeshtoken : "+refreshToken(request));
    }

    public String refreshToken(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }

        refreshToken = authHeader.substring(7);

        return refreshToken;
    }
}
