package edu.cnm.deepdive.fireman.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import edu.cnm.deepdive.fireman.R;
import edu.cnm.deepdive.fireman.model.domain.Game;
import edu.cnm.deepdive.fireman.model.domain.Plot;
import edu.cnm.deepdive.fireman.model.domain.PlotState;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class TerrainView extends View {

  private static final int BORDER_WIDTH = 32;
  private static final int[] stateDrawableIds = {
      R.drawable.tree,
      0, // FIXME: 12/7/2024 add fire drawable id
      R.drawable.waves,
      R.drawable.waves,
      0, // FIXME: 12/7/2024 add drawable/color
      0 // FIXME: 12/7/2024 add drawable/color
  };

  private static final int[] stateColorIds = {
      R.color.burnable,
      R.color.on_fire,
      R.color.wet,
      R.color.soaked,
      R.color.unburnable,
      R.color.charred
  };


  private List<Plot> plots;
  private final Paint gridPaint;
  private final Rect rect;
  private final Paint plotPaint;
  private final Paint borderControlPaint;
  private final Drawable[] stateDrawables;
  private final int[] stateColors;

  private Game game;
  private OnMoveListener onMoveListener;

  {
    plots = new LinkedList<>();
    gridPaint = new Paint();
    gridPaint.setAntiAlias(true);
    gridPaint.setStyle(Style.STROKE);
    gridPaint.setDither(true);
    gridPaint.setColor(Color.BLACK);
    gridPaint.setStrokeWidth(2);
    plotPaint = new Paint();
    plotPaint.setAntiAlias(true);
    plotPaint.setStyle(Style.FILL);
    plotPaint.setDither(true);
    plotPaint.setColor(Color.RED);
    borderControlPaint = new Paint();
    borderControlPaint.setAntiAlias(true);
    borderControlPaint.setStyle(Style.FILL);
    borderControlPaint.setDither(true);
    borderControlPaint.setColor(Color.GRAY);
    rect = new Rect();

  }


  public TerrainView(Context context) {
    super(context);
    stateDrawables = stateDrawables();
    stateColors = stateColors();
  }

  public TerrainView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    stateDrawables = stateDrawables();
    stateColors = stateColors();
  }

  public TerrainView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    stateDrawables = stateDrawables();
    stateColors = stateColors();
  }

  public TerrainView(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    stateDrawables = stateDrawables();
    stateColors = stateColors();
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int width = getSuggestedMinimumWidth();
    int height = getSuggestedMinimumHeight();
    width = resolveSizeAndState(getPaddingLeft() + getPaddingRight() + width, widthMeasureSpec, 0);
    height =
        resolveSizeAndState(getPaddingTop() + getPaddingBottom() + height, heightMeasureSpec, 0);
    int size = Math.max(width, height);
    setMeasuredDimension(size, size);
  }

  // TODO: 12/5/2024 implement onDraw() and translateTouchEvent()


  @Override
  protected void onDraw(@NonNull Canvas canvas) {
    super.onDraw(canvas);
    int borderWidth = getControlBorderWidth(game);
    int width = getWidth();
    float plotSize = (float) (width - borderWidth) / Game.SIZE;
    int height = getHeight();
    canvas.drawRect(0, 0, width - borderWidth, height - borderWidth, gridPaint);
    for (int i = 1; i < Game.SIZE; i++) {
      canvas.drawLine(i * plotSize, 0, i * plotSize, height - borderWidth, gridPaint);
      canvas.drawLine(0, i * plotSize, width - borderWidth, i * plotSize, gridPaint);
    }
    for (Plot plot : plots) {
      float top = plot.getRow() * plotSize;
      float left = plot.getColumn() * plotSize;
      float bottom = (plot.getRow() + 1) * plotSize;
      float right = (plot.getColumn() + 1) * plotSize;
      int position = plot.getPlotState().ordinal();
      plotPaint.setColor(stateColors[position]);
      canvas.drawRect(left, top, right, bottom, plotPaint);
      Drawable drawable = stateDrawables[position];
      if (drawable != null) {
        drawable.setBounds(Math.round(left), Math.round(top), Math.round(right),
            Math.round(bottom));
        drawable.draw(canvas);
      }
    }
    canvas.drawRect(width - borderWidth, 0, width, height - borderWidth, borderControlPaint);
    canvas.drawRect(0, height - borderWidth, width - borderWidth, height, borderControlPaint);
    if (game != null && game.getFinished() == null) {
      setOnTouchListener((view, event) -> translateTouchEvent(event, plotSize));
    } else {
      setOnTouchListener((view, event) -> true);
    }
  }

  public void setGame(Game game) {
    this.game = game;
    this.plots.clear();
    this.plots.addAll(game.getPlots());
    postInvalidate();
  }

  public void setOnMoveListener(OnMoveListener onMoveListener) {
    this.onMoveListener = onMoveListener;
  }

  private boolean translateTouchEvent(MotionEvent event, float plotSize) {
    if (event.getAction() == MotionEvent.ACTION_UP) {
      float x = event.getX();
      float y = event.getY();
      int row = (int) (y / plotSize);
      int col = (int) (x / plotSize);
      onMoveListener.onMove(row, col);
    } else if (event.getAction() == MotionEvent.ACTION_UP && BORDER_WIDTH == event.getAction()
        && game.isUserFireman()) {
      for (int i = 0; i < BORDER_WIDTH; i++) {
        onMoveListener.onMove(i, 0);
      }
    } else if (event.getAction() == MotionEvent.ACTION_UP && BORDER_WIDTH == event.getAction()
        && game.isUserFireman()) {
      for (int i = 0; i < BORDER_WIDTH; i++) {
        onMoveListener.onMove(0, i);
      }
    } else {
      return true;
    }
    return translateTouchEvent(MotionEvent.obtain(event), plotSize);

  }

  private int getControlBorderWidth(Game game) {
    return (game != null && game.isUserFireman()) ? BORDER_WIDTH : 0;
  }

  private Drawable[] stateDrawables() {
    Context context = getContext();
    return Arrays.stream(PlotState.values())
        .mapToInt((state) -> {
          int position = state.ordinal();
          return stateDrawableIds[position];
        })
        .mapToObj((id) -> (id != 0) ? context.getDrawable(id) : null)
        .toArray(Drawable[]::new);
  }

  private int[] stateColors() {
    Context context = getContext();
    return Arrays.stream(PlotState.values())
        .mapToInt((state) -> {
          int position = state.ordinal();
          return stateColorIds[position];
        })
        .map((id) -> (id != 0) ? context.getColor(id) : 0)
        .toArray();
  }


  public interface OnMoveListener {

    void onMove(Integer row, Integer column);
  }
}
