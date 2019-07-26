package stepthree;

import myconstants.Constants;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;

import static myconstants.Constants.MIN_PMI;
import static myconstants.Constants.RELATIVE_PMI;
import static myconstants.Constants.TAB_CHAR;

public class StepThreeMapper extends Mapper<LongWritable, Text, FinalBigramKey, Text> {
    @Override
    public void map(LongWritable longWritable, Text text, Context context) throws IOException {
        StringTokenizer tokens = new StringTokenizer(text.toString(), Constants.TAB_CHAR);
        String words = tokens.nextToken();
        String decadeStr = tokens.nextToken();
        int decade = Integer.parseInt(decadeStr);
        double npmi = Double.parseDouble(tokens.nextToken());
        double totalNpmi = Double.parseDouble(context.getConfiguration().get(decadeStr, "0"));
        double relativeThreshold = Double.parseDouble(context.getConfiguration().get(RELATIVE_PMI, "0"));
        double npithreshold = Double.parseDouble(context.getConfiguration().get(MIN_PMI, "0"));
        try {
            if (npmi / totalNpmi >= relativeThreshold || npmi >= npithreshold) {
                context.write(new FinalBigramKey(words +TAB_CHAR+ decadeStr +TAB_CHAR+ npmi,decade, npmi, npmi / totalNpmi), new Text(""));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
