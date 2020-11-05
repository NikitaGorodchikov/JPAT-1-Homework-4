import Products.Item;
import java.util.List;

public class Recommendations { //Single Responsibility Principle, Interface Segregation Principle
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
