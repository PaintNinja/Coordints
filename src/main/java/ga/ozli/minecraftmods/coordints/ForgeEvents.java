package ga.ozli.minecraftmods.coordints;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Coordints.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ForgeEvents {

    private static boolean inBlacklistedArea = false;

    @SubscribeEvent
    public static void onEnteringSection(final EntityEvent.EnteringSection event) {
        if (event.didChunkChange() && event.getEntity() instanceof final Player player) {
            inBlacklistedArea = false;
            if (Config.USE_BLACKLIST) {
                final double playerX = player.getX();
                final double playerZ = player.getZ();
                for (int i = 0; i < Config.BLACKLISTED_COORDS.length; i += 4) {
                    if (playerX >= Config.BLACKLISTED_COORDS[i] && playerX <= Config.BLACKLISTED_COORDS[i + 1]
                            && playerZ >= Config.BLACKLISTED_COORDS[i + 2] && playerZ <= Config.BLACKLISTED_COORDS[i + 3]) {
                        inBlacklistedArea = true;
                        break;
                    }
                }
            }
            toggleCoords(player);
        }
    }

    @SubscribeEvent
    public static void onSendChatMessage(final ClientChatEvent event) {
        if (Utils.messageContainsCoords(event.getMessage(), Config.BLACKLISTED_COORDS)) {
            assert Minecraft.getInstance().player != null;
            Minecraft.getInstance().player.displayClientMessage(Component.translatable("messages.coordints.blocked").withStyle(style -> style.withColor(ChatFormatting.RED)), true);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onWorldJoin(final PlayerEvent.PlayerLoggedInEvent event) {
        onEnteringSection(new EntityEvent.EnteringSection(event.getEntity(), 0L, 0L));
    }

    private static void toggleCoords(final Player player) {
        final var world = player.getCommandSenderWorld();
        if (world.isClientSide || player.hasPermissions(2)) {
            world.getGameRules()
                    .getRule(GameRules.RULE_REDUCEDDEBUGINFO)
                    .set(inBlacklistedArea, player.getCommandSenderWorld().getServer());
        }
    }

}
