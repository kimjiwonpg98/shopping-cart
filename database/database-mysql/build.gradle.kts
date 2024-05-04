plugins {
    kotlin("plugin.noarg")
    kotlin("plugin.jpa")
}

dependencies {
    implementation(project(":domain"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")

    runtimeOnly("com.mysql:mysql-connector-j")
}

/** Arg 에러 방지 **/
noArg {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}