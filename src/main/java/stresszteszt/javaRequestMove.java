package stresszteszt;

import controller.Game;
import model.Field;
import model.Map;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import java.util.List;
import java.util.Properties;
import java.util.Random;

public class javaRequestMove extends AbstractJavaSamplerClient {
    private Properties props = new Properties();
    @Override
    public void setupTest(JavaSamplerContext context) {
        Game game = new Game(2);
        props.put("game", game);
        super.setupTest(context);
    }

    @Override
    public Arguments getDefaultParameters() {
        return null;
    }

    @Override
    public SampleResult runTest(JavaSamplerContext arg0) {
        SampleResult result = new SampleResult();
        boolean success = true;
        result.sampleStart();
        try {
            Game game = (Game)props.get("game");
            Map map = game.getMap();
            Field currentField = map.getCurrentPlayer().getField();
            List<Field> n = currentField.GetNeighbours();
            Random random = new Random();
            int randomIndex = random.nextInt(n.size());
            Field field = n.get(randomIndex);
            map.getCurrentPlayer().Move(field);
            success = true;
        } catch (Exception e) {
            success = false;
            throw new RuntimeException(e);
        } finally {
            result.sampleEnd();
            result.setSuccessful(success);
        }
        return result;
    }

    @Override
    public void teardownTest(JavaSamplerContext context) {
        super.teardownTest(context);
    }
}
