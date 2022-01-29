import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class Tokenizer implements ITokenizer {
    private static final Map<Character, Token> symbols = new HashMap<>();

    private Scanner scanner;
    private Lexeme current;
    private Lexeme next;

    public Tokenizer() {
        symbols.put(';', Token.SEMICOLON);
        symbols.put('+', Token.ADD_OP);
        symbols.put('-', Token.SUB_OP);
        symbols.put('*', Token.MULT_OP);
        symbols.put('/', Token.DIV_OP);
        symbols.put('=', Token.ASSIGN_OP);
        symbols.put('(', Token.LEFT_PAREN);
        symbols.put(')', Token.RIGHT_PAREN);
        symbols.put('{', Token.LEFT_CURLY);
        symbols.put('}', Token.RIGHT_CURLY);
    }

    @Override
    public void open(String fileName) throws IOException, TokenizerException {
        scanner = new Scanner();
        scanner.open(fileName);
        scanner.moveNext();
        next = extractLexeme();
    }

    @Override
    public Lexeme current() {
        return current;
    }

    @Override
    public void moveNext() throws IOException, TokenizerException {
        if (scanner == null)
            throw new IOException("No open file.");
        current = next;
        if (next.token() != Token.EOF)
            next = extractLexeme();
    }

    private void consumeWhiteSpaces() throws IOException {
        while (Character.isWhitespace(scanner.current())){
            scanner.moveNext();
        }
    }

    private Lexeme extractLexeme() throws IOException, TokenizerException {
        consumeWhiteSpaces();
        Character ch = scanner.current();
        StringBuilder strBuilder = new StringBuilder();

        if (ch == Scanner.EOF)
            return new Lexeme(ch, Token.EOF);

        else if (Character.isLetter(ch)) {
            while (Character.isLetter(scanner.current())) {
                strBuilder.append(scanner.current());
                scanner.moveNext();
            }
            String identifier = strBuilder.toString();
            return new Lexeme(identifier, Token.IDENT);
        }

        else if (Character.isDigit(ch)) {
            while (Character.isDigit(scanner.current())) {
                strBuilder.append(scanner.current());
                scanner.moveNext();
            }
            String digit = strBuilder.toString();
            return new Lexeme(digit, Token.INT_LIT);
        }

        else if (symbols.containsKey(ch)) {
            scanner.moveNext();
            return new Lexeme(ch, symbols.get(ch));
        }

        else
            throw new TokenizerException("Unknown character: " + ch);
    }

    @Override
    public void close() throws IOException {
        if (scanner != null)
            scanner.close();
    }
}
