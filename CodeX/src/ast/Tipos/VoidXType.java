package ast.Tipos;

import ast.Expresiones.E;

public class VoidXType extends Type {

    @Override
    public KindT kind() {
        return KindT.VOIDX;
    }

    @Override
    public String toString() {
        return this.kind().toString();
    }

    @Override
    public boolean equals(Type type) {
        return type.kind() == KindT.VOIDX;
    }

    @Override
    public void checkInitialization(E exp) {
        throw new UnsupportedOperationException("ERROR: ¡No se puede inicilizar un tipo VoidX!");
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    protected String generateInitializationCode(E exp, int delta) { return ""; }
}