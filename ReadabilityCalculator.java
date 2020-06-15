import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class ReadabilityCalculator 
{
    static int countSentences(String text) 
    {
        String delemeterRegex = "[.?!]";
        String[] sentences = text.split(delemeterRegex);
        return sentences.length;
    }

    static int countWords(String text) 
    {
        String[] words = text.split(" ");
        return words.length;
    }

    static int countCharacters(String text)
    {
        text = text.replaceAll("[\\s]", "");
        String[] characters = text.split("");
        return characters.length;
    }

    static boolean isSyllable(char ch)
    {
        String vowels = "aeiouyAEIOUY";
        if (vowels.indexOf(ch) != -1)
            return true;
        else 
            return false;
    }

    static int[] countSyllables(String[] words) 
    {
        
        int vowels = 0;
        int syllableCount = 0;
        int pollySyllableCount = 0;
        /*
        boolean even = false;
        for (String word : words) 
        {
            for (int i = 0; i < word.length(); i++)
            {
                char ch = word.charAt(i);
                if (!even && i != (word.length() - 1) && (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u' || ch == 'y'))
                {    
                    vowels += 1;
                    even = true;
                }
                else 
                    if (i == word.length() && ch != 'e' && (ch == 'a' || ch == 'i' || ch == 'o' || ch == 'u' || ch == 'y'))
                    {    
                        vowels += 1;
                        even = true;
                    }
            }
            syllableCount += vowels == 0 ? 1 : vowels;
            vowels = 0;   
        }   
        return syllableCount;
        */
        for (String word : words)
        {
            //System.out.println("word : " + word);
            word = word.endsWith("e") ? word.substring(0, word.length() - 1) : word;
            //System.out.println("truncated word : " + word);
            int i = 0;
            
            if (word.equalsIgnoreCase("you"))
                syllableCount += 1;
            else
            { 
                while (i < word.length())
                { 
                    if (isSyllable(word.charAt(i)))
                    {
                        vowels += 1;
                        i += 2;
                    }
                    else 
                        i++;
                }
                //System.out.println("vowels : " + vowels);
                if (vowels > 2)
                    pollySyllableCount += 1; 
                syllableCount += vowels == 0 ? 1 : vowels;
                //System.out.println("Total syllable : " + syllableCount);
                vowels = 0;
            }
        }

        return new int[] {syllableCount, pollySyllableCount};
    }



    static int automatedReadabilityScore(long sentenceCount, long wordCount, long characterCount) 
    {
        double score = 4.71 * ((double) characterCount / wordCount) + 0.5 * ((double) wordCount / sentenceCount) - 21.43;
        String[] ages = {"5-6", "6-7", "7-8", "8-9", "9-10", "10-11", "11-12", "12-13", "13-14", "14-15", "15-16", "16-17", "17-18", "18-24", "24-25"};
        String ageRange = ages[(int)Math.ceil(score)];
        int age = Integer.parseInt(ageRange.substring(ageRange.indexOf("-") + 1));
        System.out.println("Automated Readability Index: " + score + " (about " + age + " year olds).");
        return age;
    }

    static int FleschKincaidReadabilityScore(int sentenceCount, int wordCount, int syllableCount)
    {
        double score = 0.39 * ((double) wordCount / sentenceCount) + 11.8 * ((double) syllableCount / wordCount) - 15.59;
        //System.out.println(score);
        String[] ages = {"5-6", "6-7", "7-8", "8-9", "9-10", "10-11", "11-12", "12-13", "13-14", "14-15", "15-16", "16-17", "17-18", "18-24", "24-25"};
        String ageRange = ages[(int)Math.ceil(score)];
        int age = Integer.parseInt(ageRange.substring(ageRange.indexOf("-") + 1));
        System.out.println("Flesch Kincaid Readability Index: " + score + " (about " + age + " year olds).");
        return age;
    }

    static int  SimpleMeasureOfGobbledygookScore(int sentenceCount, int pollySyllableCount)
    {
        double score = 1.043 * (Math.sqrt((double) pollySyllableCount * 30 / sentenceCount)) + 3.1291;
        String[] ages = {"5-6", "6-7", "7-8", "8-9", "9-10", "10-11", "11-12", "12-13", "13-14", "14-15", "15-16", "16-17", "17-18", "18-24", "24-25"};
        String ageRange = ages[(int)Math.ceil(score)];
        int age = Integer.parseInt(ageRange.substring(ageRange.indexOf("-") + 1));
        System.out.println("Simple Measure of Gobbledygook Index: " + score + " (about " + age + " year olds).");
        return age;
    }

    static int ColemanLiauReadabilityScore(int sentenceCount, int wordCount, int characterCount)
    {
        double avgCharacters = (double) characterCount / wordCount * 100;
        double avgSentences = (double) sentenceCount / wordCount * 100;

        double score = 0.0588 * avgCharacters - (0.296 * avgSentences) - 15.8;
        //System.out.println(score);
        String[] ages = {"5-6", "6-7", "7-8", "8-9", "9-10", "10-11", "11-12", "12-13", "13-14", "14-15", "15-16", "16-17", "17-18", "18-24", "24-25"};
        String ageRange = ages[(int)Math.ceil(score)];
        int age = Integer.parseInt(ageRange.substring(ageRange.indexOf("-") + 1));
        System.out.println("Coleman Liau Readability Index: " + score + " (about " + age + " year olds).");
        return age;
    }

    static void avgScore(int sentenceCount, int wordCount, int syllableCount, int pollySyllableCount, int characterCount)
    {
        int ari = automatedReadabilityScore(sentenceCount, wordCount, characterCount);
        int fk = FleschKincaidReadabilityScore(sentenceCount, wordCount, syllableCount);
        int smog = SimpleMeasureOfGobbledygookScore(sentenceCount, pollySyllableCount);
        int cl = ColemanLiauReadabilityScore(sentenceCount, wordCount, characterCount);
        double avgAge = (ari + fk + smog + cl) / 3.0;
        System.out.println("\nThis text should be understood in average by " + avgAge + " year olds.");

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
            System.out.println(text + "\n");
            
            int sentenceCount = countSentences(text);
            
            int wordCount = countWords(text);
           
            int characterCount = countCharacters(text);
            text = text.replaceAll("[.,?!]", "");
            int[] syllables = countSyllables(text.split(" "));
            int syllableCount = syllables[0];
            int pollySyllableCount = syllables[1];

            System.out.println("Words:" + wordCount);
            System.out.println("Sentences: " + sentenceCount);
            System.out.println("Characters: " + characterCount);
            System.out.println("Syllable: " + syllableCount);
            System.out.println("Pollysyllables: "+ pollySyllableCount);
            Scanner sc = new Scanner(System.in);
            boolean flag = false;
            do
            {
                System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
                String method = sc.next();
                System.out.println();
                
                switch (method) 
                {
                    case "ARI" :
                        automatedReadabilityScore(sentenceCount, wordCount, characterCount);
                        flag = false;
                        break;
                    case "FK" :
                        FleschKincaidReadabilityScore(sentenceCount, wordCount, syllableCount);
                        flag = false;
                        break;
                    case "SMOG" :
                        SimpleMeasureOfGobbledygookScore(sentenceCount, pollySyllableCount);
                        flag = false;
                        break;
                    case "CL" :
                        ColemanLiauReadabilityScore(sentenceCount, wordCount, characterCount);
                        flag = false;
                        break;
                    case "all" :
                        avgScore(sentenceCount, wordCount, syllableCount, pollySyllableCount, characterCount);
                        flag = false;
                        break;
                    default :
                        System.out.println("Sorry could not understand, try again...");
                        flag = true;
                }
            } while (flag);
            //System.out.println(Arrays.toString(characters));
            sc.close();
            fileScanner.close();
        } 
        
        catch (FileNotFoundException e) 
        {
            System.out.println("Sorry, file not found");
            return;
        }   
    }
}