package steptwo;

import myconstants.Constants;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;
import utils.BigramKey;

import java.util.HashMap;

public class SteptwoPartitioner extends Partitioner<BigramKey, IntWritable> {

    @Override
    public int getPartition(BigramKey key,IntWritable value, int numPartitions) {

        return  Math.abs(key.getLeft().hashCode()) % numPartitions;
    }
}
