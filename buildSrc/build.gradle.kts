plugins {
    `java`
    kotlin("jvm") version "2.1.21"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.1.21")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.19.1")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.19.1")
}
