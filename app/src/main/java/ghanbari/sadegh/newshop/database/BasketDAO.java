package ghanbari.sadegh.newshop.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BasketDAO {

    @Query("SELECT * FROM basket WHERE userId = :userId " )
    List<Basket> getBasketByUser(String userId);


    @Insert
    void insert(Basket myBasket);

    @Update
    void update(Basket myBasket);

    @Delete
    void delete(Basket myBasket);

    @Query("SELECT * FROM basket WHERE productId = :productId " )
    List<Basket> getBasketByProduct(String productId);
}
