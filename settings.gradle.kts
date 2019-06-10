//include(":samples:mpp-lib")

buildCache {
    local(DirectoryBuildCache::class) {
        directory = File(rootDir, "build-cache")
        removeUnusedEntriesAfterDays = 30
    }
}

enableFeaturePreview("GRADLE_METADATA")