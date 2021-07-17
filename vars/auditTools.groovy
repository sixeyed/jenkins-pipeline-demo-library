def call() {
    node {
      bat '''
        git version
        docker version
        dotnet --list-sdks
        dotnet --list-runtimes
      '''
    }
}
