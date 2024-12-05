package edu.cnm.deepdive.fireman.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.icu.text.ListFormatter.Width;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import edu.cnm.deepdive.fireman.model.domain.Game;
import edu.cnm.deepdive.fireman.model.domain.Plot;
import java.util.LinkedList;
import java.util.List;

public class TerrainView extends View {

  private static final int BORDER_WIDTH = 32;

  private List<Plot> plots;
  private final Paint gridPaint;
  private final Rect rect;
  private final Paint waterPaint;
  private final Paint firePaint;
  private final Paint borderControlPaint;

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
    firePaint = new Paint();
    firePaint.setAntiAlias(true);
    firePaint.setStyle(Style.FILL);
    firePaint.setDither(true);
    firePaint.setColor(Color.RED);
    waterPaint = new Paint();
    waterPaint.setAntiAlias(true);
    waterPaint.setStyle(Style.FILL);
    waterPaint.setDither(true);
    waterPaint.setColor(Color.BLUE);
    borderControlPaint = new Paint();
    borderControlPaint.setAntiAlias(true);
    borderControlPaint.setStyle(Style.FILL);
    borderControlPaint.setDither(true);
    borderControlPaint.setColor(Color.GRAY);
    rect = new Rect();

  }


  public TerrainView(Context context) {
    super(context);
  }

  public TerrainView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public TerrainView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public TerrainView(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
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
    for (int i = 1; i< Game.SIZE; i++){
      canvas.drawLine(i*plotSize, 0, i*plotSize, height - borderWidth, gridPaint);
      canvas.drawLine(0, i*plotSize, width - borderWidth, i*plotSize, gridPaint);
    }
    for(Plot plot : plots){
      float top = plot.getRow() * plotSize;
      float left = plot.getColumn() * plotSize;
      float bottom = (plot.getRow() + 1) * plotSize;
      float right = (plot.getColumn() + 1) * plotSize;
      switch(plot.getPlotState()){
        case ON_FIRE -> {
          canvas.drawRect(left, top, right, bottom, firePaint);
        }
        default -> {}
      }
    }
    canvas.drawRect(width - borderWidth, 0, width, height - borderWidth, borderControlPaint);
    canvas.drawRect(0, height - borderWidth, width - borderWidth, height, borderControlPaint);
    if(game != null && game.getFinished() == null){
      setOnTouchListener((view, event) -> translateTouchEvent(event, plotSize));
    }else{
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

    }
    return true;
  }

  private int getControlBorderWidth(Game game){
    return (game != null && game.isUserFireman()) ? BORDER_WIDTH : 0 ;
  }

  public interface OnMoveListener {
    void onMove(Integer row, Integer column);
  }
}
