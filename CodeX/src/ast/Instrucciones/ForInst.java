package ast.Instrucciones;

import java.util.ArrayList;
import java.util.Collections;

import ast.Programa;
import ast.Expresiones.E;
import ast.Tipos.BoolXType;
import ast.Tipos.Type;

public class ForInst extends Instruction {

    private DecVarInst decVar = null;
    private E cond;
    private AsigInst asigInst;
    private ArrayList<Instruction> listInst;

    public ForInst(E cond, AsigInst asigInst, ArrayList<Instruction> listInst) {
        this.cond = cond;
        this.asigInst = asigInst;
        this.listInst = listInst;
        Collections.reverse(this.listInst);
    }

    public ForInst(DecVarInst decVar, E cond, AsigInst asigInst, ArrayList<Instruction> listInst) {
        this(cond, asigInst, listInst);
        this.decVar = decVar;
    }

    @Override
    public KindI kind() {
        return KindI.FOR;
    }

    @Override
    public String toString() {
        if(this.decVar == null)
            return this.kind().toString() + "(" + this.cond.toString() + ", " + this.asigInst.toString() + ", " + this.listInst.toString() + ")";
        else
            return this.kind().toString() + "(" + this.decVar.toString() + ", " + this.cond.toString() + ", " + this.asigInst.toString() + ", " + this.listInst.toString() + ")";
    }

    @Override
    public void bind() {
        Programa.pila.abreBloque();

        if(this.decVar != null)
            decVar.bind();
        this.cond.bind();
        this.asigInst.bind();
        for(Instruction inst : this.listInst)
            inst.bind();

        Programa.pila.cierraBloque();
    }

    @Override
    public void checkType() {

        if(this.decVar != null)
            this.decVar.checkType();

        this.cond.checkType();
        Type condType = this.cond.getType();
        if(!condType.equals(new BoolXType())) {
            throw new UnsupportedOperationException("ERROR: ¡El tipo de la condición del While no es BoolX!");
        }

        this.asigInst.checkType();

        for(Instruction inst : this.listInst)
            inst.checkType();
    }

    @Override
    public int computeDelta(int totalDelta) {

        int forDelta = totalDelta;

        if(this.decVar != null)
            forDelta = decVar.computeDelta(forDelta);

        for(Instruction inst : this.listInst)
            forDelta = inst.computeDelta(forDelta);

        return totalDelta;
    }

    @Override
    public ArrayList<Integer> maxMemory(int maxMemory, int currentMemory) {

        int forMaxMemory = 0, forCurrentMemory = 0;
        if(this.decVar != null) {
            ArrayList<Integer> mem = this.decVar.maxMemory(forMaxMemory, forCurrentMemory);
            forMaxMemory = mem.get(0).intValue();
            forCurrentMemory = mem.get(1).intValue();
        }

        for(Instruction inst : this.listInst) {
            ArrayList<Integer> mem = inst.maxMemory(forMaxMemory, forCurrentMemory);
            forMaxMemory = mem.get(0).intValue();
            forCurrentMemory = mem.get(1).intValue();
        }

        if(currentMemory + forMaxMemory > maxMemory)
            maxMemory = currentMemory + forMaxMemory;

        ArrayList<Integer> nodeMem = new ArrayList<Integer>();
        nodeMem.add(new Integer(maxMemory));
        nodeMem.add(new Integer(currentMemory));

        return nodeMem;
    }

    @Override
    public String generateCode() {

        StringBuilder codigo = new StringBuilder();

        if (this.decVar != null)
            codigo.append(this.decVar.generateCode());

        codigo.append("block\n");
        codigo.append("loop\n");
        codigo.append(this.cond.generateCode());
        codigo.append("i32.eqz\n");
        codigo.append("br_if 1\n");
        for(Instruction inst : this.listInst)
            codigo.append(inst.generateCode());
        codigo.append(this.asigInst.generateCode());
        codigo.append("br 0\n");
        codigo.append("end\n");
        codigo.append("end\n");

        return codigo.toString();
    }

    @Override
    public void updateType() {
        
        if(this.decVar != null)
            this.decVar.updateType();
            
        this.cond.updateType();
        this.asigInst.updateType();
        for(Instruction inst : this.listInst)
            inst.updateType();
    }
}
