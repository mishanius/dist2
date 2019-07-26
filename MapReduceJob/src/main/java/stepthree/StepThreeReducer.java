package stepthree;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class StepThreeReducer extends Reducer<FinalBigramKey, Text, Text, Text> {

    @Override
    protected void reduce(FinalBigramKey key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        context.write(key.getText(), new Text(""));
    }
}
