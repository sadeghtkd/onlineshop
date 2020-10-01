package ghanbari.sadegh.newshop.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import ghanbari.sadegh.newshop.pojo.Product;

@Entity
public class Basket {
    @PrimaryKey(autoGenerate = true)
    public  int id;

    @ColumnInfo(name = "productId")
    public String productId;

    @ColumnInfo(name = "userId")
    public String userId;

    @ColumnInfo(name = "count")
    public int count;

    @Ignore
    public Product product;
}
