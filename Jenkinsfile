pipeline {
        agent any
        stages {
            stage('Git Clone') {
                steps {
                    script {
                        echo "Fetching the source code repo from github"
                        git branch: 'main', credentialsId: 'github-Credentials', url: 'https://github.com/Meetraj2512/AC-Automation-Task.git'
                    }
                }
            }
            stage('Build & Test') {
                steps {
                    script {
                        echo "Building Project"
                        bat "echo "Building Project %date% : %time%""
                        bat 'mvn install -Dtest=AmazonTask'
                    }
                }
            }
        }
    }
}