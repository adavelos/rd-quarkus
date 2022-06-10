package org.argonath.rd.entry.it;

import java.util.concurrent.ThreadLocalRandom;

public class RandomNumber {

    /**
     * Returns an integer in the range: [origin, bound)
     * The class uses a {@link ThreadLocalRandom}. The {@link java.util.Random} class although thread safe, can cause contention if multiple
     * parallel threads use it.
     *
     * @param origin the lower bound (inclusive)
     * @param bound  the upper bound (exclusive)
     * @return a random integer
     */
    public static Integer getInteger(int origin, int bound) {
        int r = ThreadLocalRandom.current().nextInt(origin, bound);
        return r;
    }

    public static Long getLong(Long origin, Long bound) {
        Long r = ThreadLocalRandom.current().nextLong(origin, bound);
        return r;
    }

    public static Double getGaussian() {
        double d = ThreadLocalRandom.current().nextGaussian();
        return d;
    }

    public static boolean throwDice() {
        return RandomNumber.getInteger(0, 2) == 0;
    }
}
