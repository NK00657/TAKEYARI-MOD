package io.github.nk00657.takeyari

import io.github.nk00657.takeyari.core.init.ItemInit
import io.github.nk00657.takeyari.core.init.TabInit
import net.minecraft.world.item.CreativeModeTabs
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent
import net.minecraftforge.fml.common.Mod
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod("takeyari")
object TakeYari {
    const val MOD_ID = "takeyari"

    init {
        ItemInit.ITEMS.register(MOD_BUS)
        TabInit.CREATIVE_MODE_TABS.register(MOD_BUS)

        MOD_BUS.addListener(::addCreativeTab)
    }
    private fun addCreativeTab(event: BuildCreativeModeTabContentsEvent) {
        if (event.tabKey == TabInit.TAKEYARI.key) {
            // 一括登録
            ItemInit.ITEMS.entries.forEach { registryObject ->
                event.accept(registryObject.get())
            }
        }
    }
}