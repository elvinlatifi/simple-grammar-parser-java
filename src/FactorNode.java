public class FactorNode implements INode {
    private Lexeme integer;
    private Lexeme leftParen;
    private ExpressionNode expressionNode;
    private Lexeme rightParen;

    public FactorNode(Tokenizer tokenizer) {
        try {
            if (tokenizer.current().token() == Token.INT_LIT) {
                integer = tokenizer.current();
                tokenizer.moveNext();
            }
            else if (tokenizer.current().token() == Token.LEFT_PAREN) {
                leftParen = tokenizer.current();
                tokenizer.moveNext();
                expressionNode = new ExpressionNode(tokenizer);
                rightParen = tokenizer.current();
                if (rightParen.token() != Token.RIGHT_PAREN)
                    throw new ParserException("Wrong token type, must be right parentheses.");
                tokenizer.moveNext();
            }
            else {
                throw new ParserException("Wrong token type! Must be either integer or expression within parentheses.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Object evaluate(Object[] args) throws Exception {
        Double result = 0.0;
        if (expressionNode != null)
            result = (Double)expressionNode.evaluate(null);
        else
            result = Double.parseDouble((String)integer.value());
        return result;
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {
        builder.append("\t".repeat(tabs)).append("FactorNode\n");
        if (expressionNode != null) {
            builder.append("\t".repeat(tabs + 1)).append(leftParen.toString()).append("\n");
            expressionNode.buildString(builder, tabs + 1);
            builder.append("\t".repeat(tabs + 1)).append(rightParen.toString()).append("\n");
        }
        else
            builder.append("\t".repeat(tabs + 1)).append(integer.toString()).append("\n");
    }
}
