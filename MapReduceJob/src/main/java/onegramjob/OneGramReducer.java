package onegramjob;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class OneGramReducer extends Reducer<OnegramKey, IntWritable, Text, Text> {
    @Override
    protected void reduce(OnegramKey key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum=0;
        for(IntWritable value : values){
            sum+=value.get();
        }
        String result =  key.getLeft()+ "\t" + key.getDecade();
        String valueString = sum + "\t" + sum;
        context.write(new Text(result), new Text(valueString));
    }
}
