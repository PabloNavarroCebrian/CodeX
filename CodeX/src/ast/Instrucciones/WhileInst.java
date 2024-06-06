package ast.Instrucciones;

import java.util.ArrayList;
import java.util.Collections;

import ast.Programa;
import ast.Expresiones.E;
import ast.Tipos.BoolXType;
import ast.Tipos.Type;

public class WhileInst extends Instruction {

    private E cond;
    private ArrayList<Instruction> listInst;

    public WhileInst(E cond, ArrayList<Instruction> listInst) {
        this.cond = cond;
        this.listInst = listInst;
        Collections.reverse(this.listInst);
    }

    @Override
    public KindI kind() {
        return KindI.WHILE;
    }

    @Override
    public String toString() {
        return this.kind().toString() + "(" + this.cond.toString() + ", " + this.listInst.toString() + ")";
    }

    @Override
    public void bind() {
        Programa.pila.abreBloque();

        this.cond.bind();
        for(Instruction inst : this.listInst)
            inst.bind();
            
        Programa.pila.cierraBloque();
    }

    @Override
    public void checkType() {
        this.cond.checkType();
        Type condType = this.cond.getType();
        if(!condType.equals(new BoolXType())) {
            throw new UnsupportedOperationException("ERROR: ¡El tipo de la condición del While no es BoolX!");
        }

        for(Instruction inst : this.listInst)
            inst.checkType();
    }

    @Override
    public int computeDelta(int totalDelta) {

        int whileDelta = totalDelta;

        for(Instruction inst : this.listInst)
            whileDelta = inst.computeDelta(whileDelta);

        return totalDelta;
    }

    @Override
    public ArrayList<Integer> maxMemory(int maxMemory, int currentMemory) {

        int whileMaxMemory = 0, whileCurrentMemory = 0;
        for(Instruction inst : this.listInst) {
            ArrayList<Integer> mem = inst.maxMemory(whileMaxMemory, whileCurrentMemory);
            whileMaxMemory = mem.get(0).intValue();
            whileCurrentMemory = mem.get(1).intValue();
        }

        if(currentMemory + whileMaxMemory > maxMemory)
            maxMemory = currentMemory + whileMaxMemory;

        ArrayList<Integer> nodeMem = new ArrayList<Integer>();
        nodeMem.add(new Integer(maxMemory));
        nodeMem.add(new Integer(currentMemory));

        return nodeMem;
    }

    @Override
    public String generateCode() {
        
        StringBuilder codigo = new StringBuilder();
        codigo.append("block\n");
        codigo.append("loop\n");
        codigo.append(this.cond.generateCode());
        codigo.append("i32.eqz\n");
        codigo.append("br_if 1\n");
        for(Instruction inst : this.listInst)
            codigo.append(inst.generateCode());
        codigo.append("br 0\n");
        codigo.append("end\n");
        codigo.append("end\n");
        
        return codigo.toString();
    }

    @Override
    public void updateType() {
        
        this.cond.updateType();

        for(Instruction inst : this.listInst)
            inst.updateType();
    }
}
