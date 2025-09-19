package com.mrbysco.whoopee.item;

import com.mrbysco.whoopee.block.WhoopeeBlock;
import com.mrbysco.whoopee.util.FartUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class WhoopeeItem extends BlockItem {
	public WhoopeeItem(WhoopeeBlock block, Properties properties) {
		super(block, properties);
	}

	@Override
	@Nullable
	public EquipmentSlot getEquipmentSlot(ItemStack stack) {
		return EquipmentSlot.HEAD;
	}

	@Override
	public int getUseDuration(ItemStack stack, LivingEntity entity) {
		return super.getUseDuration(stack, entity);
	}

	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		if (player != null && !player.isCrouching()) {
			player.startUsingItem(hand);
			FartUtil.playFart(level, player, false);
			player.getCooldowns().addCooldown(itemstack, 70);
			player.awardStat(Stats.ITEM_USED.get(this));
			return InteractionResult.CONSUME;
		} else {
			return InteractionResult.FAIL;
		}
	}

	@Override
	public ItemUseAnimation getUseAnimation(ItemStack stack) {
		return ItemUseAnimation.TOOT_HORN;
	}

	@Override
	protected boolean canPlace(BlockPlaceContext context, BlockState state) {
		Player player = context.getPlayer();
		boolean flag = player == null || player.isCrouching();
		return flag && super.canPlace(context, state);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
		super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
		tooltipAdder.accept(Component.translatable("whoopee.cushion_tooltip").withStyle(ChatFormatting.GRAY));
	}
}
