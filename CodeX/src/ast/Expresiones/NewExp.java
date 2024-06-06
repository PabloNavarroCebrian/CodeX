package ast.Expresiones;

import ast.Tipos.PointerType;
import ast.Tipos.Type;

public class NewExp extends E {

    private Type typeNewExp;

    public NewExp(Type type) {
        this.typeNewExp = type;
    }

    public Type type() {
        return this.typeNewExp;
    }

    @Override
    public KindE kind() {
        return KindE.NEW;
    }

    @Override
    public String toString() {
      return this.kind().toString() + "(" + this.typeNewExp.toString() + ")";
    }

    @Override
    public void bind() {
        this.typeNewExp.bind();
    }

    @Override
    public void checkType() {
        this.typeNewExp.checkType();
        this.type = new PointerType(this.typeNewExp);
    }

    @Override
    public String generateCode() {
        
        StringBuilder codigo = new StringBuilder();
        codigo.append("i32.const " + this.typeNewExp.getSize() * 4 + "\n");
        codigo.append("call $reserveHeap\n");
        codigo.append("global.get $NP\n");

        return codigo.toString();
    }

    @Override
    public void updateType() {
        this.typeNewExp.updateType();
        this.typeNewExp = this.typeNewExp.getType();
    }
}
