package com.rezende.chamados.view.MenuFloat;

import android.app.Activity;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class menuFloatH {

    ArrayList<FloatingActionButton> l0 = new ArrayList<FloatingActionButton>();

    FloatingActionButton fi = null;
    public float yI = 0;
    float vel = 1f;

    Activity a = null;

    public menuFloatH(final Activity a, int gravity, int dis, int[]... bts) {
        this.a = a;

        DisplayMetrics metrics = a.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        dis = (height * dis) / 800;


        for (int bt = 0; bt < bts.length; bt++) {
            final int fbt = bt;
            final ArrayList<FloatingActionButton> fr = new ArrayList<FloatingActionButton>();

            for (int cont = 0; cont < bts[bt].length; cont++) {
                final int fcont = cont;
                final FloatingActionButton fabButton3 = new FloatingActionButton.Builder(a)
                        .withDrawable(a.getResources()
                                .getDrawable(bts[bt][cont]))
                        .withButtonColor(Color.WHITE)
                        .withGravity(gravity)
                        .withMargins(gravity == (Gravity.BOTTOM | Gravity.LEFT) ? (bt * dis) : 5, 0, gravity == (Gravity.BOTTOM | Gravity.RIGHT) ? (bt * dis) : 5, cont == 0 ? 8 : -100)
                        .create();

                fabButton3.setAlpha(0.75f);
                fabButton3.idY = cont;
                if (cont != 0) {

                    fabButton3.setAlpha(0.0f);

                    fabButton3.setClickable(false);
                    fr.add(fabButton3);
                } else {
                    fi = fabButton3;
                    yI = fabButton3.getY();
                    l0.add(fabButton3);

                }

                if (cont == 0) {
                    fabButton3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            click(fbt, fcont);

                            for (final FloatingActionButton f : fr) {

                                if (f.getAlpha() != 0 && !f.emclick) {
                                    f.emclick = true;


                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            synchronized (this) {
                                                a.runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        while (fi.getY() >= f.getY()) {
                                                            f.setY(f.getY() + vel);


                                                        }
                                                        f.setAlpha(0.0f);
                                                        //f.setEnabled(false);
                                                        f.setY(-100);
                                                    }
                                                });
                                            }


                                            clickFinal(fbt, fcont);

                                            f.emclick = false;
                                        }
                                    }).start();

                                    f.setClickable(false);


                                } else {
                                    if (f.emclick) return;

                                    f.emclick = true;
                                    //f.setEnabled(true);
                                    if (f.mY == -1) {
                                        f.mY = yI - (f.getWidth() * f.idY);
                                    }
                                    f.setY(fi.getY());

                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            synchronized (this) {
                                                a.runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        while (f.mY < f.getY()) {
                                                            f.setY(f.getY() - vel);

                                                        }
                                                        f.emclick = false;
                                                    }
                                                });


                                                clickFinal(fbt, fcont);


                                            }
                                        }
                                    }).start();
                                    f.setClickable(true);
                                    if (f.getAlpha() == 0)
                                        f.setAlpha(0.75f);
                                }

                            }


                        }
                    });
                } else {
                    fabButton3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            click(fbt, fcont);
                            for (final FloatingActionButton f : fr) {

                                if (f.getAlpha() != 0 && !f.emclick) {
                                    f.emclick = true;
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            synchronized (this) {
                                                a.runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        while (fi.getY() >= f.getY()) {
                                                            f.setY(f.getY() + vel);
                                                        }

                                                        f.emclick = false;
                                                        f.setClickable(false);
                                                        f.setAlpha(0.0f);
                                                        // f.setEnabled(false);
                                                        f.setY(-100);
                                                    }
                                                });
                                            }


                                            clickFinal(fbt, fcont);
                                        }
                                    }).start();
                                }
                            }

                        }
                    });


                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        while (fi.getY() < 1) {

                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        yI = fi.getY();

                    }
                }).start();


            }

        }


    }


    public void click(int coluna, int bt) {


    }


    public void clickFinal(int coluna, int bt) {


    }


    public void novaTela(int l) {

        View child = a.getLayoutInflater().inflate(l, null);// aqui seu layout
        ViewGroup viewGroup = (ViewGroup) ((ViewGroup) (a.findViewById(android.R.id.content))).getChildAt(0);
        viewGroup.removeAllViews();
        viewGroup.addView(child, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT));

    }

    public void tempo(long t) {

        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
