import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;
import utils.BigramKey;

import java.util.HashMap;

public class StepOnePartitioner extends Partitioner<BigramKey, IntWritable> {
    private HashMap<Integer, Integer> total;
    @Override
    public int getPartition(BigramKey key,IntWritable value, int numPartitions) {
        return  Math.abs(key.getLeft().hashCode()) % numPartitions;
    }
}
