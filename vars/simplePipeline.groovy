def call() {
    pipeline {
        agent any
        environment {
            MODULE='m4'
        }
        stages {
            stage('Verify') {                  
                steps {
                    echo "Module: ${MODULE}"
                    sh 'git version'
                }
            }
        }
    }
}