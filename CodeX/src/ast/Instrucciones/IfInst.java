package ast.Instrucciones;

import java.util.ArrayList;
import java.util.Collections;

import ast.Programa;
import ast.Expresiones.E;
import ast.Tipos.BoolXType;
import ast.Tipos.Type;

public class IfInst extends Instruction {

    private E cond;
    private ArrayList<Instruction> listaInstIf;
    private ArrayList<Instruction> listaInstElse = null;

    public IfInst(E cond, ArrayList<Instruction> listaInstIf) {
        this.cond = cond;
        this.listaInstIf = listaInstIf;
        Collections.reverse(this.listaInstIf);
    }

    public IfInst(E cond, ArrayList<Instruction> listaInstIf, ArrayList<Instruction> listaInstElse) {
        this(cond, listaInstIf);
        this.listaInstElse = listaInstElse;
        Collections.reverse(this.listaInstElse);
    }

    @Override
    public KindI kind() {
        return KindI.IF;
    }

    @Override
    public String toString() {
        if(this.listaInstElse == null)
            return this.kind().toString() + "(" + this.cond.toString() + ", " + this.listaInstIf.toString() + ")";
        else
            return this.kind().toString() + "(" + this.cond.toString() + ", " + this.listaInstIf.toString() + ", " + this.listaInstElse.toString() + ")";
    }

    @Override
    public void bind() {
        Programa.pila.abreBloque();

        this.cond.bind();
        for(Instruction inst : this.listaInstIf)
            inst.bind();
        if(listaInstElse != null)
            for(Instruction inst : this.listaInstElse)
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

        for(Instruction inst : this.listaInstIf)
            inst.checkType();

        if(this.listaInstElse != null)
            for(Instruction inst : this.listaInstElse)
                inst.checkType();
    }

    @Override
    public int computeDelta(int totalDelta) {

        int ifDelta = totalDelta;

        for(Instruction inst : this.listaInstIf)
            ifDelta = inst.computeDelta(ifDelta);

        if(this.listaInstElse != null) {
            int elseDelta = totalDelta;
            for(Instruction inst : this.listaInstElse)
                elseDelta = inst.computeDelta(elseDelta);
        }

        return totalDelta;
    }

    @Override
    public ArrayList<Integer> maxMemory(int maxMemory, int currentMemory) {

        int ifMaxMemory = 0, ifCurrentMemory = 0;
        for(Instruction inst : this.listaInstIf) {
            ArrayList<Integer> mem = inst.maxMemory(ifMaxMemory, ifCurrentMemory);
            ifMaxMemory = mem.get(0).intValue();
            ifCurrentMemory = mem.get(1).intValue();
        }

        if(this.listaInstElse != null) {
            int elseMaxMemory = 0, elseCurrentMemory = 0;
            for(Instruction inst : this.listaInstElse) {
                ArrayList<Integer> mem = inst.maxMemory(elseMaxMemory, elseCurrentMemory);
                elseMaxMemory = mem.get(0).intValue();
                elseCurrentMemory = mem.get(1).intValue();
            }

            if(elseMaxMemory > ifMaxMemory)
                ifMaxMemory = elseMaxMemory;
        }

        if(currentMemory + ifMaxMemory > maxMemory)
            maxMemory = currentMemory + ifMaxMemory;

        ArrayList<Integer> nodeMem = new ArrayList<Integer>();
        nodeMem.add(new Integer(maxMemory));
        nodeMem.add(new Integer(currentMemory));

        return nodeMem;
    }

    @Override
    public String generateCode() {
        
        StringBuilder codigo = new StringBuilder();

        codigo.append(this.cond.generateCode());
        codigo.append("if\n");
        for(Instruction inst : this.listaInstIf)
            codigo.append(inst.generateCode());
        
        if(this.listaInstElse != null) {
            codigo.append("else\n");
            for(Instruction inst : this.listaInstElse)
                codigo.append(inst.generateCode());
        }
        codigo.append("end\n");

        return codigo.toString();
    }

    @Override
    public void updateType() {
        
        this.cond.updateType();

        for(Instruction inst : this.listaInstIf)
            inst.updateType();

        if(this.listaInstElse != null)
            for(Instruction inst : this.listaInstElse)
                inst.updateType();
    }
}