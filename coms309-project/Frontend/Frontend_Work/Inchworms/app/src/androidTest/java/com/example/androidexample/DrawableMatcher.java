package com.example.androidexample;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;
import androidx.test.espresso.matcher.BoundedMatcher;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.bumptech.glide.load.resource.gif.GifDrawable;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/*
 * Got this class from a link sent from TA Muzakker
 * Link: https://stackoverflow.com/questions/33763425/using-espresso-to-test-drawable-changes
 */
public class DrawableMatcher extends TypeSafeMatcher<View> {

    private final int expectedId;
    String resourceName;

    public DrawableMatcher(@DrawableRes int expectedId) {
        super(View.class);
        this.expectedId = expectedId;
    }

    @Override
    protected boolean matchesSafely(View target) {
        if (!(target instanceof ImageView)){
            return false;
        }

        ImageView imageView = (ImageView) target;
        if (expectedId < 0){
            return imageView.getDrawable() == null;
        }
        
        Resources resources = target.getContext().getResources();
        Drawable expectedDrawable = resources.getDrawable(expectedId, target.getContext().getTheme());
        resourceName = resources.getResourceEntryName(expectedId);

        if (expectedDrawable == null) {
            return false;
        }

        Drawable actualDrawable = imageView.getDrawable();

        // Handle BitmapDrawable comparison
        if (expectedDrawable instanceof BitmapDrawable && actualDrawable instanceof BitmapDrawable) {
            Bitmap expectedBitmap = ((BitmapDrawable) expectedDrawable).getBitmap();
        Bitmap actualBitmap = ((BitmapDrawable) actualDrawable).getBitmap();
        return expectedBitmap.sameAs(actualBitmap);
        }

        // Handle GifDrawable comparison
        if (expectedDrawable instanceof GifDrawable && actualDrawable instanceof GifDrawable) {
            return expectedDrawable.getConstantState().equals(actualDrawable.getConstantState());
        }

        return false; // Drawable types do not match
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("with drawable from resource id: ");
        description.appendValue(expectedId);
        if (resourceName != null) {
            description.appendText(" [");
            description.appendText(resourceName);
            description.appendText("]");
        }
    }
}
