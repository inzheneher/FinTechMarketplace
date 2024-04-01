plugins {
    id("java")
    id("org.springframework.boot") version "2.7.0" apply false
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
        testAnnotationProcessor("org.projectlombok:lombok:1.18.32")
    }
}