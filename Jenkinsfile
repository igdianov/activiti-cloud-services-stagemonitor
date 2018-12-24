pipeline {
	
    agent {
	    kubernetes {
	        // Change the name of jenkins-maven label to be able to use yaml configuration snippet
	        label "maven-jenkins"
	        // Inherit from Jx Maven pod template
	        inheritFrom "maven"
	        // Add scheduling configuration to Jenkins builder pod template
	        yamlFile "gke-preemptible.yaml"
	    } 
    }
    
    environment {
      ORG               = "org.activiti.cloud.common"
      APP_NAME          = "activiti-cloud-services-stagemonitor"
      CHARTMUSEUM_CREDS = credentials("jenkins-x-chartmuseum")
    }
    stages {
      stage("CI Build and push snapshot") {
        when {
          branch "PR-*"
        }
        environment {
          PREVIEW_VERSION = "0.0.0-SNAPSHOT-$BRANCH_NAME-$BUILD_NUMBER"
          PREVIEW_NAMESPACE = "$APP_NAME-$BRANCH_NAME".toLowerCase()
          HELM_RELEASE = "$PREVIEW_NAMESPACE".toLowerCase()
        }
        steps {
          container("maven") {
            // Let's make preview version
            sh "make preview-version"

            // Let's test
            sh "make install"

            // Let's deploy preview version
            sh "make deploy"
          }
        }
      }
      stage("Build Release") {
        when {
          branch "master"
        }
        steps {
          container("maven") {
            // ensure we're not on a detached head
            sh "make checkout"

            // so we can retrieve the version in later steps
            sh "make next-version"
            
            // Let's test first
            sh "make install"

            // Let's make tag in Git
            sh "make tag"
            
            // Let's deploy to Nexus
            sh "make deploy"
          }
        }
      }
      stage("Update Versions") {
        when {
          branch "master"
        }
        steps {
          container("maven") {
            // Let's push changes and open PRs to downstream repositories
            sh "make updatebot/push-version"

            // Let's update any open PRs
            sh "make updatebot/update"

            // Let's publish release notes in Github using commits between previous and last tags
            sh "make changelog"
          }
        }
      }
    }
    post {
        always {
            cleanWs()
        }
/*
        failure {

		input """Pipeline failed. 
We will keep the build pod around to help you diagnose any failures. 

Select Proceed or Abort to terminate the build pod"""
        }
*/	

    }
}
