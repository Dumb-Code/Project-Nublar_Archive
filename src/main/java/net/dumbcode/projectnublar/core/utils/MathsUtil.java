package net.dumbcode.projectnublar.core.utils;

import net.dumbcode.projectnublar.core.exceptions.UtilityClassException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class MathsUtil {
    private MathsUtil() {
        throw new UtilityClassException();
    }

    public static double fastInvSqrt(double x) {
        double xhalf = 0.5d * x;
        long i = Double.doubleToLongBits(x);
        i = 0x5fe6ec85e7de30daL - (i >> 1);
        x = Double.longBitsToDouble(i);
        x *= (1.5d - xhalf * x * x);
        return x;
    }

    public static double fastInvSqrt(double x, int iterations) {
        double xhalf = 0.5d * x;
        long i = Double.doubleToLongBits(x);
        i = 0x5fe6ec85e7de30daL - (i >> 1);
        x = Double.longBitsToDouble(i);
        for (int j = 0; j < iterations; j++) {
            x *= (1.5d - xhalf * x * x);
        }
        return x;
    }

    public static float fastInvSqrt(float x) {
        float xhalf = 0.5f * x;
        int i = Float.floatToIntBits(x);
        i = 0x5f3759df - (i >> 1);
        x = Float.intBitsToFloat(i);
        x *= (1.5f - xhalf * x * x);
        return x;
    }

    public static float fastInvSqrt(float x, int iterations) {
        float xhalf = 0.5f * x;
        int i = Float.floatToIntBits(x);
        i = 0x5f3759df - (i >> 1);
        x = Float.intBitsToFloat(i);
        for (int j = 0; j < iterations; j++) {
            x *= (1.5f - xhalf * x * x);
        }
        return x;
    }

    public static double fastSqrt(double x) {
        return 1.0 / fastInvSqrt(x);
    }

    public static double fastSqrt(double x, int iterations) {
        return 1.0 / fastInvSqrt(x, iterations);
    }

    public static float fastSqrt(float x) {
        return 1.0f / fastInvSqrt(x);
    }

    public static float fastSqrt(float x, int iterations) {
        return 1.0f / fastInvSqrt(x, iterations);
    }

    public static double fastPythagorean(double x, double y) {
        return fastSqrt(x * x + y * y);
    }

    public static double fastPythagorean(double x, double y, int iterations) {
        return fastSqrt(x * x + y * y, iterations);
    }

    public static float fastPythagorean(float x, float y) {
        return fastSqrt(x * x + y * y);
    }

    public static float fastPythagorean(float x, float y, int iterations) {
        return fastSqrt(x * x + y * y, iterations);
    }

    public static double fastPythagorean(double x, double y, double z) {
        return fastSqrt(x * x + y * y + z * z);
    }

    public static double fastPythagorean(double x, double y, double z, int iterations) {
        return fastSqrt(x * x + y * y + z * z, iterations);
    }

    public static float fastPythagorean(float x, float y, float z) {
        return fastSqrt(x * x + y * y + z * z);
    }

    public static float fastPythagorean(float x, float y, float z, int iterations) {
        return fastSqrt(x * x + y * y + z * z, iterations);
    }

    public static double fastPythagorean(double x, double y, double z, double w) {
        return fastSqrt(x * x + y * y + z * z + w * w);
    }

    public static double fastPythagorean(double x, double y, double z, double w, int iterations) {
        return fastSqrt(x * x + y * y + z * z + w * w, iterations);
    }

    public static float fastPythagorean(float x, float y, float z, float w) {
        return fastSqrt(x * x + y * y + z * z + w * w);
    }

    public static float fastPythagorean(float x, float y, float z, float w, int iterations) {
        return fastSqrt(x * x + y * y + z * z + w * w, iterations);
    }

    public static double fastPythagorean(int iterations, double @NotNull ... values) {
        double sum = 0;
        for (double value : values) {
            sum += value * value;
        }
        return fastSqrt(sum, iterations);
    }

    public static float fastPythagorean(int iterations, float @NotNull ... values) {
        float sum = 0;
        for (float value : values) {
            sum += value * value;
        }
        return fastSqrt(sum, iterations);
    }

    public static double fastPythagorean(double @NotNull ... values) {
        double sum = 0;
        for (double value : values) {
            sum += value * value;
        }
        return fastSqrt(sum);
    }

    public static float fastPythagorean(float @NotNull ... values) {
        float sum = 0;
        for (float value : values) {
            sum += value * value;
        }
        return fastSqrt(sum);
    }

    public static double pythagorean(double x, double y) {
        return Math.sqrt(x * x + y * y);
    }

    public static double pythagorean(double x, double y, double z) {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public static double pythagorean(double x, double y, double z, double w) {
        return Math.sqrt(x * x + y * y + z * z + w * w);
    }

    @Contract(pure = true)
    public static double pythagorean(double @NotNull ... values) {
        double sum = 0;
        for (double value : values) {
            sum += value * value;
        }
        return Math.sqrt(sum);
    }
}
