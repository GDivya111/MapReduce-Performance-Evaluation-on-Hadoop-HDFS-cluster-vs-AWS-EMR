import java.io.IOException;
import java.util.*;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
	
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;
import javax.json;

public class SpamDetection {

    public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, FloatWritable> {
	
    	public void map(LongWritable key, Text value, OutputCollector<Text, FloatWritable> output, Reporter reporter) throws IOException {
		       
           
            String line = value.toString();
            String[] tuple = line.split("\\n");
            try{
                for(int i=0;i<tuple.length; i++){
                	
         	       JsonFactory factory = new JsonFactory();

        	       ObjectMapper mapper = new ObjectMapper(factory);
        	       JsonNode rootNode = mapper.readTree(tuple[i]);  

        	       float val=0.0;
        	       String reviewer = "";
                   String content ="";
                   
        	       Iterator<Map.Entry<String,JsonNode>> fieldsIterator = rootNode.fields();
        	       while (fieldsIterator.hasNext()) {

        	           Map.Entry<String,JsonNode> field = fieldsIterator.next();
        	          // System.out.println("Key: " + field.getKey() + "\tValue:" + field.getValue());
                       
        	           
        	           //int value=0;
        	           if(field.getKey()=="reviewerID")
                            {
        	        	   reviewer= field.getValue().toString();
                              
                            }
                            if(field.getKey()=="helpful")
                            {
                              content= field.getValue().toString();
                               //content = rootNode.get("helpful").textValue();
                              Scanner in = new Scanner(content).useDelimiter("[^0-9]+");
                              int v1;
                              v1 =in.nextInt();
                              int v2 =in.nextInt();
                              
                               val= (float)v1/v2;
                              
                              
                            }
                            

        	       }

                   //System.out.println(key+":"+value);
                   output.collect(reviewer, val);
               }
            }
            catch(JSONException e){
                e.printStackTrace();
            }	         
    	   }
    }
    		

			   
	 public static class Reduce extends MapReduceBase implements Reducer<Text, FloatWritable, Text, Text> {
	       public void reduce(Text key, Iterator<FloatWritable> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
			       float sum = 0;
			       int count=0;
			       double threshold = 0.25;
			       while (values.hasNext()) {
			         sum += values.next().get();
			         count++;
			       }
			       String out="";
			       float res = (float)sum/count;
			       if(res<threshold)
                   {
                 	   out ="Spammer";
                   }
                   else
                 	  out ="Genuine";
                   
			       output.collect(key, out);
			     }
			   }

		public void SpamDetection() {
			// TODO Auto-generated constructor stub
			
		}

	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	             /*JobConf conf = new JobConf(SpamDetection.class);
	     	     conf.setJobName("spamdetect");
	     	
	     	     conf.setOutputKeyClass(Text.class);
	     	     conf.setOutputValueClass(Text.class);
	     	
	     	     conf.setMapperClass(Map.class);
	     	     conf.setCombinerClass(Reduce.class);
	     	     conf.setReducerClass(Reduce.class);
	     	
	     	     conf.setInputFormat(TextInputFormat.class);
	     	     conf.setOutputFormat(TextOutputFormat.class);
	     	
	     	     FileInputFormat.setInputPaths(conf, new Path(args[0]));
	     	     FileOutputFormat.setOutputPath(conf, new Path(args[1]));
	     	
	     	     JobClient.runJob(conf);*/
		
        Configuration conf = new Configuration();
        /*if (args.length != 2) {
            System.err.println("Usage: SpamDetection <in> <out>");
            System.exit(2);
        }*/

        Job job = new Job(conf, "SpamDetection");
        job.setJarByClass(SpamDetection.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FloatWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        //System.exit(job.waitForCompletion(true) ? 0 : 1);
    	
		


	}

}
