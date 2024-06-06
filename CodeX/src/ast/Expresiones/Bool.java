package ast.Expresiones;

import ast.Tipos.BoolXType;

public class Bool extends E {

    private boolean b;

    public Bool(Boolean v) {
        this.b = v.booleanValue();
    }

    public Boolean bool() {
        return b;
    }

    @Override
    public KindE kind() {
        return KindE.BOOL;
    }

    @Override
    public String toString() {
        return Boolean.toString(b);
    }

    @Override
    public void bind() {}

    @Override
    public void checkType() {
        this.type = new BoolXType();
    }

    @Override
    public String generateCode() {
        String valor;
        if(b)
            valor = "1";
        else
            valor = "0";

        return "i32.const " + valor + "\n";
    }
}