import java.util.Scanner;

public class CustomHashMapDemo {

    public static void main(String[] args) {
        CustomHashMap<String, String> map = new CustomHashMap<>();
        Scanner scanner = new Scanner(System.in);

        int generatedId = 1;

        while (true) {
            System.out.println("\n=== Демонстрация ===");
            System.out.println("1. Добавить или обновить пару");
            System.out.println("2. Получить значение");
            System.out.println("3. Удалить пару");
            System.out.println("4. Вывести всю мапу");
            System.out.println("5. Показать размер");
            System.out.println("6. Добавить сгенерированные записи");
            System.out.println("7. Показать статистику");
            System.out.println("8. Проверить equals() и hashCode()");
            System.out.println("9. Показать hashCode мапы");
            System.out.println("10. Очистить мапу");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> {
                    String key = readNullableString(
                            scanner, "Введите ключ (null для null): ");
                    String value = readNullableString(
                            scanner, "Введите значение (null для null): ");

                    map.put(key, value);
                    System.out.println("Пара добавлена или обновлена.");
                }

                case "2" -> {
                    String key = readNullableString(
                            scanner, "Введите ключ (null для null): ");

                    String value = map.get(key);
                    System.out.println("Результат: " + value);
                }

                case "3" -> {
                    String key = readNullableString(
                            scanner, "Введите ключ (null для null): ");

                    String removedValue = map.remove(key);
                    System.out.println("Результат удаления: " + removedValue);
                }

                case "4" -> map.display();

                case "5" -> System.out.println("Размер мапы: " + map.size());

                case "6" -> {
                    System.out.print("Сколько записей добавить? ");

                    try {
                        int count = Integer.parseInt(scanner.nextLine());

                        if (count < 1) {
                            System.out.println("Количество должно быть больше нуля.");
                            break;
                        }

                        for (int i = 0; i < count; i++) {
                            String key = "key_" + generatedId;
                            String value = "value_" + generatedId;

                            map.put(key, value);
                            generatedId++;
                        }

                        System.out.println("Добавлено записей: " + count);
                        System.out.println("Текущий размер мапы: " + map.size());

                    } catch (NumberFormatException e) {
                        System.out.println("Введите целое число.");
                    }
                }

                case "7" -> {
                    System.out.println("Записей: " + map.size());
                    System.out.println("Корзин: " + map.capacity());
                    System.out.printf("Заполнение: %.1f%%%n",
                            (double) map.size() / map.capacity() * 100);
                }

                case "8" -> testEqualsAndHashCode();

                case "9" -> System.out.println("hashCode: " + map.hashCode());

                case "10" -> {
                    map.clear();
                    System.out.println("Мапа очищена.");
                    System.out.println("Текущий размер: " + map.size());
                    System.out.println("Количество корзин: " + map.capacity());
                }

                case "0" -> {
                    System.out.println("Завершение программы.");
                    return;
                }

                default -> System.out.println("Неизвестная команда.");
            }
        }
    }

    private static String readNullableString(Scanner scanner, String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine();

        return input.equals("null") ? null : input;
    }

    private static void testEqualsAndHashCode() {
        CustomHashMap<String, String> first = new CustomHashMap<>();
        CustomHashMap<String, String> second = new CustomHashMap<>();

        first.put("ыыы", "ййй");
        first.put("ннн", "шшшшш");
        first.put(null, "999");
        first.put("ррр", null);

        second.put("ррр", null);
        second.put(null, "999");
        second.put("ннн", "шшшшш");
        second.put("ыыы", "ййй");

        System.out.println("\n=== Тест equals() и hashCode() ===");

        System.out.println("\nПервая мапа:");
        first.display();

        System.out.println("\nВторая мапа:");
        second.display();

        System.out.println("\nРезультаты сравнения:");

        System.out.println("first.equals(second): "
                + first.equals(second));

        System.out.println("first.hashCode(): "
                + first.hashCode());

        System.out.println("second.hashCode(): "
                + second.hashCode());

        System.out.println("Хеш-коды совпадают: "
                + (first.hashCode() == second.hashCode()));

        System.out.println("==================================");
    }
}