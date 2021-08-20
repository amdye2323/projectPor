# projectPor

# Java Springboot Project

JAVA, SPRINGBOOT, MYSQL , DOCKER-COMPOSE 로 구성된 Java Springboot Project 입니다.

## 기술 스택

| 이름           | 버전     |
| -------------- | -------- |
| JAVA        |  8  |
| docker         | 20.10.7 |
| docker-compose | 1.29.2   |
| SpringBoot         | 2.5.3    |
| MySql        | 5.0.0   |

## 스프링 주요 라이브러리 
## GRADLE 사용

| 이름           | 버전     |
| -------------- | -------- |
| Thymeleaf        |  2.5.3  |
| Jpa         | 2.5.3 |
| Jwt | 0.11.2   |
| Security         | 5.5.1    |
| MySql        | 1.18.20   |

## 시작하기

### 필수사항

도커, 도커 컴포즈 설치가 필요
JWT의 배포 이후 저장을 script 단의 localstorage를 사용하므로
실행 이전 쿠키 세션 삭제 부탁드립니다.

```
> docker --version
Docker version 20.10.7, build f0df350
> docker-compose --version
docker-compose version 1.29.2, build 5becea4c
```

### 실행

```
> cd projectPor
```

```
> docker-compose up
```

