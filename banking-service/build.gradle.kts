apply(plugin = "java")
apply(plugin = "org.springframework.boot")

sourceSets {
    main {
        java {
            srcDirs("src/main/java")
        }
        resources {
            srcDirs("src/main/resources")
        }
    }
    test {
        java {
            srcDirs("src/test/java")
        }
        resources {
            srcDirs("src/test/resources")
        }
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.projectlombok:lombok:1.18.32")
    implementation("io.jsonwebtoken:jjwt-api:0.12.5")
    implementation("org.jetbrains:annotations:24.1.0")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.5")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok:1.18.32")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    sourceCompatibility = "19"
    targetCompatibility = "19"
}

tasks.withType<ProcessResources> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.withType<Test> {
    useJUnitPlatform()
}
