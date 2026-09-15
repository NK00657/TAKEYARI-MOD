package io.github.nk00657.takeyari.core.init

import io.github.nk00657.takeyari.TakeYari
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.RegistryObject
import java.util.function.Supplier

object TabInit {
    val CREATIVE_MODE_TABS: DeferredRegister<CreativeModeTab> =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TakeYari.MOD_ID)

    val TAKEYARI: RegistryObject<CreativeModeTab> = CREATIVE_MODE_TABS.register("takeyari") {
        CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.takeyari"))
            .icon(Supplier { ItemStack(ItemInit.bamboo_spear) })
            .build()
    }
}