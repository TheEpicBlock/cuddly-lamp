package nl.theepicblock.cuddlylamp.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nl.theepicblock.cuddlylamp.PlayerInterface;
import nl.theepicblock.cuddlylamp.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class MixinPlayerEntity extends PlayerEntity implements PlayerInterface {
    public MixinPlayerEntity(World world, BlockPos pos, float yaw, GameProfile profile) {
        super(world, pos, yaw, profile);
    }

    @Shadow public abstract ServerWorld getServerWorld();

    @Unique private int lastClickedDoor;
    @Unique private boolean isInDoor;
    @Unique private BlockPos lastDoorPos;

    @Override
    public void setLastClickedDoor(int tick) {
        lastClickedDoor = tick;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tickInject(CallbackInfo ci) {
        if (this.getServerWorld().getServer().getTicks() - lastClickedDoor < 200) {
            //check if the player has interacted with a door in the last 10 seconds
            boolean wasInDoor = isInDoor;
            isInDoor = this.getServerWorld().getBlockState(this.getBlockPos()).getBlock() instanceof DoorBlock;

            if (isInDoor) lastDoorPos = this.getBlockPos().toImmutable();

            if (wasInDoor && !isInDoor) {
                closeDoorCreepily(this.getServerWorld(), lastDoorPos);
            }
        } else {
            isInDoor = false;
        }
    }

    private void closeDoorCreepily(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (!(state.getBlock() instanceof DoorBlock)) return;

        Util.setDoorOpen(world, state, pos, false);

        BlockPos pos2 = Util.getPosBesideDoor(state, pos);
        BlockState state2 = world.getBlockState(pos2);

        Util.setDoorOpen(world, state2, pos2, false);
    }
}
