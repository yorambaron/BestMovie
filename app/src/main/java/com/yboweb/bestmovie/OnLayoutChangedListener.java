/**
 * 
 */
package com.yboweb.bestmovie;

import android.view.View;

/**
 * @author Asaf
 * Call back for layout changes 
 * should be called from:  
 
 	@Override
	protected synchronized void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
    }

  
 */
public interface OnLayoutChangedListener {
	
	/**
	 * Called when a layout size changes
	 * @param view The view that its layout was changed
	 * @param changed true is there was a change
	 * @param left min x coordinate
	 * @param top min y coordinate
	 * @param right max x coordinate
	 * @param bottom max y coordinate
	 */
	void onLayoutChanged(View view, boolean changed, int left, int top, int right, int bottom);
	
}
