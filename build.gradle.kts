import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm")
	kotlin("plugin.spring")
	id("org.springframework.boot")
	id("io.spring.dependency-management")
	/** lint 관리 **/
	id("org.jlleitschuh.gradle.ktlint") version "12.1.0"
	/** 커버러지 관리 **/
	id("org.jetbrains.kotlinx.kover") version "0.7.5"
}

val projectGroup: String by project
val applicationVersion: String by project

allprojects {
	group = projectGroup
	version = applicationVersion

	repositories {
		mavenCentral()
	}

	subprojects {
		apply(plugin = "org.jetbrains.kotlin.jvm")
		apply(plugin = "org.jetbrains.kotlin.plugin.spring")
		apply(plugin = "org.springframework.boot")
		apply(plugin = "io.spring.dependency-management")

		dependencies {
			implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
			implementation("org.jetbrains.kotlin:kotlin-reflect")
			implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
			implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
		}

		tasks.getByName("bootJar") {
			enabled = false
		}

		tasks.getByName("jar") {
			enabled = true
		}

		tasks.withType<KotlinCompile> {
			kotlinOptions {
				freeCompilerArgs += "-Xjsr305=strict"
				jvmTarget = "21"
			}
		}

		tasks.withType<Test> {
			useJUnitPlatform()
		}
	}
}


