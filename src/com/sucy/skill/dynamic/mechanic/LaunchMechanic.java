/**
 * SkillAPI
 * com.sucy.skill.dynamic.mechanic.LaunchMechanic
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Steven Sucy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software") to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.sucy.skill.dynamic.mechanic;

import com.sucy.skill.dynamic.EffectComponent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.List;

/**
 * Launches the target in a given direction relative to their forward direction
 */
public class LaunchMechanic extends EffectComponent
{
    private Vector up = new Vector(0, 1, 0);

    private static final String FORWARD = "forward";
    private static final String UPWARD  = "upward";
    private static final String RIGHT   = "right";

    /**
     * Executes the component
     *
     * @param caster  caster of the skill
     * @param level   level of the skill
     * @param targets targets to apply to
     *
     * @return true if applied to something, false otherwise
     */
    @Override
    public boolean execute(LivingEntity caster, int level, List<LivingEntity> targets)
    {
        if (targets.size() == 0)
        {
            return false;
        }

        boolean isSelf = targets.size() == 1 && targets.get(0) == caster;
        double forward = attr(caster, FORWARD, level, 0, isSelf);
        double upward = attr(caster, UPWARD, level, 0, isSelf);
        double right = attr(caster, RIGHT, level, 0, isSelf);
        for (LivingEntity target : targets)
        {
            Vector dir = target.getLocation().getDirection().setY(0).normalize();
            Vector nor = dir.clone().crossProduct(up);
            dir.multiply(forward);
            dir.add(nor.multiply(right)).setY(upward);

            target.setVelocity(dir);
        }
        return targets.size() > 0;
    }
}
