package Objetos;

public class DadosCalculadora {
    private float valor1;
    private float valor2;
    private float resultado;

    public DadosCalculadora(float valor1, float valor2) {
        this.valor1 = valor1;
        this.valor2 = valor2;
    }
    
    public float somar(){
        return resultado = valor1 + valor2;
    }
    
    public float subtrair(){
        return resultado = valor1 - valor2;
    }
    
    public float dividir(){
        return resultado = valor1 / valor2;
    }
    
    public float multiplicar(){
        return resultado = valor1 * valor2;
    }
    
    
}
