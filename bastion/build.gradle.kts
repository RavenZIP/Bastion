plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.vanniktech.mavenPublish)
}

group = "io.ravenzip.bastion"

version = "0.0.1"

kotlin {
    jvm()

    sourceSets {
        val commonMain by getting {  }

        val commonTest by getting { dependencies { implementation(libs.kotlin.test) } }
    }

    jvmToolchain(22)
}

// mavenPublishing {
//    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
//
//    signAllPublications()
//
//    coordinates(group.toString(), "library", version.toString())
//
//    pom {
//        name = "My library"
//        description = "A library."
//        inceptionYear = "2024"
//        url = "https://github.com/kotlin/multiplatform-library-template/"
//        licenses {
//            license {
//                name = "XXX"
//                url = "YYY"
//                distribution = "ZZZ"
//            }
//        }
//        developers {
//            developer {
//                id = "XXX"
//                name = "YYY"
//                url = "ZZZ"
//            }
//        }
//        scm {
//            url = "XXX"
//            connection = "YYY"
//            developerConnection = "ZZZ"
//        }
//    }
// }
