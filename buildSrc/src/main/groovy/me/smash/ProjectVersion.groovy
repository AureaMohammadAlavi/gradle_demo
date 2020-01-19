package me.smash


class ProjectVersion {
    int major
    int minor
    String build

    ProjectVersion(int major, int minor, String build) {
        this.major = major
        this.minor = minor
        this.build = build
    }


    @Override
    String toString() {
        if (build == null) {
            return "$major.$minor"
        } else {
            return "$major.$minor.$build"
        }
    }
}
