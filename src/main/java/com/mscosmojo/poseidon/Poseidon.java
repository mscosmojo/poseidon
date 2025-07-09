/*
 * Copyright (c) 2025 MsCosmoJo
 *
 * This file is part of Poseidon and is licensed under the
 * GNU General Public License v3.0 or later.
 * See LICENSE for details.
 */

package com.mscosmojo.poseidon;

import net.fabricmc.api.ModInitializer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Poseidon implements ModInitializer {
	public static final String MOD_ID = "poseidon";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Poseidon mod initialized! Seize the seas!");
	}
}