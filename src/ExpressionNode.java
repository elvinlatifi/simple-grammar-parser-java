public class ExpressionNode implements INode {
    private TermNode termNode;
    private Lexeme operator;
    private ExpressionNode expressionNode;

    public ExpressionNode(Tokenizer tokenizer) {
        try {
            termNode = new TermNode(tokenizer);
            if (tokenizer.current().token() == Token.ADD_OP || tokenizer.current().token() == Token.SUB_OP) {
                operator = tokenizer.current();
                tokenizer.moveNext();
                expressionNode = new ExpressionNode(tokenizer);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Object evaluate(Object[] args) throws Exception {
        Double result = 0.0;
        if (operator != null) {
            if (operator.token() == Token.ADD_OP)
                result = (Double)termNode.evaluate(null) + (Double)expressionNode.evaluate(null);
            else
                result = (Double)termNode.evaluate(null) - (Double)expressionNode.evaluate(null);
        }
        else
            result = (Double)termNode.evaluate(null);
        return result;
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {
        builder.append("\t".repeat(tabs)).append("ExpressionNode\n");
        termNode.buildString(builder, tabs + 1);
        if (operator != null) {
            builder.append("\t".repeat(tabs + 1)).append(operator.toString()).append("\n");
            expressionNode.buildString(builder, tabs + 1);
        }
    }
}
