import org.assertj.core.api.Assertions.assertThat
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File


class Test {

    @get:Rule
    val temporaryFolder = TemporaryFolder()

    @Test
    fun `should success with older version`() {
        val tempDir = temporaryFolder.newFolder()

        val buildResult = buildResult(tempDir, "4.5.1")

        assertThat(buildResult.build().task(":recompile")?.outcome).isEqualTo(TaskOutcome.SUCCESS)
    }

    @Test
    fun `should fail with latest version`() {
        val tempDir = temporaryFolder.newFolder()

        val buildResult = buildResult(tempDir, "4.6")

        assertThat(buildResult.buildAndFail().task(":recompile")?.outcome).isEqualTo(TaskOutcome.FAILED)
    }

    private fun buildResult(tempDir: File?, version: String): GradleRunner {
        val buildScript =
                """
                plugins {
                    id 'java'
                }
                task(recompile, type: GradleBuild) {
                   tasks = [':compileJava']
                }
                """

        File("$tempDir/build.gradle").writeText(buildScript)

        return GradleRunner.create()
                .withProjectDir(tempDir)
                .withTestKitDir(tempDir)
                .withArguments("compileJava", "recompile")
                .withDebug(true)
                .withGradleVersion(version)
    }
}
