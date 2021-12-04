package Model;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Board Class: sets up the board to be played on
 * Date: November 5th, 2021
 * @author Hamza Zafar, 101158275
 * @version 2.0
 */

public class Board implements Serializable {

    private ArrayList<Square> board;
    private List<Player> players;

    @XmlTransient
    private String boardBackgroundFileName;
    private Set<Property> listOfProperties;

    public static String index = "";
    public static String square = "";
    public static String property = "";
    public static String railroad = "";
    public static String utility = "";
    public static String name = "";
    public static String color = "";
    public static String value = "";

    public Board(){
        board = new ArrayList<>();
        buildBoard();
    }

    @XmlElement(name="Property")
    public void setListOfProperties(Set<Property> listOfProperties) {
        this.listOfProperties = listOfProperties;
    }

    @XmlElement(name = "boardBackgroundFileName", nillable = true)
    public void setBoardBackgroundFileName(String boardBackgroundFileName) {
        this.boardBackgroundFileName = boardBackgroundFileName;
    }

    public Set<Property> getAllProperties() {
        return listOfProperties;
    }

    public Property getPropertyByName(String propertyName) {
        for (Property p : listOfProperties) {
            if (p.getName().toLowerCase().equals(propertyName.toLowerCase())) {
                return p;
            }
        }
        return null;
    }

    public void importFromXmlFile(String file) throws Exception {
        readSAX(new File("OriginalBoard.xml"));
    }

    public void checkConditions(){
        if((!square.equals("") || !square.equals(null)) && (!name.equals("") || !name.equals(null))){
            addSquare(Integer.parseInt(index), name);
        } else if((!property.equals("") || !property.equals(null)) && (!name.equals("") || !name.equals(null)) && (!color.equals("") || !color.equals(null)) && (!value.equals("") || !value.equals(null))){
            addProperty(Integer.parseInt(index), name, color, Integer.parseInt(value));
        } else if((!railroad.equals("") || !railroad.equals(null)) && (!name.equals("") || !name.equals(null)) && (!value.equals("") || !value.equals(null))){
            addRailroad(Integer.parseInt(index), name, Integer.parseInt(value));
        } else if((!utility.equals("") || !utility.equals(null)) && (!name.equals("") || !name.equals(null)) && (!value.equals("") || !value.equals(null))){
            addUtility(Integer.parseInt(index), name, Integer.parseInt(value));
        }
    }

    public void readSAX(File file) throws Exception {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser s = spf.newSAXParser();

        DefaultHandler dh = new DefaultHandler(){
            boolean parseIndex = false, parseSquare = false, parseProperty = false, parseRailroad = false,
                    parseUtility = false, parseName = false, parseColor = false, parseValue = false;

            public void startElement(String u, String ln, String qname, Attributes a){
                if(qname.equalsIgnoreCase("index")){
                    parseIndex = true;
                } else if(qname.equalsIgnoreCase("square")){
                    parseSquare = true;
                } else if(qname.equalsIgnoreCase("property")){
                    parseProperty = true;
                } else if(qname.equalsIgnoreCase("railroad")){
                    parseRailroad = true;
                } else if(qname.equalsIgnoreCase("utility")){
                    parseUtility = true;
                } else if(qname.equalsIgnoreCase("name")){
                    parseName = true;
                } else if(qname.equalsIgnoreCase("color")){
                    parseColor = true;
                } else if(qname.equalsIgnoreCase("value")){
                    parseValue = true;
                }
                //System.out.println("START: " + qname);
            }

            public void endElement(String uri, String localName, String qname){
                //System.out.println("END: " + qname);
            }

            public void characters(char[] ch, int start, int length) throws SAXException {
                if(parseIndex == true){
                    index = new String(ch, start, length);
                    parseIndex = false;
                } else if(parseSquare == true){
                    square = new String(ch, start, length);
                    parseSquare = false;
                } else if(parseProperty == true){
                    property = new String(ch, start, length);
                    parseProperty = false;
                } else if(parseRailroad == true){
                    railroad = new String(ch, start, length);
                    parseRailroad = false;
                } else if(parseUtility == true){
                    utility = new String(ch, start, length);
                    parseUtility = false;
                } else if(parseName == true){
                    name = new String(ch, start, length);
                    parseName = false;
                } else if(parseColor == true){
                    color = new String(ch, start, length);
                    parseColor = false;
                } else if(parseValue == true){
                    value = new String(ch, start, length);
                    parseValue = false;
                }
                //System.out.println("CHARS: " + new String(ch, start, length));
                checkConditions();
                index = "";
                square = "";
                property = "";
                railroad = "";
                utility = "";
                name = "";
                color = "";
                value = "";
            }
        };
        s.parse(file, dh);
    }

    public void addSquare(int index, String squareName){
        board.add(index, new Square(squareName));
    }

    public void addProperty(int index, String squareName, String squareColor, int squareValue){
        board.add(index, new Property(squareName, squareColor, squareValue));
    }

    public void addRailroad(int index, String railroadName, int railroadValue){
        board.add(index, new Railroad(railroadName, railroadValue));
    }

    public void addUtility(int index, String utilityName, int utilityValue){
        board.add(index, new Utility(utilityName, utilityValue));
    }

    public void buildBoard(){

    }

    /*public void importFromXmlFile(String filename){
        try {
            JAXBContext context = JAXBContext.newInstance(Board.class);
            Board boardToInitialize =  (Board) context.createUnmarshaller().unmarshal(this.getClass().getClassLoader().getResourceAsStream(filename));
            this.initializeBoard(boardToInitialize);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }*/

    public void exportToXmlFile(String content,String filename){
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(filename), "utf-8"));
            writer.write(toXML());
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initializeBoard(Board boardToInitialize){
        this.board = new ArrayList<>();
        this.buildBoard();
    }

    public String toXML(){
        String xml = "error converting to XML";
        try {
            JAXBContext context = JAXBContext.newInstance(Board.class);
            Marshaller marshaller= context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(this, stringWriter);
            xml = stringWriter.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return xml;
    }

    public String getBoardBackgroundFileName() {
        return boardBackgroundFileName;
    }

    public ArrayList<Square> getBoard() {
        return board;
    }

    public int size(){
        return board.size();
    }

    public Square getIndex(int x){
        return board.get(x);
    }

    public Property getProperty(int x){
        return (Property) board.get(players.get(x).getPosition());
    }
    public Utility getUtility(int x){
        return (Utility) board.get(players.get(x).getPosition());
    }

    public int getPrice(int x){
        return ((Property) board.get(players.get(x).getPosition())).getValue();
    }
}
