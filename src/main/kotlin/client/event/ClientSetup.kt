package io.github.nk00657.takeyari.client.event

import io.github.nk00657.takeyari.core.init.ItemInit
import net.minecraft.client.renderer.item.ItemProperties
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent

class ClientSetup {
    @SubscribeEvent
    fun onClientSetup(event: FMLClientSetupEvent){
        event.enqueueWork {
            val bowItem = ItemInit.bamboo_tube.get()


            ItemProperties.register(bowItem, ResourceLocation("pulling")) { stack, _, entity, _ ->
                if (entity != null && entity.isUsingItem && entity.useItem == stack) 1.0f else 0.0f
            }


            ItemProperties.register(bowItem, ResourceLocation("pull")) { stack, _, entity, _ ->
                if (entity == null || entity.useItem != stack) {
                    0.0f
                } else {

                    (stack.useDuration - entity.useItemRemainingTicks).toFloat() / 10.0f
                }
            }
        }
        }
    }
}