/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

import org.jetbrains.kotlin.gradle.plugin.extraProperties

plugins { id("maven-publish") }

group = "com.facebook.react"

version =
    parent?.extraProperties?.get("publishing_version")
        ?: error("publishing_version not set for flipper-integration")

configurations.maybeCreate("default")

// Those artifacts should be placed inside the `artifacts/hermes-ios-*.tar.gz` location.
val hermesiOSDebugArtifactFile: RegularFile =
    layout.projectDirectory.file("artifacts/hermes-ios-debug.tar.gz")
val hermesiOSDebugArtifact: PublishArtifact =
    artifacts.add("default", hermesiOSDebugArtifactFile) {
      type = "tgz"
      extension = "tar.gz"
      classifier = "hermes-ios-debug"
    }
val hermesiOSReleaseArtifactFile: RegularFile =
    layout.projectDirectory.file("artifacts/hermes-ios-release.tar.gz")
val hermesiOSReleaseArtifact: PublishArtifact =
    artifacts.add("default", hermesiOSReleaseArtifactFile) {
      type = "tgz"
      extension = "tar.gz"
      classifier = "hermes-ios-release"
    }

apply(from = "../publish.gradle")

publishing {
  publications {
    getByName("release", MavenPublication::class) {
      artifactId = "react-native-artifacts"
      artifact(hermesiOSDebugArtifact)
      artifact(hermesiOSReleaseArtifact)
    }
  }
}
