pipeline {
    agent any

    options {
        disableConcurrentBuilds()
    }
    environment {
        def repoName = "";
        def branchName = "";
        def versionName = "";
        def frontendImageName = "";
        def backendImageName = "";
        def dockerRegistryCredential = 'sps-dockerhub'
        def dockerRegistry = "spsproject/app"
        def backendImage = '';
        def frontendImage = '';
     }

    stages {
        stage('Init') {
            steps {
                script {
                    echo 'Init variables'
                    repoName = "git@github.com:HunYahiko/smart-parking-system.git"
                    branchName = env.BRANCH_NAME
                    versionName = env.BRANCH_NAME
                    backendImageName = "${dockerRegistry}:sps-backend-latest"
                    frontendImageName = "${dockerRegistry}:sps-frontend-latest"
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
                                    sh('npm run build_prod')
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
                                backendImage = docker.build backendImageName
                            }
                        }
                    }
                }
				stage('build docker image for front-end project') {
					steps {
						script {
							dir('sps-frontend') {
								echo 'Building docker image for front-end project...'
								frontendImage = docker.build frontendImageName
							}
						}
					}
				}
            }
		}
		stage('push docker images to repository') {
			stages {
                stage('push back-end image to repository') {
                    steps {
                        script {
                            dir('sps-backend') {
                                echo 'Pushing back-end image to dockerhub...'
                                docker.withRegistry('', dockerRegistryCredential) {
                                    backendImage.push();
                                }
                            }
                        }
                    }
                }
                stage('push front-end image to repository') {
                    steps {
                        script {
                            dir('sps-frontend') {
                                echo 'Pushing front-end image to dockerhub...'
                                docker.withRegistry('', dockerRegistryCredential) {
                                    frontendImage.push();
                                }
                            }
                        }
                    }
                }
            }
		}
        stage('deploy to application instance') {
            steps {
                script {
                    sshagent(credentials: ['app-instance-credentials']) {
                        withCredentials([
                                usernamePassword(credentialsId: dockerRegistryCredential,
                                        usernameVariable: 'username',
                                        passwordVariable: 'password')
                        ]) {
                            sh('sshpass -p "jenkins" ssh -o StrictHostKeyChecking=no jenkins@smart-parking-system uptime')
                            sh('docker login -u ' + username + ' -p ' + password)
                            sh('docker run --rm -d -p 8080:8080 ' + backendImageName)
                            sh('docker run --rm -d -p 80:4200 ' + frontendImageName)
                        }
                    }
                }
            }
        }
    }
}
