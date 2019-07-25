package steptwo;

import myconstants.Constants;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import utils.BigramKey;

import java.io.IOException;
import java.util.StringTokenizer;

import static org.apache.commons.lang3.StringUtils.SPACE;
//input is w1\sw2\tdecade\tc(w1,w2)\tc(w1)
//result is
//1. bigram(w1,w2, decade)
//2. onegram (w2,decade)
public class StepTwoMapper extends Mapper<LongWritable, Text, BigramKey, IntWritable> {
    @Override
    public void map(LongWritable longWritable, Text text, Context context) throws IOException {
        StringTokenizer tokens = new StringTokenizer(text.toString(), Constants.TAB_CHAR);
        String words = tokens.nextToken();
        String year = tokens.nextToken();

        int decade = Integer.parseInt(year.substring(0, year.length()-1)+"0");
        String occurrences = tokens.nextToken();
        String w1Count = tokens.nextToken();
        String[] wordArr = words.split(SPACE);
        try {
            context.write(new BigramKey(decade, wordArr[1]), new IntWritable(Integer.parseInt(occurrences))); //w2 count
            context.write(new BigramKey(decade, wordArr[1], wordArr[0], w1Count), new IntWritable(Integer.parseInt(occurrences))); //pair count
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
