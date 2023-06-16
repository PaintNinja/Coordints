package ga.ozli.minecraftmods.coordints

import groovy.transform.CompileStatic
import groovy.transform.stc.POJO
import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Player
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.ClientChatEvent
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent
import net.minecraftforge.event.entity.EntityEvent
import net.minecraftforge.event.entity.player.PlayerEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import org.groovymc.gml.bus.EventBusSubscriber

import static ga.ozli.minecraftmods.coordints.Action.*

@CompileStatic
@EventBusSubscriber(dist = Dist.CLIENT)
final class ForgeEvents {
    private static boolean inBlacklistedArea = false

    @SubscribeEvent
    static void onEnteringSection(final EntityEvent.EnteringSection event) {
        final var entity = event.entity
        if (event.didChunkChange() && entity instanceof Player) {
            inBlacklistedArea = false
            if (Config.USE_BLACKLIST) {
                final double playerX = entity.x
                final double playerZ = entity.z
                for (int i = 0; i < Config.BLACKLISTED_COORDS.length; i += 4) {
                    if (playerX >= Config.BLACKLISTED_COORDS[i] && playerX <= Config.BLACKLISTED_COORDS[i + 1]
                            && playerZ >= Config.BLACKLISTED_COORDS[i + 2] && playerZ <= Config.BLACKLISTED_COORDS[i + 3]) {
                        inBlacklistedArea = true
                        break
                    }
                }
            }
        }
    }

    @SubscribeEvent
    static void onSendChatMessage(final ClientChatEvent event) {
        final String message = event.message
        if (Utils.messageContainsCoords(message)) {
            switch (Config.BLACKLIST_ACTION) {
                case REDACT -> event.message = Utils.redactCoords(message)
                case RANDOMISE -> event.message = Utils.redactCoords(message, s -> String.valueOf(Utils.RANDOM.nextInt()))
                case BLOCK -> {
                    Minecraft.instance.player
                            ?.displayClientMessage(Component.translatable('messages.coordints.blocked')
                                    .withStyle(style -> style.withColor(ChatFormatting.RED)), true)
                    event.canceled = true
                }
            }
        }
    }

    @SubscribeEvent
    static void onWorldJoin(final PlayerEvent.PlayerLoggedInEvent event) {
        onEnteringSection(new EntityEvent.EnteringSection(event.entity, 0L, 0L));
    }

    @SubscribeEvent
    static void onDebugScreen(final CustomizeGuiOverlayEvent.DebugText event) {
        if (inBlacklistedArea)
            event.left.removeIf(line -> line.startsWithAny('XYZ: ', 'Block: ', 'Chunk: ', 'Chunks['))
    }
}
