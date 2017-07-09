package com.mele.games.animation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Al Mele
 *
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SpriteAnimated {
	public String spriteTag();
	public SpriteFrame[] frames();
	ERenderPass renderPass() default ERenderPass.BOTTOM;
	AnimationMethod animationMethod() default AnimationMethod.CYCLE;
	int transparency() default -65281;
}
