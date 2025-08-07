package com.example.totemmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;

public class TotemKeyMod implements ClientModInitializer {
    private static KeyBinding keyBinding;

    @Override
    public void onInitializeClient() {
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.totemmod.swap_totem",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "category.totemmod"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (keyBinding.wasPressed() && client.player != null && client.interactionManager != null) {
                PlayerEntity player = client.player;
                for (int i = 0; i < player.getInventory().size(); i++) {
                    if (player.getInventory().getStack(i).getItem() == Items.TOTEM_OF_UNDYING) {
                        client.interactionManager.clickSlot(
                                player.currentScreenHandler.syncId,
                                i,
                                40,
                                SlotActionType.SWAP,
                                player
                        );
                        break;
                    }
                }
            }
        });
    }
}