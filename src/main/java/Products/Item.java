package Products;

public class Item { //Single Responsibility Principle, Interface Segregation Principle
    Products name;
    Categories category;
    int number;
    int price;

    public Item(Products name, Categories category, int number, int price) {
        this.name = name;
        this.category = category;
        this.number = number;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Товар: " +
                "название - " + name +
                ", количество в наличии - " + number +
                ", цена (за 1 шт.) - " + price;
    }

    public String toStringInBasket() {
        return "Товар: " +
                "название - " + name +
                ", количество в корзине - " + number +
                ", цена (за 1 шт.) - " + price;
    }


    public Products getName() {
        return name;
    }

    public Categories getCategory() {
        return category;
    }

    public int getNumber() {
        return number;
    }

    public int getPrice() {
        return price;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
