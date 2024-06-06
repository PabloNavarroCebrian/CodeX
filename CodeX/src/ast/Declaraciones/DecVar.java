package ast.Declaraciones;

import java.util.ArrayList;

import ast.Programa;
import ast.Expresiones.E;
import ast.Tipos.Type;

public class DecVar extends Declaracion {

    private E exp = null;
    protected boolean isGlobal = true;
    protected int delta;

    public DecVar(Type type, String nombre) {
        this.nombre = nombre;
        this.type = type;
    }

    public DecVar(Type type, String nombre, E exp) {
        this(type, nombre);
        this.exp = exp;
    }

    @Override
    public String toString() {
        if(this.exp == null)
            return this.kind().toString() + "(" + this.type.toString() + ", " + this.nombre + ")";
        else
            return this.kind().toString() + "(" + this.type.toString() + ", " + this.nombre + ", " + this.exp.toString() + ")";
    }

    @Override
    public KindD kind() {
        return KindD.DECVAR;
    }

    @Override
    public void bind() {
        this.type.bind();
        Programa.pila.insertaId(nombre, this);
        if(this.exp != null)
            this.exp.bind();
    }

    @Override
    public void checkType() {

        this.type.checkType();

        if(this.exp != null) {
            this.exp.checkType();
            this.type.checkInitialization(this.exp);
        }
    }

    @Override
    public int computeDelta(int totalDelta) {

        this.delta = totalDelta;

        return totalDelta + this.type.getSize();
    }

    public void setIsGlobal(boolean isGlobal) {
        this.isGlobal = isGlobal;
    }

    @Override
    public ArrayList<Integer> maxMemory(int maxMemory, int currentMemory) {

        currentMemory += this.type.getSize();
        if(currentMemory > maxMemory)
            maxMemory = currentMemory;

        ArrayList<Integer> nodeMem = new ArrayList<Integer>();
        nodeMem.add(new Integer(maxMemory));
        nodeMem.add(new Integer(currentMemory));

        return nodeMem;
    }

    @Override
    public int getDelta() {
        return this.delta;
    }

    public boolean isGlobal() {
        return this.isGlobal;
    }

    public String generateDirCode() {

        StringBuilder codigo = new StringBuilder();
        codigo.append("i32.const " + this.delta * 4 + "\n");
        if(this.isGlobal)
            codigo.append("i32.const 0\n");
        else
            codigo.append("local.get $localStart\n");

        codigo.append("i32.add\n");

        return codigo.toString();
    }

    @Override
    public String generateCode() {

        return this.exp != null ? this.type.generateInitializationCode(exp, this) : "";
    }

    @Override
    public void updateType() {
        this.type.updateType();
        this.type = this.type.getType();

        if(this.exp != null)
            this.exp.updateType();
    }
}