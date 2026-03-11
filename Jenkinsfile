pipeline {
    agent any

    tools {
        maven 'Maven 3.x' // Must match the name in Jenkins Global Tool Configuration
        jdk 'JDK 17'     // Must match the name in Jenkins Global Tool Configuration
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                // Using -DskipTests to avoid the MySQL connection error
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying to Tomcat on port 8086...'
                bat '''
                set TOMCAT_WEBAPPS=C:\\dev\\apache-tomcat-10.1.52-windows-x64\\apache-tomcat-10.1.52\\webapps
                if exist "%TOMCAT_WEBAPPS%\\cmspring2.war" del /f /q "%TOMCAT_WEBAPPS%\\cmspring2.war"
                if exist "%TOMCAT_WEBAPPS%\\cmspring2" rd /s /q "%TOMCAT_WEBAPPS%\\cmspring2"
                copy "target\\cmspring2.war" "%TOMCAT_WEBAPPS%\\"
                '''
            }
        }
    }
}