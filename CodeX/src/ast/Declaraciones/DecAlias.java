package ast.Declaraciones;

import java.util.ArrayList;

import ast.Programa;
import ast.Tipos.Type;

public class DecAlias extends Declaracion {

    public DecAlias(String nombre, Type tipo) {
        this.nombre = nombre;
        this.type = tipo;
    }

    @Override 
    public String toString() {
        return this.kind().toString() + "(" + this.nombre + ", " + this.type.toString() + ")";
    }

    @Override
    public void checkType() {}

    @Override
    public void bind() {
        this.type.bind();
        Programa.pila.insertaId(this.nombre, this);

    }

    @Override
    public String generateCode() {
        return "";
    }

    @Override
    public int computeDelta(int totalDelta) {
        return totalDelta;
    }

    @Override
    public ArrayList<Integer> maxMemory(int maxMemory, int currentMemory) {

        ArrayList<Integer> nodeMem = new ArrayList<Integer>();
        nodeMem.add(new Integer(maxMemory));
        nodeMem.add(new Integer(currentMemory));

        return nodeMem;
    }

    @Override
    public KindD kind() {
        return KindD.DECALIAS;
    }

    @Override
    public void updateType() {
        this.type.updateType();
        this.type = this.type.getType();
    }
}