pipeline{

   agent any

       stages {
         stage('Checkout') {
           steps {
            git branch: 'SpringRestFull', url: 'https://github.com/gariktepanosian/UserPermittion.git'
           }
         }

         stage('Compile') {
           steps {
             echo 'Compiling with mvn'
             bat "mvn compile"
             }
           }

         stage('Test') {
           steps {
             echo 'Testing with Junit'
             bat "mvn test"
             }
           }



       }


}