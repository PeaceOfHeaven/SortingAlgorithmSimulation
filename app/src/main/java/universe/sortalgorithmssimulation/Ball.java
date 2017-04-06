package universe.sortalgorithmssimulation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;


public class Ball {
	public float x;
	public float y;
	
	public int number2,number1;
	
	Bitmap redBall, blueBall , greenBall;
	
	Paint p = new Paint(Paint.FILTER_BITMAP_FLAG);
	float scale;
	int size;
	
	
	public Ball(float x, float y ,int number,Bitmap blueBallInput,float scale,int size)
	{
		this.x = x;
		this.y = y;
		this.number1 = number;
		this.number2 = number;
		this.size = size;
		this.scale = scale;
		this.blueBall = drawTextToBitmap(blueBallInput,scale,Integer.toString(number1));
		this.blueBall = getResizedBitmap(blueBall,size, size);
		
		p.setFilterBitmap(true);
		p.setAntiAlias(true);
	}
	public void setBallSwap(Bitmap redBallInput)
	{
		redBall=drawTextToBitmap(redBallInput,scale,Integer.toString(number1));
		redBall= getResizedBitmap(redBall,size,size);
		blueBall = Bitmap.createBitmap(redBall);
	}
	public void setBallFinished(Bitmap greenBallInput)
	{
		greenBall=drawTextToBitmap(greenBallInput,scale,Integer.toString(number1));
		greenBall= getResizedBitmap(greenBall,size,size);
		blueBall = Bitmap.createBitmap(greenBall);
	}
	public void setBallDefault(Bitmap blueBallInput)
	{
		blueBall=drawTextToBitmap(blueBallInput,scale,Integer.toString(number1));
		blueBall= getResizedBitmap(blueBall,size,size);
		blueBall = Bitmap.createBitmap(blueBall);
	}
	public void drawBall(Canvas canvas)
	{
		canvas.drawBitmap(blueBall,x,y, p);
	}
	public void drawBall(Canvas canvas,float x,float y)
	{
		canvas.drawBitmap(blueBall,x,y,p);
	}
	public void drawBall(Canvas canvas,boolean isRotation,float degree)
	{
		if(isRotation == true)
		{
			canvas.drawBitmap(blueBall,rotateMe(degree), p);
		}
		else drawBall(canvas);
	}
	public Matrix rotateMe(float degree){
		
	    Matrix mtx=new Matrix();
	    
	    mtx.postRotate(degree,blueBall.getWidth()/2,blueBall.getHeight()/2);
	    //mtx.postRotate(degree);
	    mtx.postTranslate(x, y);  //The coordinates where we want to put our bitmap
	    return mtx;
	}
	public Matrix scaleMe(float degree)
	{
		Matrix mtx=new Matrix();
	    mtx.postScale(1.2f,1.2f);
	    return mtx;
	}
	float inputX=0,inputY=0;
	float thisX=0,thisY=0;
	boolean temp = false;

	public boolean run(Ball bc,int speed)
	{
		if(temp==false)
		{
			inputX=bc.x;
			inputY=bc.y;
			thisX=this.x;
			thisY=this.y;
			temp = true;
		}
		if(bc.y>inputY-size && this.x<inputX)  
		{
			this.y-=speed;
			bc.y-=speed;
			if(bc.y < inputY-size)
			{
				this.y = inputY-size;
				bc.y = inputY-size;
			}
			return true;
		}

		if(this.x<inputX)
		{
			this.x+=speed; 
			bc.x-=speed;
			if(this.x>inputX)
			{
				this.x = inputX;
				bc.x = thisX;
			}
			return true;
		}
		if(this.y < inputY)
		{
			this.y+=speed;
			bc.y+=speed;
			if(this.y > inputY)
			{
				this.y = inputY;
				bc.y = thisY;
			}
			return true;
		}
		return false;
	}
	public boolean moveout(int speed)
	{
		if(temp==false)
		{
			inputY=this.y+size+size/2+size/4;
			temp = true;
		}
		if(this.y<inputY)
		{
			this.y+=speed;
			return true;
		}
		if(this.y>inputY)
			this.y = inputY;
		return false;
	}
	public boolean movedown(int speed,float Y)
	{
		if(this.y<Y)
		{
			this.y+=speed;
			return true;
		}
		if(this.y>Y)
			this.y = Y;
		return false;
	}
	public boolean moveup(int speed,float Y)
	{
		if(this.y>Y)
		{
			this.y-=speed;
			return true;
		}
		if(this.y<Y)
			this.y = Y;
		return false;
	}
	public boolean moverightto(int x,int speed)
	{
		if(this.x<(float)x)
		{
			this.x+=speed;
			if(this.x>x)
				this.x=x;
			return true;
		}
		return false;
	}
	public boolean moveback(float x,float y,int speed)
	{
		if(this.x>x)
		{
			this.x-=speed;
			if(this.x<x)
				this.x=x;
			return true;
		}
		if(this.y>y)
		{
			this.y-=speed;
			if(this.y<y)
				this.y=y;
			return true;
		}
		return false;
	}
	double goc=0;
	boolean flag = true;
	float xball1,yball1;
	double R;
	public boolean mycustomrun(Ball bc)
	{
		if(temp==false)
		{
			
			xball1 = this.x;
			yball1 = this.y;
			R = (bc.x - xball1)/2;
			temp = true;
		}
		if(goc<90 && flag==true)
		{
			goc+=10;
			this.x =(float) (xball1 + R - R*Math.cos(goc));
			this.y =(float) (yball1 - R*Math.sin(goc));
			bc.x =(float) (xball1 + R + R*Math.cos(goc));
			bc.y = (float) (yball1 - R*Math.sin(goc));
			return true;
		}
		flag = false;
		if(goc<=90 && flag==false && goc>=0)
		{
			this.x =(float) (xball1 + R + R*Math.cos(goc));
			this.y =(float) (yball1 - R*Math.sin(goc));
			bc.x = (float) (xball1 + R - R*Math.cos(goc));
			bc.y = (float) (yball1 - R*Math.sin(goc));
			goc-=10;
			return true;
		}
		
		return false;
	}
	public static Bitmap getResizedBitmap(Bitmap image, int bitmapWidth,int bitmapHeight) 
	{
		return Bitmap.createScaledBitmap(image, bitmapWidth, bitmapHeight,true);
	}
	public static Bitmap drawTextToBitmap(Bitmap bitmap,float scale,String gText) {
		 
		  Bitmap.Config bitmapConfig =
		      bitmap.getConfig();
		  // set default bitmap config if none
		  if(bitmapConfig == null) {
		    bitmapConfig = Bitmap.Config.ARGB_8888;
		  }
		  // resource bitmaps are imutable, 
		  // so we need to convert it to mutable one
		  bitmap = bitmap.copy(bitmapConfig, true);
		 
		  Canvas canvas = new Canvas(bitmap);
		  // new antialised Paint
		  Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		  // text color - #3D3D3D
		  paint.setColor(Color.rgb(61, 61, 61));
		  // text size in pixels
		  paint.setTextSize((int) (90 * scale));
		  // text shadow
		  paint.setShadowLayer(1f, 0f, 1f, Color.RED);
		  paint.setAntiAlias(true);
		  // draw text to the Canvas center
		  Rect bounds = new Rect();
		  paint.getTextBounds(gText, 0, gText.length(), bounds);
		  int x = (bitmap.getWidth() - bounds.width())/2;
		  int y = (bitmap.getHeight() + bounds.height())/2;
		 
		  canvas.drawText(gText, x, y, paint);
		 
		  return bitmap;
		}
}
