package taxes.ws.facade;

import jakarta.annotation.security.PermitAll;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import taxes.service.impl.AuthService;
import taxes.ws.dto.AuthenticationRequestDto;
import taxes.ws.dto.AuthenticationResponse;
import taxes.ws.dto.RegisterRequestDto;

import java.time.Duration;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthRest {
    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity register(
            @RequestBody RegisterRequestDto request
    ) {
        service.register(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequestDto request
    ) {
        String token = service.authenticate(request).getToken();
        HttpHeaders headers = new HttpHeaders();
        ResponseCookie cookie = ResponseCookie.from("token", token)
                .httpOnly(true)
                .maxAge(Duration.ofDays(7))
                .path("/")
                .build();
        headers.add(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok()
                .headers(headers)
                .body(AuthenticationResponse.builder().token(token).build());
    }

}
