/**
 * Represents the triple letter square on the ScrabbleModel game board.
 *
 * @author Saad Eid
 */
public class TripleLetterSquare extends Square{
    /**
     * The name of the square
     */
    private final static String name = "Triple Letter Square";
    /**
     * Points multiplied by letter if filled
     */
    private final static int pointMultiplier = 3;

    /**
     * Constructs a new square with the specified name, row and column number.
     * Assigns the square as empty.
     *
     * @param rowNum The square's row number.
     * @param columnNum The square's row number.
     */
    public TripleLetterSquare(int rowNum, int columnNum){
        super(rowNum, columnNum);
    }

    public TripleLetterSquare(int rowNum, int columnNum, char letter){
        super(rowNum, columnNum, letter);
    }

    public int getPointMultiplier(){
        return pointMultiplier;
    }

    public String getName(){
        return name;
    }

    public String toXML()
    {
        String s= "";
        s+="<TripleLetterSquare>" + "\n";

        //s+= "<Name>"+name+"</Name>"+"\n";
        s+= "\t<rowNum>"+rowNum+"</rowNum>"+"\n";
        s+= "\t<columnNum>"+columnNum+"</columnNum>"+"\n";

        s+="</TripleLetterSquare>";
        return s;
    }
}
