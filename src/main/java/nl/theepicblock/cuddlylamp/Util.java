package nl.theepicblock.cuddlylamp;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.Material;
import net.minecraft.block.enums.DoorHinge;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class Util {
    public static BlockPos getPosBesideDoor(BlockState state, BlockPos pos) {
        boolean right = state.get(DoorBlock.HINGE) == DoorHinge.RIGHT;
        Direction facing = state.get(DoorBlock.FACING);

        switch (facing) {
            case NORTH:
                return right ? pos.west() : pos.east();
            case SOUTH:
                return right ? pos.east() : pos.west();
            case EAST:
                return right ? pos.north() : pos.south();
            case WEST:
                return right ? pos.south() : pos.north();
            default:
                return null;
        }
    }

    public static void setDoorOpen(World world, BlockState state, BlockPos pos, boolean open) {
        if (state.getMaterial() != Material.METAL && state.getBlock() instanceof DoorBlock) {
            ((DoorBlock)state.getBlock()).setOpen(world, state, pos, open);
        }
    }
}
