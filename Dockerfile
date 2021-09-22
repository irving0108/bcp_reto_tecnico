FROM openjdk:11
VOLUME /tmp
ADD ./target/exchangerate-0.0.1-SNAPSHOT.jar exchangerate.jar
EXPOSE 8083
ENTRYPOINT ["java","-jar","/exchangerate.jar"]