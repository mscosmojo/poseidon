/*
 * Copyright (c) 2025 MsCosmoJo
 *
 * This file is part of Poseidon and is licensed under the
 * GNU General Public License v3.0 or later.
 * See LICENSE for details.
 */

package com.mscosmojo.poseidon.mixin;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.ClientConnection;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


// give an unbreakable riptide 3 trident on join

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
  @Shadow public ServerPlayerEntity player;

  @Inject(method = "<init>", at = @At("RETURN"))
  private void giveTridentOnce(
    MinecraftServer server,
    ClientConnection conn,
    ServerPlayerEntity player,
    CallbackInfo ci
  ) {
    // already have one?
    if (hasRiptideTrident(player)) return;

    // create trident
    ItemStack trident = new ItemStack(Items.TRIDENT);

    // make it unbreakable with riptide 3
    CompoundTag tag = trident.getOrCreateTag();
    tag.putBoolean("Unbreakable", true);

    Map<Enchantment,Integer> ench = new HashMap<>();
    ench.put(Enchantments.RIPTIDE, 3);
    EnchantmentHelper.set(ench, trident);

    // give or drop
    if (!player.inventory.insertStack(trident)) {
      player.dropItem(trident, false);
    }
  }

  private static boolean hasRiptideTrident(ServerPlayerEntity player) {
    for (ItemStack stack : player.inventory.main) {
      if (isRiptideTrident(stack)) return true;
    }
    for (ItemStack stack : player.inventory.offHand) {
      if (isRiptideTrident(stack)) return true;
    }
    return false;
  }

  private static boolean isRiptideTrident(ItemStack stack) {
    return stack.getItem() == Items.TRIDENT
        && EnchantmentHelper.getRiptide(stack) > 0;
  }
}