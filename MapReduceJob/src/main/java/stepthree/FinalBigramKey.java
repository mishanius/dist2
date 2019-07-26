package stepthree;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

import javax.annotation.Nonnull;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class FinalBigramKey implements WritableComparable<FinalBigramKey> {
    private Text row;
    private IntWritable decade;
    private DoubleWritable pmi;
    private DoubleWritable rpmi;

    public FinalBigramKey(String row, int decade, double pmi, double rpmi) {
        this.row = new Text(row);
        this.decade = new IntWritable(decade);
        this.pmi = new DoubleWritable(pmi);
        this.rpmi = new DoubleWritable(rpmi);
    }

    public FinalBigramKey() {
        this("",-1,0,0);
    }

    public IntWritable getDecade() {
        return decade;
    }

    public DoubleWritable getPmi() {
        return pmi;
    }

    public DoubleWritable getRpmi() {
        return rpmi;
    }

    public Text getText(){
        return this.row;
    }
    @Override
    public int compareTo(@Nonnull FinalBigramKey o) {
        if (this.getDecade().equals(o.getDecade())) {
            if(this.getPmi().equals(o.getPmi())) {
                return this.getText().compareTo(o.getText());
            }
            return this.getPmi().compareTo(o.getPmi());
        }
        return this.getDecade().compareTo(o.getDecade());

    }

    @Override
    public void write(DataOutput out) throws IOException {
        this.row.write(out);
        this.decade.write(out);
        this.pmi.write(out);
        this.rpmi.write(out);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.row.readFields(in);
        this.decade.readFields(in);
        this.pmi.readFields(in);
        this.rpmi.readFields(in);
    }
}
