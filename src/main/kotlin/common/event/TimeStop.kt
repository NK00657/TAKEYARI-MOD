package io.github.nk00657.takeyari.common.event

import com.mega.endinglib.api.time.TimeStopAPI
import net.minecraft.world.entity.LivingEntity

object TimeStop {
    fun triggerTimestop(source: LivingEntity, ticks: Int){
        TimeStopAPI.use(true, source,false, ticks)
    }
}