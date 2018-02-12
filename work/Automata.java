import java.io.*;
import java.util.ArrayList;

enum STATES{WAIT, ACCEPT, CHECK, COOK}; //перечисление состояний автомата

class Autom {
    double cash;    //текущая сумма;
    ArrayList<String> menu = new ArrayList();   //массив строк названий напитков (может подгружаться из файла);
    ArrayList<Double> prices = new ArrayList(); //массив цен напитков (соответствует массиву menu);
    STATES state;   //текущее состояние автомата;
    boolean OnOff;  //автомат включен/выключен
    int NumDrink;   //номер выбранного из списка напитка
    //Integer[] AutCash = new Integer[11];    //монеты/купюры в автомате
    double AutCash; //наличность в автомате для сдачи

    public Autom(){}

    public void on(){           //включение автомата
        OnOff=true;
        LoadMenu();
        LoadPrice();
        state=STATES.WAIT;
        printState();
    }
    public void off(){          //выключение автомата
        OnOff=false;
    }

    public void coin(double CoinIn){         //занесение денег на счёт пользователем
        if(state==STATES.ACCEPT || (state==STATES.WAIT && cash==0)){
            cash=cash+CoinIn;
            state=STATES.ACCEPT;
        }
        printState();
    }
    public void printMenu(){    //отображение меню с напитками и ценами для пользователя
        for(int i=0;i<menu.size();i++) {
            System.out.print(menu.get(i));
            System.out.println(" - " + prices.get(i));
        }
        printState();
    }
    public void printState(){   //отображение текущего состояния для пользователя
        System.out.println(state);
    }

    public void choice(int N){       //выбор напитка пользователем
        if(state==STATES.ACCEPT) {
            NumDrink = N;
            check();
        }
        printState();
    }
    private void check(){        //проверка наличия необходимой суммы
        if(cash<prices.get(NumDrink)){
            System.out.println(" Внесенной суммы не достаточно ");
            state=STATES.ACCEPT;
            printState();
        }
        else if(AutCash+prices.get(NumDrink)-cash<0){
            System.out.println(" Нет сдачи ");
            cancel();
        }
        else //суммы и сдачи достаточно
            cook();
    }
    public void cancel(){       //отмена сеанса обслуживания пользователем
        cash=0;
        state=STATES.WAIT;
        printState();
    }
    private void cook(){         //имитация процесса приготовления напитка
        AutCash=AutCash-cash+prices.get(NumDrink);
        cash=0;
        //System.out.println(" Приготовление... ");
        state=STATES.COOK;
        printState();
        finish();
    }
    private void finish(){       //завершение обслуживания пользователя
        System.out.println(" Приготовление закончено ");
        state=STATES.WAIT;
        printState();
        //printMenu();
    }
    private void LoadMenu(){
        File file = new File("C:\\Users\\User\\IdeaProjects\\Lab2\\Menu.txt");
        BufferedReader bReader = null;
        InputStreamReader iReader = null;
        FileInputStream fStream = null;

        //ArrayList<String> menu = new ArrayList();

        try {
            fStream = new FileInputStream(file);
            iReader = new InputStreamReader(fStream, "windows-1251");
            bReader = new BufferedReader(iReader);

            String fileLine = bReader.readLine();

            while (fileLine != null) {
                menu.add(fileLine);
                fileLine = bReader.readLine();
            }
            /*for(int i=0;i<menu.size();i++) {
                System.out.println(menu.get(i));
            }*/
        }
        catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    private void LoadPrice(){
        File file = new File("C:\\Users\\User\\IdeaProjects\\Lab2\\Price.txt");
        BufferedReader bReader = null;
        InputStreamReader iReader = null;
        FileInputStream fStream = null;

        //ArrayList<String> menu = new ArrayList();

        try {
            fStream = new FileInputStream(file);
            iReader = new InputStreamReader(fStream, "windows-1251");
            bReader = new BufferedReader(iReader);

            String fileLine = bReader.readLine();

            while (fileLine != null) {
                prices.add(Double.parseDouble(fileLine));
                fileLine = bReader.readLine();
            }
            /*for(int i=0;i<menu.size();i++) {
                System.out.println(menu.get(i));
            }
            int c;
            while((c=bReader.read())!=-1) {
                System.out.print((char)c);
            }*/
        }
        catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

public class Automata {
    public static void main(String[] args){
        Autom autom=new Autom();
        autom.on();
        autom.cash=0.00;
        autom.AutCash=10.00;
        /*autom.menu.add("Капучино");
        autom.menu.add("Американо");
        autom.menu.add("Латте");
        autom.menu.add("Черный");
        autom.menu.add("Двойной черный");
        autom.menu.add("Эспрессо");
        autom.prices.add(20.00);
        autom.prices.add(25.00);
        autom.prices.add(30.00);
        autom.prices.add(35.00);
        autom.prices.add(40.00);
        autom.prices.add(45.00);*/
        autom.printMenu();
        autom.printState();
        autom.coin(20.00);
        System.out.println(autom.cash);
        autom.choice(1);
        autom.coin(20.00);
        System.out.println(autom.cash);
        autom.choice(3);
        System.out.println(autom.prices.get(autom.NumDrink));
        System.out.println(autom.cash);
        System.out.println(autom.AutCash);
    }
}