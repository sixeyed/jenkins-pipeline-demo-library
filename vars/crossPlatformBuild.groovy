def getBuildContext(Map config, String architecture) {
  if (architecture=='windows-amd64') {
    return config.windowsContext
  }
  return config.linuxContext
}

def buildAndPush() {
    docker.withServer("tcp://${DOCKER_SERVER}:2376", 'docker-client') {
        def image = docker.build("${REPO_NAME}:${TAG}", "--pull -f ${BUILD_CONTEXT}/${DOCKERFILE} ${BUILD_CONTEXT}")
        withDockerRegistry([credentialsId: "docker-hub", url: "" ]) {        
            image.push()
        }
    }
}

def call(Map config) {
    pipeline {
        agent any
        environment {
            REPO_NAME = "${config.repoName}"
            DOCKER_LINUX_SERVER = 'up-ub1604.sixeyed'            
            DOCKER_WINDOWS_SERVER = 'up-win2019.sixeyed'
        }
        stages {
            stage('build') {
                environment {
                    DOCKERFILE = 'Dockerfile'
                }
                parallel {
                    stage('linux-amd64') {
                        environment {
                            BUILD_CONTEXT = getBuildContext(config, env.STAGE_NAME)
                            TAG = "$STAGE_NAME"
                            DOCKER_SERVER = "$DOCKER_LINUX_SERVER"
                        }                   
                        steps {
                            script{
                                buildAndPush()
                            }
                        }
                    }               
                    stage('windows-amd64') {  
                        environment {
                            BUILD_CONTEXT = getBuildContext(config, env.STAGE_NAME)
                            TAG = "$STAGE_NAME"
                            DOCKER_SERVER = "$DOCKER_WINDOWS_SERVER"
                        }                        
                        steps {
                            script {                                
                                buildAndPush()
                            }
                        }
                    }
                }
            }
            stage('notify') {
                steps{
                    echo "https://hub.docker.com/r/$REPO_NAME"
                }
            }
        }
    }
}