package canal.example

import io.pivotal.canal.extensions.nestedstages.stages
import io.pivotal.canal.extensions.pipelines
import io.pivotal.canal.model.ManualJudgment
import io.pivotal.canal.model.Wait
import java.io.File

class PipelineJson {
    fun writeFiles() {
        val pipelineJsonFile = File("build/pipelines/pipelines.json")
        pipelineJsonFile.parentFile.mkdirs()
        pipelineJsonFile.writeText(pipelines.toJson())
    }
}

val pipelines = pipelines {
    app("app1") {
        pipeline("just waiting") {
            stages = stages {
                stage(Wait(420))
            }
        }
    }
    app("app2") {
        pipeline("just judging") {
            stages = stages {
                stage(ManualJudgment("Judge me."))
            }
        }
        pipeline("waiting then judging") {
            stages = stages {
                stage(
                        Wait(420),
                        comments = "Wait before judging me."
                ) then {
                    stage(ManualJudgment("Okay, Judge me now."))
                }
            }
        }
    }
}
