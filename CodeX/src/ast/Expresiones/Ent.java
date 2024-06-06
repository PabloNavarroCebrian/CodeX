package ast.Expresiones;

import ast.Tipos.IntXType;

public class Ent extends E {

    private int n;

    public Ent(String n) {
        this.n = Integer.parseInt(n);
    }

    public int getEnt() {
        return n;
    }

    @Override
    public KindE kind() {
        return KindE.ENT;
    }

    @Override
    public String toString() {
        return Integer.toString(n);
    }

    @Override
    public void bind() {
    }

    @Override
    public void checkType() {
        this.type = new IntXType();
    }

    @Override
    public String generateCode() {
        return "i32.const " + this.n + "\n";
    }
}