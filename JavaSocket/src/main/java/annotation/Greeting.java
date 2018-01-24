package annotation;

import java.lang.annotation.Inherited;

/**
 * 
 * @author peida
 *
 */
@Inherited
public @interface Greeting {
	public enum FontColor {
		BULE, RED, GREEN
	};

	String name();

	FontColor fontColor() default FontColor.GREEN;
}
