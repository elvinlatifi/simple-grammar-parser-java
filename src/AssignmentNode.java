public class AssignmentNode implements INode {
    private Lexeme identifier;
    private Lexeme assignOperator;
    private ExpressionNode expressionNode;
    private Lexeme semicolon;


    public AssignmentNode(Tokenizer tokenizer) {
        try {
            identifier = tokenizer.current();
            verifyLexeme(identifier, Token.IDENT);
            tokenizer.moveNext();
            assignOperator = tokenizer.current();
            verifyLexeme(assignOperator, Token.ASSIGN_OP);
            tokenizer.moveNext();
            expressionNode = new ExpressionNode(tokenizer);
            semicolon = tokenizer.current();
            verifyLexeme(semicolon, Token.SEMICOLON);
            tokenizer.moveNext();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void verifyLexeme(Lexeme lexemeToCheck, Token correctToken) throws ParserException {
        if (lexemeToCheck.token() != correctToken)
            throw new ParserException("Wrong token type! Must be " + correctToken);
    }

    @Override
    public Object evaluate(Object[] args) throws Exception {
        return identifier.value() +  " " + assignOperator.value() + " " + expressionNode.evaluate(null);
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {
        builder.append("\t".repeat(tabs)).append("AssignmentNode\n");
        builder.append("\t".repeat(tabs + 1)).append(identifier.toString()).append("\n");
        builder.append("\t".repeat(tabs + 1)).append(assignOperator.toString()).append("\n");
        expressionNode.buildString(builder, tabs + 1);
        builder.append("\t".repeat(tabs + 1)).append(semicolon.toString()).append("\n");
    }
}
