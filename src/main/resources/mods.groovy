ModsDotGroovy.make {
    modLoader = 'javafml'
    loaderVersion = '[45,)'

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
            forge = '>=45.0.40'
            minecraft = '1.19.4'
        }
    }
}
