#!groovy

def target = env.BRANCH_NAME.equals('master') ||
    env.BRANCH_NAME.startsWith('release/') ? 'deploy' : 'verify'

pipeline {
    options {
        disableConcurrentBuilds()
        buildDiscarder(logRotator(numToKeepStr: '3'))
    }

    stages {
        stage('build') {
            steps {
                sh "mvn clean ${target}"
            }
        }
    }
}
