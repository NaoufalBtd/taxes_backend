package taxes.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import taxes.bean.Token;
import taxes.bean.User;
import taxes.dao.TokenDao;
import taxes.enums.TokenType;
import taxes.enums.UserRole;
import taxes.security.JwtService;
import taxes.service.facade.AuthFacade;
import taxes.ws.dto.AuthenticationRequestDto;
import taxes.ws.dto.AuthenticationResponse;
import taxes.ws.dto.RegisterRequestDto;


@Service
@RequiredArgsConstructor
public class AuthService implements AuthFacade {
    private final UserService userService;
    private final TokenDao tokenDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public int register(RegisterRequestDto request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.USER)
                .build();
        var savedUser = userService.save(user);
//        var jwtToken = jwtService.generateToken(user);
//        saveUserToken(savedUser, jwtToken);
//        return AuthenticationResponse.builder()
//                .token(jwtToken)
//                .build();
        return 1;
    }

    public AuthenticationResponse authenticate(AuthenticationRequestDto request) {
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getEmail(),
//                        request.getPassword()
//                )
//        );
        var user = userService.findByUsername(request.getUsername());
        if (user == null) {
            System.out.println("user not found");
        }


        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenDao.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenDao.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenDao.saveAll(validUserTokens);
    }


}