package ast.Expresiones;

import java.util.ArrayList;
import java.util.Collections;

import ast.ASTNode;
import ast.Programa;
import ast.Declaraciones.DecFuncion;
import ast.Declaraciones.Declaracion;
import ast.Declaraciones.KindD;
import ast.Declaraciones.Parametro;
import ast.Expresiones.Accesos.Acceso;
import ast.Tipos.Type;

public class ExpLlamadaFun extends E {

    private String nombreFun;
    private ArrayList<E> args;
    private ASTNode bindingNode;

    public ExpLlamadaFun(String nombreFun, ArrayList<E> args) {
        this.nombreFun = nombreFun;
        this.args = args;
        Collections.reverse(this.args);
    }

    public String nombreFun() {
        return this.nombreFun;
    }

    public ArrayList<E> args() {
        return this.args;
    }

    @Override
    public KindE kind() {
        return KindE.LLAMADAFUN;
    }

    @Override
    public String toString() {
        return this.kind().toString() + "(" + nombreFun + ", " + this.args.toString() + ")";
    }

    @Override
    public void bind() {
        this.bindingNode = Programa.pila.buscaId(nombreFun);

        for(E exp : this.args)
            exp.bind();
    }

    @Override
    public void checkType() {
        if(((Declaracion) this.bindingNode).kind() != KindD.DECFUNCION)
            throw new UnsupportedOperationException("ERROR: ¡Se está intentando llamar a una función con identificador " + this.nombreFun + " que no es una función!");

        for(E arg : this.args)
            arg.checkType();

        ArrayList<Parametro> params = ((DecFuncion) this.bindingNode).getParams();

        if(this.args.size() != params.size())
            throw new UnsupportedOperationException("ERROR: ¡Se está intentando llamar a una función con identificador " + this.nombreFun + " con una cantidad incorrecta de argumentos!");

        for(int i = 0; i < params.size(); ++i) {
            Type typeParam = params.get(i).getType();
            Type typeArg = this.args.get(i).getType();

            if(!params.get(i).byValue() && this.args.get(i).kind() != KindE.ACCESO)
                throw new UnsupportedOperationException("ERROR: ¡Se está intentando llamar a una función con identificador " + this.nombreFun + " con argumentos por referencia no válidos!");

            if(!typeParam.equals(typeArg))
                throw new UnsupportedOperationException("ERROR: ¡Se está intentando llamar a una función con identificador " + this.nombreFun + " con argumentos cuyos tipos no son los correctos!");
        }

        this.type = ((DecFuncion) this.bindingNode).getType();
    }

    @Override
    public String generateCode() {

        StringBuilder codigo = new StringBuilder();

        codigo.append("global.get $SP\n");
        codigo.append("i32.const 4\n");
        codigo.append("i32.add\n");
        codigo.append("local.set $tempLF\n");

        ArrayList<Parametro> params = ((DecFuncion) this.bindingNode).getParams();
        
        for(int i = 0; i < params.size(); ++i) {

            Parametro param = params.get(i);
            E arg = this.args.get(i);

            if(!param.byValue()) {
                codigo.append("local.get $tempLF\n");
                codigo.append("i32.const " + param.getDelta() * 4 + "\n");
                codigo.append("i32.add\n");
                codigo.append(((Acceso) arg).generateDirCode());
                codigo.append("i32.store\n");
            }
            else {
                if(arg.kind() == KindE.ACCESO) {
                    codigo.append(((Acceso) arg).generateDirCode());
                    codigo.append("local.get $tempLF\n");
                    codigo.append("i32.const " + param.getDelta() * 4 + "\n");
                    codigo.append("i32.add\n");
                    codigo.append("i32.const " + param.getType().getSize() + "\n");
                    codigo.append("call $copyn\n");
                }
                else {
                    codigo.append("local.get $tempLF\n");
                    codigo.append("i32.const " + param.getDelta() * 4 + "\n");
                    codigo.append("i32.add\n");
                    codigo.append(arg.generateCode());
                    codigo.append("i32.store\n");
                }
            }
        }

        codigo.append("call $" + this.nombreFun + "\n");

        return codigo.toString();
    }

    @Override
    public void updateType() {
        for(E arg : this.args)
            arg.updateType();
    }
}
