import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import myconstants.Constants;

import java.io.*;
import java.net.URL;
import java.util.Iterator;

public class S3Client extends AwsManager {
    private AmazonS3 s3;

    public S3Client() {
        super();
        AmazonS3ClientBuilder temp = AmazonS3ClientBuilder.standard()
                .withRegion(Constants.DEFAULT_REGION);

        if(this.credentialsProvider!=null)
            temp.withCredentials(credentialsProvider);
        this.s3=temp.build();
    }

    public Iterator<String> downloadItemAsLines(String bucketName, String keyName) {
        S3ObjectInputStream s3is = downloadItem(bucketName, keyName);
        try {
            return iteratorFromReader(new BufferedReader(new InputStreamReader(s3is)));
        } catch (IOException e) {
            return new Iterator<String>() {
                public boolean hasNext() {
                    return false;
                }

                public String next() {
                    return null;
                }

                public void remove() {

                }

            };
        }
    }

    public static Iterator<String> iteratorFromReader(final BufferedReader reader) throws IOException {
        return new Iterator<String>() {
            String nextLine = reader.readLine();

            public boolean hasNext() {
                return nextLine != null;
            }

            public String next() {
                String lineToReturn = nextLine;
                try {
                    nextLine = reader.readLine();
                } catch (IOException e) {
                    nextLine = null;
                }
                return lineToReturn;
            }

            public void remove() {

            }
        };
    }

    public PutObjectResult uploadFile(String bucketName, String keyName, File file){
        return s3.putObject(bucketName, keyName, file);
    }

    public PutObjectResult uploadFile(String bucketName, String keyName, InputStream ins, ObjectMetadata mt){
        return s3.putObject(bucketName, keyName, ins, mt);
    }

    public PutObjectResult uploadFile(String bucketName, String keyName, InputStream input, String ContentType){
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentType(ContentType);
        return s3.putObject(bucketName, keyName, input, meta);
    }

    public URL getUrl(String bucketName, String keyName){
        return s3.getUrl(bucketName, keyName);
    }

    private S3ObjectInputStream downloadItem(String bucketName, String keyName) {
        try {
            S3Object o = s3.getObject(bucketName, keyName);
            return o.getObjectContent();

        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
            return null;
        }
    }
}
