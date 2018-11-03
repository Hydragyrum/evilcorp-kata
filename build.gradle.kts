import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.0"
}

group = "hydragyrum.kata"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform { }
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    testCompile("org.amshove.kluent:kluent:1.42")
    testCompile("io.kotlintest:kotlintest-runner-junit5:3.1.9")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}