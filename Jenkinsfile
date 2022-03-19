pipeline {
    agent: any
    stages {
        stage("build") {
            steps {
                echo 'Executing gradle...'
                withGradle() {
                    sh './gradlew -v'
                }
            }
        }
    }
}