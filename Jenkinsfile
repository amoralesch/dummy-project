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
        script {
          def dockerTool = tool name: 'myDocker', type: 'org.jenkinsci.plugins.docker.commons.tools.DockerTool'
          env.PATH = "${dockerTool}/bin:${env.PATH}"
          withEnv(["DOCKER=${dockerTool}/bin"]) {
            sh "docker -v"
              //stages
              //here we can trigger: sh "sudo ${DOCKER}/docker ..."
          }
        }
      }
    }

    stage('build') {
      steps {
        sh "mvn clean ${target}"
      }
    }
  }
}
