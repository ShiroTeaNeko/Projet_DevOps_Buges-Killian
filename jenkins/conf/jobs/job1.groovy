def pipelineScript = new File('var/jenkins_config/jobs/job1-pipeline.groovy').getText("UTF-8")

pipelineJob('CI/Job1') {
    description("Job Pipeline 1")
    parameters {
        stringParam {
            name('PARAM1')
            defaultValue('PARAM1')
            description("PARAM1 desc")
            trim(false)
        }
    }
    definition {
        cps {
            script(pipelineScript)
            sandbox()
        }
    }
}