package ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;

public class PilaVinculacion {

    private ArrayList<HashMap<String, ASTNode>> pila;

    public void inicializa() {
        this.pila = new ArrayList<HashMap<String, ASTNode>>();
    }

    public void abreBloque() {
        this.pila.add(new HashMap<String, ASTNode>());
    }

    public void cierraBloque() {

        if(this.pila.isEmpty())
            throw new UnsupportedOperationException("¡La pila de tablas de símbolos está vacía!");
        
        this.pila.remove(this.pila.size() - 1);
    }

    public void insertaId(String id, ASTNode nodo) {

        if(this.pila.isEmpty())
            throw new UnsupportedOperationException("ERROR: ¡La pila de tablas de símbolos está vacía! No se ha podido insertar el id.");

        HashMap<String, ASTNode> cima = this.pila.get(this.pila.size() - 1);
        
        if(cima.containsKey(id))
            throw new UnsupportedOperationException("ERROR: ¡Id: " + id + "está duplicado!");

        cima.put(id, nodo);
    }

    public ASTNode buscaId(String id) {

        ListIterator<HashMap<String, ASTNode>> it = this.pila.listIterator(this.pila.size());

        while(it.hasPrevious()) {
            HashMap<String, ASTNode> currentBlock = it.previous();
            if(currentBlock.containsKey(id))
                return currentBlock.get(id);
        }
        
        throw new UnsupportedOperationException("ERROR: ¡Uso del id " + id + " que no ha sido declarado!");
    }
}
