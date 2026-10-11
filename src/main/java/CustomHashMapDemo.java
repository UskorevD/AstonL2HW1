import java.util.Scanner;

public class CustomHashMapDemo {

    public static void main(String[] args) {
        CustomHashMap<String, String> map = new CustomHashMap<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Демонстрация ===");
            System.out.println("1. Добавить или обновить пару");
            System.out.println("2. Получить значение");
            System.out.println("3. Удалить пару");
            System.out.println("4. Вывести всю мапу");
            System.out.println("5. Показать размер");
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
}