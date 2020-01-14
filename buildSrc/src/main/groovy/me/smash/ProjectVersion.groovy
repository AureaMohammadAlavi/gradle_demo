package me.smash

class ProjectVersion {
    int minor
    int major
    boolean release

    ProjectVersion(int major, int minor, boolean release) {
        this.minor = minor
        this.major = major
        this.release = release
    }

    @Override
    String toString() {
        return "$major.$minor${release ? '' : '-SNAPSHOT'}"
    }
}