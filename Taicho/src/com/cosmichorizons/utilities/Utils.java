package com.cosmichorizons.utilities;

import java.awt.Color;

/**
 * Class i found online, used to to blend colors for different level units 
 *
 */
public class Utils {
	 /**
	   * Blend two colors.
	   * 
	   * @param color1  First color to blend.
	   * @param color2  Second color to blend.
	   * @param ratio   Blend ratio. 0.5 will give even blend, 1.0 will return
	   *                color1, 0.0 will return color2 and so on.
	   * @return        Blended color.
	   */
	  public static Color blendColor (Color color1, Color color2, double ratio)
	  {
	    float r  = (float) ratio;
	    float ir = (float) 1.0 - r;

	    float rgb1[] = new float[3];
	    float rgb2[] = new float[3];    

	    color1.getColorComponents (rgb1);
	    color2.getColorComponents (rgb2);    

	    Color color = new Color (rgb1[0] * r + rgb2[0] * ir, 
	                             rgb1[1] * r + rgb2[1] * ir, 
	                             rgb1[2] * r + rgb2[2] * ir);
	    
	    return color;
	  }


	  /**
	   * Make a color darker.
	   * 
	   * @param color     Color to make darker.
	   * @param fraction  Darkness fraction.
	   * @return          Darker color.
	   */
	  public static Color darkenColor (Color color, double fraction)
	  {
	    int red   = (int) Math.round (color.getRed()   * (1.0 - fraction));
	    int green = (int) Math.round (color.getGreen() * (1.0 - fraction));
	    int blue  = (int) Math.round (color.getBlue()  * (1.0 - fraction));

	    if (red   < 0) red   = 0; else if (red   > 255) red   = 255;
	    if (green < 0) green = 0; else if (green > 255) green = 255;
	    if (blue  < 0) blue  = 0; else if (blue  > 255) blue  = 255;    

	    int alpha = color.getAlpha();

	    return new Color (red, green, blue, alpha);
	  }

	  

	  /**
	   * Make a color lighter.
	   * 
	   * @param color     Color to make lighter.
	   * @param fraction  Darkness fraction.
	   * @return          Lighter color.
	   */
	  public static Color lightenColor (Color color, double fraction)
	  {
	    int red   = (int) Math.round (color.getRed()   * (1.0 + fraction));
	    int green = (int) Math.round (color.getGreen() * (1.0 + fraction));
	    int blue  = (int) Math.round (color.getBlue()  * (1.0 + fraction));

	    if (red   < 0) red   = 0; else if (red   > 255) red   = 255;
	    if (green < 0) green = 0; else if (green > 255) green = 255;
	    if (blue  < 0) blue  = 0; else if (blue  > 255) blue  = 255;    

	    int alpha = color.getAlpha();

	    return new Color (red, green, blue, alpha);
	  }
}
