package ast.Instrucciones;

import java.util.ArrayList;

import ast.Expresiones.E;
import ast.Expresiones.ExpLlamadaFun;

public class LlamadaFunInst extends Instruction {

    private ExpLlamadaFun llamadaFunExp;

    public LlamadaFunInst(String nombreFun, ArrayList<E> args) {
        this.llamadaFunExp = new ExpLlamadaFun(nombreFun, args);
    }

    @Override
    public KindI kind() {
        return KindI.LLAMADAFUN;
    }

    @Override
    public String toString() {
        return this.llamadaFunExp.toString();
    }

    @Override
    public void bind() {
        this.llamadaFunExp.bind();
    }

    @Override
    public void checkType() {
        this.llamadaFunExp.checkType();
        this.type = this.llamadaFunExp.getType();
    }

    @Override
    public int computeDelta(int totalDelta) {

        this.llamadaFunExp.computeDelta(totalDelta);
        
        return totalDelta;
    }

    @Override
    public String generateCode() {
        return this.llamadaFunExp.generateCode();
    }

    @Override
    public void updateType() {
        this.llamadaFunExp.updateType();
    }
}