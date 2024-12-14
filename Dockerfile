### Docker 이미지를 생성할 때 기반이 되는 베이스 이미지를 설정한다.
FROM openjdk:17-ea-jdk

WORKDIR /work

### JAR_FILE 경로에 해당하는 파일을 Docker 이미지 내부로 복사한다.
COPY build/libs/*.jar notification.jar
### Docker 컨테이너가 시작될 때 실행할 명령을 지정한다.
ENTRYPOINT ["java","-jar","-Duser.timezone=Asia/Seoul", "/work/notification.jar"]