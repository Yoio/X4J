package io.x4j.typeparser;

import static io.x4j.typeparser.DynamicParser.TRY_NEXT;
import static io.x4j.typeparser.Util.formatErrorMessage;
import static io.x4j.typeparser.Util.makeNullArgumentErrorMsg;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The purpose of this class is to parse a simple string (read from a properties file
 * or system property for example) and convert it to a specific java object/Type.
 * For example converting "1" to an {@code Integer} type, or "1,2,3" to a {@code List<Integer>}
 * type.
 * 
 * @see <a href="https://github.com/drapostolos/type-parser/wiki/User-Guide"
 *      target="_blank">User-Guide</a>
 */
public final class TypeParser {

    final Map<Type, Parser<?>> parsers;
    final List<DynamicParser> dynamicParsers;
    final SplitStrategy splitStrategy;
    final SplitStrategy keyValueSplitStrategy;
    final InputPreprocessor inputPreprocessor;
    final NullStringStrategy nullStringStrategy;

    TypeParser(TypeParserBuilder builder) {
        parsers = new HashMap<Type, Parser<?>>(builder.parsers);
        dynamicParsers = new ArrayList<DynamicParser>(builder.clientProvidedDynamicParsers);
        dynamicParsers.addAll(builder.defaultDynamicParsers);
        splitStrategy = builder.splitStrategy;
        keyValueSplitStrategy = builder.keyValueSplitStrategy;
        inputPreprocessor = builder.inputPreprocessor;
        nullStringStrategy = builder.nullStringStrategy;
    }

    /**
     * Constructs a new instance of {@link TypeParserBuilder}.
     * 
     * @return a new instance of {@link TypeParserBuilder}.
     */
    public static TypeParserBuilder newBuilder() {
        return new TypeParserBuilder();
    }

    /**
     * Parses the given {@code input} string to the given {@code targetType}.
     * <p/>
     * Example: <br/>
     * <code>
     * TypeParser parser = TypeParser.newBuilder().build();<br/>
     * Integer i = parser.parse("1", Integer.class);
     * </code>
     * 
     * @param input - string value to parse
     * @param targetType - the expected type to convert {@code input} to.
     * @return an instance of {@code targetType} corresponding to the given {@code input}.
     * @throws NullPointerException if any given argument is {@code null}.
     * @throws TypeParserException if anything goes wrong while parsing {@code input} to the given
     *         {@code targetType}.
     * @throws NoSuchRegisteredParserException if there is no registered {@link Parser} for the
     *         given {@code targetType}.
     */
    public <T> T parse(String input, Class<T> targetType) {
        if (input == null) {
            throw new NullPointerException(makeNullArgumentErrorMsg("input"));
        }
        if (targetType == null) {
            throw new NullPointerException(makeNullArgumentErrorMsg("targetType"));
        }

        @SuppressWarnings("unchecked")
        T temp = (T) parseType2(input, new TargetType(targetType));
        return temp;

    }

    /**
     * Parses the given {@code input} string to the given {@code genericType}.
     * <p/>
     * Example: <br/>
     * <code>
     * TypeParser parser = TypeParser.newBuilder().build();<br/>
     * {@code List<Integer>} list = parser.parse("1, 2", new {@code GenericType<List<Integer>>}() {});
     * </code><br/>
     * Note the ending "{}".
     * 
     * @param input - string value to parse.
     * @param genericType - the expected generic type to convert {@code input} to.
     * @return an instance of {@code genericType} corresponding to the given {@code input}.
     * @throws NullPointerException if any given argument is {@code null}.
     * @throws TypeParserException if anything goes wrong while parsing {@code input} to the given
     *         {@code targetType}.
     * @throws NoSuchRegisteredParserException if there is no registered {@link Parser} for the
     *         given {@code targetType}.
     */
    public <T> T parse(String input, GenericType<T> genericType) {
        if (input == null) {
            throw new NullPointerException(makeNullArgumentErrorMsg("input"));
        }
        if (genericType == null) {
            throw new NullPointerException(makeNullArgumentErrorMsg("genericType"));
        }

        @SuppressWarnings("unchecked")
        T temp = (T) parseType2(input, new TargetType(genericType.getType()));
        return temp;
    }

    /**
     * Parses the given {@code input} string to the given {@code targetType}.
     * <p/>
     * 
     * @param input - string value to parse.
     * @param targetType - the expected type to convert {@code input} to.
     * @return an instance of {@code targetType} corresponding to the given {@code input}.
     * @throws NullPointerException if any given argument is {@code null}.
     * @throws TypeParserException if anything goes wrong while parsing {@code input} to the given
     *         {@code targetType}.
     * @throws NoSuchRegisteredParserException if there is no registered {@link Parser} for the
     *         given {@code targetType}.
     */
    public Object parseType(String input, Type targetType) {
        if (input == null) {
            throw new NullPointerException(makeNullArgumentErrorMsg("input"));
        }
        if (targetType == null) {
            throw new NullPointerException(makeNullArgumentErrorMsg("targetType"));
        }

        return parseType2(input, new TargetType(targetType));

    }

    private Object parseType2(final String input, final TargetType targetType) {
        String preprocessedInput = null;
        try {
            preprocessedInput = preProcessInputString(input, targetType);
            return invokeParser(targetType, preprocessedInput);
        } catch (TypeParserException e) {
            // Re-throw as is (already contains context message)
            throw e;
        } catch (NoSuchRegisteredParserException e) {
            // prepend original error message with context (i.e. input and targetType)
            String message = formatErrorMessage(input, preprocessedInput, targetType, e.getMessage());
            throw new NoSuchRegisteredParserException(message);
        } catch (NumberFormatException e) {
            // Improve NumberFormatException error message and wrap it in a TypeParserException.
            // Also provide context (i.e. input and targetType)
            String message = "NumberFormatException " + e.getMessage();
            message = formatErrorMessage(input, preprocessedInput, targetType, message);
            throw new TypeParserException(message, e);
        } catch (Throwable t) {
            // Something unexpected happen. Wrap it in a TypeParserException
            // and provide context (i.e. input and targetType)
            String message = formatErrorMessage(input, preprocessedInput, targetType, t.getMessage());
            throw new TypeParserException(message, t);
        }
    }

    private String preProcessInputString(String input, TargetType targetType) {
        String result = inputPreprocessor.prepare(input, new InputPreprocessorHelper(targetType, this));
        if (result == null) {
            String message = "InputPreprocessor.prepare(...) method returned a null object "
                    + "when its contract states an actual String must be returned.";
            throw new UnsupportedOperationException(message);
        }
        return result;
    }

    private Object invokeParser(TargetType targetType, String preprocessedInput) {
        ParserHelper helper = new ParserHelper(targetType, this);

        if (parsers.containsKey(targetType.targetType())) {
            Parser<?> parser = parsers.get(targetType.targetType());
            return parser.parse(preprocessedInput, helper);
        }

        for (DynamicParser dynamicParser : dynamicParsers) {
            Object result = dynamicParser.parse(preprocessedInput, helper);
            if (result != TRY_NEXT) {
                return result;
            }
        }

        throw new NoSuchRegisteredParserException("There is no registered 'Parser' for that type.");
    }
}
