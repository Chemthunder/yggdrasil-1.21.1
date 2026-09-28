package org.chemthunder.yggdrasil.core.item;

import com.nitron.nitrogen.util.interfaces.ColorableItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.chemthunder.yggdrasil.core.cca.entity.SylvaticusComponent;
import org.chemthunder.yggdrasil.core.index.YggParticleTypes;

/**
 * @author Chemthunder
 */
public class SylvaticusItem extends Item implements ColorableItem {
    public SylvaticusItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getItemCooldownManager().isCoolingDown(this)) {
            user.setCurrentHand(hand);
        }
        return super.use(world, user, hand);
    }

    public boolean canDash(PlayerEntity user) {
        return user.isUsingItem() && user.getMainHandStack().isEmpty() && user.getOffHandStack().isOf(this) && SylvaticusComponent.KEY.get(user).getDashTicks() <= 0;
    }

    @Environment(EnvType.CLIENT)
    public void clientOnUse(ItemStack stack, PlayerEntity player) {
        player.swingHand(Hand.MAIN_HAND);
    }

    public void serverOnUse(ItemStack stack, PlayerEntity player) {}

    public void absorbDamage(PlayerEntity player, LivingEntity attacker) {
        World world = player.getWorld();
        Vec3d pPos = player.getPos();

        if (world instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(
                    YggParticleTypes.SYLV_BLOCK,
                    pPos.getX(),
                    pPos.getY() + 0.5F,
                    pPos.getZ(),
                    8,
                    0,
                    0,
                    0,
                    0.3F
            );
        }

        player.getItemCooldownManager().set(this, attacker instanceof PlayerEntity ? 90 : 40);
    }

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity player) {
            SylvaticusComponent component = SylvaticusComponent.KEY.get(player);

            if (component.getDashTicks() > 0 && !player.isUsingItem()) {
                component.setDashTicks(0);
            }
        }
    }

    public BipedEntityModel.ArmPose getArmPose(ItemStack stack, PlayerEntity player) {
        if (player.isUsingItem()) {
            return BipedEntityModel.ArmPose.BLOCK;
        }
        return BipedEntityModel.ArmPose.ITEM;
    }

    public Text getName(ItemStack stack) {
        return super.getName(stack).copy().withColor(0xFF00ffa3);
    }

    public int startColor(ItemStack itemStack) {
        return 0xFF065638;
    }

    public int endColor(ItemStack itemStack) {
        return 0xFF00ffa3;
    }

    public int backgroundColor(ItemStack itemStack) {
        return 0xFF000000;
    }
}
