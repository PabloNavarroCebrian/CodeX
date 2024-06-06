package ast.Declaraciones;

import java.util.ArrayList;
import java.util.Collections;

import ast.Programa;
import ast.Instrucciones.Instruction;
import ast.Instrucciones.KindI;
import ast.Tipos.KindT;
import ast.Tipos.Type;
import ast.Tipos.VoidXType;
import ast.Instrucciones.ReturnInst;

public class DecFuncion extends Declaracion {

    private ArrayList<Parametro> parametros;
    private ArrayList<Instruction> listaInst;
    private int maxMemory;

    public DecFuncion(Type returnType, String nombre, ArrayList<Parametro> parametros, ArrayList<Instruction> listaInst) {
        this.type = returnType;
        this.nombre = nombre;
        this.parametros = parametros;
        Collections.reverse(this.parametros);
        this.listaInst = listaInst;
        Collections.reverse(this.listaInst);
        if(this.listaInst.size() == 0 || this.listaInst.get(this.listaInst.size() - 1).kind() != KindI.RETURN)
            throw new UnsupportedOperationException("ERROR: ¡La última instrucción de la función " + this.nombre + " debe ser un Return!");
    }

    @Override
    public String toString() {
        return this.kind().toString() + "(" + this.type.toString() + ", " + this.nombre + ", " + this.parametros.toString() + ", " + this.listaInst.toString() + ")";
    }

    @Override
    public KindD kind() {
        return KindD.DECFUNCION;
    }

    @Override
    public void bind() {

        Programa.pila.insertaId(nombre, this);

        Programa.pila.abreBloque();

        this.type.bind();
        
        for(Parametro param : this.parametros)
            param.bind();

        ((ReturnInst) this.listaInst.get(this.listaInst.size() - 1)).setFunction(this);

        for(Instruction inst : this.listaInst)
            inst.bind();

        Programa.pila.cierraBloque();
    }

    public ArrayList<Parametro> getParams() {
        return this.parametros;
    }

    @Override
    public void checkType() {

        this.type.checkType();
        if(this.type.kind() != KindT.INTX && this.type.kind() != KindT.BOOLX && this.type.kind() != KindT.VOIDX)
            throw new UnsupportedOperationException("ERROR: ¡Los valores de retorno de las funciones sólo puede ser un tipo básico(intX, boolX o voidX)!");

        for(Parametro param : this.parametros)
            param.checkType();
        for(Instruction inst : this.listaInst)
            inst.checkType();        
    }

    @Override
    public int computeDelta(int totalDelta) {
        
        int funDelta = 0;

        for(Parametro param : this.parametros)
            funDelta = param.computeDelta(funDelta);

        for(Instruction inst : this.listaInst)
            funDelta = inst.computeDelta(funDelta);

        return totalDelta;
    }

    @Override
    public ArrayList<Integer> maxMemory(int maxMemory, int currentMemory) {

        int funMaxMemory = 0, funCurrentMemory = 0;
        for(Parametro param : this.parametros) {
            ArrayList<Integer> mem = param.maxMemory(funMaxMemory, funCurrentMemory);
            funMaxMemory = mem.get(0).intValue();
            funCurrentMemory = mem.get(1).intValue();
        }
        for(Instruction inst : this.listaInst) {
            ArrayList<Integer> mem = inst.maxMemory(funMaxMemory, funCurrentMemory);
            funMaxMemory = mem.get(0).intValue();
            funCurrentMemory = mem.get(1).intValue();
        }

        this.maxMemory = funMaxMemory;

        ArrayList<Integer> nodeMem = new ArrayList<Integer>();
        nodeMem.add(new Integer(maxMemory));
        nodeMem.add(new Integer(currentMemory));

        return nodeMem;
    }

    @Override
    public String generateCode() {

        StringBuilder codigo = new StringBuilder();
        codigo.append("(func $" + this.nombre + "\n");

        if(!this.type.equals(new VoidXType()))
            codigo.append("(result i32)\n");

        codigo.append("(local $localStart i32)\n");
        codigo.append("(local $temp i32)\n");
        codigo.append("(local $tempLF i32)\n");
        codigo.append("(local $tempI i32)\n");
        codigo.append("i32.const " + (this.maxMemory + 1) * 4 + "\n");
        codigo.append("call $reserveStack\n");
        codigo.append("local.set $temp\n");
        codigo.append("global.get $MP\n");
        codigo.append("local.get $temp\n");
        codigo.append("i32.store\n");
        codigo.append("global.get $MP\n");
        codigo.append("i32.const 4\n");
        codigo.append("i32.add\n");
        codigo.append("local.set $localStart\n");

        for(Instruction inst : this.listaInst)
            codigo.append(inst.generateCode());

        codigo.append(")\n");

        return codigo.toString();
    }

    @Override
    public void updateType() {
        this.type.updateType();
        this.type = this.type.getType();
        for(Parametro param : this.parametros)
            param.updateType();

        for(Instruction inst : this.listaInst)
            inst.updateType();
    }
}