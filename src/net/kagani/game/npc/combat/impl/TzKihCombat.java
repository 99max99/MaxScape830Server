package net.kagani.game.npc.combat.impl;

import net.kagani.game.Animation;
import net.kagani.game.Entity;
import net.kagani.game.npc.NPC;
import net.kagani.game.npc.combat.CombatScript;
import net.kagani.game.npc.combat.NPCCombatDefinitions;
import net.kagani.game.npc.familiar.Familiar;
import net.kagani.game.player.Player;

public class TzKihCombat extends CombatScript {

	@Override
	public Object[] getKeys() {
		return new Object[] { "Tz-Kih", 7361, 7362 };
	}

	@Override
	public int attack(NPC npc, Entity target) {// yoa
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		int damage = 0;
		if (npc instanceof Familiar) {// TODO get anim and gfx
			Familiar familiar = (Familiar) npc;
			boolean usingSpecial = familiar.hasSpecialOn();
			if (usingSpecial) {
				for (Entity entity : npc.getPossibleTargets()) {
					damage = getMaxHit(npc, 70, NPCCombatDefinitions.MELEE,
							target);
					if (target instanceof Player)
						((Player) target).getPrayer().drainPrayer(damage);
					delayHit(npc, 0, entity, getMeleeHit(npc, damage));
				}
			}
			return npc.getAttackSpeed();
		}
		npc.setNextAnimation(new Animation(defs.getAttackEmote()));
		damage = getMaxHit(npc, NPCCombatDefinitions.MELEE, target);
		if (target instanceof Player)
			((Player) target).getPrayer().drainPrayer(damage + 10);
		delayHit(npc, 0, target, getMeleeHit(npc, damage));
		return npc.getAttackSpeed();
	}
}
