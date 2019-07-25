package stepthree;

import myconstants.Constants;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import steptwo.StepTwoMapper;
import steptwo.StepTwoReducer;
import steptwo.SteptwoPartitioner;
import utils.BigramKey;


public class StepThree {
    public static void main(String[] args) throws Exception {
        Configuration jobconf = new Configuration();
        jobconf.set("threshold", "0.7");
        Job job = Job.getInstance(jobconf, Constants.STEP_ONE);
        job.setJarByClass(StepThree.class);
        job.setMapperClass(StepThreeMapper.class);
        job.setPartitionerClass(SteptwoPartitioner.class);
        job.setMapOutputKeyClass(BigramKey.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setReducerClass(StepTwoReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(BigramKey.class);
        job.setInputFormatClass(TextInputFormat.class);
        job.setNumReduceTasks(1);
        FileInputFormat.setInputPaths(job,new Path(Constants.STEP_THREE_INPUT));
        job.setOutputFormatClass(TextOutputFormat.class);
        FileOutputFormat.setOutputPath(job, new Path(Constants.STEP_THREE_OUTPUT));


        boolean status = job.waitForCompletion(true);
        if (status) {
            System.exit(0);
        } else {
            System.exit(1);
        }
    }
}
