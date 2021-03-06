package co.test.testpro.controller;

import co.test.testpro.domain.User;
import co.test.testpro.dto.DefaultResponseDto;
import co.test.testpro.dto.LoginDto;
import co.test.testpro.dto.TokenDto;
import co.test.testpro.jwt.JwtFilter;
import co.test.testpro.jwt.TokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final StringRedisTemplate redisTemplate = new StringRedisTemplate();
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authorize(@Valid @RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken); //loadUserByUsername ????????? ??????
        SecurityContextHolder.getContext().setAuthentication(authentication);  //Authentication????????? ???????????? ????????? securityContext??? ????????????

        String jwt = tokenProvider.createToken(authentication); //Authentication????????? jwt token??? ??????

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt); //jwt token??? response header??? ????????????

        TokenDto to = new TokenDto(jwt);
        String token = to.getToken();
        HashMap<String,String> result = new HashMap<>();
        result.put("token",token);
        result.put("user",loginDto.getUsername());

        return new ResponseEntity<>(result, httpHeaders, HttpStatus.OK); //tokenDTo??? ???????????? responsebody?????? ????????? ??????
    }

    @PostMapping("/auth")
    public ResponseEntity<?> auth() {
        return new ResponseEntity<>(new DefaultResponseDto(200,"???????????? ???????????????."), HttpStatus.OK);
    }

    @PostMapping("/logout")
    @Transactional
    public ResponseEntity<?> logout(String jwt){
//        ValueOperations<String, String> logoutValueOperations = redisTemplate.opsForValue();
//        logoutValueOperations.set(jwt, jwt); // redis set ?????????
        User user = (User) tokenProvider.getAuthentication(jwt).getPrincipal();
        logger.debug("???????????? ?????? ????????? : '{}' , ?????? ?????? : '{}'", user.getUserId(), user.getUsername());
        return new ResponseEntity<>(new DefaultResponseDto(200,"???????????? ???????????????."), HttpStatus.OK);
    }
}
