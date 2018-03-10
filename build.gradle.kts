plugins {
    id("java")
}

tasks {

    val recompile by creating(GradleBuild::class) {
        tasks = listOf("compileJava")
    }

}
