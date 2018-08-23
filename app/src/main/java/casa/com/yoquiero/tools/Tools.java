package casa.com.yoquiero.tools;

import android.content.Context;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import casa.com.yoquiero.constants.Constants;

public class Tools {

    /**
     * Method for playing an animation given an ID
     * @param view
     * @param id
     */
    public static void playAnimation(View view, int id, Context context)
    {
        ((ImageView)view).setImageDrawable(context.getDrawable(id));
        Drawable drawable = ((ImageView)view).getDrawable();
        if(drawable instanceof AnimatedVectorDrawableCompat)
        {
            AnimatedVectorDrawableCompat avd = (AnimatedVectorDrawableCompat) drawable;
            avd.start();
        }
        else if(drawable instanceof AnimatedVectorDrawable)
        {
            AnimatedVectorDrawable avd = (AnimatedVectorDrawable) drawable;
            avd.start();
        }
    }

    /**
     * Method for playing fade in animation
     * @param view
     * @param context
     */
    public static void fadeInAnimation(View view, Context context)
    {
        Animation fadeIn = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        fadeIn.setDuration(Constants.FADE_IN_DURATION);
        view.startAnimation(fadeIn);
        view.setVisibility(View.VISIBLE);
    }
    /**
     * Method for playing fade out animation
     * @param view
     * @param context
     */
    public static void fadeOutAnimation(View view, Context context)
    {
        Animation fadeOut = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
        fadeOut.setDuration(Constants.FADE_OUT_DURATION);
        view.startAnimation(fadeOut);
        view.setVisibility(View.INVISIBLE);
    }


}
