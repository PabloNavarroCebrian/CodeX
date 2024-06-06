

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import alex.AnalizadorLexico;
import ast.ASTNode;
import ast.ConstructorAST;
import ast.Programa;
import ast.ClaseLexica;

public class Main {
	public static void main(String[] args) throws Exception {
		Reader input = new InputStreamReader(new FileInputStream(args[0]));
		AnalizadorLexico alex = new AnalizadorLexico(input);
		ConstructorAST constructorast = new ConstructorAST(alex);
		Programa programa = null;

		try {
			programa = (Programa) constructorast.parse().value;
		} catch (Exception e) {
			System.out.println("ERROR: ¡Ha habido algún error en el parsing!\n");
			System.out.println("\n\n");
			System.exit(1);
		}

		System.out.println("------------------------------- AST -------------------------------\n");
		System.out.println(programa.toString() + "\n");

		System.out.println("--------------------------- Vinculación ---------------------------\n");
		try {
			programa.bind();
			System.out.println("¡La vinculación se realizó con éxito!\n");
		} catch (Exception e) {
			System.out.println("ERROR: ¡Ha habido algún error en la vinculación!\n");
			System.out.println(e.getMessage());
			System.out.println("\n\n");
			System.exit(1);
		}

		System.out.println("--------------------------- Actualización Alias ---------------------------\n");
		try {
			programa.updateType();
			System.out.println("¡La actualización de alias se realizó con éxito!\n");
		} catch (Exception e) {
			System.out.println("ERROR: ¡Ha habido algún error en la actualización de alias!\n");
			System.out.println(e.getMessage());
			System.out.println("\n\n");
			System.exit(1);
		}

		System.out.println("----------------------------- Tipado ------------------------------\n");
		try {
			programa.checkType();
			System.out.println("¡El tipado se realizó con éxito!\n");
		} catch (Exception e) {
			System.out.println("ERROR: ¡Ha habido algún error en el tipado!\n");
			System.out.println(e.getMessage());
			System.out.println("\n\n");
			System.exit(1);
		}

		System.out.println("------------------------- Cálculo Deltas --------------------------\n");
		programa.computeDelta();
		System.out.println("¡El cálculo de Deltas se realizó con éxito!\n");

		System.out.println("------------------------ Cálculo MaxMemory -------------------------\n");
		programa.maxMemory();
		System.out.println("¡El cálculo de MaxMemory se realizó con éxito!\n");

		System.out.println("------------------------ Generando Código -------------------------\n");
		String codigo = programa.generateCode();
		System.out.println("¡La generación de código se realizó con éxito!\n");

		try {
			String output = args[0].replace(".txt", ".wat").replace("ficherosPrueba", "ficherosWat");
            BufferedWriter writer = new BufferedWriter(new FileWriter(output));
            writer.write(codigo);
            writer.close();
            System.out.println("El código wat se ha escrito correctamente en el archivo.");
        } catch (IOException e) {
            e.printStackTrace();
        }

		System.out.println("\n\n");
	}
}