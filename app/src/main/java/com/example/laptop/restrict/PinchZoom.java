package com.example.laptop.restrict;

import android.app.Activity;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.GestureDetectorCompat;
import android.text.DynamicLayout;
import android.text.Editable;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class PinchZoom extends Activity {

    DrawingViewg dv ;
    private Paint mPaint,paintForCircle;
    Bitmap bitmap,mutableBit,helpBitmap;



    public static int degree = 0;
    public static String newcolor="#ffffffff";
    public static int color,controlValue = 0;
    public static float pencilWidth,newPencilWidht,textHeightCOntrolValue=0f;


    private ArrayList<Path> paths = new ArrayList<Path>(); //NEW
    private ArrayList<Path> undonePaths = new ArrayList<Path>();
    private Map<Path, Integer> colorsMap = new HashMap<Path, Integer>();
    private Map<Path, Float> sizesMap = new HashMap<>();

    Matrix transform = new Matrix();
    private LayoutInflater mInflater=null;

    private Animation animation,animation2;

    private boolean isZoom = false;
    private boolean isCircle = false;
    private boolean isDraw = true;
    private boolean isText = false;


    private int imageWidth;
    private int imageHeight;

    int checkSeekBar = 0;

    EditText textInput;
    TextView textOutput;
    final Context context=this;

    private String url;

    RelativeLayout myRelativeLayout;
    ImageButton btn90Minus,btn90Plus,
            imgbtnSave,imgbtnUndo,
            imgbtnResize,imgbtnText,imgBtnCircle,imgBtnRectangle,imgBtnColor,imgBtnZoom;
    private float widthDSP,heightDSP;
    DisplayMetrics metrics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Reset();

        if (getIntent().hasExtra("bitmapImage"))
        {
            if(getIntent().getStringExtra("bitmapImage")!=null)
            {
                String fileName = getIntent().getStringExtra("bitmapImage");
                url = getIntent().getStringExtra("url");
                File f  = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Download/slika1.jpg");

////////////////////////////////////////FileProvider if SDK is 24 or higher
                //check permission alowance in the future

                if(f.exists())
                {
                    try {

                        helpBitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
                        bitmap = helpBitmap.copy(Bitmap.Config.ARGB_8888, true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }






            }

            else {
                Toast.makeText(getApplicationContext(),"Error,no picture!",Toast.LENGTH_SHORT).show();
            }
        }




        dv = new DrawingViewg(this);
        setContentView(dv);
        mPaint = new Paint(Paint.FILTER_BITMAP_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(10);



        mInflater = LayoutInflater.from(this);
        final View overView = mInflater.inflate(R.layout.draw_pinch_zoom,null);
        this.addContentView(overView,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


        Init();

        DisplayMetrics();

    }
    public void DisplayMetrics()
    {
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        //float aspectRadio = (float)bitmap.getHeight()/(float)bitmap.getWidth();
        // imageWidth = metrics.widthPixels;
        //  imageHeight = Math.round(imageWidth*aspectRadio);

        heightDSP = Math.round(metrics.heightPixels);
        widthDSP = metrics.widthPixels;
    }
    private  void Reset()
    {
        degree=0;
        color=0;
        newcolor="#ffffffff";
        pencilWidth=0;

        controlValue=0;
    }

    private void Init() {

        myRelativeLayout = (RelativeLayout) findViewById(R.id.myrelative);
        animation = AnimationUtils.loadAnimation(this,R.anim.slide_from_down_to_up);
        animation2 = AnimationUtils.loadAnimation(this,R.anim.slide_from_right);

        imgbtnSave = (ImageButton) findViewById(R.id.imgbtnSave);
        imgbtnSave.startAnimation(animation2);
        imgbtnUndo = (ImageButton) findViewById(R.id.imgbtnUndo);
        imgbtnUndo.startAnimation(animation2);
        // btn90Minus = (ImageButton) findViewById(R.id.btn90minus);
        //btn90Plus = (ImageButton) findViewById(R.id.btn90plus);
        imgBtnColor = (ImageButton) findViewById(R.id.imgbtnChangeColor);
        imgBtnColor.startAnimation(animation);
        imgbtnResize = (ImageButton) findViewById(R.id.imgbtnResize);
        imgbtnResize.startAnimation(animation);
        imgbtnText = (ImageButton) findViewById(R.id.imgbtnText);
        imgbtnText.startAnimation(animation);
        imgBtnZoom = (ImageButton) findViewById(R.id.btnZoom);
        imgBtnCircle = (ImageButton)findViewById(R.id.imagecircle);
        imgBtnRectangle= (ImageButton)findViewById(R.id.imagerectangle);

        textInput = (EditText) findViewById(R.id.txtInput);
        textInput.setBackgroundColor(Color.GRAY);
        textInput.setAlpha(0.3f);
        textOutput = (TextView) findViewById(R.id.txtOutput);
        textOutput.setTextSize(50);

        imgBtnRectangle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        imgBtnCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dv.CreateCircles();
                isCircle=true;
                isZoom=false;
                isDraw=false;
                isText = false;
                //   Toast.makeText(context, "CircleOn", Toast.LENGTH_SHORT).show();
            }
        });
        imgBtnZoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dv.mScaleFactor = 1.0f;
                isZoom = true;
                isCircle=false;
                isDraw=false;
                isText=false;
            }
        });

        imgbtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgbtnSave.setEnabled(false);

                View content = dv;
                int width =content.getWidth();
                int heigt = content.getHeight();

                //*  content.setDrawingCacheEnabled(true);
                content.buildDrawingCache(true);
                content.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

                Bitmap bitty ;


                // String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());

                String path = url+"/"+timeStamp+"(copy).jpg";
                File file = new File(path);
                FileOutputStream ostream;




                try {



                    // bitty =Bitmap.createScaledBitmap(content.getDrawingCache(true),width,heigt,false);
                    bitty = getResizedBitmap(width,heigt);
                    // content.setDrawingCacheEnabled(false);
                    file.createNewFile();
                    ostream = new FileOutputStream(file);
                    bitty.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                    bitty.recycle();

                    ostream.flush();
                    ostream.close();
                    Toast.makeText(getApplicationContext(), "image saved", Toast.LENGTH_LONG).show();



                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                }

                finally {
                    //  CreateNameOfThePicture(DrawingView.this,path);
                }
            }
        });
        imgbtnUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dv.Undo();
            }
        });
        imgbtnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isZoom=false;
                isDraw=false;
                isCircle=false;
                isText=true;
                textInput.setVisibility(View.VISIBLE);
                controlValue=1;
                InputMethodManager imm = (InputMethodManager)context.getSystemService(Service.INPUT_METHOD_SERVICE);

                imm.showSoftInput(textInput,InputMethodManager.SHOW_IMPLICIT);

                dv.CreateWord();
            }
        });
        textInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                textOutput.setText(textInput.getText().toString());
                dv.invalidate();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        textInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            ResetKeyboard();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }

        });

        imgbtnResize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textInput.isShown())
                {
                    ResetKeyboard();
                }
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.customdialogsize);
                dialog.setTitle("Choose Size");

                final SeekBar progressBar = (SeekBar)dialog.findViewById(R.id.seekBar);
                final TextView size = (TextView)dialog.findViewById(R.id.txFontSize);


                progressBar.setMax(10);

                progressBar.setProgress(checkSeekBar);
                size.setText(String.valueOf(checkSeekBar));

                progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                        try {

                            size.setText(String.valueOf(progress));
                            checkSeekBar = progress;
                            newPencilWidht=progress+5f;
                            dv.invalidate();
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });



        imgBtnColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isZoom=false;
                isCircle=false;
                isDraw=true;
                isText=false;
                if(textInput.isShown())
                {
                    ResetKeyboard();
                }

                ColorPickerDialogBuilder.with(context).
                        setTitle("Choose Color").initialColor(Color.GRAY)
                        .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                        .density(12).setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int i) {

                    }
                }).setPositiveButton("OK", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, Integer[] integers) {

                        try {

                            newcolor ="#"+Integer.toHexString(i);
                            textInput.setTextColor(Color.parseColor("#"+Integer.toHexString(i)));
                            dv.mPaintText.setColor(Color.parseColor("#"+Integer.toHexString(i)));
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }


                        dv.invalidate();
                        dialogInterface.dismiss();
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                }).build().show();
            }

        });
            /*    btn90Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                degree=degree+90;
                dv.invalidate();
            }
        });
        btn90Minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                degree=degree-90;
                dv.invalidate();
            }
        });*/


    }
    private void ResetKeyboard()
    {
        controlValue=0;
        textInput.setVisibility(View.INVISIBLE);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(textInput.getWindowToken(), 0);
        //  btnText.setBackgroundColor(Color.GRAY);
        //  btnText.setClickable(true);

    }


    /* public void CreateNameOfThePicture(final Context context, final String path) {

         final SQLiteDB sqLiteDB = new SQLiteDB(context);
         AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
         alertDialog.setTitle("Name");
         alertDialog.setMessage("Enter picture name");

         final EditText input = new EditText(context);
         LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                 LinearLayout.LayoutParams.MATCH_PARENT,
                 LinearLayout.LayoutParams.MATCH_PARENT);
         input.setLayoutParams(lp);
         alertDialog.setView(input);

         alertDialog.setPositiveButton("OK",
                 new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) {
                         if (input.getText().length() == 0) {
                             finish();

                         } else {
                             sqLiteDB.InsertPicture(path, input.getText().toString());
                             finish();
                         }

                     }
                 });

         alertDialog.show();


     }*/
    public class DrawingViewg extends View {


        private Canvas mCanvas;
        private Path mPath;
        private TextPaint mPaintText;


        Context context;

        private Paint circlePaint;
        private Path circlePath;

        static final int NONE = 0;
        static final int PAN = 1;
        static final int ZOOM = 2;
        int mode = NONE;
        private float startXWord=0;
        private float startYWord=0;
        private float translateX=0;
        private float translateY=0;
        private float prevTranslateX=0;
        private float prevTranslateY=0;


        private ScaleGestureDetector mScaleDetector;
        private GestureDetectorCompat mGestureDetector;
        private LongPressGesture longPressGesture;
        private float mScaleFactor = 1f;
        private float mScaleCircleFactor=1f;
        private float mScaleTextFactor = 1f;


        private Rect clipBounds;
        private  Path transformedPath;

        private float mX, mY;
        private static final float TOUCH_TOLERANCE = 4;




        ArrayList<Circle> mCircles;
        ArrayList<Word> mWords;
        ArrayList<Circle> undonemCircles;
        private float startX=0,startY=0;

        public DrawingViewg(Context c) {
            super(c);
            context = c;
            mPath = new Path();

            mPaintText = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            mPaintText.setStyle(Paint.Style.FILL_AND_STROKE);
            mPaintText.setColor(Color.RED);
            mPaintText.setTextSize(50);
            mPaintText.setSubpixelText(true);

            mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());

            longPressGesture = new LongPressGesture();
            mGestureDetector = new GestureDetectorCompat(context,longPressGesture);

            paths.add(mPath);
            //mBitmapPaint = new Paint(Paint.DITHER_FLAG);
            circlePaint = new Paint();
            circlePath = new Path();
            circlePaint.setAntiAlias(true);
            circlePaint.setColor(Color.BLACK);
            circlePaint.setStyle(Paint.Style.STROKE);
            circlePaint.setStrokeJoin(Paint.Join.MITER);
            circlePaint.setStrokeWidth(4f);

            paintForCircle = new Paint();
            paintForCircle.setColor(Color.RED);
            paintForCircle.setAntiAlias(true);
            paintForCircle.setStyle(Paint.Style.STROKE);
            paintForCircle.setStrokeWidth(6);

            mCircles = new ArrayList<Circle>();
            undonemCircles = new ArrayList<Circle>();

            mWords = new ArrayList<Word>();


        }

        public void CreateCircles()
        {

            mCircles.add(new Circle(getWidth()/2, getHeight()/2, 30, 1f));
        }
        public void CreateWord()
        {
            mWords.add(new Word(getWidth()/2,getHeight()/2,textInput.getText().toString(),1f));
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

            int width = MeasureSpec.getSize(widthMeasureSpec);
            int heigth = MeasureSpec.getSize(heightMeasureSpec);
            //   int scaledWidth =  Math.round(imageWidth*mScaleFactor);
            //   int scaledHeight = Math.round(imageHeight*mScaleFactor);

            //   setMeasuredDimension(Math.min(width,scaledWidth),
            //          Math.min(heigth,scaledHeight));

        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);


            bitmap = BitmapResize(bitmap, w, h);
            // bitmap = Bitmap.createScaledBitmap(bitmap, w, h,false);
            mutableBit = bitmap.copy(Bitmap.Config.ARGB_8888, true);

            mCanvas = new Canvas(bitmap);


        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.save();
            canvas.drawBitmap(mutableBit, transform, mPaint);
            canvas.concat(transform);

            int wi = mCanvas.getWidth();
            int he = mCanvas.getHeight();


            if(isDraw) {

                drawPathsOnCanvas(canvas);
                drawCirclesOnCanvas(canvas);
                drawWordsOnCanvas(canvas);
            }

            if (newPencilWidht == 5) {
                mPaint.setStrokeWidth(5);
                canvas.drawPath(mPath, mPaint);
            } else if (newPencilWidht == 10) {
                mPaint.setStrokeWidth(10);
                canvas.drawPath(mPath, mPaint);
            } else if (newPencilWidht == 15) {
                mPaint.setStrokeWidth(15);
                canvas.drawPath(mPath, mPaint);
            }

            if(isText) {
                drawPathsOnCanvas(canvas);
                drawCirclesOnCanvas(canvas);

                    canvas.save();
                    drawWordsOnCanvas(canvas);
                    canvas.restore();
                /*    Rect rect = new Rect();
                    String stringToDraw = textInput.getText().toString();

                    DynamicLayout sl = new DynamicLayout(stringToDraw, mPaintText, canvas.getWidth(), Layout.Alignment.ALIGN_NORMAL, 1, 1, false);
                    canvas.save();

                    float textHeight = getTextHeight(stringToDraw, mPaintText);
                    int numberOfTextLines = sl.getLineCount();

                    float textYCoordinate = mCanvas.getHeight() -
                            ((numberOfTextLines * textHeight) / 2);

                    float textXCoordinate = rect.exactCenterX();

                    canvas.translate(textXCoordinate, textHeightCOntrolValue);
                    sl.draw(canvas);
                    canvas.restore()*/;

               /* mPaintText.getTextBounds(stringToDraw, 0, stringToDraw.length(), bounds);

                int y1 = mutableBit.getHeight() - 100;

                canvas.drawText(stringToDraw, 0,y1, mPaintText);*/

            }
            if(isCircle)
            {
                canvas.save();
                drawPathsOnCanvas(canvas);
                drawCirclesOnCanvas(canvas);
                drawWordsOnCanvas(canvas);
                canvas.restore();
              //  invalidate();
            }
            if(isZoom)
            {

                canvas.save();
                canvas.scale(mScaleFactor, mScaleFactor);
                //    canvas.scale(this.mScaleFactor, this.mScaleFactor,mScaleDetector.getFocusX(),mScaleDetector.getFocusY());


                if((translateX*-1)<0)
                {
                    translateX=0;

                }
                else if ((translateX *-1) > wi*mScaleFactor - getWidth())
                {
                    translateX = (wi * mScaleFactor - getWidth())* -1;

                }
                if((translateY*-1)<0)
                {
                    translateY=0;

                }
                else if ((translateY *-1) > he*mScaleFactor - getHeight())
                {
                    translateY = (he  * mScaleFactor - getHeight())* -1;

                }

                canvas.translate(translateX/mScaleFactor, translateY/mScaleFactor);


                canvas.drawBitmap(mutableBit, transform, mPaint);
                drawPathsOnCanvas(canvas);
                drawCirclesOnCanvas(canvas);
                drawWordsOnCanvas(canvas);

                canvas.restore();
            }
            mPaint.setColor(Color.parseColor(newcolor));
            canvas.drawPath(mPath, mPaint);

            canvas.restore();
            invalidate();


            // canvas.drawPath(circlePath, circlePaint);


        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            mScaleDetector.onTouchEvent(event);
            mGestureDetector.onTouchEvent(event);

            float x = event.getX();
            float y = event.getY();




            if (isDraw)
            {
                handleDraw(event,x,y);
            }
            else if (isCircle)
            {
                Circle cr = findCircleClosestToTouchEvent(x, y);
                // float dist =euclidDist(cr.getCurrentX(),cr.getCurrentY(),x,y);
                handleTouchedCircle(event, cr);

            }
            else if(isText)
            {
                Word w = findWordClosestToTouchEvent(x,y);
                handleText(event,w);
            }
            else if (isZoom)
            {
                handleZoom(event);
                invalidate();
            }
            return true;
        }


    /*    private void dragText(float xMovement, float yMovement) {
            for (Word word : words) {
                word.setX(word.getX() + xMovement);
                word.setY(word.getY() + yMovement);
                invalidate();
            }
        }*/

        private void handleZoom(MotionEvent event) {
            final float me_x = event.getX();
            final float me_y = event.getY();
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    mode = PAN;
                    startX = event.getX()-prevTranslateX;
                    startY= event.getY()-prevTranslateY;
                    break;

                case MotionEvent.ACTION_UP:
                    mode=NONE;
                    prevTranslateX = translateX;
                    prevTranslateY=translateY;
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    mode=ZOOM;
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (mode == PAN) {

                        translateX=event.getX()-startX;
                        translateY=event.getY()-startY;
                    } else if (mode == ZOOM) {

                    }
                    mScaleDetector.onTouchEvent(event);
                    if((mode==PAN && mScaleFactor!=1f) || mode==ZOOM)
                    {
                        invalidate();
                    }
                    break;
            }
        }
        private void handleText(MotionEvent event,Word w)
        {
            final float me_x = event.getX();
            final float me_y = event.getY();

            switch (event.getAction() & MotionEvent.ACTION_MASK) {

                case MotionEvent.ACTION_DOWN:
                    mScaleTextFactor = w.getmScaleFactorWordCurrent();
                    w.setActionDownX(w.getCurrentX());
                    w.setActionDownY(w.getCurrentY());
                    w.setActionMoveOffsetX(me_x);
                    w.setActionMoveOffsetY(me_y);
                    invalidate();
               /*     startXWord = (int) event.getX();
                    startYWord = (int) event.getY();*/
                    break;
                case MotionEvent.ACTION_POINTER_UP:

                    w.setmScaleFactorWordCurrent(mScaleTextFactor);

                    break;
                case MotionEvent.ACTION_MOVE:
                    w.setCurrentX(w.getActionDownX() + me_x - w.getActionMoveOffsetX());
                    w.setCurrentY(w.getActionDownY() + me_y - w.getActionMoveOffsetY());
                    break;
                case MotionEvent.ACTION_UP:

                    break;
            }

        }
        ///////////Circle
        private void handleTouchedCircle(MotionEvent event,Circle c) {
            final float me_x = event.getX();
            final float me_y = event.getY();

            switch (event.getAction() & MotionEvent.ACTION_MASK)
            {
                case MotionEvent.ACTION_DOWN:
                   undonemCircles.clear();
                    mScaleCircleFactor=c.getmScaleFactorCurrent();
                    c.setActionDownX(c.getCurrentX());
                    c.setActionDownY(c.getCurrentY());
                    c.setActionMoveOffsetX(me_x);
                    c.setActionMoveOffsetY(me_y);
                    invalidate();
                    break;

                case  MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_POINTER_UP:
                    c.setCurrentX(c.getActionDownX() + me_x - c.getActionMoveOffsetX());
                    c.setCurrentY(c.getActionDownY() + me_y - c.getActionMoveOffsetY());
                    c.setmScaleFactorCurrent(mScaleCircleFactor);

                   // invalidate();
                    break;

                case MotionEvent.ACTION_UP:

                    break;
               /* case MotionEvent.ACTION_CANCEL:
                    c.restoreStartPosition();
                    break;*/
            }


        }
        private Word findWordClosestToTouchEvent(float x,float y)
        {
            Word c = mWords.get(0);
            float dist = euclidDist(c.getCurrentX(), c.getCurrentY(), x, y);
            float tempdist = 0;
            for (Word cr : mWords) {
                tempdist = euclidDist(cr.getCurrentX(), cr.getCurrentY(), x, y);
                if (tempdist < dist) {
                    c = cr;
                    dist = tempdist;
                }
            }
            return c;
        }
        private Circle findCircleClosestToTouchEvent(float x, float y) {
            Circle c = mCircles.get(0);
            float dist = euclidDist(c.getCurrentX(), c.getCurrentY(), x, y);
            float tempdist = 0;
            for (Circle cr : mCircles) {
                tempdist = euclidDist(cr.getCurrentX(), cr.getCurrentY(), x, y);
                if (tempdist < dist) {
                    c = cr;
                    dist = tempdist;
                }
            }
            return c;
        }
        private  float euclidDist(float x1, float y1, float x2, float y2) {
            return (float) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1)
                    * (y2 - y1));
        }
        private void drawCirclesOnCanvas(Canvas canvas ) {

            if (mCircles != null) {


                for (Circle c : mCircles) {
                    canvas.drawCircle(c.getCurrentX(), c.getCurrentY(), c.getRadius() * c.getmScaleFactorCurrent(),
                            paintForCircle);
                }

            }
        }
        private void drawWordsOnCanvas(Canvas canvas)
        {
            if(mWords!=null)
            {
                for ( Word w : mWords)
                {
                    //rotation and arraylist for every text separately
                   mPaintText.setTextSize(mScaleTextFactor*20);
                    canvas.drawText(textInput.getText().toString(),w.getCurrentX(),w.getCurrentY(),mPaintText);

                }
            }
        }
        /////////

        //////////Path
        private void drawPathsOnCanvas(Canvas canvas) {
            for (Path p : paths) {

                if (colorsMap.isEmpty()) {
                    mPaint.setStrokeWidth(mPaint.getStrokeWidth());
                    mPaint.setColor(mPaint.getColor());
                    canvas.drawPath(p, mPaint);
                } else {
                    mPaint.setStrokeWidth(sizesMap.get(p));
                    mPaint.setColor(colorsMap.get(p));
                    canvas.drawPath(p, mPaint);
                }
            }


            canvas.drawPath(mPath, mPaint);
        }
        private void handleDraw(MotionEvent event,float x,float y) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touch_start(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    touch_move(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    touch_up();
                    invalidate();
                    break;
            }
        }
////////////

        /////////////MotionEvents
        private void touch_start(float x, float y) {

            if (controlValue != 0) {
                textHeightCOntrolValue = y;
            }
            undonePaths.clear();

            mPath.reset();

            mPath.moveTo(x, y);
            mX = x;
            mY = y;

        }
        private void touch_move(float x, float y) {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
                mX = x;
                mY = y;

                circlePath.reset();
                circlePath.addCircle(mX, mY, 30, Path.Direction.CW);
            }
        }
        private void touch_up() {


            mPath.lineTo(mX, mY);

            circlePath.reset();

            color = mPaint.getColor();
            pencilWidth = mPaint.getStrokeWidth();

            mCanvas.drawPath(mPath, mPaint);


            paths.add(mPath);

            colorsMap.put(mPath, color);
            sizesMap.put(mPath, pencilWidth);

            mPath = new Path();

        }
        ///////////////
        public void Undo() {

            if(isDraw)
            {
            if (paths.size() > 0) {
                undonePaths.add(paths.remove(paths.size() - 1));
                //  mCanvas.drawPath(mPath,  mPaint);
                invalidate();
            } else {
                return;
            }
            }
            else if(isCircle)
            {
                if(mCircles.size()>0)
                {
                    undonemCircles.add(mCircles.remove(mCircles.size()-1));
                    invalidate();
                }
                else{
                    return;
                }
            }

                //put boolean to switch between shapes and draw



        }



        private float getTextHeight(String text, Paint paint) {

            Rect rect = new Rect();
            paint.getTextBounds(text, 0, text.length(), rect);
            return rect.height();
        }

    /*    public void RotationAngle(Canvas canvas) {

            int centreX = canvas.getWidth() / 2;
            int centreY = canvas.getHeight() / 2;
            transform.setRotate(90, centreX, centreY);
            canvas.drawBitmap(mutableBit, transform, mPaint);

        }*/



        private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {


            @Override
            public boolean onScale(ScaleGestureDetector detector) {

                if(isZoom)
                {
                    mScaleFactor *= detector.getScaleFactor();
                    mScaleFactor = Math.max(1.0f, Math.min(mScaleFactor, 20.0f));

                }
                else if(isText)
                {
                    mScaleTextFactor *=detector.getScaleFactor();
                    mScaleTextFactor = Math.max(1.0f,Math.min(mScaleTextFactor,5.0f));
                }
                else
                {
                    mScaleCircleFactor *= detector.getScaleFactor();
                    mScaleCircleFactor = Math.max(1.0f, Math.min(mScaleCircleFactor, 5.0f));


                }

                // invalidate();
                return true;
            }


        }
        private class LongPressGesture extends GestureDetector.SimpleOnGestureListener
        {

            @Override
            public boolean onDoubleTap(MotionEvent e) {

                float x = e.getX();
                float y = e.getY();

                mScaleFactor=1;



                return true;

            }

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }
        }
    }
    ////////////Bitmap
    public Bitmap BitmapResize(Bitmap bitmap, int newWidth, int newHeight) {
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

        float ratioX = newWidth / (float) bitmap.getWidth();
        float ratioY = newHeight / (float) bitmap.getHeight();
        float middleX = newWidth / 2.0f;
        float middleY = newHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, middleX - bitmap.getWidth() / 2, middleY - bitmap.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaledBitmap;

    }
    public Bitmap getResizedBitmap(int originial_width, int original_height) {
        dv.setDrawingCacheEnabled(true);
        Bitmap bitmap = null;
        if(dv.getDrawingCache()==null)
        {
            bitmap = loadLargeBitmapFromView(dv);
        }
        else
        {
            bitmap = Bitmap.createBitmap(dv.getDrawingCache());
        }

        return  Bitmap.createScaledBitmap(bitmap,originial_width,original_height,false);
    }
    public static Bitmap loadLargeBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas c = new Canvas(b);
        v.layout(0, 0, b.getWidth(), b.getHeight());
        v.draw(c);
        return b;
    }

}
