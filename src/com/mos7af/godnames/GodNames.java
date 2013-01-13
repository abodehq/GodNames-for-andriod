package com.mos7af.godnames;

import java.util.ArrayList;
import java.util.List;









import android.app.Activity;
import android.content.Intent;

import android.graphics.Typeface;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;

import android.view.View;
import android.view.View.OnClickListener;

import android.webkit.WebView;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.TextView;

public class GodNames extends Activity 
{


	private ImageView leftArrowImageView;

	private ImageView rightArrowImageView;

	private Gallery gallery;

	private int selectedImagePosition = 0;
	private List<Drawable> drawables;
	private TextView txtTitle ;
	private WebView webview;
	private int zoomFactor = -1;
	private GodNamesAdapter galImageAdapter;
	public static String[] GodNames = {"","الله","الرحمن","الرحيم","الرب","الإلـه","الأول","الآخـر","الظـاهر","الباطن","العلي","الأعلى","المتعال","العظيم","الكبير","الحميد","المجيـد","الواحد","الأحـد","الصمـد","الحـيّ","القيوم","بديع السماوات والأرض","نور السماوات والأرض","ذو الجلال والإكرام","مالك الملك","المليك","الملك","القدوس","السلام","المؤمن","المهيمن","العزيز","الجبـار","المتكبر","الخلاق","الخالق","البارئ","المصور","القادر","القدير","المقتدر","القاهر","القهـار","القوي","المتين","الحـق","المبين","السميع","البصـير","العليم","الخبـير","الشهيد","الحسيب","الرقيب","القريب","المجيب","العفـو","الغفـور","الغفـار","الحليم","الرؤوف","التواب","الـبر","الشاكر","الودود","الشكور","اللطيف","المحيـط","الواسـع","الوهاب","الغـني","الكريم","الأكـرم","الرازق","الرزاق","الفتـاح","المقيـت","الهـادي","الحكـم","الحكـيم","الوكيـل","الحفـيظ","الولي","المولى","النصير","الكافي","الشافي","الرفيق","الجمـيل","القـابض","الباسـط","المعطي","المقـدم","المؤخـر","المنان","السـيد","الحييّ","السـتير","الوتـر"};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		webview = (WebView) findViewById(R.id.webkit);
		getDrawablesList();
		setupUI();
		
		ImageButton zoomOut = (ImageButton) findViewById(R.id.ZoomOut); // your image button
		zoomOut.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	        	int c = (int) (100 * webview.getScale());
	        	if(c>75)
	        	{
	        		webview.zoomOut();
	        		zoomFactor = (int) (100 * webview.getScale());
	        	}
	        }
	    });
		ImageButton zoomIn = (ImageButton) findViewById(R.id.ZoomIn); // your image button

	    // click event on your button
		zoomIn.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	        	int c = (int) (100 * webview.getScale());
	        	if(c<185)
	        	{
	        		webview.zoomIn();
	        		zoomFactor = (int) (100 * webview.getScale());
	        				
	        	}
	        }
	    });
		
		ImageButton gList = (ImageButton) findViewById(R.id.gList); // your image button

	    // click event on your button
		gList.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	        	
	        		Intent i = new Intent(getApplicationContext(), ListsActivity.class);
					startActivityForResult(i, 100);			
	        	
	        }
	    });
		
	}
	

	private void setupUI() {

		leftArrowImageView = (ImageView) findViewById(R.id.left_arrow_imageview);
		rightArrowImageView = (ImageView) findViewById(R.id.right_arrow_imageview);
		gallery = (Gallery) findViewById(R.id.gallery);
		
		txtTitle= (TextView)findViewById(R.id.textTitle);
		 String fontPath = "fonts/arabic1.ttf";
	     Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
	     txtTitle.setTypeface(tf);
		leftArrowImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (selectedImagePosition > 0) {
					--selectedImagePosition;

				}

				gallery.setSelection(selectedImagePosition, false);
			}
		});

		rightArrowImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (selectedImagePosition < drawables.size() - 1) {
					++selectedImagePosition;

				}

				gallery.setSelection(selectedImagePosition, false);

			}
		});

		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

				selectedImagePosition = pos;

				if (selectedImagePosition > 0 && selectedImagePosition < drawables.size() - 1) {

					leftArrowImageView.setImageDrawable(getResources().getDrawable(R.drawable.arrow_left_enabled));
					rightArrowImageView.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right_enabled));

				} else if (selectedImagePosition == 0) {

					leftArrowImageView.setImageDrawable(getResources().getDrawable(R.drawable.arrow_left_disabled));

				} else if (selectedImagePosition == drawables.size() - 1) {

					rightArrowImageView.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right_disabled));
				}

				changeBorderForSelectedImage(selectedImagePosition);
				setSelectedImage(selectedImagePosition);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}

		});

		galImageAdapter = new GodNamesAdapter(this, drawables);

		gallery.setAdapter(galImageAdapter);

		if (drawables.size() > 0) {

			gallery.setSelection(selectedImagePosition, false);

		}

		if (drawables.size() == 1) {

			rightArrowImageView.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right_disabled));
		}

	}

	private void changeBorderForSelectedImage(int selectedItemPos) {

		int count = gallery.getChildCount();

		for (int i = 0; i < count; i++) {

			ImageView imageView = (ImageView) gallery.getChildAt(i);
			imageView.setBackgroundDrawable(getResources().getDrawable(R.drawable.image_border));
			imageView.setPadding(3, 3, 3, 3);
		}

		ImageView imageView = (ImageView) gallery.getSelectedView();
		imageView.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_image_border));
		imageView.setPadding(3, 3, 3, 3);
	}

	private void getDrawablesList() {

		drawables = new ArrayList<Drawable>();
		for(int i =1;i<100;i++)
		{
			drawables.add(getResources().getDrawable(getResources().getIdentifier("g" + i,"drawable", getPackageName())));
		}
		
	}
	
	private int selectedIndex = 0;
	private void setSelectedImage(int selectedImagePosition) {

		
		selectedIndex = selectedImagePosition;
		
		txtTitle.setText("تحميل ...");
		
		myHandler.removeCallbacks(mMyRunnable);
		myHandler.postDelayed(mMyRunnable, 1500);
		
	}
	
	 private Handler myHandler = new Handler();
	 private Runnable mMyRunnable = new Runnable()
	 {
	     @Override
	     public void run()
	     {
	 			try {
	 				
	 				webview.loadUrl("file:///android_asset/gd"+selectedIndex+".html");
	 				webview.requestFocus();
	 				if(zoomFactor!=-1)
	 					webview.setInitialScale(zoomFactor);
	 				txtTitle.setText(GodNames[selectedIndex+1]);
	 			} catch(Exception e)
	 			{
	 				
	 			}
	     }
	  };
	  @Override
	    protected void onActivityResult(int requestCode,
	                                     int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	        if(resultCode == 100){
	        	gallery.setSelection(data.getExtras().getInt("index"));
	        }
	 
	    }
}