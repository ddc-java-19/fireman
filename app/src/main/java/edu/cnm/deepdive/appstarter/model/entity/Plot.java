package edu.cnm.deepdive.appstarter.model.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "plot", indices = {

},
    foreignKeys =
        @ForeignKey(entity = User.class,
            parentColumns = {"user_id"}, childColumns = {"user_id"},
            onDelete = ForeignKey.CASCADE)
    )
public class Plot {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "plot_id")
  private Long id;

  @ColumnInfo(name = "charred")
  private boolean charred;

  @ColumnInfo(name = "wet")
  private boolean wet;

  @ColumnInfo(name = "burnable")
  private boolean burnable;

  @ColumnInfo(name = "wind")
  private long wind;

  @ColumnInfo(name = "row")
  private int row;

  @ColumnInfo(name = "column")
  private int column;

}
