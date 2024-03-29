package org.argonath.rd.entry.it;


import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ValuesGenerator {

    final static Pattern NUMERIC_FRACTION = Pattern.compile("n(\\d+),(\\d+)");

    final static Pattern RANGE_MATCH = Pattern.compile("(a|n|an)\\.\\.(\\d+)");

    final static Pattern VARIABLE_RANGE_MATCH = Pattern.compile("(a|n|an)(\\d+)\\.\\.(\\d+)");

    final static Pattern EXACT_MATCH = Pattern.compile("(a|n|an)(\\d+)");

    public static String randomStringUppercase(String... formats) {
        return randomString(formats).toUpperCase();
    }

    public static String randomString(String... formats) {
        StringBuffer buf = new StringBuffer();
        Arrays.stream(formats).forEach(format -> buf.append(generateString(format).toUpperCase()));
        return buf.toString();
    }

    public static Long randomLong(String format) {
        StringBuffer buf = new StringBuffer();
        String str = generateString(format);
        Long ret;
        try {
            ret = Long.valueOf(str);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid Format:" + format, e);
        }
        return ret;
    }

    public static BigDecimal randomBigDecimal(String format) {
        String str = randomString(format);
        return new BigDecimal(str);
    }

    public static byte[] randomBinary(String format) {
        String str = randomString(format);
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        return bytes;
    }

    private static String generateString(String format) {

        String ret;

        Matcher fractionMatcher = NUMERIC_FRACTION.matcher(format);

        Integer minDigits;
        Integer maxDigits;
        Integer fractionDigits;
        String fmt;

        try {
            if (fractionMatcher.matches()) {
                // handle fraction
                minDigits = maxDigits = Integer.parseInt(fractionMatcher.group(1));
                fractionDigits = Integer.parseInt(fractionMatcher.group(2));
                fmt = "n";
            } else {
                Matcher rangeMatcher = RANGE_MATCH.matcher(format);
                if (rangeMatcher.matches()) {
                    // handle range
                    maxDigits = Integer.parseInt(rangeMatcher.group(2));
                    minDigits = 1;
                    fractionDigits = 0;
                    fmt = rangeMatcher.group(1);
                } else {
                    Matcher exactMatcher = EXACT_MATCH.matcher(format);
                    if (exactMatcher.matches()) {
                        maxDigits = minDigits = Integer.parseInt(exactMatcher.group(2));
                        fractionDigits = 0;
                        fmt = exactMatcher.group(1);
                    } else {
                        Matcher variableRangeMatcher = VARIABLE_RANGE_MATCH.matcher(format);
                        if (variableRangeMatcher.matches()) {
                            // handle range
                            maxDigits = Integer.parseInt(variableRangeMatcher.group(3));
                            minDigits = Integer.parseInt(variableRangeMatcher.group(2));
                            fractionDigits = 0;
                            fmt = variableRangeMatcher.group(1);
                        } else {
                            return format;
                            //throw new IllegalArgumentException("Invalid Format:" + format);
                        }
                    }
                }
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid Format:" + format);
        }

        if (fmt.contains("a") && format.contains("n")) {
            ret = generateAlphanumeric(minDigits, maxDigits);
        } else if (fmt.contains("a")) {
            ret = generateAlpha(minDigits, maxDigits);
        } else if (fmt.contains("n")) {
            ret = generateNumeric(minDigits, maxDigits, fractionDigits);
        } else {
            throw new RuntimeException("Invalid Format - Cannot Parse" + format);
        }

        return ret;

    }

    private static String generateNumeric(Integer minDigits, Integer maxDigits, Integer fractionDigits) {
        boolean hasFraction = (fractionDigits > 0);
        Boolean isRange = maxDigits != minDigits;

        Integer integralDigits = maxDigits - fractionDigits;
        String integral = generateString(integralDigits, integralDigits, FormatType.NUMERIC);
        String ret = integral;
        if (hasFraction) {
            String decimal = generateString(fractionDigits, fractionDigits, FormatType.NUMERIC);
            ret = ret + "." + decimal;
        }

        return ret;
    }

    private static String generateAlpha(Integer minDigits, Integer maxDigits) {
        return generateString(minDigits, maxDigits, FormatType.ALPHA);
    }

    private static String generateAlphanumeric(Integer minDigits, Integer maxDigits) {
        return generateString(minDigits, maxDigits, FormatType.ALPHANUMERIC);
    }

    private static String generateString(Integer minDigits, Integer maxDigits, FormatType formatType) {
        StringBuffer buf = new StringBuffer();
        Integer length = RandomNumber.getInteger(minDigits, maxDigits + 1);
        IntStream.range(0, length).forEach(id -> buf.append(generateChar(formatType)));
        return buf.toString();

    }

    private static char generateChar(FormatType numeric) {
        switch (numeric) {
            case ALPHA:
                return randomChar(ALPHA_SET);
            case NUMERIC:
                return randomChar(NUMERIC_SET);
            case ALPHANUMERIC:
                return randomChar(ALPHANUMERIC_SET);
        }
        throw new RuntimeException("Invalid Format: Type" + numeric);
    }

    private static char randomChar(String alphaSet) {
        Integer index = RandomNumber.getInteger(0, alphaSet.length());
        return alphaSet.charAt(index);
    }

    private static Integer getNumber(String format) {
        String number = format.replaceAll("\\D+", "");
        Integer ret = null;
        try {
            ret = Integer.parseInt(number);
        } catch (NumberFormatException e) {
            // preserve null
        }
        return ret;
    }

    static final String ALPHA_SET = "abcdefghijklmnopqrstuvwxyz";

    static final String NUMERIC_SET = "0123456789";

    static final String ALPHANUMERIC_SET = ALPHA_SET + NUMERIC_SET;

    public static List<String> randomStringList(int size, String... formats) {
        return IntStream.range(0, size).boxed().map(item -> randomString(formats)).collect(Collectors.toList());
    }

    enum FormatType {
        ALPHANUMERIC,
        ALPHA,
        NUMERIC;
    }
}
