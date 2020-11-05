import Products.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static List<Item> basket = new ArrayList<>();
    static List<Item> catalogue = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static Recommendations rec = new Recommendations();
    static int wallet;

    public static void main(String[] args) {
        catalogue.add(new Item(Products.APPLE, Categories.FOOD, 11, 2));
        catalogue.add(new Item(Products.BANANA, Categories.FOOD, 18, 3));
        catalogue.add(new Item(Products.OAT, Categories.FOOD, 9, 5));
        catalogue.add(new Item(Products.MILK, Categories.FOOD, 17, 6));
        catalogue.add(new Item(Products.SOAP, Categories.HOME, 10, 4));
        catalogue.add(new Item(Products.SHAMPOO, Categories.HOME, 12, 8));
        catalogue.add(new Item(Products.BROOM, Categories.HOME, 4, 5));
        catalogue.add(new Item(Products.HAMMER, Categories.HOME, 3, 7));
        catalogue.add(new Item(Products.SMARTPHONE, Categories.GADGETS, 2, 1000));
        catalogue.add(new Item(Products.EBOOK, Categories.GADGETS, 3, 800));
        catalogue.add(new Item(Products.SMARTWATCH, Categories.GADGETS, 5, 599));
        catalogue.add(new Item(Products.LAPTOP, Categories.GADGETS, 4, 5000));
        System.out.println("Добро пожаловать!");
        System.out.print("Какую сумму вы хотите добавить в Ваш виртуальный кошелек? -> ");
        wallet = scanner.nextInt();
        System.out.println("Ваш баланс: " + wallet + "$");
        while (true) {
            System.out.println("Выберете действие (введите номер):");
            System.out.println("1) Показать мой баланс\n" +
                    "2) Добавить товар в корзину\n" +
                    "3) Удалить товар из корзины\n" +
                    "4) Очистить корзину\n" +
                    "5) Стоимость всей корзины\n" +
                    "6) Показать всю корзину\n" +
                    "7) Перейти к оформлению заказа");
            int input = scanner.nextInt();
            if (input == 1) {
                System.out.println(wallet + "$");
            } else if (input == 2) {
                try {
                    addItem();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else if (input == 3) {
                deleteItem();
            } else if (input == 4) {
                basket.clear();
            } else if (input == 5) {
                System.out.println(basketPrice() + "$");
            } else if (input == 6) {
                soutBasket();
            } else if (input == 7) {
                try {
                    buy();
                    break;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("Вы ввели неверное значение, попробуйте еще раз");
            }
        }
    }

    public static void addItem() throws Exception {
        System.out.println("Выберете категорию:");
        Categories[] categoriesList = Categories.values();
        for (int i = 0; i < categoriesList.length; i++) {
            System.out.println(i + 1 + ") " + categoriesList[i]);
        }
        int input = scanner.nextInt();
        Categories category = categoriesList[input - 1];
        System.out.println("Список товаров:");
        for (int i = 0; i < catalogue.size(); i++) {
            Item item = catalogue.get(i);
            if (item.getCategory().equals(category)) System.out.println(i + 1 + ") " + item.toString());
        }
        System.out.print("Введите номер товара (0 для выхода в главное меню) -> ");
        input = scanner.nextInt() - 1;
        if (input == (-1)) {
            System.out.println("Отправляем вас назад . . .");
        } else {
            System.out.print("Введите количество -> ");
            int number = scanner.nextInt();
            if (catalogue.get(input).getNumber() < number) {
                throw new Exception("Нет в наличии столько!");
            }
            try {
                Item newItem = new Item(catalogue.get(input).getName(), catalogue.get(input).getCategory(),
                        catalogue.get(input).getNumber(), catalogue.get(input).getPrice());
                newItem.setNumber(number);
                basket.add(newItem);
                System.out.println(catalogue.get(input).getNumber());
                catalogue.get(input).setNumber(catalogue.get(input).getNumber() - number);
            } catch (IndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void deleteItem() {
        soutBasket();
        System.out.print("Выберете товар для удаления ->");
        int num = scanner.nextInt() - 1;
        basket.remove(num);
    }

    public static int basketPrice() {
        int sum = 0;
        for (Item item : basket) {
            sum = sum + item.getNumber()*item.getPrice();
        }
        return sum;
    }

    public static void buy() throws Exception {
        if (basket.size() == 0) {
            System.out.println("Нечего покупать!");
        } else {
            int sum = basketPrice();
            recommend();
            soutBasket();
            System.out.println("Нажмите ENTER чтобы подтвердить покупку");
            scanner.nextLine();
            scanner.nextLine();
            if (sum > wallet) {
                throw new Exception("У ВАС НЕДОСТАТОЧНО СРЕДСТВ!");
            } else {
                wallet = wallet - sum;
                System.out.println("Успешно!");
                System.out.println("Ваш чек:");
                for (int i = 0; i < basket.size(); i++) {
                    Item item = basket.get(i);
                    System.out.println(i + 1 + ") " + item.toStringInBasket());
                }
                System.out.println("Сумма: " + sum + "$");
            }
        }
    }

    public static void recommend() {
        System.out.println("У нас для вас новая рекомендация!");
        Item item = rec.recommendation(basket);
        System.out.println(item.toStringInBasket());
        System.out.print("Хотите приобрести? (1 - \"да\"/2 - \"нет\") -> ");
        int input = scanner.nextInt();
        switch (input) {
            case (1) -> {
                System.out.print("Введите кол-во -> ");
                int num = scanner.nextInt();
                item.setNumber(num);
                basket.add(item);
            }
            case (2) -> System.out.println("Зря отказались! :^D");
            default -> System.out.println("Вы ввели неверное значение");
        }
    }

    public static void soutBasket() {
        if (basket.size() == 0) {
            System.out.println("Ваша корзина пуста!");
        } else {
            System.out.println("Ваша корзина:");
            for (int i = 0; i < basket.size(); i++) {
                Item item = basket.get(i);
                System.out.println(i + 1 + ") " + item.toStringInBasket());
            }
        }
    }
}
