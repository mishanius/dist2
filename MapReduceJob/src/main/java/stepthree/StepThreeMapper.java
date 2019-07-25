package stepthree;

import myconstants.Constants;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;

import static myconstants.Constants.MIN_PMI;
import static myconstants.Constants.RELATIVE_PMI;

public class StepThreeMapper extends Mapper<LongWritable, Text, Text, Text> {
    @Override
    public void map(LongWritable longWritable, Text text, Context context) throws IOException {
        StringTokenizer tokens = new StringTokenizer(text.toString(), Constants.TAB_CHAR);
        String words = tokens.nextToken();
        String decade = tokens.nextToken();
        double npmi = Double.parseDouble(tokens.nextToken());
        double totalNpmi = Double.parseDouble(context.getConfiguration().get(decade, "0"));
        double relativeThreshold = Double.parseDouble(context.getConfiguration().get(RELATIVE_PMI, "0"));
        double npithreshold = Double.parseDouble(context.getConfiguration().get(MIN_PMI, "0"));
        try {
            if (npmi / totalNpmi >= relativeThreshold || npmi >= npithreshold) {
                context.write(new Text(words), new Text(decade));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}