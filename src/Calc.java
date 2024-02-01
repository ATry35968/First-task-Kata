import java.util.Scanner;

public class Calc {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        System.out.println(calc(in.nextLine())); // в метод calc передается строка; результат выводится на консоль
    }

    public static String calc(String input) throws Exception {
        String newInput = input.replace(" ", "");
        char[] inputCharArray = newInput.toCharArray();
        String firstNum = "";
        String secondNum = "";
        String operator = "";
        int arabicOrRoman = 0; // 1 = arabic, 2 = roman

        for (char i: inputCharArray) {
            if (isArabicNumberSymbol(i)) {
                if (arabicOrRoman == 2) {
                    throw new Exception("Калькулятор умеет работать только с арабскими или римскими цифрами одновременно.");
                }
                if (operator.isEmpty()) {
                    if (firstNum.isEmpty() && i == '0' || !firstNum.isEmpty() && i != '0') {
                        throw new Exception("Число должно быть от 1 до 10 включительно.");
                    }
                    firstNum += i;
                } else {
                    if (secondNum.isEmpty() && i == '0' || !secondNum.isEmpty() && i != '0') {
                        throw new Exception("Число должно быть от 1 до 10 включительно.");
                    }
                    secondNum += i;
                }
                arabicOrRoman = 1;
            } else if (isRomanNumberSymbol(i)) {
                if (arabicOrRoman == 1) {
                    throw new Exception("Калькулятор умеет работать только с арабскими или римскими цифрами одновременно.");
                }
                if (operator.isEmpty()) firstNum += i;
                else secondNum += i;
                arabicOrRoman = 2;
            } else if (isMathOperatorSymbol(i)) {
                if (operator.isEmpty()) {
                    operator += i;
                } else {
                    throw new Exception("Неверный формат. Повторите попытку."); //По заданию д.б. два операнда и один оператор
                }
            } else {
                throw new Exception("Некорректный ввод.");
            }
        }

        if (operator.isEmpty() || firstNum.isEmpty() || secondNum.isEmpty()) {
            throw new Exception("Строка не является математической операцией.");
        }

        String result = "";  //YT+VA
        int a, b, c = 0;
        if (arabicOrRoman == 2) {
            a = convertRomanToArabic(firstNum);
            b = convertRomanToArabic(secondNum);
            if (a == 0 || a > 10 || b == 0 || b > 10) {
                throw new Exception("На вход принимаются числа от 1 до 10 включительно.");
            }
            c = calculateOperation(a, b, operator);
            if (c < 1) {
                throw new Exception("Результат с римскими числами может быть только положительным.");
            }
            result = convertArabicToRoman(c);
        } else {
            a = Integer.parseInt(firstNum);
            b = Integer.parseInt(secondNum);
            c = calculateOperation(a, b, operator);
            result += c;
        }

        return result;
    }

    static boolean isArabicNumberSymbol(char symbol) {
        int value = Character.getNumericValue(symbol);
        return value >= 0 && value <= 9;
    }

    static boolean isRomanNumberSymbol(char symbol) {
        return switch (symbol) {
            case 'I', 'V', 'X' -> true;
            default -> false;
        };
    }

    static boolean isMathOperatorSymbol(char symbol) {
        return switch (symbol) {
            case '+', '-', '/', '*' -> true;
            default -> false;
        };
    }

    static String[] romanNumbers = {
            "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X",
            "XI", "XII", "XIII", "XIV", "XV", "XVI", "XVII", "XVIII", "XIX", "XX",
            "XXI", "XXII", "XXIII", "XXIV", "XXV", "XXVI", "XXVII", "XXVIII", "XXIX", "XXX",
            "XXXI", "XXXII", "XXXIII", "XXXIV", "XXXV", "XXXVI", "XXXVII", "XXXVIII", "XXXIX", "XL",
            "XLI", "XLII", "XLIII", "XLIV", "XLV", "XLVI", "XLVII", "XLVIII", "XLIX", "L",
            "LI", "LII", "LIII", "LIV", "LV", "LVI", "LVII", "LVIII", "LIX", "LX",
            "LXI", "LXII", "LXIII", "LXIV", "LXV", "LXVI", "LXVII", "LXVIII", "LXIX", "LXX",
            "LXXI", "LXXII", "LXXIII", "LXXIV", "LXXV", "LXXVI", "LXXVII", "LXXVIII", "LXXIX", "LXXX",
            "LXXXI", "LXXXII", "LXXXIII", "LXXXIV", "LXXXV", "LXXXVI", "LXXXVII", "LXXXVIII", "LXXXIX", "XC",
            "XCI", "XCII", "XCIII", "XCIV", "XCV", "XCVI", "XCVII", "XCVIII", "XCIX", "C"
    };   // L-50, C-100, D-500, M-1000/ встречала, как в перечислении фигурировали не все возможные цифры, но не понимаю как сделать так, чтобы кальк. работал корректно

    static String convertArabicToRoman(int index) {  //YT
        return romanNumbers[index - 1];
    }

    static int convertRomanToArabic(String str) {
        int result = 0;

        for (int i = 0; i < romanNumbers.length; i++) {
            if (romanNumbers[i].equals(str)) {
                result = i + 1;
                break;
            }
        }

        return result;
    }

    static int calculateOperation(int a, int b, String operator) {  //VA
        return switch (operator) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "/" -> a / b;
            case "*" -> a * b;
            default -> 0;
        };
    }
}