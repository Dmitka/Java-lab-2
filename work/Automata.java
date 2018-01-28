//java-lab2
//Efremov Pavel

enum STATES {OFF, WAIT, ACCEPT, CHECK, COOK};                     //список состояний автомата

public class Automata {

    private int depositreturn = 0;                                //сдача
    private int maxcomand = 7;                                    //max число команд
    private int numbercoffe = 6;                                  //число кофе
    private STATES state;                                         //текущее состояние автомата
    private int cash = 0;                                         //наличные
    private int userchoice = -1;                                  //выбор пользователя (номер строки)

    private String[] comandlist = new String[maxcomand];          //список команд
    private String[] price = new String[numbercoffe];             //Меню цен
    private String[] menu = new String[numbercoffe];              //Меню напитков

//----------------------------------------------------------------------------------------------------

    public void comanda (String str){
        if (str.equals("on")) {
            on();
            printState();
        }
        else if (str.equals("off")) off();
        else if (str.equals("printmenu")) printmenu();
        else if (str.matches("addcash(.*)")){                   //провертка на совпадение строки типа addcach 50
                String[] temp;
                try {
                    temp = str.split(" ", 2);                   //нужна проверка на неввод цифр
                    if (temp.length != 0) {
                        addcash(temp[1]);
                        printState();
                    }
                }
                catch (Exception e1){
                    String str3 = new String("Неверный формат строки");     //по заданию: нельзя испольвовать вывод строк напрямую
                    System.out.println(str3);
                }
            }
        else
            if (str.matches("choice(.*)")){                    //провертка на совпадение строки типа choice 2 или choice Latte
                 String[] temp;
                 try {
                     temp = str.split(" ", 2);
                     choice(temp[1]);
                     printState();
                 }
                 catch (Exception e1){
                     String str3 = new String("Неверный формат строки");
                     System.out.println(str3);
                 }
            }
        else if (str.equals("cancel")){
                 cancel();
                 printState();
        }

        else if (str.equals("help")) help();
        else {
                 String str2 = new String("Неверная команда. Используйте команду help");
                 System.out.println(str2);
        }
    }                       //Выполнение команд

//-------------------------setters------------------------------

    private void setcomandlist(){
        comandlist[0] = "on  --> включение автомата";
        comandlist[1] = "off  --> выключение автомата";
        comandlist[2] = "printmenu  --> печать меню";
        comandlist[3] = "addcash --> ввод наличных";
        comandlist[4] = "choice --> выберите кофе, указав, через пробел, порядковый номер или название напитка";
        comandlist[5] = "cancel --> отмена операции";
      //  comandlist[6] = "cook";
        comandlist[6] = "help --> вызов справки";
    }                            //Устанновка списка команд

    private void setmenu() {
        menu[0] = "1.Espresso";
        menu[1] = "2.Makiato";
        menu[2] = "3.Cappuccino";
        menu[3] = "4.Latte";
        menu[4] = "5.Amerecano";
        menu[5] = "6.Mokkoccino";
    }                                //Установка меню

    private void setprice(){
        price[0] = "50";
        price[1] = "55";
        price[2] = "60";
        price[3] = "30";
        price[4] = "100";
        price[5] = "30";
    }                                //Установка цен

    public void addcash( String str) {
        if (state == STATES.WAIT || state == STATES.ACCEPT || state == STATES.CHECK) {
            state = STATES.ACCEPT;
            int a = Integer.parseInt(str);
            cash = cash + a;
        }
    }                      //Ввод денег

    public void setstate(String str){
        if(str.equals("OFF")) state = STATES.OFF;
        else if (str.equals("WAIT")) state = STATES.WAIT;
        else if (str.equals("ACCEPT")) state = STATES.ACCEPT;
        else if (str.equals("CHECK")) state = STATES.CHECK;
        else if (str.equals("COOK")) state = STATES.COOK;
    }                       //Установка состояния автомата

//--------------------------getters-----------------------------

    public int getCash() {
        return cash;
    }                       //хз зачем

    public String getmenu(int a){
        return menu[a];
    }            //получение меню

    public String getprice(int a){
        return  (price[a]);
    }       //получение цен

    public String getState(){
        return state.toString();
    }       //получение состояния

//---------------------------------------------------------------

    public void on(){
        if (state == STATES.OFF) {
            state = STATES.WAIT;
            String str = new String("Добро пожаловать в программу автомат кофе-машина: ");
            System.out.println(str);
            setcomandlist();
            setmenu();
            setprice();
        }
//        else System.out.print("Error: invalid comand!");
    }                                       //включение автомата

    public void off() {
        if (state == STATES.WAIT){
            state = STATES.OFF;
            return;
        }
      //  else System.out.print("Error: invalid comand!");
    }                                     //выключение автомата

    public void printmenu() {                   //отображение меню с напитками и ценами для пользователя
        for(int i = 0; i < 6; i++ ) {
            System.out.println(getmenu(i) + " -> " + getprice(i));
        }
    }                               //печать меню

    public void printState(){
        String str = new String("Состояние автомата: ");
        System.out.println(str+ getState());
    }                               //печать состояния

    public void choice(String str){
        if (str.matches("[0-9]")) {         //строка содержит цифру  "^[^0-9]*$"  //"^\\D"
            userchoice = Integer.parseInt(str)-1;
            if (state == STATES.ACCEPT)  check(userchoice);

        }
        else                                   //строка не содержит цифру
            if(state == STATES.ACCEPT)
            {
                matcheschoice(str);
                check(userchoice);
            }

    }                         //выбор напитка

    public void matcheschoice(String str){
        if (str.equals("Espresso"))         userchoice = 0;
        else if (str.equals("Makiato"))     userchoice = 1;
        else if (str.equals("Cappuccino"))  userchoice = 2;
        else if (str.equals("Latte"))       userchoice = 3;
        else if (str.equals("Amerecano"))   userchoice = 4;
        else if (str.equals("Mokkoccino"))  userchoice = 5;
    }                  //проверка на ввод слов пользователем из меню

    public void check(int a){
        if (state == STATES.ACCEPT) {
            state = STATES.CHECK;
          //  userchoice = a - 1;
            int b = Integer.parseInt(price[a]);
            if (cash >= b) {
                depositreturn = cash - b;
                cook();
            }
            else {
                state = STATES.CHECK;
                String str2 = new String("Недостаточно денег!");
                System.out.println(str2);
             //   cancel();
            }
        }
        }                               //проверка наличности

    public void cancel(){
        if (state == STATES.ACCEPT || state == STATES.CHECK) state = STATES.WAIT;
        String str = new String("Отмена операции");
        System.out.println(str);
    }                                   //отмена

    public void cook(){
        if (state == STATES.CHECK) {
            state = STATES.COOK;
            String str = new String("Готовиться: ");
            System.out.println(str + menu[userchoice]);
            str = "Ваша сдача: ";
            System.out.println(str + depositreturn);
            finish();
        }
    }                                     //готовка

    public void finish(){
        if(state == STATES.COOK) {
            state = STATES.WAIT;
            cash = 0;
            depositreturn = 0;
            userchoice = -1;
           // printState();
        }
    }                                   //завершение работы с пользователем

    public void help(){
        for (String str : comandlist)
        System.out.println(str);
    }                                    //печать команд через команду help
}
