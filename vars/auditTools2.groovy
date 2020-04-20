def call(Map config) {
    node {
      echo ${config.message}
      sh '''
        git version
        docker version
        dotnet --list-sdks
        dotnet --list-runtimes
      '''
    }
}