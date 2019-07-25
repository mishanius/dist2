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
import stepthree.StepThreeMapper;
import steptwo.StepTwoMapper;
import steptwo.StepTwoReducer;
import steptwo.SteptwoPartitioner;
import utils.BigramKey;

public class PairExtractor {
    private static Job job;
    private static Job job2;
    private static Job job3;
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        job = Job.getInstance(conf, Constants.STEP_ONE);
        job.setJarByClass(PairExtractor.class);
        job.setMapperClass(StepOneMapper.class);
        job.setPartitionerClass(StepOnePartitioner.class);
        job.setMapOutputKeyClass(BigramKey.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setReducerClass(StepOneReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(BigramKey.class);
        job.setInputFormatClass(TextInputFormat.class);
        FileInputFormat.setInputPaths(job,new Path(Constants.STEP_ONE_INPUT));
        job.setOutputFormatClass(TextOutputFormat.class);
        FileOutputFormat.setOutputPath(job, new Path(Constants.STEP_ONE_OUTPUT));

        if (!job.waitForCompletion(true)) {
            System.exit(1);
        }

        job2 = Job.getInstance(conf, Constants.STEP_TWO);
        job.getCounters().getGroup("decades").forEach(PairExtractor::setConfig);
        job2.setJarByClass(PairExtractor.class);
        job2.setMapperClass(StepTwoMapper.class);
        job2.setPartitionerClass(SteptwoPartitioner.class);
        job2.setMapOutputKeyClass(BigramKey.class);
        job2.setMapOutputValueClass(IntWritable.class);
        job2.setReducerClass(StepTwoReducer.class);
        job2.setOutputKeyClass(Text.class);
        job2.setOutputValueClass(BigramKey.class);
        job2.setInputFormatClass(TextInputFormat.class);
        FileInputFormat.setInputPaths(job2,new Path(Constants.STEP_TWO_INPUT));
        job2.setOutputFormatClass(TextOutputFormat.class);
        FileOutputFormat.setOutputPath(job2, new Path(Constants.STEP_TWO_OUTPUT));
        if (!job2.waitForCompletion(true)) {
            System.exit(1);
        }
        job3 = Job.getInstance(conf, Constants.STEP_TWO);
        job3.getConfiguration().set(Constants.RELATIVE_PMI,"0.9");
        job3.getConfiguration().set(Constants.MIN_PMI,"0.9");
        job2.getCounters().getGroup("rnpmi").forEach(PairExtractor::setConfigRpmi);
        job3.setJarByClass(PairExtractor.class);
        job3.setMapperClass(StepThreeMapper.class);
        job3.setMapOutputKeyClass(Text.class);
        job3.setMapOutputValueClass(Text.class);
        job3.setOutputKeyClass(Text.class);
        job3.setOutputValueClass(Text.class);
        job3.setInputFormatClass(TextInputFormat.class);
        FileInputFormat.setInputPaths(job3,new Path(Constants.STEP_THREE_INPUT));
        job3.setOutputFormatClass(TextOutputFormat.class);
        FileOutputFormat.setOutputPath(job3, new Path(Constants.STEP_THREE_OUTPUT));
        if (!job3.waitForCompletion(true)) {
            System.exit(1);
        }

    }

    private static void setConfig(Counter c){
        job2.getConfiguration().setLong(c.getName(), c.getValue());
    }

    private static void setConfigRpmi(Counter c){
        job3.getConfiguration().set(c.getName(), ""+(((double)c.getValue())/10000));
    }
}
