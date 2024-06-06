package ast.Tipos;

import ast.Expresiones.E;

public class PointerType extends Type {

    private Type pointedType;

    public PointerType(Type type) {
        this.pointedType = type;
    }

    public Type pointedType() {
        return this.pointedType;
    }

    @Override
    public KindT kind() {
        return KindT.PUNTERO;
    }

    @Override
    public String toString() {
        return this.kind().toString() + "(" + this.pointedType.toString() + ")";
    }

    @Override
    public void bind() {
        this.pointedType.bind();
    }

    @Override
    public void checkType() {
        this.pointedType.checkType();
    }

    @Override
    public boolean equals(Type type) {
        return type.kind() == KindT.PUNTERO && ((PointerType) type).pointedType().equals(this.pointedType);
    }

    @Override
    public void checkInitialization(E exp) {

        Type expType = exp.getType();

        if(!this.equals(expType))
            throw new UnsupportedOperationException("ERROR: ¡Intentando inicializar un puntero con una expresion que no es de tipo puntero o cuyo tipo apuntado no coincide!");
    }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    protected String generateInitializationCode(E exp, int delta) {

        StringBuilder codigo = new StringBuilder();
        codigo.append("local.get $tempI\n");
        codigo.append("i32.const " + delta + "\n");
        codigo.append("i32.add\n");
        codigo.append(exp.generateCode());
        codigo.append("i32.store\n");

        return codigo.toString();
    }

    @Override
    public void updateType() {
        this.pointedType.updateType();
        this.pointedType = this.pointedType.getType();
    }
}
