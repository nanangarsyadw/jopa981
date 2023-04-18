package com.zftlive.android.sample.animation.viewpager.transfor;

import android.view.View;

import com.nineoldandroids.view.ViewHelper;

public class ScaleInOutTransformer extends ABaseTransformer {

	@Override
	protected void onTransform(View view, float position) {
		ViewHelper.setPivotX(view,position < 0 ? 0 : view.getWidth());
		ViewHelper.setPivotY(view,view.getHeight() / 2f);
		float scale = position < 0 ? 1f + position : 1f - position;
		ViewHelper.setScaleX(view,scale);
		ViewHelper.setScaleY(view,scale);
	}

}
