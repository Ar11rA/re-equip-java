package basics;

public class Recursion {

    private static int sumOfDigits(int number) {
        if (number / 10 == 0) {
            return number;
        }
        return (number % 10) + sumOfDigits(number/10);
    }

    private static int powerOfNumber(int base, int exp) {
        if (exp == 1) {
            return base;
        }
        return base * powerOfNumber(base, exp - 1);
    }

    private static int gcd(int num1, int num2) {
        if (num2 == 0) {
            return num1;
        }
        return gcd(num2, num1 % num2);
    }

    public static void main(String[] args) {
        System.out.println(sumOfDigits(543));
        System.out.println(sumOfDigits(12));
        System.out.println(sumOfDigits(1));
        System.out.println(powerOfNumber(2, 4));
        System.out.println(powerOfNumber(3, 3));
        System.out.println(gcd(28, 12));
        System.out.println(gcd(56, 32));
    }
}