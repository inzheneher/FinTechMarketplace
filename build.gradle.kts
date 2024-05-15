plugins {
    java
    id("org.springframework.boot") version "3.2.5" apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE" apply false
}

subprojects {
    repositories {
        mavenCentral()
    }

    apply(plugin = "java")
    apply(plugin = "io.spring.dependency-management")

    dependencies {
        testImplementation("org.projectlombok:lombok:1.18.32")
        testImplementation("org.springframework.boot:spring-boot-starter-test:3.2.4")
        testImplementation("org.springframework.security:spring-security-test")
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.2")
        testImplementation("org.mockito:mockito-core:5.12.0")
        testImplementation("org.mockito:mockito-junit-jupiter:5.12.0")
        testAnnotationProcessor("org.projectlombok:lombok:1.18.32")
    }
}