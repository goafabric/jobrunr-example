import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

val group: String by project
val version: String by project
java.sourceCompatibility = JavaVersion.VERSION_21

val dockerRegistry = "goafabric"
val nativeBuilder = "dashaun/builder:20240403"
val baseImage = "ibm-semeru-runtimes:open-21.0.1_12-jre-focal@sha256:24d43669156684f7bc28536b22537a7533ab100bf0a5a89702b987ebb53215be"

plugins {
	java
	jacoco
	id("org.springframework.boot") version "3.3.0"
	id("io.spring.dependency-management") version "1.1.5"
	id("org.graalvm.buildtools.native") version "0.10.2"

	id("com.google.cloud.tools.jib") version "3.4.2"
	id("net.researchgate.release") version "3.0.2"
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
	maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
	constraints {
		implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")
		implementation("org.mapstruct:mapstruct:1.5.5.Final")
		annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
		implementation("io.github.resilience4j:resilience4j-spring-boot3:2.1.0")
	}
}

dependencies {
	//web
	implementation("org.springframework.boot:spring-boot-starter")

	implementation("org.jobrunr:jobrunr-spring-boot-3-starter:7.1.2")

	//persistence
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")

	implementation("com.h2database:h2")
	implementation("org.postgresql:postgresql")
	implementation("org.flywaydb:flyway-core")
	implementation("org.flywaydb:flyway-database-postgresql")

	//mongodb
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	//developmentOnly("de.flapdoodle.embed:de.flapdoodle.embed.mongo.spring30x:4.7.0")

	//test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
	exclude("**/*NRIT*")
	finalizedBy("jacocoTestReport")
}
tasks.jacocoTestReport { reports {csv.required.set(true) } }

jib {
	val amd64 = com.google.cloud.tools.jib.gradle.PlatformParameters(); amd64.os = "linux"; amd64.architecture = "amd64"; val arm64 = com.google.cloud.tools.jib.gradle.PlatformParameters(); arm64.os = "linux"; arm64.architecture = "arm64"
	from.image = baseImage
	to.image = "${dockerRegistry}/${project.name}:${project.version}"
	container.jvmFlags = listOf("-Xms256m", "-Xmx256m")
	from.platforms.set(listOf(amd64, arm64))
}

tasks.register("dockerImageNative") { group = "build"; dependsOn("bootBuildImage") }
tasks.named<BootBuildImage>("bootBuildImage") {
	val nativeImageName = "${dockerRegistry}/${project.name}-native" + (if (System.getProperty("os.arch").equals("aarch64")) "-arm64v8" else "") + ":${project.version}"
	builder.set(nativeBuilder)
	imageName.set(nativeImageName)
	environment.set(mapOf("BP_NATIVE_IMAGE" to "true", "BP_JVM_VERSION" to "21", "BP_NATIVE_IMAGE_BUILD_ARGUMENTS" to "-J-Xmx5000m -march=compatibility"))
	doLast {
		exec { commandLine("/bin/sh", "-c", "docker run --rm $nativeImageName -check-integrity") }
		exec { commandLine("/bin/sh", "-c", "docker push $nativeImageName") }
	}
}

graalvmNative { binaries.named("main") { quickBuild.set(true) } }

configure<net.researchgate.release.ReleaseExtension> {
	buildTasks.set(listOf("build", "test", "jib", "dockerImageNative"))
	tagTemplate.set("v${version}".replace("-SNAPSHOT", ""))
}