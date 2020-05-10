#!groovy

def target = env.BRANCH_NAME.equals('master') ||
  env.BRANCH_NAME.startsWith('release/') ? 'deploy' : 'verify'

pipeline {
  agent {
    docker {
      image 'maven:3-jdk-13-alpine'
    }
  }

  options {
    disableConcurrentBuilds()
    buildDiscarder(logRotator(numToKeepStr: '3'))
  }

  stages {
    stage('init') {
      steps {
        def dockerHome = tool 'myDocker'
        env.PATH = "${dockerHome}/bin:${env.PATH}"
      }
    }

    stage('build') {
      steps {
        sh "mvn clean ${target}"
      }
    }
  }
}
