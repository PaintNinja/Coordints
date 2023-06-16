package ga.ozli.minecraftmods.coordints

import groovy.transform.CompileStatic
import groovy.transform.PackageScope
import groovy.util.logging.Slf4j
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.config.ModConfig
import org.groovymc.gml.GMod

// todo: support server-side-only, too
// todo: support different blacklists for different worlds and servers
@Slf4j
@CompileStatic
@GMod(Coordints.MOD_ID)
final class Coordints {
    @PackageScope static final String MOD_ID = 'coordints'

    Coordints() {
        log.info "Coordin'ts starting"
        modBus.addListener(Config.&onLoad)
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.SPEC)
    }
}
