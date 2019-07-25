import myconstants.Constants;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import utils.BigramKey;

import java.io.IOException;
import java.util.StringTokenizer;

import static org.apache.commons.lang3.StringUtils.SPACE;
//result is w1 w2 ocuurance\t c(w1)
public class StepOneMapper extends Mapper<LongWritable, Text, BigramKey, IntWritable> {
    @Override
    public void map(LongWritable longWritable, Text text, Context context) throws IOException {
        StringTokenizer tokens = new StringTokenizer(text.toString(), Constants.TAB_CHAR);
        String words = tokens.nextToken();
        String year = tokens.nextToken();
        int decade = Integer.parseInt(year.substring(0, year.length()-1)+"0");
        String occurrences = tokens.nextToken();
        String[] wordArr = words.split(SPACE);
        try {
            context.write(new BigramKey(decade, wordArr[0]), new IntWritable(Integer.parseInt(occurrences)));
            context.write(new BigramKey(decade, wordArr[0], wordArr[1]), new IntWritable(Integer.parseInt(occurrences)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
