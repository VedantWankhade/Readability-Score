import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class ReadabilityCalculator 
{
    static long countSentences(String text) 
    {
        String delemeterRegex = "[.?!]";
        String[] sentences = text.split(delemeterRegex);
        return sentences.length;
    }

    static long countWords(String text) 
    {
        String[] words = text.split(" ");
        return words.length;
    }

    static long countCharacters(String text)
    {
        text = text.replaceAll("[\\s]", "");
        String[] characters = text.split("");
        return characters.length;
    }

    static void automatedReadabilityScore(long sentenceCount, long wordCount, long characterCount) 
    {
        double score = 4.71 * ((double) characterCount / wordCount) + 0.5 * ((double) wordCount / sentenceCount) - 21.43;
        System.out.println("The score is:" + score);
        score = Math.ceil(score);
        String[] age = {"5-6", "6-7", "7-8", "8-9", "9-10", "10-11", "11-12", "12-13", "13-14", "14-15", "15-16", "16-17", "17-18", "18-24", "24+"};
        System.out.println("This text should be understood by " + age[(int)score] + " year olds.");
    }

    public static void main(String[] args) 
    {    
        File inFile = new File(args[0]);
        String text = "";

        try 
        {
            Scanner fileScanner = new Scanner(inFile);
            //System.out.println("File " + inFile.getName() + " found\nProcessing...");
            
            while (fileScanner.hasNext()) 
                text += fileScanner.nextLine();

            System.out.println("The text is:");
            System.out.println(text);
            
            long sentenceCount = countSentences(text);
            
            long wordCount = countWords(text);
           
            long characterCount = countCharacters(text);

            System.out.println("Words:" + wordCount);
            System.out.println("Sentences: " + sentenceCount);
            System.out.println("Characters: " + characterCount);
    
            //System.out.println(Arrays.toString(characters));
            fileScanner.close();
            automatedReadabilityScore(sentenceCount, wordCount, characterCount);
        } catch (FileNotFoundException e) {
            System.out.println("Sorry, file not found");
            return;
        }   
    }
}