ModsDotGroovy.make {
    modLoader = 'gml'
    loaderVersion = '[4,)'

    license = 'MIT'
    issueTrackerUrl = 'https://github.com/PaintNinja/Coordints/issues'

    mod {
        modId = 'coordints'
        displayName = "Coordin'ts"
        version = this.version
        author = 'Paint_Ninja'
        description = "Helps prevent accidentally leaking your base's coordinates"
        displayUrl = 'https://www.curseforge.com/minecraft/mc-mods/coordints'

        displayTest = DisplayTest.IGNORE_ALL_VERSION

        dependencies {
            forge = '>=46.0.14'
            minecraft = this.minecraftVersionRange
            mod {
                modId = 'gml'
                versionRange = '>=4.0.2'
                side = DependencySide.CLIENT
            }
        }
    }
}
