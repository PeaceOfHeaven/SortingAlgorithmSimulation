package universe.sortalgorithmssimulation;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.io.IOException;

public class BubbleSortActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{

	OurView sufface;
	LinearLayout surfaceContainer,rootLayout;
	ImageButton buttonPause,buttondefault,buttonIncr,buttonDecr,buttonVolume,buttonLogOut;
	MediaPlayer m;
	
	Button start;
	ViewFlipper vf1,vf2;
	GestureDetectorCompat mDetector;
	Animation slideinleft,slideoutleft,slideinright,slideoutright,rotation,combo,alpha;
    int resourceIdButton = R.drawable.pause;
	
	int n = 6;
	int a[] = new int[]{5,3,7,1,4,8};
	Ball ball[] = new Ball[n];
	int speed = 0;
	int distance = 0;
	long time = 1000;
	float movearround = 5;
	boolean bStopClicked = false;
	boolean finished = false;
	boolean turnon = true;
	float xBall[] = new float[n];
	float yBall[] = new float[n];
	float scale;
	int size;
	Bitmap redBall, blueBall , greenBall , arrowup , arrowdown , pause , play ;
	String[] sArr = new String[7];
    TextView[] tv = new TextView[7];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bubble_sort);

//		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//		getSupportActionBar().setTitle("Bubble Sort");
		
		sufface = new OurView(this);
		
		InitViews();
		InitSBubble();
		InitTxtV();
		
		vf2.setVisibility(View.INVISIBLE);
		
		buttonPause.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(finished)
				{
					reinitBalls();
					sufface.onRestart();
					resourceIdButton = R.drawable.pause;
					buttonPause.setImageResource(R.drawable.pause);
					finished = false;
				}
				else if(resourceIdButton == R.drawable.play) 
				{
					resourceIdButton = R.drawable.pause;
					onResume();
					buttonPause.setImageResource(R.drawable.pause);	                    
	            }
				else if(resourceIdButton == R.drawable.pause)
				{
					resourceIdButton = R.drawable.play;
                	onPause();
                	buttonPause.setImageResource(R.drawable.play);
	            }
				buttonPause.startAnimation(alpha);
			}
		});
		buttondefault.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				movearround = 5;
				speed = 2;
				time = 1000;
			}
		});
		
		start.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				InitBallAttr();
				resourceIdButton = R.drawable.pause;
				buttonPause.setImageResource(R.drawable.pause);
				vf1.setInAnimation(slideinleft);
				vf1.setOutAnimation(slideoutleft);
				vf1.showNext();
				vf2.setVisibility(View.VISIBLE);
			}
		});
		buttonIncr.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub				
				if(speed<distance)
				{
					speed+=3;
					movearround+=5;
					Toast.makeText(getBaseContext(),"Speed up ...",Toast.LENGTH_SHORT).show();
				}
				else Toast.makeText(getBaseContext(),"Max speed : "+distance,Toast.LENGTH_SHORT).show();
				if(time>100)
					time-=150;
				buttonIncr.startAnimation(alpha);
			}
		});
		buttonDecr.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(speed>2)
				{
					speed-=3;
					movearround-=5;
					Toast.makeText(getBaseContext(),"Speed down ...",Toast.LENGTH_SHORT).show();
				}
				else Toast.makeText(getBaseContext(),"Minimum speed : "+ 2,Toast.LENGTH_SHORT).show();
				if(time<1000 )
					time+=100;
				buttonDecr.startAnimation(alpha);
			}
		});
		buttonLogOut.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				buttonLogOut.startAnimation(alpha);
				onStop();
			}
		});
		buttonVolume.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(turnon)
				{
					MusicPlayerService.player.pause();
					buttonVolume.setImageResource(R.drawable.volumeoff);
					m.stop();
					turnon = false;
				}
				else
				{
					MusicPlayerService.player.start();
					buttonVolume.setImageResource(R.drawable.volumeon);
					m.reset();
					m = MediaPlayer.create(getBaseContext(), R.raw.game);
					try {
						m.prepare();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					turnon = true;
				}
			}
		});
		
		vf2.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				mDetector.onTouchEvent(event);
				return true;
			}
		});
	
		surfaceContainer.addView(sufface);
		/*
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}*/
	}

	public void InitViews()
	{
		buttondefault = (ImageButton) findViewById(R.id.btndefault);
		buttonIncr = (ImageButton) findViewById(R.id.btnincrease);
		buttonDecr = (ImageButton) findViewById(R.id.btndecrease);
		buttonLogOut = (ImageButton) findViewById(R.id.exitbtn);
		buttonPause = (ImageButton) findViewById(R.id.btnplay);		
		buttonVolume = (ImageButton) findViewById(R.id.btnvolume);
		start = (Button) findViewById(R.id.buttonStart);
		
		mDetector = new GestureDetectorCompat(this,this);
		
		buttonPause.setMaxHeight(buttonPause.getMaxHeight()-15);
		buttonPause.setMaxWidth(buttonPause.getMaxWidth()-15);
		
		rootLayout = (LinearLayout) findViewById(R.id.container);
		rootLayout.getDividerDrawable().setColorFilter(Color.parseColor("#FF483D8B"),Mode.OVERLAY);
	
		surfaceContainer = (LinearLayout) findViewById(R.id.SuffaceViewLinearLayout);
		vf1 = (ViewFlipper) findViewById(R.id.viewflipper1);
		vf2 = (ViewFlipper) findViewById(R.id.viewflipper2);
		
		combo = AnimationUtils.loadAnimation(getBaseContext(), R.anim.combo);
		slideinleft = AnimationUtils.loadAnimation(getBaseContext(), R.anim.slideinleft);
		slideoutleft = AnimationUtils.loadAnimation(getBaseContext(), R.anim.slideoutleft);
		slideinright = AnimationUtils.loadAnimation(getBaseContext(), R.anim.sildeinright);
		slideoutright = AnimationUtils.loadAnimation(getBaseContext(), R.anim.slideoutright);
		alpha = AnimationUtils.loadAnimation(getBaseContext(), R.anim.alpha);
		rotation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotation);	
		
		m = MediaPlayer.create(getBaseContext(), R.raw.game);
	}
	
	public void InitBallAttr()
	{
		Resources resources = sufface.getContext().getResources();

		int width = resources.getDisplayMetrics().widthPixels/2;
		if(n<=7)
			size = resources.getDisplayMetrics().densityDpi/6+30;
		else size = resources.getDisplayMetrics().densityDpi/9+30;
		scale = resources.getDisplayMetrics().density;
		
		distance = size+size/4+1;
		
		redBall = BitmapFactory.decodeResource(resources,R.drawable.ballcomparing);
		blueBall = BitmapFactory.decodeResource(resources, R.drawable.bluebubble);
		greenBall = BitmapFactory.decodeResource(resources, R.drawable.ballfinished);
		arrowup = BitmapFactory.decodeResource(resources, R.drawable.arrowup3);
		arrowdown = BitmapFactory.decodeResource(resources, R.drawable.arrowdown2);
		
		int xT=0;
		
		for(int i = 0 ; i < ball.length ; i++)
		{
			if(i==0)
				xT+=width-ball.length*(size+size/4)/2+10;
			else
				xT+=distance;
			ball[i] = new Ball(xT,resources.getDisplayMetrics().heightPixels/4-size,a[i],blueBall,scale,size);
			xBall[i] = ball[i].x;
			yBall[i] = ball[i].y;
		}
		
		speed = 2;
		arrowup = Ball.getResizedBitmap(arrowup,size,size);
		arrowdown = Ball.getResizedBitmap(arrowdown,size+10,size+20);
	}
	
	public void reinitBalls()
	{
		for(int i = 0 ; i < ball.length ; i++)
		{
			ball[i] = new Ball(xBall[i],yBall[i],a[i],blueBall,scale,size);
		}
	}

	public void InitSBubble()
	{
		sArr[0] = " BubbleSort()";
		sArr[1] = " {";
		sArr[2] = "     for( int i = 0 ; i < n - 1; i++ )";
		sArr[3] = "        for( int j = n - 1; j > i ; j-- )";
		sArr[4] = "           if( Ball [ j ] < Ball [ j - 1 ] )";
		sArr[5] = "             swap( Ball [ j ] , Ball [ j - 1 ] );";
		sArr[6] = " }";
	}

	public void InitTxtV()
	{
		LinearLayout holdertv = (LinearLayout) findViewById(R.id.holderTV);
		SpannableString ss= null;
		Paint pnt = new Paint();
    	pnt.setAntiAlias(true);
		pnt.setStyle(Style.FILL);
		
	    for(int i=0; i<7; i++)
	    {
	        tv[i] = new TextView(BubbleSortActivity.this);
	        tv[i].setTextSize(20);
	        
	        if(i==0)
	        {
	        	ss = new SpannableString (sArr[i]);
		    	ss.setSpan(new TextPaint(pnt), 0, ss.length(),Spanned.SPAN_INCLUSIVE_INCLUSIVE);
	        	SpannableString voidS = new SpannableString("void");
	        	voidS.setSpan(new TextPaint(pnt), 0, voidS.length(),Spanned.SPAN_INCLUSIVE_INCLUSIVE);
		    	voidS.setSpan(new ForegroundColorSpan(Color.BLUE), 0, voidS.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		    	ss.setSpan(new ForegroundColorSpan(Color.parseColor("#FFB22222")), 0, ss.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		    	
	        	tv[i].setPadding(18, 20, 0, 0);
	        	tv[i].setTypeface(null,Typeface.BOLD);
	        	tv[i].setText(TextUtils.concat(voidS," ",ss));
	        }
	        else 
	        {
	        	pnt.setColor(Color.BLACK);
	        	ss = new SpannableString (sArr[i]);
		    	ss.setSpan(new TextPaint(pnt), 0, ss.length(),Spanned.SPAN_INCLUSIVE_INCLUSIVE);

	        	tv[i].setPadding(15, 5, 0, 0);
	        	tv[i].setText(ss);
	        }
	        tv[i].setLayoutParams(new  ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT ));
	        holdertv.addView(tv[i],i);
	    }
	}
	
	public void setTextView(final int i,final int colorBackground,final int colorForeground,final long t){
	    BubbleSortActivity.this.runOnUiThread(new Runnable() {     
	        public void run() {      
	        	int vitri=0;
	        	SpannableString ss = null;
	        	while(true)
	        	{
	        		if(sArr[i].charAt(vitri)!=' ')
	        			break;
	        		vitri++;
	        	}
	        	
	        	ss = new SpannableString (sArr[i]);
	        	ss.setSpan (new BackgroundColorSpan(colorBackground), vitri, ss.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	        	ss.setSpan(new ForegroundColorSpan(colorForeground), vitri,ss.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	        	tv[i].setBackgroundColor(Color.TRANSPARENT);
	            tv[i].setText(ss);     
	        } 
	     });
	    try {
			Thread.sleep(t);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void finished()
	{
		BubbleSortActivity.this.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				buttonPause.setImageResource(R.drawable.repeat);
			}
		});	
	}
	

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bubble_sort, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public class OurView extends SurfaceView implements Runnable,SurfaceHolder.Callback{

		private SurfaceHolder Holder; 
		private Thread t = null;
		private Values value;
		private Object pauseLock = new Object();
		private boolean isMoving = false;
		
		Paint p = new Paint();
		Paint myP = new Paint();
		int myColorText = 0;

		DisplayMetrics screen = getResources().getDisplayMetrics();
		
		public OurView(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
			getHolder().addCallback(this);
			Holder = getHolder();
			
			p.setColor(Color.BLACK);
			p.setAntiAlias(true);
			p.setTextSize(screen.densityDpi/10);
			p.setStyle(Style.FILL);
			p.setTextAlign(Align.CENTER);
			
			myP.setTextAlign(Align.CENTER);
			myP.setColor(Color.parseColor("#FFFF4500"));
			myP.setAntiAlias(true);
			myP.setTextSize(screen.densityDpi/7);
			myP.setStyle(Style.FILL);
		}
		
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			if(t==null)
			{
				t = new Thread(this);
				t.start();
			}
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			// TODO Auto-generated method stub
	        t = null;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Canvas canvas = null;
			int i = 0 , solansosanh = 0 , solanhoanvi = 0;
			
			
			
			
			myColorText = Color.parseColor("#C4DC143C");
			value = new Values(-1, -1, 0, 0, "");
			value.isRotated = false;
			try{
				for(i=0;i<ball.length-1;i++)
				{
					if(!Holder.getSurface().isValid()){
						continue;
					}

					drawGamePanel(canvas, value);
					
					((BubbleSortActivity) getContext()).setTextView(2,myColorText,Color.BLACK,time);
					((BubbleSortActivity) getContext()).setTextView(2,Color.TRANSPARENT,Color.BLACK,0);
					
					for(int j=ball.length-1;j>i;j--) 
					{
			
						value.setValues(i, j, solansosanh, solanhoanvi,"");
						drawGamePanel(canvas, value);
						
						((BubbleSortActivity) getContext()).setTextView(3,myColorText,Color.BLACK,time);
						((BubbleSortActivity) getContext()).setTextView(3,Color.TRANSPARENT,Color.BLACK,0);
						((BubbleSortActivity) getContext()).setTextView(4,myColorText,Color.BLACK,0);
						
						ball[j].setBallSwap(redBall);	
						ball[j-1].setBallSwap(redBall);
						
						solansosanh++;

						
						m.start();
						value.setValues(i, j, solansosanh, solanhoanvi,"Compare Ball [ "+j+" ] && Ball [ "+(j-1)+" ]");
						drawGamePanel(canvas, value);
						Thread.sleep(time);
						
						((BubbleSortActivity) getContext()).setTextView(4,Color.TRANSPARENT,Color.BLACK,time);
						
						if(ball[j].number2<ball[j-1].number2) 
						{
							((BubbleSortActivity) getContext()).setTextView(5,myColorText,Color.BLACK,0);
							
							isMoving = true;
							value.degreeLeft = 170;
							value.degreeRight = 170;
					
							while(isMoving)
							{
								value.setValues(i, j, solansosanh, solanhoanvi,"Swap Ball [ "+j+" ] && Ball [ "+(j-1)+" ]");
								
								value.isRotated = true;
								value.ballIndexertoRotate = j;
								
								isMoving = changeGamePanelWith(value);
								drawGamePanel(canvas, value);			
							}
							
							value.isRotated = false;
							Ball temp = ball[j];
							ball[j] = new Ball(ball[j-1].x,ball[j-1].y,ball[j-1].number2,blueBall,scale,size);
							ball[j-1] = new Ball(temp.x,temp.y,temp.number2,blueBall,scale,size);
							solanhoanvi++;
						}	
						
						((BubbleSortActivity) getContext()).setTextView(5,Color.TRANSPARENT,Color.BLACK,0);
						ball[j].setBallDefault(blueBall);
						ball[j-1].setBallDefault(blueBall);				
					}
					ball[i].setBallFinished(greenBall);	

				}
			}catch(InterruptedException e)
			{
				e.printStackTrace();
			}
			finally
			{
				((BubbleSortActivity) getContext()).finished();
				ball[ball.length-1].setBallFinished(greenBall);
				value.setValues(-1, -1, solansosanh, solanhoanvi, "Successful");
				drawGamePanel(canvas, value);
				finished = true;
			}
		}


		protected void pauseifBtnStopClicked()
		{
			synchronized (pauseLock) {
			    while (bStopClicked)
			    {
					try {
						pauseLock.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    }
			}
		}
		
		protected void drawGamePanel(Canvas canvas,Values value) 
		{  
			pauseifBtnStopClicked();
			
			canvas = Holder.lockCanvas();	
			//canvas.drawARGB(255, 255, 255, 255);   
			
			canvas.drawColor(Color.parseColor("#FFFFFFFF"));
			drawBall(canvas);
			drawDetails(canvas, value);
			
			Holder.unlockCanvasAndPost(canvas);
		} 
		protected boolean changeGamePanelWith(Values value)
		{
			boolean isNotCompleted = ball[value.j-1].run(ball[value.j],speed);
			return isNotCompleted;
		}

		private void drawDetails(Canvas canvas,Values value)
		{
			drawNumbers(canvas);
			if(value.i != -1 && value.j != -1)
				drawIJ(canvas,value.i,value.j);
			drawSSHV(canvas,value.solansosanh, value.solanhoanvi);
			drawStates(canvas, value.state);
		}
		private void drawBall(Canvas canvas)
		{
			for(int i=0;i<ball.length;i++)
			{
				if(value.isRotated && i==value.ballIndexertoRotate)
				{
					ball[i].drawBall(canvas, true, value.degreeLeft);
					ball[i-1].drawBall(canvas, true, value.degreeRight);
				}
				else ball[i].drawBall(canvas);
			}
			value.degreeLeft-=movearround;
			value.degreeRight+=movearround;
		}

		private void drawNumbers(Canvas canvas)
		{
			for(int i=0;i<ball.length;i++)
			{
				String s = String.valueOf(i);
				canvas.drawText(s,xBall[i]+size/2,yBall[i]+size+size/2, p);
			}
		}
		private void drawSSHV(Canvas canvas,int ss,int hv)
		{
			String sss = "Số lần so sánh : "+ss;
			canvas.drawText(sss,screen.widthPixels/4,yBall[0]+size*4, p);
			String shv = "Số lần hoán vị : "+hv;
			canvas.drawText(shv, ((screen.widthPixels*3)/4),yBall[0]+size*4, p);
		}
		private void drawStates(Canvas c,String str)
		{
			c.drawText(str,screen.widthPixels/2,yBall[0]/2-10,myP);
		}
		private void drawIJ(Canvas canvas,int i,int j)
		{
			arrowup = Ball.drawTextToBitmap(arrowup,scale/10,"i");
			canvas.drawBitmap(arrowup, xBall[i], yBall[i]+size*3/2, null);
			
			arrowdown = Ball.drawTextToBitmap(arrowdown,scale/10,"j");
			canvas.drawBitmap(arrowdown, xBall[j], ball[j].y-size-20, null);
		}
		
		protected void onResume()
		{
			if(t!=null)
			{
				synchronized (pauseLock) {
					bStopClicked = false;
			        pauseLock.notifyAll();
				}
			}
		}
		protected void onPause()
		{
			bStopClicked = true;
		}
		protected void onRestart()
		{
			t = null;
			surfaceCreated(Holder);
		}
	}



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		sufface.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		sufface.onPause();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		finish();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		
		// TODO Auto-generated method stub
		return false;
		
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		float sensitvity = 50;
		
		if((e1.getX() - e2.getX()) > sensitvity){
			  
			  vf2.setInAnimation(getBaseContext(),R.anim.slideinleft);
			  vf2.setOutAnimation(getBaseContext(),R.anim.slideoutleft);
              vf2.showNext();
		}else if((e2.getX() - e1.getX()) > sensitvity){
			  
              vf2.setInAnimation(getBaseContext(),R.anim.sildeinright);
			  vf2.setOutAnimation(getBaseContext(),R.anim.slideoutright);
              vf2.showPrevious();
		}
		return true;
	}
}
