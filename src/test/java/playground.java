//import myconstants.Constants;
//import org.junit.Test;
//
//import java.io.*;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.StringTokenizer;
//import java.util.stream.Stream;
//
//public class playground {
//    String FILE_PATH = "C:/Users/Itay/Dist2/src/input/old/ngrams";
//
//    @Test
//    public void name() throws IOException {
//        BufferedReader reader;
//        String line;
//        try (Stream<String> lines = Files.lines(Paths.get(FILE_PATH))) {
//            Constants.KeyType k = Constants.KeyType.OneGram;
//            Constants.KeyType f = Constants.KeyType.Decade;
//            System.out.println(f.compareTo(k));
//            whoami(f);
//            whoami(k);
//        }
//    }
//
//    @Test
//    public void cutFile() throws IOException {
//        File fout = new File("ngram_cut");
//        FileOutputStream fos = new FileOutputStream(fout);
//        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
//        BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
//        long skiped = reader.skip(1000000);
//        System.out.println("we skiped " + skiped);
//        for (int j = 0; j < 35; j++) {
//            int i=0;
//            while(i < 1000) {
//                String line = reader.readLine();
//                StringTokenizer tokens = new StringTokenizer(line, "\t");
//                try {
//                    String words = tokens.nextToken();
//                    if(isAlpha(words)){
//                        bw.write(reader.readLine());
//                        bw.newLine();
//                        i++;
//                    }
//                }
//                catch (Exception e){
//                    System.out.println(e.toString());
//                }
//            }
//            skiped = reader.skip(500000);
//            System.out.println("we skiped " + skiped);
//        }
//        bw.close();
//    }
//
//    @Test
//    public void underScroeTest() {
//        System.out.println(isAlpha("apart more_ADV"));
//    }
//
//    public static boolean isAlpha(String s) {
//        return s != null && s.chars().allMatch(ch-> (Character.isAlphabetic(ch)||ch==' '|| ch=='\t')&& ch!='_');
//    }
//
//
//    private void whoami(Constants.KeyType f) {
//        switch (f) {
//            case OneGram:
//                System.out.println("one");
//                break;
//            case Decade:
//                System.out.println("decade");
//                break;
//            default:
//                System.out.println("dont know");
//        }
//    }
//}
//
