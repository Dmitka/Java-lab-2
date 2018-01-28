//java-lab2 , main class
//Efremov Pavel


import java.util.Scanner;


public class AutomataDemoMain {

    public static void main (String[] args) {

        Automata automat = new Automata();
        Scanner in = new Scanner(System.in);            //ввод из консоли

        automat.setstate("OFF");                         // задаем начальное состояние автомата

        do {
            automat.comanda(in.nextLine());               //выполняем команды
        }
        while (!automat.getState().equals("OFF"));      //выход из автомата

    }
}
