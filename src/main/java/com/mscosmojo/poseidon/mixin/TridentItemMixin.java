/*
 * Copyright (c) 2025 MsCosmoJo
 *
 * This file is part of Poseidon and is licensed under the
 * GNU General Public License v3.0 or later.
 * See LICENSE for details.
 */

package com.mscosmojo.poseidon.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.TridentItem;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


// force riptide’s wetness check (isTouchingWaterOrRain)
// to always return true

@Mixin(TridentItem.class)
public class TridentItemMixin {
   // redirect in use()

  @Redirect(
    method = "use(Lnet/minecraft/world/World;"
           + "Lnet/minecraft/entity/player/PlayerEntity;"
           + "Lnet/minecraft/util/Hand;)"
           + "Lnet/minecraft/util/TypedActionResult;",
    at = @At(
      value = "INVOKE",
      target = "Lnet/minecraft/entity/player/PlayerEntity;"
             + "isTouchingWaterOrRain()Z"
    )
  )
  private boolean alwaysWetForUse(PlayerEntity player) {
    return true;
  }


    // redirect the check in onStoppedUsing()

  @Redirect(
    method = "onStoppedUsing("
           + "Lnet/minecraft/item/ItemStack;"
           + "Lnet/minecraft/world/World;"
           + "Lnet/minecraft/entity/LivingEntity;I)V",
    at = @At(
      value = "INVOKE",
      target = "Lnet/minecraft/entity/player/PlayerEntity;"
             + "isTouchingWaterOrRain()Z"
    )
  )
  private boolean alwaysWetForStop(PlayerEntity player) {
    return true;
  }
}