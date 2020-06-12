package com.rezende.chamados.view.MenuFloat;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;

public class menuFloatV {

    ArrayList<FloatingActionButton> l0 = new ArrayList<FloatingActionButton>();
    FloatingActionButton fi=null;
    public float yI=0;
    float vel=0.1f;
  public ArrayList<FloatingActionButton> all = new ArrayList<FloatingActionButton>();

    Activity a=null;
    public menuFloatV(final Activity a, final boolean nV, int gravity, int dis, int[] ...bts) {
        this.a=a;

        DisplayMetrics metrics = a.getResources().getDisplayMetrics();
        final   int width = metrics.widthPixels;
        final int height = metrics.heightPixels;

        dis=(height*dis)/800;



        for (int bt=0;bt<bts.length;bt++){
            final int fbt=bt;
final ArrayList<FloatingActionButton> fr = new ArrayList<FloatingActionButton>();

            for (int cont = 0; cont < bts[bt].length; cont++) {
                final int fcont = cont;
                final FloatingActionButton fabButton3 = new FloatingActionButton.Builder(a)
                        .withDrawable(a.getResources()
                                .getDrawable(bts[bt][cont]))
                        .withButtonColor(Color.WHITE)
                        .withGravity(gravity)
                        .withMargins(gravity==(Gravity.BOTTOM| Gravity.LEFT)?(bt * dis):5, 0, gravity==(Gravity.BOTTOM| Gravity.RIGHT)?(bt * dis):5, cont==0?8:-100 )
                        .create();

                fabButton3.setAlpha(0.75f);
                fabButton3.idY=cont;
                fabButton3.idBt=bt;


                if (cont != 0) {

                    fabButton3.setAlpha(0.0f);

                    fabButton3.setClickable(false);
                    fr.add(fabButton3);
                    all.add(fabButton3);
                } else {
                    fi = fabButton3;
                    yI = fabButton3.getY();
                    l0.add(fabButton3);

                }




                if (cont == 0) {
                    fabButton3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            click(fbt,fcont);
                            boolean possui=false;
                            for (final FloatingActionButton f : fr){
                                if (f.getAlpha() != 0  && !f.emclick){
                                    possui=true;
                                    break;

                                }

                            }

                              for (final FloatingActionButton f : (possui&&!nV?rr():fr)) {
Log.d("Log "," Log "+f.idY+" "+f.idBt);
System.gc();
                                if (f.getAlpha() != 0 && !f.emclick) {
                                    f.emclick=true;

                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            synchronized (this) {
                                                a.runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        while (l0.get((int)f.idBt).getX() >= f.getX()) {
                                                            f.setX(f.getX() + vel);


                                                        }
                                                        f.setAlpha(0.0f);
                                                        //f.setEnabled(false);
                                                        f.setY(-900);
                                                    }
                                                });
                                            }





                                            clickFinal(fbt,fcont);

                                            f.emclick=false;
                                        }
                                    }).start();

                                    f.setClickable(false);
                                    if(!nV){

                                        for (int cont=0;cont<l0.size();cont++) {

                                            if(cont!=0&&!nV){


                                                l0.get(cont).animate().alpha(0.0f).setDuration(800).start();

                                            }
                                        }

                                        for (int cont=0;cont<l0.size();cont++) {
                                                          final  int contf=cont;
                                            if(cont!=0&&!nV){

                                               new Thread(new Runnable() {
                                                   @Override
                                                   public void run() {
                                                       while(l0.get(contf).getAlpha()!=0){

                                                           try {
                                                               Thread.sleep(100);
                                                           } catch (InterruptedException e) {
                                                               e.printStackTrace();
                                                           }
                                                       }

                                                       a.runOnUiThread(new Runnable() {
                                                           @Override
                                                           public void run() {
                                                               l0.get(contf).setY(-900);
                                                           }
                                                       });


                                                   }
                                               }).start();

                                            }
                                        }

                                    }

                                } else {

                                    if(possui&&!nV)continue;

                                    for (int cont=0;cont<l0.size();cont++) {

                                        if(cont!=0&&!nV){


                                            l0.get(cont).setY(l0.get(cont).mY);

                                        }
                                    }
                                    if(f.emclick)continue;

                                    f.emclick=true;
                                    //f.setEnabled(true);

                                    f.setY(l0.get((int)f.idBt).getY());
                                    f.setX(l0.get((int)f.idBt).getX());
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            synchronized (this){
                                                a.runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        while (((l0.get((int)f.idBt).getX() ))-(f.getWidth()*f.idY)<f.getX()) {
                                                            f.setX(f.getX() - vel);
                                                             }
                                                        f.emclick=false;
                                                    }
                                                });



                                                clickFinal(fbt,fcont);


                                            }}
                                    }).start();
                                    f.setClickable(true);
                                    if (f.getAlpha() == 0)
                                    {
                                        f.animate().alpha(0.75f).setDuration(800).start();

                                    }

                                    if(!nV&&fcont==0&&fbt==0){
                                        for (int cont=0;cont<l0.size();cont++) {

                                            if(cont!=0&&!nV){


                                                l0.get(cont).animate().alpha(0.75f).setDuration(800).start();

                                            }
                                        }


                                    }
                                }

                            }






                        }
                    });
                } else {
                    fabButton3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            click(fbt,fcont);
                            for (final FloatingActionButton f : nV?fr:all) {

                                if (f.getAlpha() != 0&&!f.emclick) {
                                    f.emclick=true;
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            synchronized (this) {
                                                a.runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        while (l0.get((int)f.idBt).getX() >= f.getX()) {
                                                            f.setX(f.getX() + vel);
                                                        }

                                                        f.emclick = false;
                                                        f.setClickable(false);
                                                        f.animate().alpha(0.0f).setDuration(800).start();

                                                        // f.setEnabled(false);
                                                        f.setY(-900);
                                                    }
                                                });
                                            }



                                            clickFinal(fbt, fcont);
                                        }
                                    }).start();
                                }
                            }

                            if(!nV){

                                for (int cont=0;cont<l0.size();cont++) {

                                    if(cont!=0&&!nV){


                                        l0.get(cont).animate().alpha(0.0f).setDuration(800).start();

                                    }
                                    final int contf=cont;
                                    if(cont!=0&&!nV){

                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                while(l0.get(contf).getAlpha()!=0){

                                                    try {
                                                        Thread.sleep(100);
                                                    } catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                    }
                                                }

                                                a.runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        l0.get(contf).setY(-900);
                                                    }
                                                });


                                            }
                                        }).start();

                                    }
                                }

                            }



                        }
                    });


                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        while(fi.getY()<1){

                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        yI=fi.getY();

                    }
                }).start();



            }

        }



        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int cont=0;cont<l0.size();cont++) {
                    while (l0.get(cont).getY() < 1) {

                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    final int contl=cont;
                    a.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            ViewGroup viewGroup = (ViewGroup) ((ViewGroup) (a.findViewById(android.R.id.content))).getChildAt(0);
                             l0.get(contl).setY(viewGroup.getHeight()-l0.get(contl).getHeight()-l0.get(contl).getHeight()*contl );
                            l0.get(contl).setX(width-l0.get(contl).getWidth()-5);
                        }
                    });

if(cont!=0&&!nV){


        if(cont!=0&&!nV){

            a.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    l0.get(contl).animate().alpha(0.0f).start();
                    l0.get(contl).mY=l0.get(contl).getY();
                    l0.get(contl).setY(-900);

                }
            });
      } }
                }
            }
        }).start();


    }


    public void click(int coluna,int bt){




    }
    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }


    public void clickFinal(int coluna,int bt){




    }


    public void novaTela(int l){

        View child = a.getLayoutInflater().inflate(l, null);// aqui seu layout
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) (a.findViewById(android.R.id.content))).getChildAt(0);
        viewGroup.removeAllViews();
        viewGroup.addView(child,new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT));
            viewGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    hideKeyboardFrom(a,viewGroup);

                }
            });

    }



    public   void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



    public void tempo(long t){

        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<FloatingActionButton> rr(){


        return all;
    }
}
