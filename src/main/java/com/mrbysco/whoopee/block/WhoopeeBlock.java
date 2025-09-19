package com.mrbysco.whoopee.block;

import com.mojang.serialization.MapCodec;
import com.mrbysco.whoopee.config.WhoopeeConfig;
import com.mrbysco.whoopee.util.FartUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WhoopeeBlock extends HorizontalDirectionalBlock {

	public static final MapCodec<WhoopeeBlock> CODEC = simpleCodec(WhoopeeBlock::new);
	private static final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 4, 15);

	@Override
	protected MapCodec<WhoopeeBlock> codec() {
		return CODEC;
	}

	public static final BooleanProperty HIDDEN = BooleanProperty.create("hidden");

	public WhoopeeBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(HIDDEN, false));
	}

	@Override
	protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		if (!state.getValue(HIDDEN) && stack.is(Items.LEATHER)) {
			level.setBlock(pos, state.setValue(HIDDEN, true), 3);
			stack.consume(1, player);
		}
		return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		if (state.getValue(HIDDEN) && player.isCrouching()) {
			level.setBlock(pos, state.setValue(HIDDEN, false), 3);
			ItemEntity itementity = new ItemEntity(level, pos.getX(), pos.getY() + 0.5, pos.getZ(), Items.LEATHER.getDefaultInstance());
			itementity.setDeltaMovement(0.0D, 0.2D, 0.0D);
			itementity.hurtMarked = true;
			level.addFreshEntity(itementity);
		}
		return super.useWithoutItem(state, level, pos, player, hitResult);
	}

	@Override
	public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, double fallDistance) {
		super.fallOn(level, state, pos, entity, fallDistance);
		if (FartUtil.canPlay(level.getRandom(), WhoopeeConfig.COMMON.fallTootChance.getAsDouble())) {
			FartUtil.playFart(level, entity, true);
		}
	}

	@Override
	public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
		super.stepOn(level, pos, state, entity);
		//Check if player is moving
		if (entity instanceof LivingEntity livingEntity && livingEntity.getDeltaMovement().lengthSqr() > 0.03 &&
				FartUtil.canPlay(level.getRandom(), WhoopeeConfig.COMMON.stepTootChance.getAsDouble())) {
			FartUtil.playFart(level, entity, false);
		}
	}

	@Override
	public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
		if (state.getValue(HIDDEN)) {
			ItemEntity itementity = new ItemEntity(level, pos.getX(), pos.getY() + 0.5, pos.getZ(), Items.LEATHER.getDefaultInstance());
			itementity.setDeltaMovement(0.0D, 0.2D, 0.0D);
			itementity.hurtMarked = true;
			level.addFreshEntity(itementity);
		}
		return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
	}

	@Override
	protected RenderShape getRenderShape(BlockState state) {
		return state.getValue(HIDDEN) ? RenderShape.INVISIBLE : RenderShape.MODEL;
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, HIDDEN);
	}
}
