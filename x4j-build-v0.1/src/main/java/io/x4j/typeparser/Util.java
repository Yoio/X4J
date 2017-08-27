package io.x4j.typeparser;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

final class Util {

    private static final SplitStrategy DEFAULT_SPLIT_STRATEGY = new SplitStrategy() {

        @Override
        public List<String> split(String input, SplitStrategyHelper helper) {
            return Arrays.asList(input.split(","));
        }
    };
    private static final SplitStrategy DEFAULT_KEY_VALUE_SPLIT_STRATEGY = new SplitStrategy() {

        @Override
        public List<String> split(String input, SplitStrategyHelper helper) {
            return Arrays.asList(input.split("=", 2));
        }
    };
    private static final InputPreprocessor DEFAULT_INPUT_PREPROCESSOR = new InputPreprocessor() {

        @Override
        public String prepare(String input, InputPreprocessorHelper helper) {
            return input;
        }
    };
    private static final NullStringStrategy DEFAULT_NULL_STRING_STRATEGY = new NullStringStrategy() {

        @Override
        public boolean isNullString(String input, NullStringStrategyHelper helper) {
            return "null".equalsIgnoreCase(input.trim());
        }
    };

    private Util() {
        throw new AssertionError("Not meant for instantiation");
    }

    static InputPreprocessor defaultInputPreprocessor() {
        return DEFAULT_INPUT_PREPROCESSOR;
    }

    static SplitStrategy defaultSplitStrategy() {
        return DEFAULT_SPLIT_STRATEGY;
    }

    static SplitStrategy defaultKeyValueSplitStrategy() {
        return DEFAULT_KEY_VALUE_SPLIT_STRATEGY;
    }

    static NullStringStrategy defaultNullStringStrategy() {
        return DEFAULT_NULL_STRING_STRATEGY;
    }

    static String makeNullArgumentErrorMsg(String argName) {
        return String.format("Argument named '%s' is illegally set to null!", argName);
    }

    static <T> Parser<T> decorateParser(Type targetType, final Parser<T> parser) {
        if (targetType instanceof Class) {
            Class<?> c = (Class<?>) targetType;
            if (c.isPrimitive()) {
                return new Parser<T>() {

                    @Override
                    public T parse(String input, ParserHelper helper) {
                        if (helper.isNullString(input)) {
                            throw new UnsupportedOperationException("Primitive can not be set to null");
                        }
                        return parser.parse(input, helper);
                    }
                };
            }
        }

        return new Parser<T>() {
            @Override
            public T parse(String input, ParserHelper helper) {
                if (helper.isNullString(input)) {
                    return null;
                }
                return parser.parse(input, helper);
            }
        };
    }

    /**
     * @param o
     * @return %s {instance of: %s}
     */
    static String objectToString(Object o) {
        return String.format("%s {instance of: %s}", o, o.getClass());
    }

    static String formatErrorMessage(String input, String preprocessed, TargetType targetType, String message) {
        String messageTemplate = "\n\t"
                + "Can not parse \"%s\" {preprocessed: %s} "
                + "to type \"%s\" {instance of: %s} \n\t"
                + "due to: %s";
        String preprocessedInput = formatPreprocessedInput(preprocessed);
        String targetTypeName = getTargetTypeName(targetType.targetType());
        String className = targetType.targetType().getClass().getName();
        return String.format(messageTemplate,
                input, preprocessedInput, targetTypeName, className, message);
    }

    private static String formatPreprocessedInput(String preprocessedInput) {
        if (preprocessedInput == null) {
            return null;
        }
        return String.format("\"%s\"", preprocessedInput);
    }

    private static String getTargetTypeName(Type targetType) {
        if (targetType instanceof Class) {
            Class<?> c = (Class<?>) targetType;
            return c.getCanonicalName();
        }
        return targetType.toString();
    }
}
