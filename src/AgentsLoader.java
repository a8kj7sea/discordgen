
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class AgentsLoader {

    private @Getter final String filePath;
    private @Getter final List<String> agents;

    public AgentsLoader(@NonNull final String filePath) {
        this.filePath = filePath;
        this.agents = new ArrayList<>();
    }

    public void load(int toLoad) {

        if (toLoad <= 0)
            throw new IllegalStateException("Error while loading agents , to laod number cannot be less than zero !");

        File agentsFile = new File(filePath);

        int index = 0;

        if (!agentsFile.isFile())
            throw new IllegalStateException("Agents file not found !");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(agentsFile))) {
            String line;

            while ((line = bufferedReader.readLine()) != null && index <= toLoad) {
                agents.add(line);
                index++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void load() {
        File agentsFile = new File(filePath);
        if (!agentsFile.isFile())
            throw new IllegalStateException("Agents file not found !");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(agentsFile))) {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                agents.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}