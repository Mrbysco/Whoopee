package com.mrbysco.whoopee.item;

import com.mrbysco.whoopee.block.WhoopeeBlock;
import com.mrbysco.whoopee.util.FartUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
		ItemStack itemstack = player.getItemInHand(usedHand);
		if (player != null && !player.isCrouching()) {
			player.startUsingItem(usedHand);
			FartUtil.playFart(level, player, false);
			player.getCooldowns().addCooldown(this, 70);
			player.awardStat(Stats.ITEM_USED.get(this));
			return InteractionResultHolder.consume(itemstack);
		} else {
			return InteractionResultHolder.fail(itemstack);
		}
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.TOOT_HORN;
	}

	@Override
	protected boolean canPlace(BlockPlaceContext context, BlockState state) {
		Player player = context.getPlayer();
		boolean flag = player == null || player.isCrouching();
		return flag && super.canPlace(context, state);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
		super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
		tooltipComponents.add(Component.translatable("whoopee.cushion_tooltip").withStyle(ChatFormatting.GRAY));
	}
}
