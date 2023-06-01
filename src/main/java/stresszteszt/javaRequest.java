package stresszteszt;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

public class javaRequest extends AbstractJavaSamplerClient {
    @Override
    public void setupTest(JavaSamplerContext context){
        super.setupTest(context);
    }

    @Override
    public Arguments getDefaultParameters(){
        return null;    //TODO
    }

    @Override
    public SampleResult runTest(JavaSamplerContext arg0){
        SampleResult result = new SampleResult();
        boolean success = true;
        result.sampleStart();
        //Test Code
        result.sampleEnd();
        result.setSuccessful(success);
        return result;
    }

    @Override
    public void teardownTest(JavaSamplerContext context){
        super.teardownTest(context);
    }
}
