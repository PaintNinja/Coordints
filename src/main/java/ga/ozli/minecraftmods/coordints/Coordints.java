package ga.ozli.minecraftmods.coordints;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

// todo: support server-side-only, too
@Mod(Coordints.MODID)
public class Coordints {
    private static final Logger LOGGER = LogUtils.getLogger();
    static final String MODID = "coordints";

    public Coordints() {
        LOGGER.info("Coordin'ts starting");
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.SPEC);
    }
}
