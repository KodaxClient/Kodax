package me.kodingking.kodax.mixins.block;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Block.class)
public abstract class MixinBlock {

  @Shadow
  protected double minY;
  @Shadow
  protected double maxY;
  @Shadow
  protected double minZ;
  @Shadow
  protected double maxZ;
  @Shadow
  protected double minX;
  @Shadow
  protected double maxX;

  /**
   * @author KodingKing
   */
  @Overwrite
  public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
    for (EnumFacing facing : EnumFacing.values()) {
      Block block = worldIn.getBlockState(pos.offset(facing)).getBlock();
      if (block.isOpaqueCube() || block.isTranslucent()) {
        return true;
      }
    }
    return side == EnumFacing.DOWN && this.minY > 0.0D || (
        side == EnumFacing.UP && this.maxY < 1.0D || (
            side == EnumFacing.NORTH && this.minZ > 0.0D || (
                side == EnumFacing.SOUTH && this.maxZ < 1.0D || (
                    side == EnumFacing.WEST && this.minX > 0.0D || (
                        side == EnumFacing.EAST && this.maxX < 1.0D || !worldIn.getBlockState(pos)
                            .getBlock()
                            .isOpaqueCube())))));
  }
}
