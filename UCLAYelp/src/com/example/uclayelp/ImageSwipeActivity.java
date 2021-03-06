package com.example.uclayelp;

import java.io.File;
import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class ImageSwipeActivity extends Activity {
	int numImages;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.image_swipe);
        
        setupActionBar();

        Intent j = getIntent();
        numImages = j.getIntExtra("numImages", 0);
        String entree = j.getStringExtra(Constants.ENTREE);
        
        setTitle("Photos of " + entree);
        
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        ImageSwiper adapter = new ImageSwiper();
        viewPager.setAdapter(adapter);

	}
	
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_menus, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	
	}
	
	
	/**
	 * private class for image swiper stuff
	 */
	
	private class ImageSwiper extends PagerAdapter {
		
		@Override
		public int getCount() {
			return numImages;
		}
		
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == ((ImageView) object) ;
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int pos) {
			Context context = ImageSwipeActivity.this;
			ImageView imageView = new ImageView(context);
			int padding = context.getResources().getDimensionPixelSize(R.dimen.padding_medium); // TODO: don't use fixed constant here
			imageView.setPadding(padding, padding, padding, padding);
			imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			
			int filePos = pos+1;
			String fileName = "image" + filePos + ".jpg";
			File file = new File(Environment.getExternalStorageDirectory()+File.separator + fileName);
			Bitmap imageBitmap = EntreeDetailsActivity.decodeSampledBitmapFromFile(file.getAbsolutePath(), 500, 250);
			imageView.setImageBitmap(imageBitmap);
			imageView.setAdjustViewBounds(true);
			
			((ViewPager) container).addView(imageView, 0);
			return imageView;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int pos, Object object) {
			((ViewPager) container).removeView((ImageView) object);
		}
	} // end image swiper class def

}
