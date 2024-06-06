package ast;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import ast.Declaraciones.DecFuncion;
import ast.Declaraciones.Declaracion;
import ast.Declaraciones.KindD;
import ast.Tipos.Type;

public class Programa implements ASTNode {

    public static PilaVinculacion pila = new PilaVinculacion();
    private ArrayList<Declaracion> declaraciones;
    private int maxMemory;

    public Programa(DecFuncion funMain) {
        this.declaraciones = new ArrayList<Declaracion>();
        this.declaraciones.add(funMain);
    }

    public void addDec(Declaracion dec) {
        this.declaraciones.add(0, dec);
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.PROGRAMA;
    }

    @Override
    public String toString(){
        return this.nodeKind().toString() + "(" + declaraciones.toString() + ")";
    }

    @Override
    public void bind() {

        Programa.pila.inicializa();
        Programa.pila.abreBloque();
        for (Declaracion dec : declaraciones)
            dec.bind();
        Programa.pila.cierraBloque();
    }

    @Override
    public void checkType() {
        for (Declaracion dec : this.declaraciones)
            dec.checkType();
    }

    @Override
    public Type getType() { return null; }

    @Override
    public int computeDelta(int totalDelta) {
        for(Declaracion dec : this.declaraciones)
            totalDelta = dec.computeDelta(totalDelta);

        return totalDelta;
    }

    public void computeDelta() {
        this.computeDelta(0);
    }

    @Override
    public ArrayList<Integer> maxMemory(int maxMemory, int currentMemory) {

        for(Declaracion dec : this.declaraciones) {
            ArrayList<Integer> mem = dec.maxMemory(maxMemory, currentMemory);
            maxMemory = mem.get(0).intValue();
            currentMemory = mem.get(1).intValue();
        }

        this.maxMemory = maxMemory;

        ArrayList<Integer> nodeMem = new ArrayList<Integer>();
        nodeMem.add(new Integer(maxMemory));
        nodeMem.add(new Integer(currentMemory));

        return nodeMem;
    }

    public void maxMemory() {
        this.maxMemory(0, 0);
    }

    @Override
    public String generateCode() {

        StringBuilder codigo = new StringBuilder();
        
        try {
            String path = "../wat/start.wat";
            String start = new String(Files.readAllBytes(Paths.get(path)));
            codigo.append(start);
        } catch (IOException e) {
            e.printStackTrace();
        }

        codigo.append("(func $premain\n");
        codigo.append("(local $localStart i32)\n");
        codigo.append("(local $temp i32)\n");
        codigo.append("(local $tempLF i32)\n");
        codigo.append("(local $tempI i32)\n");
        codigo.append("i32.const " + this.maxMemory * 4 + "\n");
        codigo.append("call $reserveStack\n");
        codigo.append("global.get $MP\n");
        codigo.append("local.set $localStart\n");

        for(Declaracion dec : this.declaraciones) {
            if(dec.kind() == KindD.DECVAR)
                codigo.append(dec.generateCode());
        }

        codigo.append("call $main\n");
        codigo.append("return\n");
        codigo.append(")\n");

        for(Declaracion dec : this.declaraciones) {
            if(dec.kind() != KindD.DECVAR)
                codigo.append(dec.generateCode());
        }

        codigo.append(")\n");

        return codigo.toString();
    }

    @Override
    public int getDelta() { return 0; }

    @Override
    public void updateType() {
        for (Declaracion dec : declaraciones)
            dec.updateType();
    }
}