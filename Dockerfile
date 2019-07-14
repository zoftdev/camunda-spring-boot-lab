# FROM maven as builder
# WORKDIR /work
# COPY pom.xml .
# RUN mvn clean compile dependency:resolve-plugins
# COPY ./src ./src
# RUN mvn dependency:resolve-plugins dependency:resolve
# RUN mvn test
# RUN mvn package -DskipTests

FROM openjdk:13-alpine
ENV TZ=Asia/Bangkok
EXPOSE 8081
WORKDIR /app
# COPY --from=builder /work/target/*.jar .
COPY ./target/*.jar .
CMD java -jar *.jar