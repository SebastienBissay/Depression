package parameters;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class Parameters {
    public static final long SEED = 11021985;
    public static final int WIDTH = 2000;
    public static final int HEIGHT = 2000;
    public static final int NUMBER_OF_ITERATIONS = 20000;
    public static final float BEZIER_FIRST_CONTROL_POINT_OFFSET = 1.5f;
    public static final float BEZIER_SECOND_CONTROL_POINT_OFFSET = -.5f;
    public static final float BEZIER_GAUSSIAN_FACTOR = .5f;
    public static final float CHANCE_OF_CIRCLE = .01f;
    public static final float CIRCLE_RADIUS_FACTOR = 1 / 250f;
    public static final Color BACKGROUND_COLOR = new Color(240);
    public static final Color STROKE_COLOR = new Color(5, 10, 15, 10);
    public static final Color FILL_COLOR = new Color(5, 10, 15, 20);

    /**
     * Helper method to extract the constants in order to save them to a json file
     *
     * @return a Map of the constants (name -> value)
     */
    public static Map<String, ?> toJsonMap() throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();

        Field[] declaredFields = Parameters.class.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(Parameters.class));
        }

        return Collections.singletonMap(Parameters.class.getSimpleName(), map);
    }

    public record Color(float red, float green, float blue, float alpha) {
        public Color(float red, float green, float blue) {
            this(red, green, blue, 255);
        }

        public Color(float grayscale, float alpha) {
            this(grayscale, grayscale, grayscale, alpha);
        }

        public Color(float grayscale) {
            this(grayscale, 255);
        }

        public Color(String hexCode) {
            this(decode(hexCode));
        }

        public Color(Color color) {
            this(color.red, color.green, color.blue, color.alpha);
        }

        public static Color decode(String hexCode) {
            return switch (hexCode.length()) {
                case 2 -> new Color(Integer.valueOf(hexCode, 16));
                case 4 -> new Color(Integer.valueOf(hexCode.substring(0, 2), 16),
                        Integer.valueOf(hexCode.substring(2, 4), 16));
                case 6 -> new Color(Integer.valueOf(hexCode.substring(0, 2), 16),
                        Integer.valueOf(hexCode.substring(2, 4), 16),
                        Integer.valueOf(hexCode.substring(4, 6), 16));
                case 8 -> new Color(Integer.valueOf(hexCode.substring(0, 2), 16),
                        Integer.valueOf(hexCode.substring(2, 4), 16),
                        Integer.valueOf(hexCode.substring(4, 6), 16),
                        Integer.valueOf(hexCode.substring(6, 8), 16));
                default -> throw new IllegalArgumentException();
            };
        }
    }
}
