package ga.ozli.minecraftmods.coordints;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

final class Utils {
    private static final Pattern NUMBER_PATTERN = Pattern.compile("(-?\\d+[.,]?\\d+)");
    private static final Random RANDOM = new Random();

    static String redactCoordsFromMessage(String message, final double[] blacklist) {
        // first, get all parts of the string that contain a number
        final var matcher = NUMBER_PATTERN.matcher(message);
        final List<String> numberParts = new ObjectArrayList<>();
        while (matcher.find()) {
            numberParts.add(matcher.group());
        }

        // convert the number parts to doubles
        final double[] numbers = numberParts.stream().mapToDouble(Double::parseDouble).toArray();

        // then, check if any of those parts are close to a blacklisted coordinate
        for (int i = 0; i < numbers.length; i++) {
            final double number = numbers[i];
            for (final double blacklistedCoord : blacklist) {
                if (relativeDifference(number, blacklistedCoord) < 0.1) {
                    // if so, redact the number with a random number of Xs between 1 and 3
                    message = message.replace(numberParts.get(i), "X".repeat(RANDOM.nextInt(3) + 1));
                }
            }
        }

        return message;
    }

    static boolean messageContainsCoords(final String message, final double[] blacklist) {
        // first, get all parts of the string that contain a number
        final var matcher = NUMBER_PATTERN.matcher(message);
        final List<String> numberParts = new ObjectArrayList<>();
        while (matcher.find()) {
            numberParts.add(matcher.group());
        }

        // convert the number parts to doubles
        final double[] numbers = numberParts.stream().mapToDouble(Double::parseDouble).toArray();

        // then, check if any of those parts are close to a blacklisted coordinate
        for (final double number : numbers) {
            // use a higher sensitivity for coords with 3 digits
            final double sensitivity = number < 999 && number > -999 ? 0.2 : 0.1;

            for (final double blacklistedCoord : blacklist) {
                if (relativeDifference(number, blacklistedCoord) <= sensitivity)
                    return true;
            }
        }

        return false;
    }

    /**
     * Calculates the relative difference between two doubles.
     * @param a The first double to compare
     * @param b The second double to compare
     * @return 0 if the doubles are equal, 1 if they are completely different
     */
    private static double relativeDifference(final double a, final double b) {
        return Math.abs(a - b) / ((a + b) / 2.0);
    }
}
