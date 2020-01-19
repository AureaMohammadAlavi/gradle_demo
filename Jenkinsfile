pipeline {
    agent {
        node {
            label 'myLabel'
        }
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/AureaMohammadAlavi/gradle_demo'
            }
        }
        stage('Compile') {
            steps {
                gradlew('clean', 'classes')
            }
        }
        stage('Unit Tests') {
            steps {
                gradlew('test')
            }
            post {
                always {
                    junit '**/build/test-results/test/TEST-*.xml'
                }
            }
        }
        stage('Integration & Functional Tests') {
/*
            environment {
               SERVER_BASE_URL="http://jenkins-blueocean:8090/"
               REMOTE_WEB_DRIVER_URL="http://blueo-selenium:4444/wd/hub"
            }
*/
            steps {
                gradlew('integrationTest funTest')
            }
            post {
                always {
                    junit '**/build/test-results/integrationTest/TEST-*.xml'
                    junit '**/build/test-results/funcTest/TEST-*.xml'
                    jacoco(execPattern: '**/*.exec', classPattern: '**/build/classes/*/main', sourcePattern: '**/src/main/java,**/src/main/scala,**/src/main/groovy', sourceInclusionPattern: '**/*.java,**/*.scala,**/*.groovy')
                }
            }
        }
        stage('Code Analysis') {
/*
            environment {
                SONAR_SERVER_URL = "http://host.docker.internal:9000"
            }
*/
            steps {
                gradlew('jacocoTestReport sonarqube')
            }
        }

        stage('Assemble') {
            steps {
                gradlew('assemble -PjsOptimized')
                stash includes: '**/build/libs/*.war', name: 'app'
            }
        }

        stage('Publish') {
            environment {
                BINTRAY_API_KEY = credentials('BINTRAY_API_KEY')
            }
            steps {
                gradlew('publishMyPublicationPublicationToBintaryRepository')
            }
        }

        stage('Promotion') {
            steps {
                timeout(time: 1, unit: 'DAYS') {
                    input 'Deploy to Production?'
                }
            }
        }
/*
        stage('Deploy to Production') {
            environment {
                HEROKU_API_KEY = credentials('HEROKU_API_KEY')
            }
            steps {
                unstash 'app'
                gradlew('deployHeroku')
            }
        }
*/
        stage('Deploy to Production') {
            environment {
                SERVER_PRIVATE_KEY_FILE = "/Users/mohammad/Downloads/test/gradle/gradle-demo/tomcat-vagrant/.vagrant/machines/tomcat/virtualbox/private_key"
            }
            steps {
                unstash 'app'
                gradlew('startTomcat')
            }
        }
    }
    post {
        failure {
            mail to: 'm.alavi1986@gmail.com', subject: 'Build failed', body: 'Please fix!'
        }
        success {
            mail to: 'm.alavi1986@gmail.com', subject: 'Build succeeded', body: 'Have fun!'
        }
    }
}

def gradlew(String... args) {
    sh "./gradlew ${args.join(' ')} -s"
}