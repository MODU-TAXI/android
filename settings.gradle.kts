pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://devrepo.kakao.com/nexus/content/groups/public/")
        maven("https://naver.jfrog.io/artifactory/maven/")
    }
}

rootProject.name = "modutaxi"
include(":app")
include(":data")
include(":domain")
include(":presentation")
