import mbjava.MbUtils

plugins {
    `java`
    id("me.champeau.jmh") version "0.7.3"
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter("5.13.1")
        }
    }
}

java {                                      
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.register("benchmarkJson") {
    val json = MbUtils.benchmarksJson()
    System.out.println(json);
}

allprojects {
  repositories {
    mavenCentral()
  }
}

val benchmarkName = providers.systemProperty("benchmark").getOrElse("")

jmh {
    fork.set(2)
    includes.set(listOf(benchmarkName))
    iterations.set(5)
    warmupIterations.set(2)
    failOnError.set(true)
    jmhVersion.set("1.37")
    profilers.set(listOf("gc"))
}

val log4jVersion = "2.25.1"

dependencies {
    jmh("org.apache.logging.log4j:log4j-core:$log4jVersion")
    jmh("org.slf4j:slf4j-api:2.0.17")
}
