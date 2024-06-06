package ast.Declaraciones;

import java.util.ArrayList;

import ast.Tipos.Type;

public class Parametro extends DecVar {

    private boolean byValue;

    public Parametro(Type typeParam, Boolean byValue, String nombre) {
        super(typeParam, nombre);
        this.byValue = byValue.booleanValue();
        this.isGlobal = false;
    }

    public boolean byValue() {
        return this.byValue;
    }

    @Override
    public String toString() {
        String ampersand = "";
        if (!this.byValue) {
            ampersand = "&";
        }
        return this.kind().toString() + "(" + this.type.toString() + ", " + ampersand + ", " + this.nombre + ")";
    }

    @Override
    public KindD kind() {
        return KindD.DECPARAMETRO;
    }

    @Override
    public int computeDelta(int totalDelta) {

        this.delta = totalDelta;

        if(this.byValue)
            return totalDelta + this.type.getSize();
        else
            return totalDelta + 1;
    }

    @Override
    public ArrayList<Integer> maxMemory(int maxMemory, int currentMemory) {

        if(this.byValue)
            currentMemory += this.type.getSize();
        else
            currentMemory += 1;

        if(currentMemory > maxMemory)
            maxMemory = currentMemory;

        ArrayList<Integer> nodeMem = new ArrayList<Integer>();
        nodeMem.add(new Integer(maxMemory));
        nodeMem.add(new Integer(currentMemory));

        return nodeMem;
    }

    @Override
    public String generateDirCode() {

        StringBuilder codigo = new StringBuilder();
        codigo.append(super.generateDirCode());
        if(!this.byValue) {
            codigo.append("i32.load\n");
        }
        
        return codigo.toString();
    }
}