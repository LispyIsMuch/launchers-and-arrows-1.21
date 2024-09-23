package net.stln.launchersandarrows.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.stln.launchersandarrows.item.bow.ModfiableBowItem;

public abstract class ModifierItem extends Item {
    public ModifierItem(Settings settings) {
        super(settings);
    }

    protected Class<?> targetItemClass;

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack mainhandStack = user.getMainHandStack();
        ItemStack offhandStack = user.getOffHandStack();
        if (hand == Hand.MAIN_HAND && getCorrectTarget(offhandStack.getItem()) != null && user.isSneaking()) {
            ModfiableBowItem bow = (ModfiableBowItem)offhandStack.getItem();
            for (int i = 0; i < bow.getSlotsize(); i++) {
                if (bow.getModifier(i, offhandStack) == null) {
                    bow.setModifier(i, offhandStack, mainhandStack.copy());
                    mainhandStack.setCount(mainhandStack.getCount() - 1);

                    float h = 1.0F / (user.getRandom().nextFloat() * 0.5F + 1.8F) + 0.53F;
                    user.getWorld().playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_DISPENSER_FAIL, user.getSoundCategory(), 1.0F, h);
                    user.getWorld().playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_IRON_DOOR_OPEN, user.getSoundCategory(), 1.0F, h);
                    user.getWorld().playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, user.getSoundCategory(), 1.0F, h);
                    return TypedActionResult.consume(user.getMainHandStack());
                }
            }
        }
        return super.use(world, user, hand);
    }

    protected abstract Item getCorrectTarget(Item item);
}
