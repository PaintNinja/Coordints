ModsDotGroovy.make {
    modLoader = 'javafml'
    loaderVersion = '[44,)'

    license = 'MIT'
    issueTrackerUrl = 'https://github.com/PaintNinja/Coordints/issues'

    mod {
        modId = 'coordints'
        displayName = "Coordin'ts"
        version = this.version
        author = 'Paint_Ninja'
        description = "Helps prevent accidentally leaking your base's coordinates"

        displayTest = DisplayTest.IGNORE_ALL_VERSION

        dependencies {
            forge = '>=44.1.0'
            minecraft = '1.19.3'
        }
    }
}
