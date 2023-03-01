package converter;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Main {
    static java.util.Scanner scanner = new java.util.Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.print("Enter two numbers in format: {source base} {target base} (To quit type /exit) >");
            String firstInput = scanner.nextLine();
            if ("/exit".equals(firstInput)) {
                break;
            } else {
                String integerPart = "";
                String fractionalPart = "";
                while (true) {
                    String[] bases = firstInput.split(" ");
                    System.out.printf("Enter number in base %s to convert to base %s (To go back type /back) >", bases[0], bases[1]);
                    String secondInput = scanner.nextLine();
                    if ("/back".equals(secondInput)) {
                        break;
                    } else if (secondInput.contains(".")) {
                        String[] fractions = secondInput.split("\\.");
                        integerPart = fractions[0];
                        if (!bases[0].equals("10")) {
                            if (fractions[0].equals("0")) {
                                integerPart = "0";
                            } else {
                                integerPart = fromAnyToDec(fractions[0], bases[0]);
                            }
                            fractionalPart = fractionalToDecimal(secondInput, Integer.parseInt(bases[0]));
                        } else {
                            fractionalPart = "0." + fractions[1];
                        }
                        System.out.print("Conversion result: ");
                        if (!integerPart.equals("0")) {
                            fromDecToAny(integerPart, bases[1]);
                        } else System.out.print("0");
                        System.out.print(".");
                        decimalToFractional(fractionalPart, Integer.parseInt(bases[1]));
                        System.out.println(" ");
                    } else {
                        if (!bases[0].equals("10")) {
                            secondInput = fromAnyToDec(secondInput, bases[0]);
                        }
                        System.out.print("Conversion result: ");
                        fromDecToAny(secondInput, bases[1]);
                        System.out.println(" ");
                    }
                }
            }
        }
    }

    public static void fromDecToAny(String inputString, String baseString) {
        BigInteger input = new BigInteger(inputString);
        BigInteger base = new BigInteger(baseString);
        BigInteger[] hexNum = new BigInteger[100];
        int i = 0;

        while (!input.equals(BigInteger.ZERO)) {
            hexNum[i] = input.remainder(base);
            input = input.divide(base);
            i++;
        }
        BigInteger nine = new BigInteger("9");

        for (int j = i - 1; j >= 0; j--) {
            if (hexNum[j].compareTo(nine) > 0)
                System.out.print((char) (55 + hexNum[j].intValue()));
            else
                System.out.print(hexNum[j]);
        }
    }

    public static String fromAnyToDec(String hexVal, String baseString) {
        int len = hexVal.length();
        BigInteger base = BigInteger.ONE;
        BigInteger decVal = BigInteger.ZERO;

        for (int i = len - 1; i >= 0; i--) {
            if (hexVal.charAt(i) >= '0'
                    && hexVal.charAt(i) <= '9') {
                decVal = decVal.add(base.multiply(BigInteger.valueOf(hexVal.charAt(i) - 48)));
                base = base.multiply(new BigInteger(baseString));
            } else if (hexVal.charAt(i) >= 'a'
                    && hexVal.charAt(i) <= 'z') {
                decVal = decVal.add(base.multiply(BigInteger.valueOf(hexVal.charAt(i) - 87)));
                base = base.multiply(new BigInteger(baseString));
            }
        }
        return decVal.toString();
    }

    public static void decimalToFractional(String a, int target) {
        StringBuilder output = new StringBuilder();
        double number = Double.parseDouble(a);
        for (int i = 0; i < 5; i++) {
            number *= target;
            String[] test = String.valueOf(number).split("\\.");
            number -= Double.parseDouble(test[0]);
            int t;
            if (Integer.parseInt(test[0]) >= 0 && Integer.parseInt(test[0]) <= 9) {
                t = 48 + Integer.parseInt(test[0]);
            } else {
                t = 87 + Integer.parseInt(test[0]);
            }
            output.append((char) (t));
        }
        System.out.println(output);
    }

    public static String fractionalToDecimal(String a, int base) {
        BigDecimal result = BigDecimal.ZERO;
        String[] test = a.split("\\.");
        for (int i = 0; i < test[1].length(); i++) {
            int b = test[1].charAt(i);
            if (b <= 57) {
                b = test[1].charAt(i) - 48;
            } else {
                b = test[1].charAt(i) - 87;
            }
            result = result.add(new BigDecimal(b * (1 / Math.pow(base, i + 1))));
        }
        return result.toString();
    }
}