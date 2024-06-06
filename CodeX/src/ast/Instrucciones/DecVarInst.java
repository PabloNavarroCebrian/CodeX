package ast.Instrucciones;

import java.util.ArrayList;

import ast.Declaraciones.DecVar;
import ast.Expresiones.E;
import ast.Tipos.Type;

public class DecVarInst extends Instruction {

    private DecVar dec;
    
    public DecVarInst(Type tipo, String nombre) {
        this.dec = new DecVar(tipo, nombre);
        this.dec.setIsGlobal(false);
    }

    public DecVarInst(Type tipo, String nombre, E exp) {
        this.dec = new DecVar(tipo, nombre, exp);
        this.dec.setIsGlobal(false);
    }

    @Override
    public KindI kind() {
        return KindI.DECVAR;
    }

    @Override
    public String toString() {
        return this.dec.toString();
    }

    @Override
    public void bind() {
        this.dec.bind();
    }

    @Override
    public void checkType() {
        this.dec.checkType();
    }

    @Override
    public int computeDelta(int totalDelta) {
        return dec.computeDelta(totalDelta);
    }

    @Override
    public ArrayList<Integer> maxMemory(int maxMemory, int currentMemory) {

        return this.dec.maxMemory(maxMemory, currentMemory);
    }

    @Override
    public String generateCode() {
        return this.dec.generateCode();
    }

    @Override
    public void updateType() {
        this.dec.updateType();
    }
    
}
