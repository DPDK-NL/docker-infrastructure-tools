#!groovy

folder("DPDK") { }
folder("DPDK/Infrastructure") {}
folder("DPDK/Infrastructure/tools"){}

['terraform', 'jenkins'].each { job ->
    pipelineJob("DPDK/Infrastructure/tools/${job}") {
        description("Infrastructure job for ${job} tool")
        displayName("job-${job} to build ${job} docker image")

        definition {
            cpsScm {
                scriptPath("${job}/Jenkinsfile")
                scm {
                    git {
                        branch('main')
                        remote{
                            github("mavelasquezl85/docker-infrastructure-tools", "ssh")
                            credentials("automation-credentials")
                        }
                    }
                }
            }
        }
    }
}

// pipelineJob("Admin/Configure") {
//   parameters {
//     gitParam("revision") {
//       type('BRANCH_TAG')
//       sortMode('ASCENDING_SMART')
//       defaultValue('origin/master')
//     }
//   }
//
//   logRotator {
//     numToKeep(50)
//   }
//
//   definition {
//     cpsScm {
//       scm {
//         git {
//           remote {
//             github("mvlsqz/dpdk-casc", "ssh")
//             credentials("infrastructure-automation")
//           }
//
//           branch('$revision')
//         }
//       }
//
//       scriptPath('resources/init/ConfigurationAndSeedingPipeline.groovy')
//     }
//   }
// }
