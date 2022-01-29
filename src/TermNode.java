public class TermNode implements INode {
    private FactorNode factorNode;
    private Lexeme operator;
    private TermNode termNode;

    public TermNode(Tokenizer tokenizer) {
        try {
            factorNode = new FactorNode(tokenizer);
            if (tokenizer.current().token() == Token.MULT_OP || tokenizer.current().token() == Token.DIV_OP) {
                operator = tokenizer.current();
                tokenizer.moveNext();
                termNode = new TermNode(tokenizer);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Object evaluate(Object[] args) throws Exception {
        Double result = 0.0;
        if (operator != null) {
            if (operator.token() == Token.MULT_OP)
                result = (Double)factorNode.evaluate(null) * (Double)termNode.evaluate(null);
            else
                result = (Double)factorNode.evaluate(null) / (Double)termNode.evaluate(null);
        }
        else
            result = (Double)factorNode.evaluate(null);
        return result;
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {
        builder.append("\t".repeat(tabs)).append("TermNode\n");
        factorNode.buildString(builder, tabs + 1);
        if (operator != null) {
            builder.append("\t".repeat(tabs + 1)).append(operator.toString()).append("\n");
            termNode.buildString(builder, tabs + 1);
        }
    }
}
