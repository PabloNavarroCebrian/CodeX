package ast.Tipos;

import ast.Expresiones.E;

public class IntXType extends Type {

    @Override
    public KindT kind() {
        return KindT.INTX;
    }

    @Override
    public String toString() {
        return this.kind().toString();
    }

    @Override
    public boolean equals(Type type) {
        return type.kind() == KindT.INTX;
    }

    @Override
    public void checkInitialization(E exp) {

        Type expType = exp.getType();

        if(!this.equals(expType))
            throw new UnsupportedOperationException("ERROR: ¡Intentando inicializar un IntX con una expresion que no es de tipo IntX!");
    }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    protected String generateInitializationCode(E exp, int delta) {

        StringBuilder codigo = new StringBuilder();
        codigo.append("local.get $tempI\n");
        codigo.append("i32.const " + delta + "\n");
        codigo.append("i32.add\n");
        codigo.append(exp.generateCode());
        codigo.append("i32.store\n");

        return codigo.toString();
    }
}
