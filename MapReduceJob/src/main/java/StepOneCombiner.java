import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;
import utils.BigramKey;

import java.io.IOException;

public class StepOneCombiner extends Reducer<BigramKey,IntWritable,BigramKey,IntWritable> {
    @Override
    public void reduce (BigramKey key , Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {

        int sum=0;
        for(IntWritable value : values){
            sum+=value.get();
        }
        context.write(key,new IntWritable(sum));
    }
}