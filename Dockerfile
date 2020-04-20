FROM gradle:6.3-jdk8 as builder

#COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
COPY . .
RUN gradle test --no-daemon