import Products.Item;
import java.util.List;

public class Recommendations {
    public Item recommendation(List<Item> basket) {
        Item itemForRec = null;
        int num = 0;
        for (Item item : basket) {
            int sells = item.getNumber();
            if (sells > num) {
                num = sells;
                itemForRec = item;
            }
        }
        return itemForRec;
    }
}
