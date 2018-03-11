plugins {
    java
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

dependencies {
    testCompile (gradleTestKit())
    testCompile (group = "junit",                          name = "junit",                        version = property("junit.version") as String)
    testCompile (group = "org.assertj",                    name = "assertj-core",                 version = property("assertj.version") as String)
}

tasks {
    val recompile by creating(GradleBuild::class) {
        tasks = listOf("compileJava")
    }
}
