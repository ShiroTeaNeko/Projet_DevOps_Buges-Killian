pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "maven"
    }

    environment {
        TEST = 'test'
    }

    stages {
        stage('Job describ') {
            steps {
                script {
                    println('Env var: ' + env.TEST)
                    sh 'java --version'
                    sh 'mvn --version'
                    sh 'python3 --version'
                    currentBuild.displayName = "#${BUILD_NUMBER} ${params.PARAM1}"
                }
            }
        }
        stage('Job git checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/Ozz007/sb3t'
            }
        }
        stage('Job compile') {
            steps {
                sh 'mvn compile'
            }
        }
        stage('Job testUT') {
            when {
                expression { params.SKIP_TESTS == false }
            }
            steps {
                sh 'mvn test'
            }
        }
        stage('job verifyIT') {
            when {
                expression { params.SKIP_TESTS == false }
            }
            steps {
                sh 'mvn verify'
            }
        }
        stage('rename Jar') {
            steps {
                script{
                    sh "mv sb3t-ws/target/sb3t-ws-1.0-SNAPSHOT.jar sb3t-${params.VERSION}-${params.VERSION_TYPE}.jar"
                }
            }
        }
        stage('Job install') {
            steps {
                sh 'mvn install'
            }
        }
    }
}