package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Main {
    public static String calc(String input) {
        // Разделение строки на операнды и операцию
        String[] tokens = input.split(" ");
        if (tokens.length != 3) {
            throw new IllegalArgumentException("Неверный формат выражения");
        }

        // Получение операндов и операции
        String operand1 = tokens[0];
        String operator = tokens[1];
        String operand2 = tokens[2];

        // Проверка входных данных
        boolean isRoman1 = isRomanNumber(operand1);
        boolean isRoman2 = isRomanNumber(operand2);

        if (isRoman1 && isRoman2) {
            // Если оба операнда римские числа
            int num1 = romanToDecimal(operand1);
            int num2 = romanToDecimal(operand2);
            int result = performOperation(num1, num2, operator);
            if (result <= 0) {
                throw new IllegalArgumentException("Результат работы калькулятора с римскими числами не может быть меньше 1");
            }
            return decimalToRoman(result);
        } else if (!isRoman1 && !isRoman2) {
            // Если оба операнда арабские числа
            int num1 = Integer.parseInt(operand1);
            int num2 = Integer.parseInt(operand2);
            return String.valueOf(performOperation(num1, num2, operator));
        } else {
            throw new IllegalArgumentException("Операнды разных систем счисления");
        }
    }

    private static int performOperation(int num1, int num2, String operator) {
        int result;
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                result = num1 / num2;
                break;
            default:
                throw new IllegalArgumentException("Неподдерживаемая операция: " + operator);
        }
        return result;
    }

    private static boolean isRomanNumber(String number) {
        String romanRegex = "^[IVX]+$";
        return number.matches(romanRegex);
    }

    private static int romanToDecimal(String romanNumber) {
        Map<Character, Integer> romanValues = new HashMap<>();
        romanValues.put('I', 1);
        romanValues.put('V', 5);
        romanValues.put('X', 10);

        int result = 0;
        int prevValue = 0;

        for (int i = romanNumber.length() - 1; i >= 0; i--) {
            int currentValue = romanValues.get(romanNumber.charAt(i));
            if (currentValue < prevValue) {
                result -= currentValue;
            } else {
                result += currentValue;
            }
            prevValue = currentValue;
        }

        return result;
    }

    private static String decimalToRoman(int decimalNumber) {
        if (decimalNumber <= 0) {
            throw new IllegalArgumentException("Невозможно представить отрицательное или нулевое число в римской системе");
        }

        TreeMap<Integer, String> romanValues = new TreeMap<>();
        romanValues.put(1, "I");
        romanValues.put(4, "IV");
        romanValues.put(5, "V");
        romanValues.put(9, "IX");
        romanValues.put(10, "X");
        romanValues.put(40, "XL");
        romanValues.put(50, "L");
        romanValues.put(90, "XC");
        romanValues.put(100, "C");
        romanValues.put(400, "CD");
        romanValues.put(500, "D");
        romanValues.put(900, "CM");
        romanValues.put(1000, "M");

        StringBuilder romanNumber = new StringBuilder();
        while (decimalNumber > 0) {
            int closestValue = romanValues.floorKey(decimalNumber);
            romanNumber.append(romanValues.get(closestValue));
            decimalNumber -= closestValue;
        }

        return romanNumber.toString();
    }

    public static void main(String[] args) {
        // Примеры использования
        try {
            System.out.println(calc("1 + 2")); // Output: 3
            System.out.println(calc("VI / III")); // Output: II
            System.out.println(calc("I - II")); // Output: throws Exception
            System.out.println(calc("I + 1")); // Output: throws Exception
            System.out.println(calc("1")); // Output: throws Exception
            System.out.println(calc("1 + 2 + 3")); // Output: throws Exception
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}