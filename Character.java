import java.util.Random;
import java.lang.Math;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;


/**
* Represents a character in Alice in Wonderland.
*/
public class Character{
    String name;
    double size;
    final double originalSize;
    int locationC;
    int locationR;
    Boolean canFly;
    Boolean canWalk;
    int health;
    Boolean hasPill;
    Boolean hasFungi;
    String[][] map = new String[5][5];


    /**
     * Constructs a wonderland character.
     * @param name string that stores the characters name.
     * @param size the height of the character double in inches.
     * @param oS the immutible original height of the character double in inches.
     * @param locationC the location of the character column on the map.    
     * @param locationR the location of the character row on the map.
     */
    public Character(String n, double s, double oS, int locationC, int locationR){
        this.name = n;
        this.size = s;
        this.originalSize = oS;
        this.locationC = locationC;
        this.locationR = locationR;
        this.canFly = false;
        this.canWalk = true;
        this.health = 100;
        this.hasPill = false;
        this.hasFungi = false;

         //Key words: fungi, water, pills, door
         this.map = new String[][]{{"You have reached the edge of Wonderland. The sky and sea melt together into one grey plain.", "You walk along the beach to find a bottle of pills washed up on the shore.", "You have reached the light house at the endless sea... ", "After a long treck throught the mountains you have reached the top of Mont Jubjub! You can see anything from here", "The far castle!"}, 
         {"You have entered the croquet grounds. An intense game is taking place between Tweedledee and Tweedledum.","You have reached the grandest Castle in all the land, the castle of the queen of hearts.", "Court House", "You have reached the royal rose gardens.", "You are at the base of the mountains of division."}, 
         {"You have reached the Mad Hatters house. It appear there is a tea party in session!", "You have reached the duchesses manner.", "You emerge from the woods into a meadow surrounding a small gabled house. You enter the house on the table is a container of pills.", "You enter a grove of massive mushrooms. Your eyes meet those of caterpillar who ignores you and takes a drag of a long hookah.", "Chilled to the bone you hurry on through the woods in the hopes of finding shelter."}, 
         {"Welcome to the dismal mire...","You found a river. Should you drink the water? It could be dangerous...Strange things happen in Wonderland...", "Giant dragonflies swarm through the sky over your head squabling over potential perches.","The trees around you begin to morph into oversized mushrooms. You are pleased pick some fungi and arrange them into a bouquet. Your stomache growls...", "You sense someone is watching you in the darkness! Move faster"}, 
         {"You cross the water to find a small door.","You reach the edge of a calm pond. Thirsty, you cup the water to your lips only to spit out a mouthful of tears.","","The forrest thicken. A sign points east saying, 'Enter at your own peril!'", "You wander down another one of the Tulgey woods winding purple roads. Will you ever escape?"}};
        
    }

    /**
     * Changes the users location on the map based on direction they choose.
     * @param direction the direction the user wants to walk.
     * @return canWalk boolean that describes whether or not the area is reachable.
     */
    public Boolean walk(String direction){

        if (direction == "north" || direction == "North"){
            if (this.locationC == 0){
                throw new RuntimeException("You have reached the North most edge of Wonderland. Please try a different direction.");

            } else {
                this.locationC-=1;
                this.health -= 1;
            }
        } else if (direction == "south" || direction == "South"){
            if (this.locationC == 5){
                throw new RuntimeException("You have reached the North most edge of Wonderland. Please try a different direction.");

            } else {
                this.locationC+=1;
                this.health -= 1;

            }

        } else if (direction == "west" || direction == "West"){
            if (this.locationR == 0){
                throw new RuntimeException("You have reached the North most edge of Wonderland. Please try a different direction.");

            } else {
                this.locationR -= 1;
                this.health -= 1;

            }
        } else if (direction == "east" || direction == "East"){
            if (this.locationR == 5){
                throw new RuntimeException("You have reached the North most edge of Wonderland. Please try a different direction.");

            } else {
                this.locationR += 1;
                this.health -= 1;

            }

        } else {
            System.out.println("Please enter a valid direction: north, south, east or west.");
        }
        System.out.println(this.map[this.locationC][this.locationR]);

        return this.canWalk;
    }
    
    /**
     * Moves the player to any point they want on the board if they can fly and prints out the story of the place.
     * @param x the amount the user wants to shift horizontaly
     * @param y the amount the user wants to shift vertically
     * @return boolean for whether or not can fly
     */
    public Boolean fly(int x, int y){

        if (canFly && y >= 0 && y <= 4 && x >= 0 && x <= 4){
            this.locationC = y;
            this.locationR = x;
            this.health -= Math.abs(y);
            this.health -= Math.abs(x);
            System.out.println(this.map[this.locationC][this.locationR]);

        } else {
            System.out.println("Please enter valid coordinates, you may need to check if you can fly using toSting()");

        }

        return this.canFly;
    }

    /**
     * Checks for usable items at the location. If the user can fly prints statement that looser could loose that ability,
     */
    public void investigate(){

        if (this.map[this.locationC][this.locationR].contains("pill")){
            System.out.println("You can use grab(\"pill\") to learn more.");

        } if (this.map[this.locationC][this.locationR].contains("fungi")){
            System.out.println("You can use grab(\"fungi\") to learn more.");

        } if (this.map[this.locationC][this.locationR].contains("water")){
            System.out.println("You can use use(\"water\") to learn more.");

        } if (this.map[this.locationC][this.locationR].contains("door")){
            System.out.println("You can use use(\"door\") to learn more.");    

        } if (this.map[this.locationC][this.locationR].contains("croquet")){
            playCroquet();

        } if (this.map[this.locationC][this.locationR].contains("caterpillar")){
            conversation();

        } if (this.canFly && (this.map[this.locationC][this.locationR].contains("pill") || this.map[this.locationC][this.locationR].contains("fungi") 
        || this.map[this.locationC][this.locationR].contains("water") || this.map[this.locationC][this.locationR].contains("door") 
        || this.map[this.locationC][this.locationR].contains("croquet") || this.map[this.locationC][this.locationR].contains("caterpillar"))){
          
            System.out.println("Note: If you grab an item you will loose the ability to fly.");
    
        }
    }

    /**
     * Randomly generates a conversation with the caterpillar for the users chosen amount of rounds.
     */
    public void conversation(){

        var user_input = new Scanner(System.in);

        //input = new Scanner(System.in);

        System.out.println("How many conversational rounds do you want?");

        //variable that stores the number of rounds.
        int num_rounds = user_input.nextInt();

        //List that stores canned responses
        ArrayList <String> canned_responses = new ArrayList<String>(Arrays.asList("Why?", "mmhm...", 
        "That is wrong beginning to end!", "Are you going to loose your temper?", "Why are you talking to me about this?",
        "Alright, I'm going to give you some advice, look for the magic mushrooms..."));

        System.out.println("Whooo are yooou?");

        //define scanner
        Scanner response = new Scanner(System.in);

        //for loop for the amount of rounds the user sellected
        for (int i= 0; i< num_rounds; i++){

            String user_response = response.nextLine();
            //user_response = user_response + " ";
            String bot_response;
            bot_response = " ";
            String [] words = user_response.split(" ");

            //iterate through the words in the user response, and replace mirror words.
            for (int j=0; j < words.length; j++) {
                
                if (words[j].equals("I") || words[j].equals("i")) {
                    bot_response = "You! Who are you!?";   

                } else{
                    int randomResponse;
                    Random rand = new Random(); 
                    randomResponse = rand.nextInt(5);
                    bot_response = canned_responses.get(randomResponse);

                }
            }

            System.out.println(bot_response);

        }

        System.out.println("You storm off in a furry");
         //close scanners
         user_input.close();
         //close response scanner
         response.close();
    }

    /**
     * Changes health status when user plays croquet based on size.
     */
    public void playCroquet(){
        System.out.println("The queen spotted you! She gave you a choice to play or \"OFF WITH YOUR HEAD\".");

        if (this.size<= 30.0){
            this.health /= 2;
            System.out.println("You are too small to be a player, so the Queen decided you would be more useful as a ball. After the game your health is: " + this.health + ".");

        } else if (this.size >= 100){
            this.health -= this.health / 8;
            System.out.println("You play a few games. Although, you could use a rest due to your massive size the physical excertion is not too taxing. After the game your health is: " + this.health + ".");

        } else {
            this.health -= this.health / 4;
            System.out.println("You play a few games to keep your head. After the games your health is: " + this.health + ".");
        }
    }
    
    /**
     * Picks up item to call examine and prevents movement
     * @param item string describing the item
     */
    public void grab(String item){
        this.canWalk = false;
        this.canFly = false;

        if (this.map[this.locationC][this.locationR].contains("pill")){
            this.hasPill = true;
            System.out.println("When you are done using the item then use the drop() method.");


        } else if (this.map[this.locationC][this.locationR].contains("fungi")){
            this.hasFungi = true;

        }

        examine(item);
    
    }

    /**
     * Examines pill and explains possible steps going forward.
     * @param item string describing item.
     */
    public void examine(String item){
        if (item.equals("pill") && this.hasPill){
            System.out.println("These pills are popular in Wonderland, but they are notorious. Be careful. If you want to take a pill enter use(\"pill\")");

        } else if (item.equals("fungi") && this.hasFungi){
            System.out.println("You have found a magic mushroom try the use() method to become magical.");

        }else{
            System.out.println("We dont know the item you are trying to examine.");

        }
        
    }

    /**
     * Calls functions to use the items.
     * @param item string that represents item.
     */
    public void use(String item){
        Random random = new Random(); 
        int randomPill;

        if (item.equals("pill") && this.hasPill){
            randomPill = random.nextInt(3);

            if (randomPill == 0){
                shrink();

            } else {
                grow();

            }
        } else if (item.equals("fungi") && this.hasFungi){
            setFly();

        } else if(item.equals("water")){
            drink();
        }
    }

    /**
     * Adds health points when the user drinks.
     */
    public void drink (){
        this.health += 5;
    }

    /**
     * Shrinks the size of the character
     * @return Number representing size
     */
    public Number shrink(){
        System.out.println("Oh no...you have shrunk to half your size.");
        this.size /= 2;
        return this.size;
    }

    /**
     * Doubles the characters size
     * @return Number representing characters size
     */
    public Number grow(){
        System.out.println("You have grown to double your size!!!");
        this.size *= 2;
        return this.size;
    }

    /**
     * Sets canFly to true.
     */
    public void setFly(){
        this.canFly = true;
        System.out.println("Now you can fly! Input the coordinates you would like to fly to into the method fly(x, y).");
    }
    
    /**
     * drop item and gives back the ability to walk
     * @param item string describing the item
     */
    public void drop(String item){
        this.canWalk = true;
        this.canFly = false;

        if (item.equals("pill")){
            this.hasPill = false;

        } else if (item.equals("fungi")){
            this.hasFungi = false;
        }
    }

    /**
     * Replenishes health points. If the health is above 90 the program returns it to 100.
     */
    public void rest(){
        if (this.health <= 90){
            this.health +=10;

        } else {
            this.health = 100;
        }
    }

    /**
     * Undoes all physical modifications.
     */
    public void undo(){
        this.size = this.originalSize;
        this.health = 100;
        this.canFly = false;
        this.canWalk = true;
    }

    /**
     * Creates string to describe character.
     * @return string that describes the character.
     */
    public String toString(){
        String fly;

        if (this.canFly){
            fly = "can";
        } else {
            fly = "cannot";
        }
        return this.name + " is " + this.size + " inches tall. " + this.name + " is located at coordinates (" + this.locationR +", " + this.locationC + ") on the map of Wonderland. Currently alice " + fly + " fly. According to the map: " + this.map[this.locationC][this.locationR];
    }

     public static void main(String[] args){
        System.out.println("Oh no... you are lost in Wonderland");
        //Random random = new Random(); 
       // Character alice = new Character("Alice", 58, random.nextInt(5), random.nextInt(5));

       Character alice = new Character("Alice", 58, 58, 3, 3);
       
       System.out.println(alice.toString());
       alice.investigate();
       alice.grab("fungi");
       alice.use("fungi");
       alice.fly(3, 2);
       alice.investigate();
       System.out.println(alice.toString());

     }
}