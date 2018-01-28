

    enum STATES{off, wait};

    class Drink{
        public String name;
        public int number;
        public int cost;

        Drink( int n, String abc, int sum){
            name = abc;
            number = n;
            cost = sum;
        }
        public int get_cost(){return cost;}
        };

    public class CoffeMachine {
        private int total_deposit;
        public int deposit;
        STATES state;
        Drink[] full_menu;
        int variants;
        int user_choice;
        int flag;

        CoffeMachine() {
            state =STATES.off;
            total_deposit=0;
            deposit=0;
            full_menu = new Drink[5];
            variants = 5;
            user_choice=0;
            flag=0;

            Drink tea = new Drink(1,"tea",15);
            Drink latte = new Drink(2,"latte",30);
            Drink hotChocolate = new Drink(3,"hotChocolate",25);
            Drink espresso = new Drink(4,"espresso",20);
            Drink mocha = new Drink(5,"mocha",35);

            full_menu[0] = tea;
            full_menu[1] = latte;
            full_menu[2] = hotChocolate;
            full_menu[3] = espresso;
            full_menu[4] = mocha;

        }
        public void on(){
            if (state==STATES.off){
                state=STATES.wait;
            }
        }
        public void off(){
            if (state==STATES.wait){
                state=STATES.off;
            }
        }
        public String getState(){
            return state.toString();
        }
        public void add_money(int n){
            deposit+=n;
        }

        public void print_menu(){
            System.out.println("Меню:");
         //   String[] menu= new String[5];
            for (int i=0;i<5;i++){
                System.out.println(this.full_menu[i].number +".  " +this.full_menu[i].name +"   "+ this.full_menu[i].cost );
            }

        }

        public void print_state(){
            System.out.println("Текущий счет:  "+deposit);
        }

        public void choice(int n)
        {
            if((n>this.variants)||(n<=0)){
                System.out.println("Напитка с таким номером нет");
            }
            else
            {
                user_choice = n;
               // System.out.println(user_choice);
            }
        }

        public int getCost(){
            if(user_choice!=0) {
                return full_menu[user_choice-1].cost;
            }
            return 0;
        }
        public int check(){
            int cost1 = getCost();
            if (deposit<cost1) {
                System.out.println("Недостаточно средств");
                return -1;
            }
            else {
                return 1;
            }
        }

        public void return_odd_money(){
            System.out.println("Ваша сдача: "+ deposit);
        }
        public void cancel(){
            return_odd_money();
            deposit=0;
            user_choice = 0;
        }

            public void cook()
            {
                int i= this.check();
                if (i==1) {
                    int m= getCost();
                  //  odd_money = deposit -m ;
                    deposit = deposit - m;
                    System.out.println("Готовится напиток: " + full_menu[user_choice-1].name + "  ");
                    System.out.println("1, 2, 3, 4, 5..");
                    System.out.println("Возьмите напиток ");
                   flag=1;
                }


            }

            public void finish(){

            if (flag==1) {


                if (deposit != 0) {
                    System.out.println("Не забудьте сдачу");
                    return_odd_money();
                }
                total_deposit += full_menu[user_choice-1].cost;
                deposit = 0;
            }
            }

            public void showdep(){System.out.println(total_deposit);}


    }



