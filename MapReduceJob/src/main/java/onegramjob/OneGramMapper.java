package onegramjob;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;

import static org.apache.commons.lang3.StringUtils.SPACE;

public class OneGramMapper extends Mapper<LongWritable, Text, OnegramKey, IntWritable> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        StringTokenizer tokens = new StringTokenizer(value.toString(), "\t");
        String words = tokens.nextToken();
        String year = tokens.nextToken();
        int decade = Integer.parseInt(year);
        int val = Integer.parseInt(tokens.nextToken());
        String[] wordArr = words.split(SPACE);
        context.write(new OnegramKey(decade, wordArr[0]), new IntWritable(val));
    }
}
