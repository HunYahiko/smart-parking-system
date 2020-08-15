pipeline {
    agent any

    options {
        disableConcurrentBuilds()
    }
    environment {
        def repoName = "";
        def branchName = "";
        def versionName = "";
    }

    stages {
        stage('Init') {
            steps {
                script {
                    echo 'Init variables'
                    repoName = "git@github.com:HunYahiko/smart-parking-system.git"
                    branchName = env.BRANCH_NAME
                    versionName = env.BRANCH_NAME
                }

            }
        }
        stage('clone') {
            steps {
                script {
                    echo 'Clone repository'

                    // for single branch job
                    // git credentialsId: 'git-key', url: repoName, branch: branchName

                    //for multibranch job
                    checkout scm
                }
            }
        }
        stage('build project') {
            stages {
                stage('build back-end project') {
                    steps {
                        script {
                            dir('sps-backend') {
                                echo 'Building back-end project...'
                                withMaven(maven: 'maven3.5.2', mavenOpts: '-Dmaven.test.skip=true') {
                                    sh("mvn clean install")
                                }
                            }
                        }
                    }
                }
                stage('build front-end project') {
                    steps {
                        script {
                            dir ('sps-frontend') {
                                echo 'Building front-end project...'
                                nodejs('nodejs12.16') {
                                    echo 'Installing dependencies...'
                                    sh('npm i')
                                    echo 'Building project...'
                                    sh('npm run build')
                                }
                            }
                        }
                    }
                }
            }
        }
		stage('build docker images') {
            stages {
                stage('build docker image for back-end project') {
                    steps {
                        script {
                            dir('sps-backend') {
                                echo 'Building docker image for back-end project...'
                                def image = docker.build "sps-backend-image"
                            }
                        }
                    }
                }
            }
		}
    }
}
