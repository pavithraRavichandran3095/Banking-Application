package com.covertatt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeMap;
import java.util.Vector;

import org.apache.commons.lang.ArrayUtils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class ProposedDrawView extends View implements OnTouchListener {
	Vector vPriClr = new Vector();
	Vector vPriClrChck = new Vector();
	Vector ChkdCol = new Vector();
	TreeMap tPriClr = new TreeMap();
	TreeMap TNXGCOlCOunt = new TreeMap<String, Integer>();
	Vector vecColArra[] = new Vector[4];
	TreeMap TNXGadroid = new TreeMap();
	Vector vKeyFinal = new Vector();
	Vector vecKeyArra[] = new Vector[4];
	Paint paint = new Paint();
	Paint paint1 = new Paint();
	Canvas canvas;
	int width, height;
	Context context;
	String username = "", accno = "";
	int Count = 0;
	String ucheck = "";
	String ucheckarr[];
	HashSet FinalKey = new HashSet();
	ArrayList<Rect> rectposList = new ArrayList<Rect>();
	static boolean finalStatus = true;

	public ProposedDrawView(Context context, int w, int h, String use,
			String accno, String ucheck) {
		super(context);
		this.context = context;
		width = w;
		height = h;
		username = use;
		this.accno = accno;
		this.ucheck = ucheck;
		vPriClr.add("White");
		vPriClr.add("Black");
		vPriClr.add("Blue");
		vPriClr.add("Yellow");
		ucheckarr = ucheck.split("");
		ucheckarr = (String[]) ArrayUtils.removeElement(ucheckarr, "");
		for (int i = 0; i < 4; i++) {
			tPriClr.put(i, vPriClr.get(i));
			TNXGCOlCOunt.put((String) vPriClr.get(i), 0);
			vecColArra[i] = new Vector();
			vPriClrChck.add(vPriClr.get(i));
		}
		ColourGen();
	}

	@Override
	public void onDraw(Canvas canvas) {
		this.canvas = canvas;
		gen();
	}

	public void gen() {
		try {
			if (Count == 4) {
				ProposePinActivity.vproposepin.add(FinalKey);
				Intent intt = new Intent(context, ProposePinActivity.class);
				intt.putExtra("username", username);
				intt.putExtra("accno", accno);
				intt.putExtra("ucheck", ucheck);
				super.getContext().startActivity(intt);
			} else {
				int lt = 180;
				int tp = 500;
				int rt = 190;
				int bm = 550;

				for (int i = 0; i < ProposePinActivity.vproposepin.size() + 1; i++) {
					paint.setColor(Color.rgb(188, 3, 89));
					paint.setStrokeWidth(3);
					canvas.drawRect(lt, tp, rt, bm, paint);
					lt = lt + 30;
					rt = rt + 30;
				}

				TNXGadroid.put(10, "Black");
				TNXGadroid.put(11, "Black");
				int left = 30;
				int top = 30;
				int right = 80;
				int bottom = 80;
				int count = 0;
				Rect r = new Rect();
				for (int i = 0; i <= 3; i++) {
					left = 60;
					right = 120;

					for (int j = 0; j <= 2; j++) {
						count++;
						String color = "";
						if (count <= 9) {
							color = TNXGadroid.get(count).toString();
							String spl[] = color.split("\\,");
							Vector vAddColor = new Vector();
							vAddColor.add(spl[0]);
							vAddColor.add(spl[1]);
							boolean bool = false;

							if (vAddColor.contains("White")) {
								r.set(left, top, right, bottom);
								paint.setColor(Color.WHITE);
								canvas.drawRect(r, paint);
								bool = true;
							}
							if (vAddColor.contains("Black")) {
								if (!bool) {
									r.set(left, top, right, bottom);
									bool = true;
								} else {
									r.set(left, top + 25, right, bottom);
								}
								paint.setColor(Color.DKGRAY);
								canvas.drawRect(r, paint);
							}
							if (vAddColor.contains("Yellow")) {
								if (!bool) {
									r.set(left, top, right, bottom);
									bool = true;
								} else {
									r.set(left, top + 25, right, bottom);
								}
								paint.setColor(Color.rgb(230, 219, 10));
								canvas.drawRect(r, paint);
							}
							if (vAddColor.contains("Blue")) {
								if (!bool) {
									r.set(left, top, right, bottom);
									bool = true;
								} else {
									r.set(left, top + 25, right, bottom);
								}
								paint.setColor(Color.rgb(35, 50, 127));
								canvas.drawRect(r, paint);
							}
						}

						if (i == 3) {
							if (j == 1) {
								color = TNXGadroid.get(0).toString();
								String spl[] = color.split("\\,");
								Vector vAddColor = new Vector();
								vAddColor.add(spl[0]);
								vAddColor.add(spl[1]);
								boolean bool = false;

								if (vAddColor.contains("White")) {
									r.set(left, top, right, bottom);
									paint.setColor(Color.WHITE);
									canvas.drawRect(r, paint);
									bool = true;
								}
								if (vAddColor.contains("Black")) {
									if (!bool) {
										r.set(left, top, right, bottom);
										bool = true;
									} else {
										r.set(left, top + 25, right, bottom);
									}
									paint.setColor(Color.DKGRAY);
									canvas.drawRect(r, paint);
								}
								if (vAddColor.contains("Yellow")) {
									if (!bool) {
										r.set(left, top, right, bottom);
										bool = true;
									} else {
										r.set(left, top + 25, right, bottom);
									}
									paint.setColor(Color.rgb(230, 219, 10));
									canvas.drawRect(r, paint);
								}
								if (vAddColor.contains("Blue")) {
									if (!bool) {
										r.set(left, top, right, bottom);
										bool = true;
									} else {
										r.set(left, top + 25, right, bottom);
									}
									paint.setColor(Color.rgb(35, 50, 127));
									canvas.drawRect(r, paint);
								}
								paint.setTextSize(25);
								paint.setTextScaleX(1.8f);
								paint.setColor(Color.BLACK);
								canvas.drawText(String.valueOf(0), left + 20,
										top + 33, paint);
							}
						} else {
							paint.setTextSize(25);
							paint.setTextScaleX(1.8f);
							paint.setColor(Color.BLACK);
							canvas.drawText(String.valueOf(count), left + 20,
									top + 33, paint);
						}
						left = left + 140;
						right += 140;
					}
					bottom += 120;
					top += 120;
				}

				left = 30;
				top = 585;
				right = 100;
				bottom = 650;
				String val = "";
				for (int i = 0; i <= 3; i++) {
					r = new Rect();
					if (i == 0) {
						paint.setColor(Color.WHITE);
						val = "White";
					}
					if (i == 1) {
						paint.setColor(Color.DKGRAY);
						val = "Black";
					}
					if (i == 2) {
						paint.setColor(Color.rgb(230, 219, 10));
						val = "Yellow";
					}
					if (i == 3) {
						paint.setColor(Color.rgb(35, 50, 127));
						val = "Blue";
					}

					r.set(left, top, right, bottom);
					rectposList.add(r);
					canvas.drawRect(r, paint);
					paint.setTextSize(18);
					paint.setColor(Color.WHITE);
					canvas.drawText(String.valueOf(val), left + 2, top + 95,
							paint);

					left = left + 120;
					right += 120;
				}
			}
			setOnTouchListener(this);
		} catch (Exception e) {
			Toast.makeText(getContext(),
					"Color Gen ProposedDrawView " + e.toString(),
					Toast.LENGTH_SHORT).show();
		}
	}

	public boolean onTouch(View v, MotionEvent event) {
		try {
			int touchX = (int) event.getX();
			int touchY = (int) event.getY();
			int action = event.getAction();

			switch (action) {
			case MotionEvent.ACTION_DOWN:
				int i = 0;
				for (Rect rect : rectposList) {
					i++;
					if (rect.contains(touchX, touchY)) {
						if (i == 1) {
							rectposList = new ArrayList<Rect>();
							blackWhite("White");
							invalidate();
						} else if (i == 2) {
							rectposList = new ArrayList<Rect>();
							blackWhite("Black");
							invalidate();
						} else if (i == 3) {
							rectposList = new ArrayList<Rect>();
							blackWhite("Yellow");
							invalidate();
						} else if (i == 4) {
							rectposList = new ArrayList<Rect>();
							blackWhite("Blue");
							invalidate();
						}
					}
				}
				break;
			}
		} catch (Exception e) {
			Toast.makeText(getContext(), "OnTouch ProposedDrawView " + e,
					Toast.LENGTH_SHORT).show();
		}
		return false;
	}

	public void call() {
		vPriClr = new Vector();
		vPriClrChck = new Vector();
		ChkdCol = new Vector();
		tPriClr = new TreeMap();
		TNXGCOlCOunt = new TreeMap<String, Integer>();
		vecColArra = new Vector[4];
		TNXGadroid = new TreeMap();
		vKeyFinal = new Vector();
		vPriClr.add("White");
		vPriClr.add("Black");
		vPriClr.add("Blue");
		vPriClr.add("Yellow");
		for (int i = 0; i < 4; i++) {
			tPriClr.put(i, vPriClr.get(i));
			TNXGCOlCOunt.put((String) vPriClr.get(i), 0);
			vecColArra[i] = new Vector();
			vPriClrChck.add(vPriClr.get(i));
		}
		ColourGen();
	}

	public void ColourGen() {
		String col = "";
		int oldIndx = 0, loopCnt = 0;
		while (!vPriClrChck.isEmpty()) {
			col = (String) vPriClrChck.get(new Random().nextInt(vPriClrChck
					.size()));
			int ColINdx = vPriClr.indexOf(col);
			if (!ChkdCol.contains(col)) {
				ChkdCol.add(col);
				loopCnt++;
				if (loopCnt % 2 == 1) {
					oldIndx = ColINdx;
				}
				int count = (Integer) TNXGCOlCOunt.get(col);
				while ((Integer) TNXGCOlCOunt.get(col) < 5) {
					while (true) {
						int rand = new Random().nextInt(10);
						if (!vecColArra[oldIndx].contains(rand)) {
							if (!vecColArra[ColINdx].contains(rand)) {
								vecColArra[ColINdx].add(rand);
								count = count + 1;
								TNXGCOlCOunt.put(col, count);
								break;
							}
						}
					}
				}
				vPriClrChck.remove(col);
			}
		}

		for (int jj = 0; jj < vecColArra.length; jj++) {
			Vector v = (Vector) vecColArra[jj];
			for (int ij = 0; ij < v.size(); ij++) {
				if (!TNXGadroid.containsKey(v.get(ij))) {
					TNXGadroid.put(v.get(ij), vPriClr.get(jj));
				} else {
					String val = TNXGadroid.get(v.get(ij)).toString();
					val = val + "," + vPriClr.get(jj).toString();
					TNXGadroid.put(v.get(ij), val);
				}
			}
		}
	}

	public void blackWhite(String cond) {
		try {
			String CorrectCols = TNXGadroid.get(
					Integer.parseInt(ucheckarr[ProposePinActivity.vproposepin
							.size()])).toString();
			Toast.makeText(getContext(), "the correctcols is "+CorrectCols, Toast.LENGTH_LONG).show();
			if (finalStatus) {
				if (!CorrectCols.contains(cond)) {
					finalStatus = false;
				}
			}
			for (int i = 0; i < 10; i++) {
				String iter = TNXGadroid.get(i).toString();
				if (iter.contains(cond)) {
					vKeyFinal.add(i);
				}
			}
			vecKeyArra[Count] = vKeyFinal;
			Count++;
			if (Count == 4) {
				Vector vFrst = vecKeyArra[0];
				TreeMap chck = new TreeMap();
				int c = 0;
				ArrayList l = null;
				vecKeyArra = (Vector[]) ArrayUtils.removeElement(vecKeyArra,
						vFrst);
				for (int jj = 0; jj < vFrst.size(); jj++) {
					for (int i = 0; i < vecKeyArra.length; i++) {
						if (vecKeyArra[i].contains(vFrst.get(jj))) {
							if (chck.containsKey(vFrst.get(jj))) {
								c = (Integer) chck.get(vFrst.get(jj));
							} else {
								c = 0;
							}
							c++;
							chck.put((Integer) vFrst.get(jj), c);
						}
					}
				}

				l = new ArrayList(chck.values());
				Collections.sort(l);
				Iterator ii = chck.keySet().iterator();
				while (ii.hasNext()) {
					int iterKey = (Integer) ii.next();
					int chk = (Integer) chck.get(iterKey);
					if (chk == (Integer) (l.get(l.size() - 1))) {
						FinalKey.add(iterKey);
					}
				}
			} else {
				call();
			}
		} catch (Exception e) {
			Toast.makeText(getContext(), "blackwhite===>" + e,
					Toast.LENGTH_LONG).show();
		}
	}
}