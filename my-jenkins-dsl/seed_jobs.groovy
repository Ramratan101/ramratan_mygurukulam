def jobs = [
    [name: 'build-java-project', task: 'echo "Building Java Project..."'],
    [name: 'run-shell-script', task: 'sh "echo Running Shell Script"'],
    [name: 'deploy-app', task: 'echo "Deploying Application..."'],
    [name: 'run-python-script', task: 'sh "python3 -c \'print(\\"Hello from Python\\")\'"'],
    [name: 'send-notification', task: 'echo "Sending Notification..."']
]

jobs.each { job ->
    pipelineJob(job.name) {
        description("Automatically generated job: ${job.name}")

        triggers {
            scm('* * * * *') // Runs every minute (modify as needed)
        }

        definition {
            cps {
                script("""
                    pipeline {
                        agent any
                        stages {
                            stage('Task') {
                                steps {
                                    ${job.task}
                                }
                            }
                        }
                    }
                """)
                sandbox()
            }
        }
    }

    // Trigger job immediately after creation
    queue(job.name)
}
