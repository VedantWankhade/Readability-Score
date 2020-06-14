import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class ReadabilityCalculator {
    public static void main(String[] args) {
        
        File inFile = new File(args[0]);
        String text = "";
        String delemeterRegex = "[.?!]";
        
        try {
            Scanner fileScanner = new Scanner(inFile);
            //System.out.println("File " + inFile.getName() + " found\nProcessing...");
            
            while (fileScanner.hasNext()) {
                text += fileScanner.nextLine();
            }
            System.out.println("The text is:");
            System.out.println(text);
            String[] sentences = text.split(delemeterRegex);
            String[] words = text.split(" ");
            text = text.replaceAll("[\\s]", "");
            String[] characters = text.split("");

            System.out.println("Words:" + words.length);
            System.out.println("Sentences: " + sentences.length);
            System.out.println("Characters: " + characters.length);
    
            //System.out.println(Arrays.toString(characters));
            fileScanner.close();

            double score = 4.71 * ((double) characters.length / words.length) + 0.5 * ((double) words.length / sentences.length) - 21.43;
            System.out.println("The score is:" + score);
            score = Math.ceil(score);
            
            String age = "";
            switch ((int)score) {
                case 1 :
                    age = "5-6"; // Kindergarten
                    break;
                case 2 :
                    age = "6-7"; //First/second grade
                    break;
                case 3 :
                    age = "7-9"; //Third grade
                    break;
                case 4 : 
                    age = "9-10"; //Fourth grade
                    break;
                case 5 : 
                    age = "10-11"; //Fifth grade
                    break;
                case 6 :
                    age = "11-12";  //Sixt grade
                    break;
                case 7 :
                    age = "12-13"; //Seventh grafe
                    break;
                case 8 :
                    age = "13-14"; //Eighth grade
                    break;
                case 9 : 
                    age = "14-15"; //Ninth grade
                    break;
                case 10 :
                    age = "15-16";  //Tenth grade
                    break;
                case 11 :
                    age = "16-17";  //Eleventh grade
                    break;
                case 12 :
                    age = "17-18";  //Twelfth grade
                    break;
                case 13 :
                    age = "18-24";  //College student
                    break;
                case 14 :
                    age = "24+";    //Professor
            }
             
            System.out.println("This text should be understood by " + age + " year olds.");
        } catch (FileNotFoundException e) {
            System.out.println("Sorry, file not found");
            return;
        }   
    }
}