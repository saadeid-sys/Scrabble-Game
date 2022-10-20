/*
The tile method describes the number of points for each lettered tile is based on the
board
@author Khalid Merai 101159203
@version Sunday, October 16, 2022
 */

import java.util.*;
import java.util.HashMap;

public class Tile {
    char letter;
    int value;
    static HashMap<Character, Integer> letterVal ;
    public Tile(char letter, int value){
        this.letter = letter;
        this.value = value;
    }

    /*
    This method gets the letter from the HashMap letterVal
    @returns the letter
     */
    public char getLetter() {
        return this.letter;
    }
    /*
    This method gets the value of letter from the HashMap letterVal
    @returns the value of the letter
     */
    public int getValue() {
        return this.value;
    }

    /*
    This method returns the tile with the selected letter and value.
    @return the tile with the selected letter and value
     */
    @Override
    public String toString() {
        return "Tile{" +
                "letter=" + letter +
                ", value=" + value +
                '}';
    }

    /*
    This method checks whether the object is equal to the tile
    @returns True if both letters are equal to each other
    @returns false if object is instance of the Tile
    @returns false if both letters are not equal to each other
     */
    public boolean equals(Object obj) {
        Tile x;
        if(!(obj instanceof Tile)){
            return false;
        }
        else {
            x = (Tile)obj;
        }
        if (this.letter == x.letter){
            return true;
        } else{
            return false;
        }
    }
    public static void main(String[] args){
        letterVal = new HashMap<Character, Integer>();
        letterVal.put('A',1);
        letterVal.put('B',3);
        letterVal.put('C',3);
        letterVal.put('D',2);
        letterVal.put('E',1);
        letterVal.put('F',4);
        letterVal.put('G',2);
        letterVal.put('H',4);
        letterVal.put('I',1);
        letterVal.put('J',8);
        letterVal.put('K',5);
        letterVal.put('L',1);
        letterVal.put('M',3);
        letterVal.put('N',1);
        letterVal.put('O',1);
        letterVal.put('P',1);
        letterVal.put('Q',10);
        letterVal.put('R',1);
        letterVal.put('S',1);
        letterVal.put('T',1);
        letterVal.put('U',1);
        letterVal.put('V',4);
        letterVal.put('W',4);
        letterVal.put('X',8);
        letterVal.put('Y',4);
        letterVal.put('Z',10);
        System.out.println("Value: "+ letterVal);
    }
}
